package com.example.lpmslicenceplatemanagementapp.dtos;

public class LicensePlateDTO {

    private String plateNumber;

    private boolean available;

    private int price;

    private String buyerName;

    private String lastName;

    private String email;

    private String phone;

    private String vehicleMake;

    private String vehicleModel;

    private String vehicleType;

    public LicensePlateDTO(String plateNumber, boolean available, int price, String buyerName, String lastName, String email, String phone, String vehicleMake, String vehicleModel, String vehicleType) {
        this.plateNumber = plateNumber;
        this.available = available;
        this.price = price;
        this.buyerName = buyerName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
    }

    public LicensePlateDTO() {

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
