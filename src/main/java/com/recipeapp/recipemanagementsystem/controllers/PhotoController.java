package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.PhotoDto;
import com.recipeapp.recipemanagementsystem.services.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/photos")
@Tag(name = "Photos", description = "Photos API")
@Validated
public class PhotoController {

    private final PhotoService photoService;
    private static final long MAX_FILE_SIZE = 10485760; // 10MB
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp", "image/webp"
    );

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/recipe/upload")
    @Operation(summary = "Upload a photo for recipe")
    public ResponseEntity<PhotoDto> uploadPhotoForRecipe(
            @Parameter(description = "Recipe ID") @RequestParam @NotNull(message = "Το ID της συνταγής είναι υποχρεωτικό") @Positive Long recipeId,
            @Parameter(description = "Description") @RequestParam @Size(max = 500, message = "Η περιγραφή δεν μπορεί να υπερβαίνει τους 500 χαρακτήρες") String description,
            @Parameter(description = "Photo file itself") @RequestParam("photo") @NotNull(message = "Το αρχείο φωτογραφίας είναι υποχρεωτικό") MultipartFile file) throws IOException {

        validatePhotoFile(file);

        PhotoDto savedPhoto = photoService.uploadPhotoForRecipe(recipeId, file, description);
        return ResponseEntity.ok(savedPhoto);
    }

    @PostMapping("/step/upload")
    @Operation(summary = "Upload a photo for step")
    public ResponseEntity<PhotoDto> uploadPhotoForStep(
            @Parameter(description = "Step ID") @RequestParam @NotNull(message = "Το ID του βήματος είναι υποχρεωτικό") @Positive Long stepId,
            @Parameter(description = "Description") @RequestParam @Size(max = 500, message = "Η περιγραφή δεν μπορεί να υπερβαίνει τους 500 χαρακτήρες") String description,
            @Parameter(description = "Photo file itself") @RequestParam("photo") @NotNull(message = "Το αρχείο φωτογραφίας είναι υποχρεωτικό") MultipartFile file) throws IOException {

        validatePhotoFile(file);

        PhotoDto savedPhoto = photoService.uploadPhotoForStep(stepId, file, description);
        return ResponseEntity.ok(savedPhoto);
    }

    @GetMapping
    @Operation(summary = "Get photo by id")
    public ResponseEntity<PhotoDto> getPhotoById(
            @Parameter(description = "Photo ID") @RequestParam @NotNull(message = "Το ID της φωτογραφίας είναι υποχρεωτικό") @Positive Long id) {
        PhotoDto photo = photoService.getPhotoById(id);
        return ResponseEntity.ok(photo);
    }

    @GetMapping("/by-recipe")
    @Operation(summary = "Get all photos of a recipe")
    public ResponseEntity<List<PhotoDto>> getPhotosByRecipe(
            @Parameter(description = "RecipeID") @RequestParam @NotNull(message = "Το ID της συνταγής είναι υποχρεωτικό") @Positive Long recipeId) {
        List<PhotoDto> photos = photoService.getPhotosByRecipeId(recipeId);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/by-step")
    @Operation(summary = "Get all photos of a step")
    public ResponseEntity<List<PhotoDto>> getPhotosByStep(
            @Parameter(description = "StepID") @RequestParam @NotNull(message = "Το ID του βήματος είναι υποχρεωτικό") @Positive Long stepId) {
        List<PhotoDto> photos = photoService.getPhotosByStepId(stepId);
        return ResponseEntity.ok(photos);
    }

    @DeleteMapping
    @Operation(summary = "Delete a photo by id")
    public ResponseEntity<Void> deletePhoto(
            @Parameter(description = "PhotoID") @RequestParam @NotNull(message = "Το ID της φωτογραφίας είναι υποχρεωτικό") @Positive Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image")
    @Operation(summary = "Get the actual image displayed")
    public ResponseEntity<byte[]> getPhotoImage(
            @Parameter(description = "PhotoID") @RequestParam @NotNull(message = "Το ID της φωτογραφίας είναι υποχρεωτικό") @Positive Long id) {
        PhotoDto photo = photoService.getPhotoById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getMimeType()))
                .body(photo.getImageData());
    }

    private void validatePhotoFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Το αρχείο φωτογραφίας είναι υποχρεωτικό");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Το μέγεθος του αρχείου δεν μπορεί να υπερβαίνει τα 10MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Το αρχείο πρέπει να είναι έγκυρη εικόνα (JPEG, PNG, GIF, BMP, WebP)");
        }

        if (file.getSize() == 0) {
            throw new IllegalArgumentException("Το αρχείο δεν μπορεί να είναι κενό");
        }
    }
}
