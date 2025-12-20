package com.recipeapp.recipemanagementsystem.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "steps")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer stepOrder;

    @Column(nullable = false)
    private Integer duration;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StepIngredient> stepIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    // Default constructor
    public Step() {}

    // Constructor with main parameters
    public Step(String title, String description, Integer stepOrder, Integer duration) {
        this.title = title;
        this.description = description;
        this.stepOrder = stepOrder;
        this.duration = duration;
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

    public Recipe getRecipe() {
        return recipe;
    }

    public List<StepIngredient> getStepIngredients() {
        return stepIngredients;
    }

    public List<Photo> getPhotos() {
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

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setStepIngredients(List<StepIngredient> stepIngredients) {
        this.stepIngredients = stepIngredients;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    // toString method
    @Override
    public String toString() {
        return "RecipeStep{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", stepOrder=" + stepOrder +
                ", duration=" + duration +
                '}';
    }
}