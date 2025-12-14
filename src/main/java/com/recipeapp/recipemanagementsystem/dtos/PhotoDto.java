package com.recipeapp.recipemanagementsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PhotoDto {

    private Long id;
    @NotBlank(message = "Το όνομα αρχείου είναι υποχρεωτικό")
    @Size(min = 1, max = 200, message = "Το όνομα αρχείου πρέπει να είναι μεταξύ 1 και 200 χαρακτήρων")
    private String fileName;
    @NotBlank(message = "Ο τύπος MIME είναι υποχρεωτικός")
    @Pattern(regexp = "^image/(jpeg|jpg|png|gif|bmp|webp)$",
            message = "Ο τύπος MIME πρέπει να είναι έγκυρη μορφή εικόνας")
    private String mimeType;
    @NotNull(message = "Τα δεδομένα εικόνας είναι υποχρεωτικά")
    @Size(min = 1, max = 52428800, message = "Τα δεδομένα εικόνας πρέπει να είναι μεταξύ 1 byte και 50MB")
    private byte[] imageData;
    @Size(max = 200, message = "Η περιγραφή δεν μπορεί να υπερβαίνει τους 200 χαρακτήρες")
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
