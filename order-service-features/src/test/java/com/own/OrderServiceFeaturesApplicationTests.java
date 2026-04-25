package com.own;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.entity.OrderItem;
import com.own.service.OrderService;

@SpringBootTest
class OrderServiceFeaturesApplicationTests {

    @Autowired
    private OrderService service;

    @Test
    void testCreateOrder() {
        OrderItem item = new OrderItem(null, "Phone", 2, 500.0);

        OrderRequest request = new OrderRequest();
        request.setItems(List.of(item));

        Order order = service.createOrder(request);

        assertNotNull(order.getId());
        assertEquals(1000.0, order.getTotalPrice());
    }
}
