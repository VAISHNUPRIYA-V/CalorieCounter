package com.example.CalorieCalcu.controllers;


import com.example.CalorieCalcu.models.RegisterDetails;
import com.example.CalorieCalcu.models.UserDetailsDto;
import com.example.CalorieCalcu.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authservice;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserDetailsDto register){
        return authservice.AddNewUser(register);
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody RegisterDetails login){
        return authservice.authenticate(login);
    }

    @GetMapping("/details")
    public List<RegisterDetails> getDetails(){
        return authservice.getDetails();
    }
}
