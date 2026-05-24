package com.own.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.OrderRequest;
import com.own.service.BffService;

@RestController
@RequestMapping("/bff")
public class BffController {

	@Autowired
	private BffService bffService;

	@PostMapping("/place-order")
	public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {

		System.out.println("CONTROLLER HIT");

		String response = bffService.placeOrder(request);

		System.out.println("FINAL RESPONSE = " + response);

		return ResponseEntity.ok(response);
	}
}