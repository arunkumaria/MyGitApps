package com.own.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.own.entity.Customer;
import com.own.entity.Order;
import com.own.entity.OrderItem;
import com.own.entity.Product;
import com.own.enums.OrderStatus;
import com.own.repository.CustomerRepository;
import com.own.repository.OrderRepository;
import com.own.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CustomerRepository customerRepo;

	public Order createOrder(Long customerId, List<OrderItem> items) {

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Order order = new Order();
		order.setCustomer(customer);
		order.setStatus(OrderStatus.PENDING);
		order.setCreatedAt(LocalDateTime.now());

		for (OrderItem item : items) {

			Product product = productRepo.findById(item.getProduct().getId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			// 🔥 INVENTORY CHECK
			if (product.getStock() < item.getQuantity()) {
				throw new RuntimeException("Insufficient stock for product: " + product.getName());
			}

			// 🔥 REDUCE STOCK
			product.setStock(product.getStock() - item.getQuantity());

			item.setPrice(product.getPrice());
			item.setOrder(order);
		}

		order.setItems(items);

		return orderRepo.save(order);
	}

	public Order updateStatus(Long orderId, OrderStatus newStatus) {

		Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		// 🔥 STATE TRANSITION LOGIC
		if (order.getStatus() == OrderStatus.CANCELLED) {
			throw new RuntimeException("Cannot update cancelled order");
		}

		order.setStatus(newStatus);
		return orderRepo.save(order);
	}

	public Order getOrder(Long id) {
		return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
	}

	public List<Order> getCustomerOrders(Long customerId) {
		return orderRepo.findByCustomerId(customerId);
	}
}