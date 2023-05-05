package com.example.lpmslicenceplatemanagementapp.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testSetAndGetUserId() {
        Long testId = 1L;
        user.setUserId(testId);
        Assertions.assertEquals(testId, user.getUserId());
    }

    @Test
    public void testSetAndGetFirstName() {
        String testFirstName = "John";
        user.setFirstName(testFirstName);
        Assertions.assertEquals(testFirstName, user.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String testLastName = "Doe";
        user.setLastName(testLastName);
        Assertions.assertEquals(testLastName, user.getLastName());
    }

    @Test
    public void testSetAndGetEmail() {
        String testEmail = "johndoe@example.com";
        user.setEmail(testEmail);
        Assertions.assertEquals(testEmail, user.getEmail());
    }

    @Test
    public void testSetAndGetPhone() {
        String testPhone = "123-456-7890";
        user.setPhone(testPhone);
        Assertions.assertEquals(testPhone, user.getPhone());
    }

    @Test
    public void testSetAndGetVehicleMake() {
        String testVehicleMake = "Honda";
        user.setVehicleMake(testVehicleMake);
        Assertions.assertEquals(testVehicleMake, user.getVehicleMake());
    }

    @Test
    public void testSetAndGetVehicleModel() {
        String testVehicleModel = "Civic";
        user.setVehicleModel(testVehicleModel);
        Assertions.assertEquals(testVehicleModel, user.getVehicleModel());
    }

    @Test
    public void testSetAndGetVehicleType() {
        String testVehicleType = "Sedan";
        user.setVehicleType(testVehicleType);
        Assertions.assertEquals(testVehicleType, user.getVehicleType());
    }
}