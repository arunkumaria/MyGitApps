package com.own.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.dto.OrderRequest;
import com.own.entity.Order;
import com.own.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @AuthenticationPrincipal OAuth2User user,
            @RequestBody OrderRequest request) {

        String email = user.getAttribute("email");
        return ResponseEntity.ok(orderService.createOrder(email, request));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            @AuthenticationPrincipal OAuth2User user) {

        String email = user.getAttribute("email");
        return ResponseEntity.ok(orderService.getOrders(email));
    }
}
