package com.example.lpmslicenceplatemanagementapp.services;

import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import com.example.lpmslicenceplatemanagementapp.entities.User;
import com.example.lpmslicenceplatemanagementapp.repositories.LicensePlateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LicensePlateServiceTest {

    @InjectMocks
    private LicensePlateService licensePlateService;

    @Mock
    private LicensePlateRepository licensePlateRepository;

    @Test
    public void getLicensePlateByNumber_NotFound() {
        when(licensePlateRepository.findByPlateID("NOT_FOUND")).thenReturn(Optional.empty());
        LicensePlate licensePlate = licensePlateService.getLicensePlateByNumber("NOT_FOUND");
        assertNull(licensePlate);
        verify(licensePlateRepository, times(1)).findByPlateID("NOT_FOUND");
    }


    @Test
    public void createOwnershipLog() {
        LicensePlate licensePlate = new LicensePlate("AB123CD", 1L);
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPhone("123456789");
        user.setVehicleMake("Toyota");
        user.setVehicleModel("Camry");
        user.setVehicleType("Sedan");
    }

    @Test
    public void saveLicensePlate() {
        LicensePlate licensePlate = new LicensePlate("AB123CD", 1L);
        when(licensePlateRepository.save(licensePlate)).thenReturn(licensePlate);
        LicensePlate savedLicensePlate = licensePlateService.saveLicensePlate(licensePlate);
        assertEquals(licensePlate, savedLicensePlate);
        verify(licensePlateRepository, times(1)).save(licensePlate);
    }
}
