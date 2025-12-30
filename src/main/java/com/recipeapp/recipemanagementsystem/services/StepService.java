package com.recipeapp.recipemanagementsystem.services;

import com.recipeapp.recipemanagementsystem.dtos.StepDto;

import java.util.List;

public interface StepService {

    StepDto createStep(StepDto stepDto);
    StepDto updateStep(Long id, StepDto stepDto);
    void deleteStep(Long id);
    StepDto findById(Long id);

    List<StepDto> findByRecipeId(Long recipeId);
    StepDto findByRecipeIdAndStepOrder(Long recipeId, Integer stepOrder);
}