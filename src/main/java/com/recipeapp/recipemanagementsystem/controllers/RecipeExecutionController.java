package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.services.RecipeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-execution")
public class RecipeExecutionController {

    private final RecipeExecutionService recipeExecutionService;

    @Autowired
    public RecipeExecutionController(RecipeExecutionService recipeExecutionService) {
        this.recipeExecutionService = recipeExecutionService;
    }

    @PostMapping("/start")
    public ResponseEntity<Long> startRecipeExecution(@RequestParam Long recipeId) {
        Long executionId = recipeExecutionService.startExecution(recipeId);
        return ResponseEntity.ok(executionId);
    }

    @PutMapping("/complete-step")
    public ResponseEntity<Void> markStepCompleted(@RequestParam Long executionId,
                                                  @RequestParam Long stepId) {
        recipeExecutionService.markStepCompleted(executionId, stepId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/complete")
    public ResponseEntity<Void> completeRecipeExecution(@RequestParam Long executionId) {
        recipeExecutionService.completeExecution(executionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/progress")
    public ResponseEntity<Double> getExecutionProgress(@RequestParam Long executionId) {
        Double progress = recipeExecutionService.calculateProgress(executionId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/current-step")
    public ResponseEntity<Integer> getCurrentStepOrder(@RequestParam Long executionId) {
        Integer currentStep = recipeExecutionService.getCurrentStepOrder(executionId);
        return ResponseEntity.ok(currentStep);
    }

    @GetMapping("/completed-steps")
    public ResponseEntity<List<StepDto>> getCompletedSteps(@RequestParam Long executionId) {
        List<StepDto> completedSteps = recipeExecutionService.getCompletedSteps(executionId);
        return ResponseEntity.ok(completedSteps);
    }

    @GetMapping("/remaining-steps")
    public ResponseEntity<List<StepDto>> getRemainingSteps(@RequestParam Long executionId) {
        List<StepDto> remainingSteps = recipeExecutionService.getRemainingSteps(executionId);
        return ResponseEntity.ok(remainingSteps);
    }

    @GetMapping("/current-recipe")
    public ResponseEntity<RecipeDto> getCurrentExecutingRecipe(@RequestParam Long executionId) {
        RecipeDto recipe = recipeExecutionService.getCurrentExecutingRecipe(executionId);
        return ResponseEntity.ok(recipe);
    }
}
