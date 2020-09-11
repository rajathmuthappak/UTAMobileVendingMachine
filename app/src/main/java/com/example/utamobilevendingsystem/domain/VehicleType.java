package com.example.utamobilevendingsystem.domain;

public enum VehicleType {

    CART(1, "Cart"),
    FOOD_TRUCK(2, "Food Truck");

    int id;
    String description;

    VehicleType(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
