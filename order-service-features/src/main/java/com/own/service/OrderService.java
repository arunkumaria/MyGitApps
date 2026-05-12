package com.own.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.own.dto.OrderItemRequest;
import com.own.dto.OrderRequest;
import com.own.entity.Customer;
import com.own.entity.Order;
import com.own.entity.OrderItem;
import com.own.entity.Product;
import com.own.enums.OrderStatus;
import com.own.enums.PaymentStatus;
import com.own.exceptions.InsufficientStockException;
import com.own.exceptions.ResourceNotFoundException;
import com.own.repository.CustomerRepository;
import com.own.repository.OrderRepository;
import com.own.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final PaymentService paymentService;

    public Order createOrder(OrderRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Product not found"));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - itemRequest.getQuantity());

            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice());

            orderItems.add(item);
        }

        PaymentStatus paymentStatus = paymentService.processPayment();

        order.setPaymentStatus(paymentStatus);
        order.setItems(orderItems);

        if (paymentStatus == PaymentStatus.SUCCESS) {
            order.setOrderStatus(OrderStatus.CONFIRMED);
        }

        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setOrderStatus(status);

        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));
    }

    public List<Order> getCustomerOrders(Long customerId) {

        return orderRepository.findByCustomerId(customerId);
    }
}