// src/main/java/com/example/CalorieCalcu/repository/MealFoodRepository.java
package com.example.CalorieCalcu.repository;

import com.example.CalorieCalcu.models.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {
}