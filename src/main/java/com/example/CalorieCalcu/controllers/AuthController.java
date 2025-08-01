package com.example.CalorieCalcu.controllers;


import com.example.CalorieCalcu.models.JwtResponse;
import com.example.CalorieCalcu.models.RegisterDetails;
import com.example.CalorieCalcu.models.UserDetailsDto;
import com.example.CalorieCalcu.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authservice;

    //@PreAuthorize("ADMIN")
    @PostMapping("/register")
    public String addNewUser(@RequestBody UserDetailsDto register){
        return authservice.AddNewUser(register);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> Login(@RequestBody RegisterDetails login){
        JwtResponse response = authservice.authenticate(login);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details")
    public List<RegisterDetails> getDetails(){
        return authservice.getDetails();
    }
}
