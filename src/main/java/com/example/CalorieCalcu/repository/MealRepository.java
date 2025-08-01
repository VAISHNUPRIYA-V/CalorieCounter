// src/main/java/com/example/CalorieCalcu/repository/MealRepository.java
package com.example.CalorieCalcu.repository;

import com.example.CalorieCalcu.models.Meal;
import com.example.CalorieCalcu.models.RegisterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserAndMealDate(RegisterDetails user, LocalDate mealDate);
}