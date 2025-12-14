package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.services.RecipeIngredientService;
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
@RequestMapping("/api/recipe-ingredients")
@Tag(name = "RecipeIngredients", description = "RecipeIngredients API")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    @Autowired
    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @PostMapping
    @Operation(summary = "Create a new recipeIngredient")
    @ApiResponse(responseCode = "200", description = "RecipeIngredient created successfully")
    public ResponseEntity<RecipeIngredientDto> createRecipeIngredient(@Valid @RequestBody RecipeIngredientDto recipeIngredientDto) {
        RecipeIngredientDto createdRecipeIngredient = recipeIngredientService.createRecipeIngredient(recipeIngredientDto);
        return ResponseEntity.ok(createdRecipeIngredient);
    }

    @PutMapping
    @Operation(summary = "Update an existing recipeIngredient")
    public ResponseEntity<RecipeIngredientDto> updateRecipeIngredient(@Parameter(description = "RecipeIngredient ID") @RequestParam Long id,
                                                                      @Valid @RequestBody RecipeIngredientDto recipeIngredientDto) {
        RecipeIngredientDto updatedRecipeIngredient = recipeIngredientService.updateRecipeIngredient(id, recipeIngredientDto);
        return ResponseEntity.ok(updatedRecipeIngredient);
    }

    @DeleteMapping
    @Operation(summary = "Delete a recipeIngredient")
    public ResponseEntity<Void> deleteRecipeIngredient(@Parameter(description = "RecipeIngredient ID") @RequestParam Long id) {
        recipeIngredientService.deleteRecipeIngredient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get recipeIngredient by ID")
    public ResponseEntity<RecipeIngredientDto> getRecipeIngredientById(@Parameter(description = "RecipeIngredient ID") @RequestParam Long id) {
        RecipeIngredientDto recipeIngredient = recipeIngredientService.findById(id);
        return ResponseEntity.ok(recipeIngredient);
    }

    @GetMapping("/by-recipe")
    @Operation(summary = "Get stepIngredient by recipeId")
    public ResponseEntity<List<RecipeIngredientDto>> getRecipeIngredientsByRecipe(@Parameter(description = "Recipe ID") @RequestParam Long recipeId) {
        List<RecipeIngredientDto> recipeIngredients = recipeIngredientService.findByRecipeId(recipeId);
        return ResponseEntity.ok(recipeIngredients);
    }

    @GetMapping("/by-ingredient")
    @Operation(summary = "Get stepIngredient by ingredientId")
    public ResponseEntity<List<RecipeIngredientDto>> getRecipeIngredientsByIngredient(@Parameter(description = "Ingredient ID") @RequestParam Long ingredientId) {
        List<RecipeIngredientDto> recipeIngredients = recipeIngredientService.findByIngredientId(ingredientId);
        return ResponseEntity.ok(recipeIngredients);
    }
}