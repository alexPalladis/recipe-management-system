package com.recipeapp.recipemanagementsystem.dtos;

import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeDto {

    private Long id;
    @NotBlank(message = "Το όνομα της συνταγής είναι υποχρεωτικό")
    @Size(min = 2, max = 30, message = "Το όνομα της συνταγής πρέπει να είναι μεταξύ 2 και 30 χαρακτήρων")
    private String name;
    @NotNull(message = "Ο βαθμός δυσκολίας είναι υποχρεωτικός")
    private Difficulty difficulty;
    @NotNull(message = "Η συνολική διάρκεια είναι υποχρεωτική")
    @Min(value = 1, message = "Η συνολική διάρκεια πρέπει να είναι τουλάχιστον 1 λεπτό")
    @Max(value = 1440, message = "Η συνολική διάρκεια δεν μπορεί να υπερβαίνει τις 24 ώρες (1440 λεπτά)")
    private Integer totalDuration;
    @NotNull(message = "Η κατηγορία συνταγής είναι υποχρεωτική")
    private RecipeCategory category;
    @Size(max = 500, message = "Η περιγραφή δεν μπορεί να υπερβαίνει τους 500 χαρακτήρες")
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<StepDto> steps;
    private List<RecipeIngredientDto> recipeIngredients;
    private List<PhotoDto> photos;

    // Default constructor
    public RecipeDto() {}

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Difficulty getDifficulty() { return difficulty; }
    public Integer getTotalDuration() { return totalDuration; }
    public RecipeCategory getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<StepDto> getSteps() { return steps; }
    public List<RecipeIngredientDto> getRecipeIngredients() { return recipeIngredients; }
    public List<PhotoDto> getPhotos() { return photos; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
    public void setTotalDuration(Integer totalDuration) { this.totalDuration = totalDuration; }
    public void setCategory(RecipeCategory category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setSteps(List<StepDto> steps) { this.steps = steps; }
    public void setRecipeIngredients(List<RecipeIngredientDto> recipeIngredients) { this.recipeIngredients = recipeIngredients; }
    public void setPhotos(List<PhotoDto> photos) { this.photos = photos; }
}
