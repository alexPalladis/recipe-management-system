package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.PhotoDto;
import com.recipeapp.recipemanagementsystem.entities.Photo;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import com.recipeapp.recipemanagementsystem.entities.Step;
import com.recipeapp.recipemanagementsystem.mappers.PhotoMapper;
import com.recipeapp.recipemanagementsystem.repositories.PhotoRepository;
import com.recipeapp.recipemanagementsystem.repositories.RecipeRepository;
import com.recipeapp.recipemanagementsystem.repositories.StepRepository;
import com.recipeapp.recipemanagementsystem.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final RecipeRepository recipeRepository;
    private final StepRepository stepRepository;
    private final PhotoMapper photoMapper;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository,
                            RecipeRepository recipeRepository,
                            StepRepository stepRepository,
                            PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.recipeRepository = recipeRepository;
        this.stepRepository = stepRepository;
        this.photoMapper = photoMapper;
    }

    @Override
    public PhotoDto uploadPhotoForRecipe(Long recipeId, MultipartFile file, String description) throws IOException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));

        Photo photo = new Photo();
        photo.setFileName(file.getOriginalFilename());
        photo.setMimeType(file.getContentType());
        photo.setImageData(file.getBytes());
        photo.setDescription(description);
        photo.setRecipe(recipe);

        Photo saved = photoRepository.save(photo);
        return photoMapper.toDTO(saved);
    }

    @Override
    public PhotoDto uploadPhotoForStep(Long stepId, MultipartFile file, String description) throws IOException {
        Step step = stepRepository.findById(stepId)
                .orElseThrow(() -> new RuntimeException("Step not found with id: " + stepId));

        Photo photo = new Photo();
        photo.setFileName(file.getOriginalFilename());
        photo.setMimeType(file.getContentType());
        photo.setImageData(file.getBytes());
        photo.setDescription(description);
        photo.setStep(step);

        Photo saved = photoRepository.save(photo);
        return photoMapper.toDTO(saved);
    }

    @Override
    public PhotoDto getPhotoById(Long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Photo not found with id: " + id));
        return photoMapper.toDTO(photo);
    }

    @Override
    public List<PhotoDto> getPhotosByRecipeId(Long recipeId) {
        List<Photo> photos = photoRepository.findByRecipeId(recipeId);
        return photos.stream()
                .map(photoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhotoDto> getPhotosByStepId(Long stepId) {
        List<Photo> photos = photoRepository.findByStepId(stepId);
        return photos.stream()
                .map(photoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePhoto(Long id) {
        if (!photoRepository.existsById(id)) {
            throw new RuntimeException("Photo not found with id: " + id);
        }
        photoRepository.deleteById(id);
    }

    @Override
    public boolean existsByRecipeId(Long recipeId) {
        return photoRepository.existsByRecipeId(recipeId);
    }

    @Override
    public boolean existsByStepId(Long stepId) {
        return photoRepository.existsByStepId(stepId);
    }
}