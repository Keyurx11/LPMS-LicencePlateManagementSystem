package com.example.lpmslicenceplatemanagementapp;

import com.example.lpmslicenceplatemanagementapp.entities.*;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class RestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(LicensePlate.class);
        config.exposeIdsFor(OwnershipLog.class);
        config.exposeIdsFor(LicensePlatePurchaseRequest.class);
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(ApiResponse.class);
    }
}
