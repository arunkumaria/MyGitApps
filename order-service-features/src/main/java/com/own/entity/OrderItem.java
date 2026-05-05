package com.own.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;
    private double price;

    @ManyToOne
    private Order order;
}