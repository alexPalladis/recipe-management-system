package com.recipeapp.recipemanagementsystem.services;

import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import java.util.List;

public interface StepIngredientService {

    StepIngredientDto createStepIngredient(StepIngredientDto stepIngredientDto);
    StepIngredientDto updateStepIngredient(Long id, StepIngredientDto stepIngredientDto);
    void deleteStepIngredient(Long id);
    StepIngredientDto findById(Long id);
    List<StepIngredientDto> findByStepId(Long stepId);
    List<StepIngredientDto> findByIngredientId(Long ingredientId);
}