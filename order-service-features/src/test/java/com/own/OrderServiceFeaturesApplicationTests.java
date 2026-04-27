package com.own;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.own.dto.CreateOrderRequest;
import com.own.entity.OrderItem;
import com.own.entity.Product;
import com.own.service.OrderServiceImpl;
import com.own.service.ProductService;

class OrderServiceTest {

	@Test
	void shouldFailWhenStockInsufficient() {
		ProductService productService = new ProductService();

		Product p = new Product();
		p.setName("Phone");
		p.setStock(1);
		p.setPrice(1000);
		productService.add(p);

		OrderServiceImpl orderService = new OrderServiceImpl(productService);

		OrderItem item = new OrderItem();
		item.setId(Long.parseLong(p.getId()));
		item.setQuantity(5);

		CreateOrderRequest req = new CreateOrderRequest();
		req.setItems(List.of(item));

		assertThrows(RuntimeException.class, () -> orderService.create(req));
	}
}