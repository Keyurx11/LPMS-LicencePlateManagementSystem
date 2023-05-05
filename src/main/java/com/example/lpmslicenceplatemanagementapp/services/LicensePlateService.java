package com.example.lpmslicenceplatemanagementapp.services;

import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import com.example.lpmslicenceplatemanagementapp.entities.OwnershipLog;
import com.example.lpmslicenceplatemanagementapp.entities.User;
import com.example.lpmslicenceplatemanagementapp.repositories.LicensePlateRepository;
import com.example.lpmslicenceplatemanagementapp.repositories.OwnershipLogRepository;

import com.example.lpmslicenceplatemanagementapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LicensePlateService {

    private final LicensePlateRepository licensePlateRepository;
    private final OwnershipLogRepository ownershipLogRepository;
    private final UserRepository userRepository;

    public LicensePlateService(LicensePlateRepository licensePlateRepository, OwnershipLogRepository ownershipLogRepository, UserRepository userRepository) {
        this.licensePlateRepository = licensePlateRepository;
        this.ownershipLogRepository = ownershipLogRepository;
        this.userRepository = userRepository;
    }

    public List<LicensePlate> getAvailableLicensePlates() {
        return licensePlateRepository.findByAvailableTrue();
    }

    public int generateRandomPrice(String plateNumber) {
        int length = plateNumber.length();
        if (length == 7) {
            return 250;
        } else {
            int price = 250;
            int lengthDifference = 7 - length;
            for (int i = 0; i < lengthDifference; i++) {
                price += 100;
            }
            return price;
        }
    }

    public LicensePlate getLicensePlateByNumber(String number) {
        Optional<LicensePlate> optionalLicensePlate = licensePlateRepository.findByPlateID(number);
        return optionalLicensePlate.orElse(null);
    }

    public LicensePlate saveLicensePlate(LicensePlate licensePlate) {
        return licensePlateRepository.save(licensePlate);
    }

    public void updateLicensePlate(LicensePlate licensePlate) {
        licensePlateRepository.save(licensePlate);
    }

    public void createOwnershipLog(LicensePlate licensePlate, User user, int price) {
        OwnershipLog ownershipLog = new OwnershipLog();
        ownershipLog.setPlateNumber(licensePlate.getPlateID());
        ownershipLog.setBuyerId(user.getUserId());
        ownershipLog.setPurchaseDate(new Date());
        ownershipLog.setPrice(price);
        ownershipLog.setVehicleMake(user.getVehicleMake());
        ownershipLog.setVehicleModel(user.getVehicleModel());
        ownershipLog.setVehicleType(user.getVehicleType());
        ownershipLogRepository.save(ownershipLog);
    }

    public boolean purchaseLicensePlate(User buyer, String plateNumber, int price) {
        // Get the license plate by number
        LicensePlate licensePlate = getLicensePlateByNumber(plateNumber);
        if (licensePlate == null) {
            // Create a new license plate and save it to the database
            licensePlate = new LicensePlate(plateNumber, buyer.getUserId());
            licensePlate.setPrice(price);
            licensePlate.setEmail(buyer.getEmail());
            licensePlate.setFirstName(buyer.getFirstName());
            licensePlate.setLastName(buyer.getLastName());
            licensePlate.setAvailable(false);
            saveLicensePlate(licensePlate);
        } else {
            // Update the existing license plate
            licensePlate.setAvailable(false);
            updateLicensePlate(licensePlate);
        }
        // Log the ownership change with the price
        createOwnershipLog(licensePlate, buyer, price);
        return true;
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

    public Map<String, Object> generateLicensePlateResponse(String plateNumber) {
        // Validate plate number format
        if (!plateNumber.matches("[A-Z]{2}[0-9]{2}[A-Z]{3}|[A-Z]{1,3}[0-9]{1,4}[A-Z]{1,3}")) {
            return Collections.singletonMap("message", "Invalid plate number format.");
        }

        int price = generateRandomPrice(plateNumber);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("plateNumber", plateNumber);
        response.put("status", "available");
        response.put("price", price);
        response.put("message", "License plate is available for purchase");
        return response;
    }


    public String generateRandomString(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }

    public boolean containsWildcard(String plateNumber) {
        return plateNumber.contains("*");
    }

    public String generateRandomLicensePlate(String pattern) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(pattern.length());

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c == '*') {
                sb.append((char) ('A' + random.nextInt(26)));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public List<Map<String, Object>> generateRandomLicensePlates(String pattern, int numPlates) {
        List<Map<String, Object>> response = new ArrayList<>();

        for (int i = 0; i < numPlates; i++) {
            String plateNumber = generateRandomLicensePlate(pattern);
            Map<String, Object> plateInfo = generateLicensePlateResponse(plateNumber);
            response.add(plateInfo);
        }

        return response;
    }


}
