package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.services.RecipeExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-execution")
@Tag(name = "Recipe Execution", description = "Execution API")
public class RecipeExecutionController {

    private final RecipeExecutionService recipeExecutionService;

    @Autowired
    public RecipeExecutionController(RecipeExecutionService recipeExecutionService) {
        this.recipeExecutionService = recipeExecutionService;
    }

    @PostMapping("/start")
    @Operation(summary = "Start a recipe's execution")
    public ResponseEntity<Long> startRecipeExecution(@Parameter(description = "Recipe ID") @RequestParam Long recipeId) {
        Long executionId = recipeExecutionService.startExecution(recipeId);
        return ResponseEntity.ok(executionId);
    }

    @PutMapping("/complete-step")
    @Operation(summary = "Complete a step of a recipe execution")
    public ResponseEntity<Void> markStepCompleted(@Parameter(description = "Execution ID") @RequestParam Long executionId,
                                                  @Parameter(description = "Step ID") @RequestParam Long stepId) {
        recipeExecutionService.markStepCompleted(executionId, stepId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/complete")
    @Operation(summary = "Complete a recipe execution")
    public ResponseEntity<Void> completeRecipeExecution(@Parameter(description = "Execution ID") @RequestParam Long executionId) {
        recipeExecutionService.completeExecution(executionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/progress")
    @Operation(summary = "Get the progress of a recipe execution")
    public ResponseEntity<Double> getExecutionProgress(@Parameter(description = "Execution ID") @RequestParam Long executionId) {
        Double progress = recipeExecutionService.calculateProgress(executionId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/current-step")
    @Operation(summary = "Get the current step of a recipe execution")
    public ResponseEntity<Integer> getCurrentStepOrder(@Parameter(description = "Execution ID") @RequestParam Long executionId) {
        Integer currentStep = recipeExecutionService.getCurrentStepOrder(executionId);
        return ResponseEntity.ok(currentStep);
    }

    @GetMapping("/completed-steps")
    @Operation(summary = "Get the completed steps of a recipe execution")
    public ResponseEntity<List<StepDto>> getCompletedSteps(@Parameter(description = "Execution ID") @RequestParam Long executionId) {
        List<StepDto> completedSteps = recipeExecutionService.getCompletedSteps(executionId);
        return ResponseEntity.ok(completedSteps);
    }

    @GetMapping("/remaining-steps")
    @Operation(summary = "Get the remaining steps of a recipe execution")
    public ResponseEntity<List<StepDto>> getRemainingSteps(@Parameter(description = "Execution ID") @RequestParam Long executionId) {
        List<StepDto> remainingSteps = recipeExecutionService.getRemainingSteps(executionId);
        return ResponseEntity.ok(remainingSteps);
    }

    @GetMapping("/current-recipe")
    @Operation(summary = "Get which recipe is currently executing")
    public ResponseEntity<RecipeDto> getCurrentExecutingRecipe(@Parameter(description = "Execution ID")  @RequestParam Long executionId) {
        RecipeDto recipe = recipeExecutionService.getCurrentExecutingRecipe(executionId);
        return ResponseEntity.ok(recipe);
    }
}
