package com.own.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.repository.ShopRepository;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final ShopRepository shopRepository;

    public WebhookController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @PostMapping("/uninstall")
    public ResponseEntity<Void> uninstall(@RequestHeader("X-Shopify-Shop-Domain") String shop) {

        shopRepository.deleteById(shop);

        return ResponseEntity.ok().build();
    }
}
