package com.example.utamobilevendingsystem.domain;

public class Vehicle {

    private int vehicleId;
    private String vehicleName;
    private VehicleType vehicleType;
    private Status availability;
    private int locationId;
    private String locationName;

    public Vehicle(String vehicleName, VehicleType vehicleType, Status availability, int locationId, String locationName) {
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.availability = availability;
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public Vehicle() {

    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Status isAvailability() {
        return availability;
    }

    public void setAvailability(Status status) {
        this.availability = status;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
