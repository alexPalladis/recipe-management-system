package com.recipeapp.recipemanagementsystem.dtos;

import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;

public class StepIngredientDto {

    private Long id;
    private Double quantity;
    private MeasurementUnit measurementUnit;
    private Long stepId;
    private Long ingredientId;

    public StepIngredientDto() {}

    // Getters
    public Long getId() { return id; }
    public Double getQuantity() { return quantity; }
    public MeasurementUnit getMeasurementUnit() { return measurementUnit; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setMeasurementUnit(MeasurementUnit measurementUnit) { this.measurementUnit = measurementUnit; }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
