package com.example.lpmslicenceplatemanagementapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "license_plates")
public class LicensePlate {

    @Id
    @NotBlank
    @Size(min = 2, max = 7)
    private String plateID;

    @NotNull
    private boolean available;

    @Min(1)
    private int price;

    @NotNull
    private Long buyerId;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Email
    private String email;

    public LicensePlate(String plateID, Long buyerId) {
        if (plateID.length() < 2 || plateID.length() > 7) {
            throw new IllegalArgumentException("Plate ID must be between 2 and 7 characters long.");
        }
        if (buyerId == null) {
            throw new IllegalArgumentException("Buyer ID cannot be null.");
        }
        this.plateID = plateID;
        this.buyerId = buyerId;
        this.available = true; // true by default
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
