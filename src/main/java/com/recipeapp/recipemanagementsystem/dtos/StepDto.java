package com.recipeapp.recipemanagementsystem.dtos;

import jakarta.validation.constraints.*;

import java.util.List;

public class StepDto {

    private Long id;
    @NotBlank(message = "Ο τίτλος του βήματος είναι υποχρεωτικός")
    @Size(min = 2, max = 100, message = "Ο τίτλος του βήματος πρέπει να είναι μεταξύ 2 και 100 χαρακτήρων")
    private String title;
    @NotBlank(message = "Η περιγραφή του βήματος είναι υποχρεωτική")
    @Size(min = 5, max = 500, message = "Η περιγραφή του βήματος πρέπει να είναι μεταξύ 5 και 500 χαρακτήρων")
    private String description;
    @NotNull(message = "Η σειρά του βήματος είναι υποχρεωτική")
    @Min(value = 1, message = "Η σειρά του βήματος πρέπει να είναι τουλάχιστον 1")
    private Integer stepOrder;
    @NotNull(message = "Η διάρκεια του βήματος είναι υποχρεωτική")
    @Min(value = 1, message = "Η διάρκεια του βήματος πρέπει να είναι τουλάχιστον 1 λεπτό")
    @Max(value = 480, message = "Η διάρκεια του βήματος δεν μπορεί να υπερβαίνει τις 8 ώρες (480 λεπτά)")
    private Integer duration;
    @NotNull(message = "Το ID της συνταγής είναι υποχρεωτικό")
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
