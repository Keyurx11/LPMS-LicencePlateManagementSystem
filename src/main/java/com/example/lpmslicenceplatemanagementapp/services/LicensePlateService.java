package com.example.lpmslicenceplatemanagementapp.services;

import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import com.example.lpmslicenceplatemanagementapp.entities.OwnershipLog;
import com.example.lpmslicenceplatemanagementapp.entities.User;
import com.example.lpmslicenceplatemanagementapp.repositories.LicensePlateRepository;
import com.example.lpmslicenceplatemanagementapp.repositories.OwnershipLogRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LicensePlateService {

    private final LicensePlateRepository licensePlateRepository;

    private final OwnershipLogRepository ownershipLogRepository;

    public LicensePlateService(LicensePlateRepository licensePlateRepository, OwnershipLogRepository ownershipLogRepository) {
        this.licensePlateRepository = licensePlateRepository;
        this.ownershipLogRepository = ownershipLogRepository;
    }

    public List<LicensePlate> getAvailableLicensePlates() {
        return licensePlateRepository.findByAvailableTrue();
    }

    public int generateRandomPrice() {
        Random random = new Random();
        int price = random.nextInt(51) + 10; // generates a random number between 10 and 60
        while (price % 5 != 0) {
            price++; // ensures price is divisible by 5
        }
        return price;
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

    public void createOwnershipLog(LicensePlate licensePlate, User user) {
        OwnershipLog ownershipLog = new OwnershipLog();
        ownershipLog.setPlateNumber(licensePlate.getPlateID());
        ownershipLog.setBuyerId(user.getUserId());
        ownershipLog.setPurchaseDate(new Date());
        ownershipLog.setVehicleMake(user.getVehicleMake());
        ownershipLog.setVehicleModel(user.getVehicleModel());
        ownershipLog.setVehicleType(user.getVehicleType());
        ownershipLogRepository.save(ownershipLog);
    }

    public void purchaseLicensePlate(User buyer, LicensePlate licensePlate) {
        licensePlate.setAvailable(false);
        licensePlateRepository.save(licensePlate);
        createOwnershipLog(licensePlate, buyer);
    }

    public Optional<LicensePlate> findByPlateID(String plateID) {
        return licensePlateRepository.findByPlateID(plateID);
    }
}