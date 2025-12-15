package com.recipeapp.recipemanagementsystem.services;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;

import java.util.List;

public interface RecipeService {

    // CRUD με DTOs
    RecipeDto createRecipe(RecipeDto recipeDTO);
    RecipeDto updateRecipe(Long id, RecipeDto recipeDTO);
    void deleteRecipe(Long id);
    RecipeDto findById(Long id);
    List<RecipeDto> findAll();


    // Search functionality
    List<RecipeDto> searchByName(String name);
    List<RecipeDto> findByCategory(RecipeCategory category);
    List<RecipeDto> findByDifficulty(Difficulty difficulty);
    //TODO:
    // List<RecipeDto> findByIngredientName(String ingredientName);

    // Recipe with steps
    RecipeDto findByIdWithSteps(Long id);
}
