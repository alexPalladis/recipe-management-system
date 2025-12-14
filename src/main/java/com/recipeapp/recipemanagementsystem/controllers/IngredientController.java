package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.IngredientDto;
import com.recipeapp.recipemanagementsystem.services.IngredientService;
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
@RequestMapping("/api/ingredients")
@Tag(name = "Ingredients", description = "Ingredient API")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @Operation(summary = "Create a new ingredient")
    @ApiResponse(responseCode = "200", description = "Ingredient created successfully")
    public ResponseEntity<IngredientDto> createIngredient(@Valid @RequestBody IngredientDto ingredientDto) {
        IngredientDto createdIngredient = ingredientService.createIngredient(ingredientDto);
        return ResponseEntity.ok(createdIngredient);
    }

    @PutMapping
    @Operation(summary = "Update an existing ingredient")
    public ResponseEntity<IngredientDto> updateIngredient(@Parameter(description = "Ingredient ID") @RequestParam Long id,
                                                          @Valid @RequestBody IngredientDto ingredientDto) {
        IngredientDto updatedIngredient = ingredientService.updateIngredient(id, ingredientDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping
    @Operation(summary = "Delete an ingredient")
    public ResponseEntity<Void> deleteIngredient(@Parameter(description = "Ingredient ID") @RequestParam Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get ingredient by ID")
    public ResponseEntity<IngredientDto> getIngredientById(@Parameter(description = "Ingredient ID") @RequestParam Long id) {
        IngredientDto ingredient = ingredientService.findById(id);
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all ingredients")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<IngredientDto> ingredients = ingredientService.findAll();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/search")
    @Operation(summary = "Search ingredients by name")
    public ResponseEntity<List<IngredientDto>> searchIngredientsByName( @Parameter(description = "Ingredient name to search") @RequestParam String name) {
        List<IngredientDto> ingredients = ingredientService.searchByName(name);
        return ResponseEntity.ok(ingredients);
    }
}
