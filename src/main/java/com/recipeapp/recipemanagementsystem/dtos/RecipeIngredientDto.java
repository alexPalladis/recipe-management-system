package com.recipeapp.recipemanagementsystem.dtos;

import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;

public class RecipeIngredientDto {

    private Long id;
    private Double quantity;
    private MeasurementUnit measurementUnit;
    private IngredientDto ingredient;

    public RecipeIngredientDto() {}

    // Getters
    public Long getId() { return id; }
    public Double getQuantity() { return quantity; }
    public MeasurementUnit getMeasurementUnit() { return measurementUnit; }
    public IngredientDto getIngredient() { return ingredient; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setMeasurementUnit(MeasurementUnit measurementUnit) { this.measurementUnit = measurementUnit; }
    public void setIngredient(IngredientDto ingredient) { this.ingredient = ingredient; }
}
