package com.own.model;

public class Food {

    private Long id;
    private String name;

    public Food(Long id, String name) {
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