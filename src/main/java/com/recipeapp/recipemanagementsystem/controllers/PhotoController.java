package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.PhotoDto;
import com.recipeapp.recipemanagementsystem.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/recipe/upload")
    public ResponseEntity<PhotoDto> uploadPhotoForRecipe(@RequestParam Long recipeId,
                                                         @RequestParam String description,
                                                         @RequestParam("photo") MultipartFile file) throws IOException {
        PhotoDto savedPhoto = photoService.uploadPhotoForRecipe(recipeId, file, description);
        return ResponseEntity.ok(savedPhoto);
    }

    @PostMapping("/step/upload")
    public ResponseEntity<PhotoDto> uploadPhotoForStep(@RequestParam Long stepId,
                                                       @RequestParam String description,
                                                       @RequestParam("photo") MultipartFile file) throws IOException {
        PhotoDto savedPhoto = photoService.uploadPhotoForStep(stepId, file, description);
        return ResponseEntity.ok(savedPhoto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoDto> getPhotoById(@PathVariable Long id) {
        PhotoDto photo = photoService.getPhotoById(id);
        return ResponseEntity.ok(photo);
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<PhotoDto>> getPhotosByRecipe(@PathVariable Long recipeId) {
        List<PhotoDto> photos = photoService.getPhotosByRecipeId(recipeId);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/step/{stepId}")
    public ResponseEntity<List<PhotoDto>> getPhotosByStep(@PathVariable Long stepId) {
        List<PhotoDto> photos = photoService.getPhotosByStepId(stepId);
        return ResponseEntity.ok(photos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getPhotoImage(@PathVariable Long id) {
        PhotoDto photo = photoService.getPhotoById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getMimeType()))
                .body(photo.getImageData());
    }
}
