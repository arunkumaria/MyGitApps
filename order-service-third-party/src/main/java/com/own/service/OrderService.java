package com.own.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.own.dto.OrderRequest;
import com.own.dto.PaymentRequest;
import com.own.entity.Order;
import com.own.repository.OrderRepository;

import lombok.Data;

@Service
@Data
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private WebClient webClient;

	public ResponseEntity<?> createOrder(OrderRequest request) {

		// 🔹 Call third-party API (Payment)
		String response = webClient.post()
				.uri("developer.paypal.com/dashboard/?statusId=eyJzdGF0dXMiOiJBQk9SVEVEIn0%3D")
				.bodyValue(new PaymentRequest(request.getAmount())).retrieve().bodyToMono(String.class).block(); // blocking
																													// for
																													// simplicity

		if (response == null) {
			return ResponseEntity.ok("\"Payment failed\"");
			// throw new RuntimeException("Payment failed");

		}

		// 🔹 Save order
		Order order = new Order();
		order.setProduct(request.getProduct());
		order.setAmount(request.getAmount());

		return ResponseEntity.ok(repository.save(order));
	}
}