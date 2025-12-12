package com.recipeapp.recipemanagementsystem.dtos;

import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;
import java.time.LocalDateTime;
import java.util.List;

public class RecipeDTO {

    private Long id;
    private String name;
    private Difficulty difficulty;
    private Integer totalDuration;
    private RecipeCategory category;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<StepDTO> steps;
    private List<RecipeIngredientDTO> recipeIngredients;
    private List<PhotoDTO> photos;

    // Default constructor
    public RecipeDTO() {}

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Difficulty getDifficulty() { return difficulty; }
    public Integer getTotalDuration() { return totalDuration; }
    public RecipeCategory getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<StepDTO> getSteps() { return steps; }
    public List<RecipeIngredientDTO> getRecipeIngredients() { return recipeIngredients; }
    public List<PhotoDTO> getPhotos() { return photos; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
    public void setTotalDuration(Integer totalDuration) { this.totalDuration = totalDuration; }
    public void setCategory(RecipeCategory category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setSteps(List<StepDTO> steps) { this.steps = steps; }
    public void setRecipeIngredients(List<RecipeIngredientDTO> recipeIngredients) { this.recipeIngredients = recipeIngredients; }
    public void setPhotos(List<PhotoDTO> photos) { this.photos = photos; }
}
