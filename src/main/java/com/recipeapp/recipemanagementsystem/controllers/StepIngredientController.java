package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import com.recipeapp.recipemanagementsystem.services.StepIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/step-ingredients")
public class StepIngredientController {

    private final StepIngredientService stepIngredientService;

    @Autowired
    public StepIngredientController(StepIngredientService stepIngredientService) {
        this.stepIngredientService = stepIngredientService;
    }

    @PostMapping
    public ResponseEntity<StepIngredientDto> createStepIngredient(@RequestBody StepIngredientDto stepIngredientDto) {
        StepIngredientDto createdStepIngredient = stepIngredientService.createStepIngredient(stepIngredientDto);
        return ResponseEntity.ok(createdStepIngredient);
    }

    @PutMapping
    public ResponseEntity<StepIngredientDto> updateStepIngredient(@RequestParam Long id,
                                                                  @RequestBody StepIngredientDto stepIngredientDto) {
        StepIngredientDto updatedStepIngredient = stepIngredientService.updateStepIngredient(id, stepIngredientDto);
        return ResponseEntity.ok(updatedStepIngredient);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStepIngredient(@RequestParam Long id) {
        stepIngredientService.deleteStepIngredient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<StepIngredientDto> getStepIngredientById(@RequestParam Long id) {
        StepIngredientDto stepIngredient = stepIngredientService.findById(id);
        return ResponseEntity.ok(stepIngredient);
    }

    @GetMapping("/by-step")
    public ResponseEntity<List<StepIngredientDto>> getStepIngredientsByStep(@RequestParam Long stepId) {
        List<StepIngredientDto> stepIngredients = stepIngredientService.findByStepId(stepId);
        return ResponseEntity.ok(stepIngredients);
    }

    @GetMapping("/by-ingredient")
    public ResponseEntity<List<StepIngredientDto>> getStepIngredientsByIngredient(@RequestParam Long ingredientId) {
        List<StepIngredientDto> stepIngredients = stepIngredientService.findByIngredientId(ingredientId);
        return ResponseEntity.ok(stepIngredients);
    }
}
