// src/main/java/com/example/CalorieCalcu/repository/UserRepository.java
package com.example.CalorieCalcu.repository;

import com.example.CalorieCalcu.models.RegisterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<RegisterDetails, Long> {
    Optional<RegisterDetails> findByUserName(String username);
}