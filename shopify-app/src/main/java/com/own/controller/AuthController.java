package com.own.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Shop;
import com.own.repository.ShopRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ShopRepository shopRepository;

    @Value("${shopify.api.key}")
    private String apiKey;

    @Value("${shopify.api.secret}")
    private String apiSecret;

    @Value("${shopify.redirect.uri}")
    private String redirectUri;

    public AuthController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping("/install")
    public void install(@RequestParam String shop, HttpServletResponse response) throws IOException {

        String url = "https://" + shop + "/admin/oauth/authorize" +
                "?client_id=" + apiKey +
                "&scope=read_products,write_products" +
                "&redirect_uri=" + redirectUri;

        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam String shop,
                                           @RequestParam String code) {

        // TODO: Exchange code for token (simplified here)
        String accessToken = "mock_token";

        shopRepository.save(new Shop(shop, accessToken));

        return ResponseEntity.ok("App Installed for " + shop);
    }
}

