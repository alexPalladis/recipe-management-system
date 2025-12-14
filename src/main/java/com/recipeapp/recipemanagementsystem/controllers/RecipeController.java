package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;
import com.recipeapp.recipemanagementsystem.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipes", description = "Recipes API")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @Operation(summary = "Create a new stepIngredient")
    @ApiResponse(responseCode = "200", description = "Recipe created successfully")
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto createdRecipe = recipeService.createRecipe(recipeDto);
        return ResponseEntity.ok(createdRecipe);
    }

    @PutMapping
    @Operation(summary = "Update an existing recipe")
    public ResponseEntity<RecipeDto> updateRecipe(@Parameter(description = "Recipe ID") @RequestParam Long id,
                                                  @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping
    @Operation(summary = "Delete a recipe")
    public ResponseEntity<Void> deleteRecipe(@Parameter(description = "Recipe ID") @RequestParam Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get recipe by ID")
    public ResponseEntity<RecipeDto> getRecipeById(@Parameter(description = "Recipe ID") @RequestParam Long id) {
        RecipeDto recipe = recipeService.findById(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.findAll();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/search")
    @Operation(summary = "Search recipes by name")
    public ResponseEntity<List<RecipeDto>> searchRecipesByName(@Parameter(description = "Recipe name") @RequestParam String name) {
        List<RecipeDto> recipes = recipeService.searchByName(name);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/by-category")
    @Operation(summary = "Search recipes by category")
    public ResponseEntity<List<RecipeDto>> getRecipesByCategory(@Parameter(description = "Recipe category") @RequestParam RecipeCategory category) {
        List<RecipeDto> recipes = recipeService.findByCategory(category);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/by-difficulty")
    @Operation(summary = "Search recipes by difficulty")
    public ResponseEntity<List<RecipeDto>> getRecipesByDifficulty(@Parameter(description = "Recipe difficulty") @RequestParam Difficulty difficulty) {
        List<RecipeDto> recipes = recipeService.findByDifficulty(difficulty);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/with-steps")
    @Operation(summary = "Search recipe with steps")
    public ResponseEntity<RecipeDto> getRecipeWithSteps(@Parameter(description = "Recipe ID") @RequestParam Long id) {
        RecipeDto recipe = recipeService.findByIdWithSteps(id);
        return ResponseEntity.ok(recipe);
    }

}