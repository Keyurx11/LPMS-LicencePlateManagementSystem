package com.example.lpmslicenceplatemanagementapp.controllers;

import com.example.lpmslicenceplatemanagementapp.dtos.LicensePlateDTO;
import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import com.example.lpmslicenceplatemanagementapp.entities.LicensePlatePurchaseRequest;
import com.example.lpmslicenceplatemanagementapp.entities.User;
import com.example.lpmslicenceplatemanagementapp.services.LicensePlateService;
import com.example.lpmslicenceplatemanagementapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class LicensePlateController {

    private final LicensePlateService licensePlateService;
    private final UserService userService;

    @Autowired
    public LicensePlateController(LicensePlateService licensePlateService, UserService userService) {
        this.licensePlateService = licensePlateService;
        this.userService = userService;
    }

    @Autowired
    private LicensePlateService LicensePlateService;

    @GetMapping("/license-plates/{plateNumber}")
    public ResponseEntity<LicensePlate> getLicensePlateByNumber(@PathVariable String plateNumber) {
        Optional<LicensePlate> optionalLicensePlate = Optional.ofNullable(licensePlateService.getLicensePlateByNumber(plateNumber));
        return ResponseEntity.ok(optionalLicensePlate.get());
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseLicensePlate(@RequestBody LicensePlateDTO licensePlateDTO) {
        String plateNumber = licensePlateDTO.getPlateNumber();
        String buyerName = licensePlateDTO.getBuyerName();
        int price = licensePlateDTO.getPrice();

        // Check if the license plate exists in the database
        LicensePlate existingPlate = licensePlateService.getLicensePlateByNumber(plateNumber);
        if (existingPlate != null) {
            return ResponseEntity.badRequest().body("License plate not available");
        }

        // Create a new user or retrieve an existing user
        System.out.println(buyerName);
        User buyer = userService.getUserByName(buyerName);
        if (buyer == null) {
            buyer = new User();
            buyer.setFirstName(licensePlateDTO.getBuyerName());

            buyer.setLastName(licensePlateDTO.getLastName());

            buyer.setEmail(licensePlateDTO.getEmail());

            buyer.setPhone(licensePlateDTO.getPhone());
            buyer.setVehicleMake(licensePlateDTO.getVehicleMake());
            buyer.setVehicleModel(licensePlateDTO.getVehicleModel());
            buyer.setVehicleType(licensePlateDTO.getVehicleType());

            buyer = userService.saveUser(buyer);
        }

        // Create a new license plate and save it to the database
        LicensePlate licensePlate = new LicensePlate(plateNumber, buyer.getUserId());
        licensePlate.setPrice(price);
        licensePlate.setEmail(buyer.getEmail());
        licensePlate.setFirstName(buyer.getFirstName());
        licensePlate.setLastName(buyer.getLastName());
        licensePlate.setAvailable(false);
        licensePlateService.saveLicensePlate(licensePlate);

        return ResponseEntity.ok("License plate purchased successfully!");
    }

    @GetMapping("/license-plates/search/{plateNumber}")
    public ResponseEntity<?> getLicense(@PathVariable String plateNumber) {
        String input = plateNumber; // replace with your input string
        List<String> Response = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String randomString = generateRandomString(input);
            Response.add(randomString.toUpperCase());
        }
        return ResponseEntity.ok(Response);
    }


    private static String generateRandomString(String input) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            if (i < input.length()) {
                // Use a character from the input string
                char c = input.charAt(i);
                sb.append(c);
            } else {
                // Use a random character
                char c = chars.charAt(random.nextInt(chars.length()));
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
