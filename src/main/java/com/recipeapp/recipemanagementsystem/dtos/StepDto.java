package com.recipeapp.recipemanagementsystem.dtos;

import java.util.List;

public class StepDto {

    private Long id;
    private String title;
    private String description;
    private Integer stepOrder;
    private Integer duration;
    private Long recipeId;

    private List<StepIngredientDto> stepIngredients;
    private List<PhotoDto> photos;

    public StepDto() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStepOrder() {
        return stepOrder;
    }

    public Integer getDuration() {
        return duration;
    }

    public List<StepIngredientDto> getStepIngredients() {
        return stepIngredients;
    }

    public List<PhotoDto> getPhotos() {
        return photos;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
