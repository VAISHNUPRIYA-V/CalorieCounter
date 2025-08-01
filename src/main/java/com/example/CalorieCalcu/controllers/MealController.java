// src/main/java/com/example/CalorieCalcu/controllers/MealController.java
package com.example.CalorieCalcu.controllers;

import com.example.CalorieCalcu.models.MealDto;
import com.example.CalorieCalcu.models.Meal;
import com.example.CalorieCalcu.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping
    public ResponseEntity<MealDto> logMeal(@RequestBody MealDto mealDto) {
        String username = getCurrentUsername();
        Meal newMeal = mealService.logMeal(username, mealDto);
        return ResponseEntity.ok(convertToDto(newMeal));
    }

    @GetMapping("/daily/{date}")
    public ResponseEntity<List<MealDto>> getDailyMeals(@PathVariable String date) {
        String username = getCurrentUsername();
        LocalDate mealDate = LocalDate.parse(date);
        List<Meal> meals = mealService.getMealsForDate(username, mealDate);
        List<MealDto> mealDtos = meals.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(mealDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable Long id, @RequestBody MealDto mealDto) {
        String username = getCurrentUsername();
        Meal updatedMeal = mealService.updateMeal(username, id, mealDto);
        return ResponseEntity.ok(convertToDto(updatedMeal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        String username = getCurrentUsername();
        mealService.deleteMeal(username, id);
        return ResponseEntity.noContent().build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new IllegalStateException("User not authenticated");
    }

    private MealDto convertToDto(Meal meal) {
        MealDto dto = new MealDto();
        dto.setId   (meal.getId());
        dto.setMealName(meal.getMealName());
        dto.setMealDate(meal.getMealDate());
        dto.setTotalCalories(meal.getMealFoods().stream()
                .mapToDouble(mf -> mf.getCalculatedCalories()).sum());
        dto.setFoodItems(meal.getMealFoods().stream().map(mf -> {
            MealDto.MealFoodItemDto itemDto = new MealDto.MealFoodItemDto();
            itemDto.setFoodId(mf.getFood().getId());
            itemDto.setFoodName(mf.getFood().getName());
            itemDto.setQuantity(mf.getQuantity());
            itemDto.setCalculatedCalories(mf.getCalculatedCalories());
            return itemDto;
        }).collect(Collectors.toList()));
        return dto;
    }
}