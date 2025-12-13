package com.recipeapp.recipemanagementsystem.controllers;

import com.recipeapp.recipemanagementsystem.dtos.IngredientDto;
import com.recipeapp.recipemanagementsystem.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        IngredientDto createdIngredient = ingredientService.createIngredient(ingredientDto);
        return ResponseEntity.ok(createdIngredient);
    }

    @PutMapping
    public ResponseEntity<IngredientDto> updateIngredient(@RequestParam Long id,
                                                          @RequestBody IngredientDto ingredientDto) {
        IngredientDto updatedIngredient = ingredientService.updateIngredient(id, ingredientDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIngredient(@RequestParam Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<IngredientDto> getIngredientById(@RequestParam Long id) {
        IngredientDto ingredient = ingredientService.findById(id);
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<IngredientDto> ingredients = ingredientService.findAll();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/search")
    public ResponseEntity<List<IngredientDto>> searchIngredientsByName(@RequestParam String name) {
        List<IngredientDto> ingredients = ingredientService.searchByName(name);
        return ResponseEntity.ok(ingredients);
    }
}
