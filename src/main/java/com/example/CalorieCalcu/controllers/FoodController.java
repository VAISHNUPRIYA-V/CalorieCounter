// src/main/java/com/example/CalorieCalcu/controllers/FoodController.java
package com.example.CalorieCalcu.controllers;

import com.example.CalorieCalcu.models.Food;
import com.example.CalorieCalcu.models.FoodDto;
import com.example.CalorieCalcu.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping
    public ResponseEntity<FoodDto> addFoodItem(@RequestBody FoodDto foodDto) {
        Food newFood = foodService.addFood(foodDto);
        return ResponseEntity.ok(convertToDto(newFood));
    }

    @GetMapping
    public ResponseEntity<List<FoodDto>> getAllFoodItems() {
        List<Food> foods = foodService.getAllFoods();
        List<FoodDto> foodDtos = foods.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(foodDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> getFoodItemById(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        return ResponseEntity.ok(convertToDto(food));
    }

    private FoodDto convertToDto(Food food) {
        FoodDto dto = new FoodDto();
        dto.setId(food.getId());
        dto.setName(food.getName());
        dto.setCaloriesPerServing(food.getCaloriesPerServing());
        dto.setServingUnit(food.getServingUnit());
        return dto;
    }
}