package com.example.lpmslicenceplatemanagementapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "license_plates")
public class LicensePlate {

    @Id
    @Column(name = "number_plate")
    private String plateID;

    private boolean available;

    private int price;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    public LicensePlate(String plateID, Long buyerId) {
        this.plateID = plateID;
        this.buyerId = buyerId;
        this.available = true; //ture by default
    }

    public LicensePlate() {

    }

    public String getPlateID() {
        return plateID;
    }

    public void setPlateID(String plateID) {
        this.plateID = plateID;
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

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}