package com.own.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.own.model.Shop;
import com.own.repository.ShopRepository;
import com.own.service.ShopifyService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ShopifyService shopifyService;
    private final ShopRepository shopRepository;

    public ProductController(ShopifyService shopifyService, ShopRepository shopRepository) {
        this.shopifyService = shopifyService;
        this.shopRepository = shopRepository;
    }

    @GetMapping("/{shop}")
    public String getProducts(@PathVariable String shop) {

        Shop shopData = shopRepository.findById(shop).orElseThrow();

        String query = """
        {
          products(first: 5) {
            edges {
              node {
                id
                title
              }
            }
          }
        }
        """;

        return shopifyService.executeGraphQL(
                shop,
                shopData.getAccessToken(),
                query
        );
    }
}
