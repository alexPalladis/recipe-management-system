package com.recipeapp.recipemanagementsystem.dtos;

public class PhotoDto {

    private Long id;
    private String fileName;
    private String mimeType;
    private byte[] imageData;
    private String description;

    public PhotoDto() {}

    // Getters
    public Long getId() { return id; }
    public String getFileName() { return fileName; }
    public String getMimeType() { return mimeType; }
    public byte[] getImageData() { return imageData; }
    public String getDescription() { return description; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }
    public void setDescription(String description) { this.description = description; }
}
