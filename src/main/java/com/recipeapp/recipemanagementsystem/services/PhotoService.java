package com.recipeapp.recipemanagementsystem.services;

import com.recipeapp.recipemanagementsystem.dtos.PhotoDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {

    PhotoDto uploadPhotoForRecipe(Long recipeId, MultipartFile file, String description) throws IOException;

    PhotoDto uploadPhotoForStep(Long stepId, MultipartFile file, String description) throws IOException;

    PhotoDto getPhotoById(Long id);

    List<PhotoDto> getPhotosByRecipeId(Long recipeId);

    List<PhotoDto> getPhotosByStepId(Long stepId);

    void deletePhoto(Long id);

    boolean existsByRecipeId(Long recipeId);

    boolean existsByStepId(Long stepId);
}
