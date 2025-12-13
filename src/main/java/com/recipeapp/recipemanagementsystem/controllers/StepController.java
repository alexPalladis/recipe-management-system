package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
public class StepController {

    private final StepService stepService;

    @Autowired
    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping
    public ResponseEntity<StepDto> createStep(@RequestBody StepDto stepDto) {
        StepDto createdStep = stepService.createStep(stepDto);
        return ResponseEntity.ok(createdStep);
    }

    @PutMapping
    public ResponseEntity<StepDto> updateStep(@RequestParam Long id,
                                              @RequestBody StepDto stepDto) {
        StepDto updatedStep = stepService.updateStep(id, stepDto);
        return ResponseEntity.ok(updatedStep);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStep(@RequestParam Long id) {
        stepService.deleteStep(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<StepDto> getStepById(@RequestParam Long id) {
        StepDto step = stepService.findById(id);
        return ResponseEntity.ok(step);
    }

    @GetMapping("/by-recipe")
    public ResponseEntity<List<StepDto>> getStepsByRecipe(@RequestParam Long recipeId) {
        List<StepDto> steps = stepService.findByRecipeId(recipeId);
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/by-recipe-and-order")
    public ResponseEntity<StepDto> getStepByRecipeAndOrder(@RequestParam Long recipeId,
                                                           @RequestParam Integer stepOrder) {
        StepDto step = stepService.findByRecipeIdAndStepOrder(recipeId, stepOrder);
        return ResponseEntity.ok(step);
    }
}
