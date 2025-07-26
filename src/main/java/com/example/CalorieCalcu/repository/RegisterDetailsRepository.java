package com.example.CalorieCalcu.repository;

import com.example.CalorieCalcu.models.RegisterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RegisterDetailsRepository extends JpaRepository< RegisterDetails,Integer> {
    Optional<RegisterDetails> findByUserName(String userName);
}
