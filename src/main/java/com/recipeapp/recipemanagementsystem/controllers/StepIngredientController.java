package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import com.recipeapp.recipemanagementsystem.services.StepIngredientService;
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
@RequestMapping("/api/step-ingredients")
@Tag(name = "StepIngredients", description = "StepIngredients API")
public class StepIngredientController {

    private final StepIngredientService stepIngredientService;

    @Autowired
    public StepIngredientController(StepIngredientService stepIngredientService) {
        this.stepIngredientService = stepIngredientService;
    }

    @PostMapping
    @Operation(summary = "Create a new stepIngredient")
    @ApiResponse(responseCode = "200", description = "StepIngredient created successfully")
    public ResponseEntity<StepIngredientDto> createStepIngredient(@Valid @RequestBody StepIngredientDto stepIngredientDto) {
        StepIngredientDto createdStepIngredient = stepIngredientService.createStepIngredient(stepIngredientDto);
        return ResponseEntity.ok(createdStepIngredient);
    }

    @PutMapping
    @Operation(summary = "Update an existing stepIngredient")
    public ResponseEntity<StepIngredientDto> updateStepIngredient(@Parameter(description = "StepIngredient ID") @RequestParam Long id,
                                                                  @Valid @RequestBody StepIngredientDto stepIngredientDto) {
        StepIngredientDto updatedStepIngredient = stepIngredientService.updateStepIngredient(id, stepIngredientDto);
        return ResponseEntity.ok(updatedStepIngredient);
    }

    @DeleteMapping
    @Operation(summary = "Delete a stepIngredient")
    public ResponseEntity<Void> deleteStepIngredient(@Parameter(description = "StepIngredient ID") @RequestParam Long id) {
        stepIngredientService.deleteStepIngredient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get stepIngredient by ID")
    public ResponseEntity<StepIngredientDto> getStepIngredientById(@Parameter(description = "StepIngredient ID") @RequestParam Long id) {
        StepIngredientDto stepIngredient = stepIngredientService.findById(id);
        return ResponseEntity.ok(stepIngredient);
    }

    @GetMapping("/by-step")
    @Operation(summary = "Get stepIngredient by stepId")
    public ResponseEntity<List<StepIngredientDto>> getStepIngredientsByStep(@Parameter(description = "Step ID") @RequestParam Long stepId) {
        List<StepIngredientDto> stepIngredients = stepIngredientService.findByStepId(stepId);
        return ResponseEntity.ok(stepIngredients);
    }

    @GetMapping("/by-ingredient")
    @Operation(summary = "Get stepIngredient by ingredientId")
    public ResponseEntity<List<StepIngredientDto>> getStepIngredientsByIngredient(@Parameter(description = "Ingredient ID") @RequestParam Long ingredientId) {
        List<StepIngredientDto> stepIngredients = stepIngredientService.findByIngredientId(ingredientId);
        return ResponseEntity.ok(stepIngredients);
    }
}
