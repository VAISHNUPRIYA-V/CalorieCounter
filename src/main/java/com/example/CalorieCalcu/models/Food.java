// src/main/java/com/example/CalorieCalcu/models/Food.java
package com.example.CalorieCalcu.models;

import jakarta.persistence.*;

@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double caloriesPerServing;
    private String servingUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(Double caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }
}