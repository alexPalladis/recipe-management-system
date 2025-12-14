package com.recipeapp.recipemanagementsystem.dtos;

import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class StepIngredientDto {

    private Long id;
    @NotNull(message = "Η ποσότητα είναι υποχρεωτική")
    @DecimalMin(value = "0.01", message = "Η ποσότητα πρέπει να είναι μεγαλύτερη από 0")
    @DecimalMax(value = "10000.0", message = "Η ποσότητα δεν μπορεί να υπερβαίνει το 10000")
    private Double quantity;
    @NotNull(message = "Η μονάδα μέτρησης είναι υποχρεωτική")
    private MeasurementUnit measurementUnit;
    @NotNull(message = "Το ID του βήματος είναι υποχρεωτικό")
    private Long stepId;
    @NotNull(message = "Το ID του υλικού είναι υποχρεωτικό")
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
