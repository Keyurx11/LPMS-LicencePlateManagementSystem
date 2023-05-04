package com.example.lpmslicenceplatemanagementapp.controllers;

import com.example.lpmslicenceplatemanagementapp.dtos.UserDTO;
import com.example.lpmslicenceplatemanagementapp.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerNewUser(@RequestBody UserDTO userDTO) {
        userService.registerNewUser(userDTO);
    }

}
