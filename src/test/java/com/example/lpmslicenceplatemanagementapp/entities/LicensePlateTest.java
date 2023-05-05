package com.example.lpmslicenceplatemanagementapp.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LicensePlateTest {

    @Test
    public void testGettersAndSetters() {
        LicensePlate licensePlate = new LicensePlate("AB123CD", 1L);
        licensePlate.setAvailable(false);
        licensePlate.setPrice(100);
        licensePlate.setFirstName("John");
        licensePlate.setLastName("Doe");
        licensePlate.setEmail("john.doe@example.com");

        Assertions.assertEquals("AB123CD", licensePlate.getPlateID());
        Assertions.assertFalse(licensePlate.isAvailable());
        Assertions.assertEquals(100, licensePlate.getPrice());
        Assertions.assertEquals(1L, licensePlate.getBuyerId());
        Assertions.assertEquals("John", licensePlate.getFirstName());
        Assertions.assertEquals("Doe", licensePlate.getLastName());
        Assertions.assertEquals("john.doe@example.com", licensePlate.getEmail());
    }

    @Test
    public void testConstructor() {
        LicensePlate licensePlate = new LicensePlate("AB123CD", 1L);
        Assertions.assertEquals("AB123CD", licensePlate.getPlateID());
        Assertions.assertTrue(licensePlate.isAvailable());
        Assertions.assertEquals(1L, licensePlate.getBuyerId());
    }

    @Test
    public void testInvalidPlateID() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LicensePlate licensePlate = new LicensePlate("", 1L);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LicensePlate licensePlate = new LicensePlate("A", 1L);
        });

    }

    @Test
    public void testInvalidBuyerId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LicensePlate licensePlate = new LicensePlate("AB123CD", null);
        });
    }
}
