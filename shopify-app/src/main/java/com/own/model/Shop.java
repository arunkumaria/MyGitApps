package com.own.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Shop {

    @Id
    private String shopDomain;

    private String accessToken;

    public Shop() {}

    public Shop(String shopDomain, String accessToken) {
        this.shopDomain = shopDomain;
        this.accessToken = accessToken;
    }

    public String getShopDomain() {
        return shopDomain;
    }

    public void setShopDomain(String shopDomain) {
        this.shopDomain = shopDomain;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
