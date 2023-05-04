package com.example.lpmslicenceplatemanagementapp.services;

import com.example.lpmslicenceplatemanagementapp.dtos.UserDTO;
import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import com.example.lpmslicenceplatemanagementapp.entities.User;
import com.example.lpmslicenceplatemanagementapp.repositories.LicensePlateRepository;
import com.example.lpmslicenceplatemanagementapp.repositories.OwnershipLogRepository;
import com.example.lpmslicenceplatemanagementapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final LicensePlateRepository licensePlateRepository;

    private final OwnershipLogRepository ownershipLogRepository;

    public UserService(UserRepository userRepository, LicensePlateRepository licensePlateRepository, OwnershipLogRepository ownershipLogRepository) {
        this.userRepository = userRepository;
        this.licensePlateRepository = licensePlateRepository;
        this.ownershipLogRepository = ownershipLogRepository;
    }

    public void registerNewUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getName());
        user.setLastName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setVehicleMake(userDTO.getVehicleMake());
        user.setVehicleModel(userDTO.getVehicleModel());
        user.setVehicleType(userDTO.getVehicleType());
        userRepository.save(user);
    }

    public void assignLicensePlate(User user, LicensePlate licensePlate) {
        licensePlate.setAvailable(false);
        licensePlate.setBuyerId(user.getUserId());
        licensePlateRepository.save(licensePlate);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByName(String userId) {
        return userRepository.findByFirstName(userId).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }


}
