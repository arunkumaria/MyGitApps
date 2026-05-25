package com.own.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {

        return ResponseEntity.ok(
                bffService.dashboard()
        );
    }

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(
            @RequestBody OrderRequest request
    ) {

        return ResponseEntity.ok(
                bffService.placeOrder(request)
        );
    }
}