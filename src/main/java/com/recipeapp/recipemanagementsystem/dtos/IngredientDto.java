package com.recipeapp.recipemanagementsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IngredientDto {

    private Long id;
    @NotBlank(message = "Το όνομα του υλικού είναι υποχρεωτικό")
    @Size(min = 2, max = 40, message = "Το όνομα του υλικού πρέπει να είναι μεταξύ 2 και 40 χαρακτήρων")
    private String name;
    @Size(max = 200, message = "Η περιγραφή δεν μπορεί να υπερβαίνει τους 200 χαρακτήρες")
    private String description;

    public IngredientDto() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
