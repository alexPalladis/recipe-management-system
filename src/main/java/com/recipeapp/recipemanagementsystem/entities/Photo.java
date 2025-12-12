package com.recipeapp.recipemanagementsystem.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String fileName;

    @Column(nullable = false, length = 100)
    private String mimeType;

    @Lob
    @Column(nullable = false)
    private byte[] imageData;

    @Column(length = 500)
    private String description;

    // Relationships - Μια φωτογραφία μπορεί να ανήκει είτε σε Recipe είτε σε RecipeStep
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    private Step step;

    public Photo() {}

    public Photo(String fileName, String mimeType, byte[] imageData, Recipe recipe) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.imageData = imageData;
        this.recipe = recipe;
    }


    // Getters
    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getDescription() {
        return description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Step getStep() {
        return step;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    // toString method (χωρίς το imageData γιατί είναι μεγάλο)
    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}