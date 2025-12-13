package com.recipeapp.recipemanagementsystem.services;

import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import java.util.List;

public interface RecipeIngredientService {

    RecipeIngredientDto createRecipeIngredient(RecipeIngredientDto recipeIngredientDto);
    RecipeIngredientDto updateRecipeIngredient(Long id, RecipeIngredientDto recipeIngredientDto);
    void deleteRecipeIngredient(Long id);
    RecipeIngredientDto findById(Long id);
    List<RecipeIngredientDto> findByRecipeId(Long recipeId);
    List<RecipeIngredientDto> findByIngredientId(Long ingredientId);
}

