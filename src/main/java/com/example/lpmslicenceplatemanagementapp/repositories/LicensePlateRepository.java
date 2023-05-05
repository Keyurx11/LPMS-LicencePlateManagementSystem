package com.example.lpmslicenceplatemanagementapp.repositories;

import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicensePlateRepository extends CrudRepository<LicensePlate, String> {

    // Finds all available license plates in the database
    List<LicensePlate> findByAvailableTrue();

    // Finds a license plate by its unique plate ID
    Optional<LicensePlate> findByPlateID(String plateID);

}
