package com.recipeapp.recipemanagementsystem.entities;

import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;
import jakarta.persistence.*;

@Entity
@Table(name = "step_ingredients")
public class StepIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementUnit measurementUnit;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public StepIngredient() {}

    public StepIngredient(Double quantity, MeasurementUnit measurementUnit, Step step, Ingredient ingredient) {
        this.quantity = quantity;
        this.measurementUnit = measurementUnit;
        this.step = step;
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public Step getStep() {
        return step;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    // toString method
    @Override
    public String toString() {
        return "StepIngredient{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurementUnit=" + measurementUnit +
                ", ingredient=" + (ingredient != null ? ingredient.getName() : "null") +
                '}';
    }
}
