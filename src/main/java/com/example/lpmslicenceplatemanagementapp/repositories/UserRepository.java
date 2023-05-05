package com.example.lpmslicenceplatemanagementapp.repositories;

import com.example.lpmslicenceplatemanagementapp.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // Finds a user by there unique user ID
    Optional<User> findById(Long userId);

    // Finds a user by their first name
    Optional<User> findByFirstName(String name);
}

