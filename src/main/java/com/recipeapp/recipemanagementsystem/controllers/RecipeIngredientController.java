package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.services.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    @Autowired
    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @PostMapping
    public ResponseEntity<RecipeIngredientDto> createRecipeIngredient(@RequestBody RecipeIngredientDto recipeIngredientDto) {
        RecipeIngredientDto createdRecipeIngredient = recipeIngredientService.createRecipeIngredient(recipeIngredientDto);
        return ResponseEntity.ok(createdRecipeIngredient);
    }

    @PutMapping
    public ResponseEntity<RecipeIngredientDto> updateRecipeIngredient(@RequestParam Long id,
                                                                      @RequestBody RecipeIngredientDto recipeIngredientDto) {
        RecipeIngredientDto updatedRecipeIngredient = recipeIngredientService.updateRecipeIngredient(id, recipeIngredientDto);
        return ResponseEntity.ok(updatedRecipeIngredient);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRecipeIngredient(@RequestParam Long id) {
        recipeIngredientService.deleteRecipeIngredient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<RecipeIngredientDto> getRecipeIngredientById(@RequestParam Long id) {
        RecipeIngredientDto recipeIngredient = recipeIngredientService.findById(id);
        return ResponseEntity.ok(recipeIngredient);
    }

    @GetMapping("/by-recipe")
    public ResponseEntity<List<RecipeIngredientDto>> getRecipeIngredientsByRecipe(@RequestParam Long recipeId) {
        List<RecipeIngredientDto> recipeIngredients = recipeIngredientService.findByRecipeId(recipeId);
        return ResponseEntity.ok(recipeIngredients);
    }

    @GetMapping("/by-ingredient")
    public ResponseEntity<List<RecipeIngredientDto>> getRecipeIngredientsByIngredient(@RequestParam Long ingredientId) {
        List<RecipeIngredientDto> recipeIngredients = recipeIngredientService.findByIngredientId(ingredientId);
        return ResponseEntity.ok(recipeIngredients);
    }
}