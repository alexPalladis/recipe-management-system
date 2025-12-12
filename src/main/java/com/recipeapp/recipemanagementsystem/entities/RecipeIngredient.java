package com.recipeapp.recipemanagementsystem.entities;

import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

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
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public RecipeIngredient() {}

    public RecipeIngredient(Double quantity, MeasurementUnit measurementUnit, Recipe recipe, Ingredient ingredient) {
        this.quantity = quantity;
        this.measurementUnit = measurementUnit;
        this.recipe = recipe;
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

    public Recipe getRecipe() {
        return recipe;
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

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    // toString method
    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurementUnit=" + measurementUnit +
                ", ingredient=" + (ingredient != null ? ingredient.getName() : "null") +
                '}';
    }
}