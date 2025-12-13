package com.recipeapp.recipemanagementsystem.dtos;

import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;

public class RecipeIngredientDto {

    private Long id;
    private Double quantity;
    private MeasurementUnit measurementUnit;
    private Long recipeId;
    private Long ingredientId;

    public RecipeIngredientDto() {}

    // Getters
    public Long getId() { return id; }
    public Double getQuantity() { return quantity; }
    public MeasurementUnit getMeasurementUnit() { return measurementUnit; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setMeasurementUnit(MeasurementUnit measurementUnit) { this.measurementUnit = measurementUnit; }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
