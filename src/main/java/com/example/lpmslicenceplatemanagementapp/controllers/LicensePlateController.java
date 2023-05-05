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

    @Autowired
    private LicensePlateRepository licensePlateRepository;

    @Autowired
    private OwnershipLogService ownershipLogService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseLicensePlate(@RequestBody LicensePlateDTO licensePlateDTO) {
        String plateNumber = licensePlateDTO.getPlateNumber();


        // Validate plate number format - checks 3 formats XX99XXX, X000XXX and XX11XX
        if (!plateNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{3}|[A-Z]{1,3}[0-9]{1,4}[A-Z]{1,3}")) {
            return ResponseEntity.badRequest().body("Invalid plate number format.");
        }

        if (containsInappropriateWords(plateNumber)) {
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
        User buyer = userService.getUserByName(licensePlateDTO.getBuyerName());
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
        licensePlate.setPrice(licensePlateDTO.getPrice());
        licensePlate.setEmail(buyer.getEmail());
        licensePlate.setFirstName(buyer.getFirstName());
        licensePlate.setLastName(buyer.getLastName());
        licensePlate.setAvailable(false);
        licensePlateService.saveLicensePlate(licensePlate);
        // Log the ownership change with the price
        ownershipLogService.logOwnershipChange(plateNumber, buyer.getUserId(), licensePlateDTO.getVehicleMake(), licensePlateDTO.getVehicleModel(), licensePlateDTO.getVehicleType(), licensePlateDTO.getPrice());

        return ResponseEntity.ok("License plate purchased successfully!");
    }


    @GetMapping("/license-plates/{plateNumber}")
    public ResponseEntity<?> getLicensePlateByNumber(@PathVariable String plateNumber) {
        LicensePlate licensePlate = licensePlateService.getLicensePlateByNumber(plateNumber);

        if (licensePlate == null) {
            return ResponseEntity.ok(generateLicensePlateResponse(plateNumber));
        } else {
            return ResponseEntity.ok(licensePlate);
        }
    }

    @GetMapping("/license-plates/search/{plateNumber}")
    public ResponseEntity<?> getLicense(@PathVariable String plateNumber) {
        if (containsInappropriateWords(plateNumber)) {
            return ResponseEntity.badRequest().body("The provided plate number contains inappropriate words.");
        }

        String input = plateNumber.toUpperCase();

        String regexPattern = input.replaceAll("\\*", "\\\\w");

        List<Map<String, Object>> response = new ArrayList<>();

        // Generate random license plates based on the input pattern
        int numGenerated = 0;
        while (numGenerated < 10) {
            String randomString = generateRandomString(input.length());
            LicensePlate licensePlate = licensePlateService.getLicensePlateByNumber(randomString);
            if (licensePlate == null) {
                Map<String, Object> plateInfo = generateLicensePlateResponse(randomString);
                if (plateInfo.containsKey("status") && plateInfo.get("status").equals("available")) {
                    response.add(plateInfo);
                    numGenerated++;
                }
            }
        }

        return ResponseEntity.ok(response);
    }


    private Map<String, Object> generateLicensePlateResponse(String plateNumber) {
        // Validate plate number format
        if (!plateNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{3}|[A-Z]{1,3}[0-9]{1,4}[A-Z]{1,3}")) {
            return Collections.singletonMap("message", "Invalid plate number format.");
        }

        int price = licensePlateService.generateRandomPrice(plateNumber);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("plateNumber", plateNumber);
        response.put("status", "available");
        response.put("price", price);
        response.put("message", "License plate is available for purchase");
        return response;
    }

    private String generateRandomString(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }


    public boolean containsInappropriateWords(String plateNumber) {
        // Just for demonstration so small list, can also be done by creating insert script and checing with db
        List<String> inappropriateWords = Arrays.asList("FF23GOT", "NG23GRR", "N23GER", "FF23KED", "NN23GAA", "N23GGA", "FF23KER",
                "NN23GAR", "N23GGR", "FK23RUS", "NN23GAS", "N23GGR", "FK23UKR", "NN23GAZ",
                "N23GRO", "FK23VAJ", "NN23GER", "N23GRR", "GA23EDD", "NN23GGA", "N23GRS",
                "GA23LOW", "NN23GGR", "N23GRZ", "GA23NJA", "PIG");
        String plateNumberUpperCase = plateNumber.toUpperCase();

        for (String word : inappropriateWords) {
            if (plateNumberUpperCase.contains(word)) {
                return true;
            }
        }
        return false;
    }

}
