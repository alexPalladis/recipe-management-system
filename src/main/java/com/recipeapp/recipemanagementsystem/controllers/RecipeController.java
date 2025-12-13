package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;
import com.recipeapp.recipemanagementsystem.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto createdRecipe = recipeService.createRecipe(recipeDto);
        return ResponseEntity.ok(createdRecipe);
    }

    @PutMapping
    public ResponseEntity<RecipeDto> updateRecipe(@RequestParam Long id,
                                                  @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRecipe(@RequestParam Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<RecipeDto> getRecipeById(@RequestParam Long id) {
        RecipeDto recipe = recipeService.findById(id);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.findAll();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> searchRecipesByName(@RequestParam String name) {
        List<RecipeDto> recipes = recipeService.searchByName(name);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<RecipeDto>> getRecipesByCategory(@RequestParam RecipeCategory category) {
        List<RecipeDto> recipes = recipeService.findByCategory(category);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/by-difficulty")
    public ResponseEntity<List<RecipeDto>> getRecipesByDifficulty(@RequestParam Difficulty difficulty) {
        List<RecipeDto> recipes = recipeService.findByDifficulty(difficulty);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/with-steps")
    public ResponseEntity<RecipeDto> getRecipeWithSteps(@RequestParam Long id) {
        RecipeDto recipe = recipeService.findByIdWithSteps(id);
        return ResponseEntity.ok(recipe);
    }

}