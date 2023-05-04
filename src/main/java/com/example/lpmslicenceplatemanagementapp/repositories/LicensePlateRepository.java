package com.example.lpmslicenceplatemanagementapp.repositories;

import com.example.lpmslicenceplatemanagementapp.entities.LicensePlate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicensePlateRepository extends CrudRepository<LicensePlate, String> {

    List<LicensePlate> findByAvailableTrue();

    Optional<LicensePlate> findByPlateID(String plateID);

}
