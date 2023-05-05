package com.example.lpmslicenceplatemanagementapp.controllers;

import com.example.lpmslicenceplatemanagementapp.dtos.LicensePlateDTO;
import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import com.example.lpmslicenceplatemanagementapp.entities.User;
import com.example.lpmslicenceplatemanagementapp.repositories.LicensePlateRepository;
import com.example.lpmslicenceplatemanagementapp.services.LicensePlateService;
import com.example.lpmslicenceplatemanagementapp.services.OwnershipLogService;
import com.example.lpmslicenceplatemanagementapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class LicensePlateController {

    private final LicensePlateService licensePlateService;
    private final UserService userService;

    @Autowired
    public LicensePlateController(LicensePlateService licensePlateService, UserService userService) {
        this.licensePlateService = licensePlateService;
        this.userService = userService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseLicensePlate(@RequestBody LicensePlateDTO licensePlateDTO) {
        String plateNumber = licensePlateDTO.getPlateNumber();

        // Validate plate number format - checks 3 formats XX99XXX, X000XXX and XX11XX
        if (!plateNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{3}|[A-Z]{1,3}[0-9]{1,4}[A-Z]{1,3}")) {
            return ResponseEntity.badRequest().body("Invalid plate number format.");
        }

        if (licensePlateService.containsInappropriateWords(plateNumber)) {
            return ResponseEntity.badRequest().body("The provided plate number contains inappropriate words.");
        }

        // Validate plate number length - 3 is fine
        if (plateNumber.length() < 2 || plateNumber.length() > 7) {
            return ResponseEntity.badRequest().body("Plate number must be between 2 and 7 characters long.");
        }

        // Check if the license plate exists in the database
        LicensePlate existingPlate = licensePlateService.getLicensePlateByNumber(plateNumber);
        if (existingPlate != null) {
            return ResponseEntity.badRequest().body("License plate not available");
        }

        // Create a new user or retrieve an existing user
        User buyer = userService.createNewUser(licensePlateDTO);

        // Purchase the license plate
        boolean purchaseSuccess = licensePlateService.purchaseLicensePlate(buyer, plateNumber, licensePlateDTO.getPrice());

        if (purchaseSuccess) {
            return ResponseEntity.ok("License plate purchased successfully!");
        } else {
            return ResponseEntity.badRequest().body("License plate purchase failed.");
        }
    }

    @GetMapping("/license-plates/{plateNumber}")
    public ResponseEntity<?> getLicensePlateByNumber(@PathVariable String plateNumber) {
        // Get the license plate by number
        LicensePlate licensePlate = licensePlateService.getLicensePlateByNumber(plateNumber);

        // If the license plate doesn't exist, return a response with information about generating a new one
        if (licensePlate == null) {
            return ResponseEntity.ok(licensePlateService.generateLicensePlateResponse(plateNumber));
        } else {
            // If the license plate exists, return the license plate object
            return ResponseEntity.ok(licensePlate);
        }
    }

    @GetMapping("/license-plates/search/{plateNumber}")
    public ResponseEntity<?> searchLicensePlatesByPattern(@PathVariable String plateNumber) {
        // Check if the plate number contains inappropriate words
        if (licensePlateService.containsInappropriateWords(plateNumber)) {
            return ResponseEntity.badRequest().body("The provided plate number contains inappropriate words.");
        }

        // Convert the input to uppercase
        String input = plateNumber.toUpperCase();

        // Check if the input contains a wildcard character
        if (licensePlateService.containsWildcard(input)) {
            // Generate random license plates based on the search pattern
            List<Map<String, Object>> response = licensePlateService.generateRandomLicensePlates(input, 10);
            return ResponseEntity.ok(response);
        } else {
            // Generate information about the license plate for the response
            Map<String, Object> plateInfo = licensePlateService.generateLicensePlateResponse(input);

            // If the license plate is not available, return an error message
            if (plateInfo.containsKey("status") && !plateInfo.get("status").equals("available")) {
                return ResponseEntity.badRequest().body("The provided license plate is not available for purchase.");
            }

            // Return the information about the available license plate
            return ResponseEntity.ok(plateInfo);
        }
    }

}
