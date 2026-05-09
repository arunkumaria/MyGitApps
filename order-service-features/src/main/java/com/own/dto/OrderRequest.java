package com.own.dto;


import java.util.List;

import com.own.entity.OrderItem;

import lombok.Data;

@Data
public class OrderRequest {
    private List<OrderItem> items;
}
