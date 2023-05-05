package com.example.lpmslicenceplatemanagementapp.controllers;

import com.example.lpmslicenceplatemanagementapp.dtos.LicensePlateDTO;
import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import com.example.lpmslicenceplatemanagementapp.entities.User;
import com.example.lpmslicenceplatemanagementapp.services.LicensePlateService;
import com.example.lpmslicenceplatemanagementapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LicensePlateControllerTest {

    @Mock
    LicensePlateService licensePlateService;

    @Mock
    UserService userService;

    @InjectMocks
    LicensePlateController licensePlateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void purchaseLicensePlate_validPlateNumberAndLengthAndNoInappropriateWords() {
        LicensePlateDTO licensePlateDTO = new LicensePlateDTO();
        licensePlateDTO.setPlateNumber("AB12CDE");
        licensePlateDTO.setBuyerName("John");
        licensePlateDTO.setLastName("Doe");
        licensePlateDTO.setPrice(100);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        when(licensePlateService.containsInappropriateWords("AB12CDE")).thenReturn(false);
        when(licensePlateService.getLicensePlateByNumber("AB12CDE")).thenReturn(null);
        when(userService.createNewUser(licensePlateDTO)).thenReturn(user);
        when(licensePlateService.purchaseLicensePlate(user, "AB12CDE", 100)).thenReturn(true);

        ResponseEntity<String> response = licensePlateController.purchaseLicensePlate(licensePlateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("License plate purchased successfully!", response.getBody());
    }

    @Test
    void purchaseLicensePlate_invalidPlateNumberFormat() {
        LicensePlateDTO licensePlateDTO = new LicensePlateDTO();
        licensePlateDTO.setPlateNumber("ABCD");

        ResponseEntity<String> response = licensePlateController.purchaseLicensePlate(licensePlateDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid plate number format.", response.getBody());
    }

    @Test
    void purchaseLicensePlate_plateNumberContainsInappropriateWords() {
        LicensePlateDTO licensePlateDTO = new LicensePlateDTO();
        licensePlateDTO.setPlateNumber("ASSHOLE");

        when(licensePlateService.containsInappropriateWords("ASSHOLE")).thenReturn(true);

        ResponseEntity<String> response = licensePlateController.purchaseLicensePlate(licensePlateDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void purchaseLicensePlate_plateNumberLengthOutOfRange() {
        LicensePlateDTO licensePlateDTO = new LicensePlateDTO();
        licensePlateDTO.setPlateNumber("A");

        ResponseEntity<String> response = licensePlateController.purchaseLicensePlate(licensePlateDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void purchaseLicensePlate_licensePlateNotAvailable() {
        LicensePlateDTO licensePlateDTO = new LicensePlateDTO();
        licensePlateDTO.setPlateNumber("AB12CDE");

        when(licensePlateService.containsInappropriateWords("AB12CDE")).thenReturn(false);
        when(licensePlateService.getLicensePlateByNumber("AB12CDE")).thenReturn(new LicensePlate());
        ResponseEntity<String> response = licensePlateController.purchaseLicensePlate(licensePlateDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("License plate not available", response.getBody());
    }

    @Test
    void getLicensePlateByNumber_licensePlateExists() {
        LicensePlate licensePlate = new LicensePlate();
        licensePlate.setPlateID("AB12CDE");

        when(licensePlateService.getLicensePlateByNumber("AB12CDE")).thenReturn(licensePlate);

        ResponseEntity<?> response = licensePlateController.getLicensePlateByNumber("AB12CDE");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(licensePlate, response.getBody());
    }

    @Test
    void getLicensePlateByNumber_licensePlateDoesNotExist() {
        String plateNumber = "AB12CDE";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "License plate " + plateNumber + " not found.");
        responseMap.put("instructions", "Please generate a new license plate using POST /license-plates");

        when(licensePlateService.getLicensePlateByNumber(plateNumber)).thenReturn(null);
        when(licensePlateService.generateLicensePlateResponse(plateNumber)).thenReturn(responseMap);

        ResponseEntity<?> response = licensePlateController.getLicensePlateByNumber(plateNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMap, response.getBody());
    }

    @Test
    void searchLicensePlatesByPattern_containsInappropriateWords() {
        String plateNumber = "ASSHOLE";

        when(licensePlateService.containsInappropriateWords(plateNumber)).thenReturn(true);

        ResponseEntity<?> response = licensePlateController.searchLicensePlatesByPattern(plateNumber);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The provided plate number contains inappropriate words.", response.getBody());
    }

    @Test
    void searchLicensePlatesByPattern_containsWildcard() {
        String plateNumber = "A*1";

        List<Map<String, Object>> responseList = List.of(
                Map.of("plateNumber", "AC123DF", "status", "available", "price", 100),
                Map.of("plateNumber", "AE1BDFG", "status", "available", "price", 200),
                Map.of("plateNumber", "AJ1BDFG", "status", "unavailable", "firstName", "John", "lastName", "Doe")
        );

        when(licensePlateService.containsInappropriateWords(plateNumber)).thenReturn(false);
        when(licensePlateService.containsWildcard(plateNumber)).thenReturn(true);
        when(licensePlateService.generateRandomLicensePlates(plateNumber, 10)).thenReturn(responseList);

        ResponseEntity<?> response = licensePlateController.searchLicensePlatesByPattern(plateNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
    }

    @Test
    void searchLicensePlatesByPattern_noWildcard() {
        String plateNumber = "AB123CD";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("plateNumber", "AB123CD");
        responseMap.put("status", "available");
        responseMap.put("price", 100);

        when(licensePlateService.containsInappropriateWords(plateNumber)).thenReturn(false);
        when(licensePlateService.containsWildcard(plateNumber)).thenReturn(false);
        when(licensePlateService.generateLicensePlateResponse(plateNumber)).thenReturn(responseMap);

        ResponseEntity<?> response = licensePlateController.searchLicensePlatesByPattern(plateNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals
                (responseMap, response.getBody());
    }

    @Test
    void searchLicensePlatesByPattern_noWildcard_plateUnavailable() {
        String plateNumber = "AB12CDE";

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("plateNumber", "AB12CDE");
        responseMap.put("status", "unavailable");
        responseMap.put("firstName", "John");
        responseMap.put("lastName", "Doe");

        when(licensePlateService.containsInappropriateWords(plateNumber)).thenReturn(false);
        when(licensePlateService.containsWildcard(plateNumber)).thenReturn(false);
        when(licensePlateService.generateLicensePlateResponse(plateNumber)).thenReturn(responseMap);

        ResponseEntity<?> response = licensePlateController.searchLicensePlatesByPattern(plateNumber);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The provided license plate is not available for purchase.", response.getBody());
    }
}
