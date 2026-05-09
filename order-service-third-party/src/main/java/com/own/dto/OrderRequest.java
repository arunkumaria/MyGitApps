package com.own.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class OrderRequest {
    private String product;
    private int amount;
}
