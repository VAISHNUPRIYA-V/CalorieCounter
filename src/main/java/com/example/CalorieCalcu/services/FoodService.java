// src/main/java/com/example/CalorieCalcu/services/FoodService.java
package com.example.CalorieCalcu.services;

import com.example.CalorieCalcu.models.Food;
import com.example.CalorieCalcu.models.FoodDto;
import com.example.CalorieCalcu.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public Food addFood(FoodDto foodDto) {
        Food food = new Food();
        food.setName(foodDto.getName());
        food.setCaloriesPerServing(foodDto.getCaloriesPerServing());
        food.setServingUnit(foodDto.getServingUnit());
        return foodRepository.save(food);
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
    }
}