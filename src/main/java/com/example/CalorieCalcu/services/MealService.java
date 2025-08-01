// src/main/java/com/example/CalorieCalcu/services/MealService.java
package com.example.CalorieCalcu.services;

import com.example.CalorieCalcu.models.Food;
import com.example.CalorieCalcu.models.Meal;
import com.example.CalorieCalcu.models.MealDto;
import com.example.CalorieCalcu.models.MealFood;
import com.example.CalorieCalcu.models.RegisterDetails;
import com.example.CalorieCalcu.repository.FoodRepository;
import com.example.CalorieCalcu.repository.MealRepository;
import com.example.CalorieCalcu.repository.RegisterDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RegisterDetailsRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Transactional
    public Meal logMeal(String username, MealDto mealDto) {
        RegisterDetails user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealName(mealDto.getMealName());
        meal.setMealDate(mealDto.getMealDate());

        Set<MealFood> mealFoods = new HashSet<>();
        for (MealDto.MealFoodItemDto itemDto : mealDto.getFoodItems()) {
            Food food = foodRepository.findById(itemDto.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found with id: " + itemDto.getFoodId()));

            MealFood mealFood = new MealFood();
            mealFood.setMeal(meal);
            mealFood.setFood(food);
            mealFood.setQuantity(itemDto.getQuantity());
            mealFood.setCalculatedCalories(food.getCaloriesPerServing() * itemDto.getQuantity() / 100.0);
            mealFoods.add(mealFood);
        }
        meal.setMealFoods(mealFoods);
        return mealRepository.save(meal);
    }

    public List<Meal> getMealsForDate(String username, LocalDate date) {
        RegisterDetails user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return mealRepository.findByUserAndMealDate(user, date);
    }

    @Transactional
    public Meal updateMeal(String username, Long mealId, MealDto mealDto) {
        RegisterDetails user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Meal existingMeal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + mealId));

        if (!existingMeal.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("User not authorized to update this meal.");
        }

        existingMeal.setMealName(mealDto.getMealName());
        existingMeal.setMealDate(mealDto.getMealDate());

        existingMeal.getMealFoods().clear();
        for (MealDto.MealFoodItemDto itemDto : mealDto.getFoodItems()) {
            Food food = foodRepository.findById(itemDto.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found with id: " + itemDto.getFoodId()));

            MealFood mealFood = new MealFood();
            mealFood.setMeal(existingMeal);
            mealFood.setFood(food);
            mealFood.setQuantity(itemDto.getQuantity());
            mealFood.setCalculatedCalories(food.getCaloriesPerServing() * itemDto.getQuantity() / 100.0);
            existingMeal.getMealFoods().add(mealFood);
        }
        return mealRepository.save(existingMeal);
    }

    public void deleteMeal(String username, Long mealId) {
        RegisterDetails user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + mealId));

        if (!meal.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("User not authorized to delete this meal.");
        }
        mealRepository.delete(meal);
    }
}