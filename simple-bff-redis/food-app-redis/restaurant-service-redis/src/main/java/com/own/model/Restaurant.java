package com.own.model;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private Long id;
    private String name;

    public Restaurant() {
    }

    public Restaurant(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}