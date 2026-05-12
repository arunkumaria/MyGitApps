package com.own.dto;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Long productId;
    private Integer quantity;
}