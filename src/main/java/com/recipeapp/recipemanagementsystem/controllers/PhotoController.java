package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.PhotoDto;
import com.recipeapp.recipemanagementsystem.services.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
@Tag(name = "Photos", description = "Photos API")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/recipe/upload")
    @Operation(summary = "Upload a photo for recipe")
    public ResponseEntity<PhotoDto> uploadPhotoForRecipe(@Parameter(description = "Recipe ID") @RequestParam Long recipeId,
                                                         @Parameter(description = "Description") @RequestParam String description,
                                                         @Parameter(description = "Photo file itself") @RequestParam("photo") MultipartFile file) throws IOException {
        PhotoDto savedPhoto = photoService.uploadPhotoForRecipe(recipeId, file, description);
        return ResponseEntity.ok(savedPhoto);
    }

    @PostMapping("/step/upload")
    @Operation(summary = "Upload a photo for step")
    public ResponseEntity<PhotoDto> uploadPhotoForStep(@Parameter(description = "Step ID") @RequestParam Long stepId,
                                                       @Parameter(description = "Description") @RequestParam String description,
                                                       @Parameter(description = "Photo file itself") @RequestParam("photo") MultipartFile file) throws IOException {
        PhotoDto savedPhoto = photoService.uploadPhotoForStep(stepId, file, description);
        return ResponseEntity.ok(savedPhoto);
    }

    @GetMapping
    @Operation(summary = "Get photo by id")
    public ResponseEntity<PhotoDto> getPhotoById(@Parameter(description = "Photo ID") @RequestParam Long id) {
        PhotoDto photo = photoService.getPhotoById(id);
        return ResponseEntity.ok(photo);
    }

    @GetMapping("/by-recipe")
    @Operation(summary = "Get all photos of a recipe")
    public ResponseEntity<List<PhotoDto>> getPhotosByRecipe(@Parameter(description = "RecipeID") @RequestParam Long recipeId) {
        List<PhotoDto> photos = photoService.getPhotosByRecipeId(recipeId);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/by-step")
    @Operation(summary = "Get all photos of a step")
    public ResponseEntity<List<PhotoDto>> getPhotosByStep(@Parameter(description = "StepID") @RequestParam Long stepId) {
        List<PhotoDto> photos = photoService.getPhotosByStepId(stepId);
        return ResponseEntity.ok(photos);
    }

    @DeleteMapping
    @Operation(summary = "Delete a photo by id")
    public ResponseEntity<Void> deletePhoto(@Parameter(description = "PhotoID") @RequestParam Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image")
    @Operation(summary = "Get the actual image displayed")
    public ResponseEntity<byte[]> getPhotoImage(@Parameter(description = "PhotoID") @RequestParam Long id) {
        PhotoDto photo = photoService.getPhotoById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getMimeType()))
                .body(photo.getImageData());
    }
}
