package com.recipeapp.recipemanagementsystem.services;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.dtos.StepDto;

import java.util.List;

public interface RecipeExecutionService {

    // Recipe execution tracking
    Long startExecution(Long recipeId);
    void markStepCompleted(Long executionId, Long stepId);
    void completeExecution(Long executionId);

    // Progress tracking με Dtos
    Double calculateProgress(Long executionId);
    Integer getCurrentStepOrder(Long executionId);
    List<StepDto> getCompletedSteps(Long executionId);
    List<StepDto> getRemainingSteps(Long executionId);

    // Current execution state
    RecipeDto getCurrentExecutingRecipe(Long executionId);
}
