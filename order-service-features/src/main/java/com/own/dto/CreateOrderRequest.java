package com.own.dto;


import java.util.List;

import com.own.entity.OrderItem;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotEmpty
    private List<OrderItem> items;

    // getters & setters
}
