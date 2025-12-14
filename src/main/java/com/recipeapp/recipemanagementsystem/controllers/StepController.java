package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import com.recipeapp.recipemanagementsystem.services.StepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
@Tag(name = "Steps", description = "Steps API")
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping
    @Operation(summary = "Create a new step")
    @ApiResponse(responseCode = "200", description = "Step created successfully")
    public ResponseEntity<StepDto> createStep(@Valid @RequestBody StepDto stepDto) {
        StepDto createdStep = stepService.createStep(stepDto);
        return ResponseEntity.ok(createdStep);
    }

    @PutMapping
    @Operation(summary = "Update an existing step")
    public ResponseEntity<StepDto> updateStep(@Parameter(description = "Step ID") @RequestParam Long id,
                                              @Valid @RequestBody StepDto stepDto) {
        StepDto updatedStep = stepService.updateStep(id, stepDto);
        return ResponseEntity.ok(updatedStep);
    }

    @DeleteMapping
    @Operation(summary = "Delete an ingredient")
    public ResponseEntity<Void> deleteStep(@Parameter(description = "Step ID") @RequestParam Long id) {
        stepService.deleteStep(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get step by ID")
    public ResponseEntity<StepDto> getStepById(@Parameter(description = "Step ID") @RequestParam Long id) {
        StepDto step = stepService.findById(id);
        return ResponseEntity.ok(step);
    }

    @GetMapping("/by-recipe")
    @Operation(summary = "Get all steps by recipeId")
    public ResponseEntity<List<StepDto>> getStepsByRecipe(@Parameter(description = "Recipe ID") @RequestParam Long recipeId) {
        List<StepDto> steps = stepService.findByRecipeId(recipeId);
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/by-recipe-and-order")
    @Operation(summary = "Get step by recipeId and stepOrder")
    public ResponseEntity<StepDto> getStepByRecipeAndOrder(@Parameter(description = "Recipe ID") @RequestParam Long recipeId,
                                                           @Parameter(description = "stepOrder") @RequestParam Integer stepOrder) {
        StepDto step = stepService.findByRecipeIdAndStepOrder(recipeId, stepOrder);
        return ResponseEntity.ok(step);
    }

}
