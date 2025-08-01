// src/main/java/com/example/CalorieCalcu/models/MealFood.java
package com.example.CalorieCalcu.models;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_foods")
public class MealFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    private Double quantity;
    private Double calculatedCalories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
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