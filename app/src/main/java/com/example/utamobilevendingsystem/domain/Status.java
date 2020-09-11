package com.example.utamobilevendingsystem.domain;

public enum Status {

    IN_PROGRESS(1, "In Progress"),
    CONFIRMED(2, "Confirmed"),
    DECLINED(3, "Declined"),
    SUCCESS(4, "SUCCESS"),
    ORDER_COMPLETED(5, "Order Completed"),
    AVAILABLE(6, "Available"),
    UNAVAILABLE(7, "Unavailable"),
    UNASSIGNED(8, "Unassigned");

    private int id;
    private String description;

    Status(int id, String description){
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
