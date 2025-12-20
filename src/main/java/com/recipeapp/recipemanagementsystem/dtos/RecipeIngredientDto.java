package com.recipeapp.recipemanagementsystem.dtos;

import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class RecipeIngredientDto {

    private Long id;
    @NotNull(message = "Η ποσότητα είναι υποχρεωτική")
    @DecimalMin(value = "0.01", message = "Η ποσότητα πρέπει να είναι μεγαλύτερη από 0")
    @DecimalMax(value = "10000.0", message = "Η ποσότητα δεν μπορεί να υπερβαίνει το 10000")
    private Double quantity;
    @NotNull(message = "Η μονάδα μέτρησης είναι υποχρεωτική")
    private MeasurementUnit measurementUnit;
    @NotNull(message = "Το ID της συνταγής είναι υποχρεωτικό")
    private Long recipeId;
    @NotNull(message = "Το ID του υλικού είναι υποχρεωτικό")
    private Long ingredientId;
    private String name;

    public RecipeIngredientDto() {}

    // Getters
    public Long getId() { return id; }
    public Double getQuantity() { return quantity; }
    public MeasurementUnit getMeasurementUnit() { return measurementUnit; }
    public String getName() { return name; }
    //EKANA PROSTHIKI EDW

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    public void setMeasurementUnit(MeasurementUnit measurementUnit) { this.measurementUnit = measurementUnit; }
    public void setName(String name) { this.name = name; }

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
