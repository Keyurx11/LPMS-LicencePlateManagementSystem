package com.example.lpmslicenceplatemanagementapp.dtos;

public class PurchaseDTO {

    private String plateNumber;

    private Long userId;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
