// src/main/java/com/example/CalorieCalcu/models/MealDto.java
package com.example.CalorieCalcu.models;

import java.time.LocalDate;
import java.util.List;

public class MealDto {
    private Long id;
    private String mealName;
    private LocalDate mealDate;
    private List<MealFoodItemDto> foodItems;
    private Double totalCalories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public LocalDate getMealDate() {
        return mealDate;
    }

    public void setMealDate(LocalDate mealDate) {
        this.mealDate = mealDate;
    }

    public List<MealFoodItemDto> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<MealFoodItemDto> foodItems) {
        this.foodItems = foodItems;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public static class MealFoodItemDto {
        private Long foodId;
        private String foodName;
        private Double quantity;
        private Double calculatedCalories;

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public Double getCalculatedCalories() {
            return calculatedCalories;
        }

        public void setCalculatedCalories(Double calculatedCalories) {
            this.calculatedCalories = calculatedCalories;
        }
    }
}