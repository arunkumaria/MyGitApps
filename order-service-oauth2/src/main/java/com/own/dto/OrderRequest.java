package com.own.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String productName;
    private Double price;
}
