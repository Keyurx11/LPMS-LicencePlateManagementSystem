package com.example.lpmslicenceplatemanagementapp.services;

import com.example.lpmslicenceplatemanagementapp.entities.OwnershipLog;
import com.example.lpmslicenceplatemanagementapp.repositories.OwnershipLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OwnershipLogService {

    private final OwnershipLogRepository ownershipLogRepository;

    @Autowired
    public OwnershipLogService(OwnershipLogRepository ownershipLogRepository) {
        this.ownershipLogRepository = ownershipLogRepository;
    }

    public OwnershipLog logOwnershipChange(String plateNumber, Long buyerId, String vehicleMake, String vehicleModel, String vehicleType, int price) {
        OwnershipLog ownershipLog = new OwnershipLog();
        ownershipLog.setPlateNumber(plateNumber);
        ownershipLog.setBuyerId(buyerId);
        ownershipLog.setPurchaseDate(new Date());
        ownershipLog.setPrice(price);
        ownershipLog.setVehicleMake(vehicleMake);
        ownershipLog.setVehicleModel(vehicleModel);
        ownershipLog.setVehicleType(vehicleType);

        return ownershipLogRepository.save(ownershipLog);
    }
}
