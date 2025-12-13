package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.entities.Ingredient;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import com.recipeapp.recipemanagementsystem.entities.RecipeIngredient;
import com.recipeapp.recipemanagementsystem.mappers.RecipeIngredientMapper;
import com.recipeapp.recipemanagementsystem.repositories.IngredientRepository;
import com.recipeapp.recipemanagementsystem.repositories.RecipeIngredientRepository;
import com.recipeapp.recipemanagementsystem.repositories.RecipeRepository;
import com.recipeapp.recipemanagementsystem.services.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository,
                                       RecipeIngredientMapper recipeIngredientMapper,RecipeRepository recipeRepository,
                                       IngredientRepository ingredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public RecipeIngredientDto createRecipeIngredient(RecipeIngredientDto recipeIngredientDto) {
        Recipe recipe = recipeRepository.findById(recipeIngredientDto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeIngredientDto.getRecipeId()));

        Ingredient ingredient = ingredientRepository.findById(recipeIngredientDto.getIngredientId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + recipeIngredientDto.getIngredientId()));

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(recipeIngredientDto.getQuantity());
        recipeIngredient.setMeasurementUnit(recipeIngredientDto.getMeasurementUnit());

        RecipeIngredient saved = recipeIngredientRepository.save(recipeIngredient);
        return recipeIngredientMapper.toDto(saved);
    }

    @Override
    public RecipeIngredientDto updateRecipeIngredient(Long id, RecipeIngredientDto recipeIngredientDto) {
        RecipeIngredient existingRecipeIngredient = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RecipeIngredient not found with id: " + id));

        existingRecipeIngredient.setQuantity(recipeIngredientDto.getQuantity());
        existingRecipeIngredient.setMeasurementUnit(recipeIngredientDto.getMeasurementUnit());

        RecipeIngredient updatedRecipeIngredient = recipeIngredientRepository.save(existingRecipeIngredient);
        return recipeIngredientMapper.toDto(updatedRecipeIngredient);
    }

    @Override
    public void deleteRecipeIngredient(Long id) {
        recipeIngredientRepository.deleteById(id);
    }

    @Override
    public RecipeIngredientDto findById(Long id) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RecipeIngredient not found with id: " + id));
        return recipeIngredientMapper.toDto(recipeIngredient);
    }

    @Override
    public List<RecipeIngredientDto> findByRecipeId(Long recipeId) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findByRecipeId(recipeId);
        return recipeIngredients.stream()
                .map(recipeIngredientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeIngredientDto> findByIngredientId(Long ingredientId) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findByIngredientId(ingredientId);
        return recipeIngredients.stream()
                .map(recipeIngredientMapper::toDto)
                .collect(Collectors.toList());
    }
}