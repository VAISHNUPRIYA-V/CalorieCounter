// src/main/java/com/example/CalorieCalcu/services/UserService.java
package com.example.CalorieCalcu.services;

import com.example.CalorieCalcu.models.RegisterDetails;
import com.example.CalorieCalcu.repository.RegisterDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RegisterDetailsRepository registerDetailsRepository;

    public Optional<RegisterDetails> findByUsername(String username) {
        return registerDetailsRepository.findByUserName(username);
    }
}