package com.example.lpmslicenceplatemanagementapp.repositories;

import com.example.lpmslicenceplatemanagementapp.entities.OwnershipLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnershipLogRepository extends CrudRepository<OwnershipLog, Long> {

    // add any custom query methods here

}
