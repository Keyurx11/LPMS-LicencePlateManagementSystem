package com.example.lpmslicenceplatemanagementapp.controllers;

import com.example.lpmslicenceplatemanagementapp.dtos.UserDTO;
import com.example.lpmslicenceplatemanagementapp.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
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
