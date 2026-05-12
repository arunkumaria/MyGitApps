package com.own;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.own.dto.OrderItemRequest;
import com.own.dto.OrderRequest;
import com.own.entity.Customer;
import com.own.entity.Product;
import com.own.enums.PaymentStatus;
import com.own.repository.CustomerRepository;
import com.own.repository.OrderRepository;
import com.own.repository.ProductRepository;
import com.own.service.OrderService;
import com.own.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderTest() {

        Customer customer = Customer.builder()
                .id(1L)
                .name("Arun")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(1000.0)
                .stock(10)
                .build();

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setProductId(1L);
        itemRequest.setQuantity(2);

        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        request.setItems(List.of(itemRequest));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(paymentService.processPayment())
                .thenReturn(PaymentStatus.SUCCESS);

        assertDoesNotThrow(() -> orderService.createOrder(request));
    }
}
