package com.recipeapp.recipemanagementsystem.services;

import com.recipeapp.recipemanagementsystem.dtos.IngredientDto;

import java.util.List;

public interface IngredientService {

    IngredientDto createIngredient(IngredientDto ingredientDto);
    IngredientDto updateIngredient(Long id, IngredientDto ingredientDto);
    void deleteIngredient(Long id);
    IngredientDto findById(Long id);
    List<IngredientDto> findAll();

    List<IngredientDto> searchByName(String name);
}