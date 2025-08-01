// src/main/java/com/example/CalorieCalcu/repository/FoodRepository.java
package com.example.CalorieCalcu.repository;

import com.example.CalorieCalcu.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}