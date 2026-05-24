package com.own.model;


import java.io.Serializable;

public class Order implements Serializable {

    private Long orderId;

    private String foodName;

    private String restaurantName;

    private String status;

    public Order() {
    }

    public Order(
            Long orderId,
            String foodName,
            String restaurantName,
            String status
    ) {
        this.orderId = orderId;
        this.foodName = foodName;
        this.restaurantName = restaurantName;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", foodName='" + foodName + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}