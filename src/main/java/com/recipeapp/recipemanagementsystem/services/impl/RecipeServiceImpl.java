package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.*;
import com.recipeapp.recipemanagementsystem.entities.*;
import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.MeasurementUnit;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;
import com.recipeapp.recipemanagementsystem.mappers.RecipeMapper;
import com.recipeapp.recipemanagementsystem.mappers.StepIngredientMapper;
import com.recipeapp.recipemanagementsystem.mappers.StepMapper;
import com.recipeapp.recipemanagementsystem.repositories.*;
import com.recipeapp.recipemanagementsystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeIngredientService recipeIngredientService;
    private final StepService stepService;
    private final StepIngredientService stepIngredientService;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeMapper recipeMapper,
                             RecipeIngredientService recipeIngredientService,
                             StepService stepService,
                             StepIngredientService stepIngredientService) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.recipeIngredientService = recipeIngredientService;
        this.stepService = stepService;
        this.stepIngredientService = stepIngredientService;
    }


    @Override
    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();

        recipe.setName(recipeDto.getName());
        recipe.setDifficulty(recipeDto.getDifficulty());
        recipe.setTotalDuration(recipeDto.getTotalDuration());
        recipe.setCategory(recipeDto.getCategory());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        Recipe savedRecipe = recipeRepository.save(recipe);
        Long recipeId = savedRecipe.getId();

        try {
            if (recipeDto.getRecipeIngredients() != null && !recipeDto.getRecipeIngredients().isEmpty()) {
                for (RecipeIngredientDto ingredientDto : recipeDto.getRecipeIngredients()) {
                    ingredientDto.setRecipeId(recipeId);
                    recipeIngredientService.createRecipeIngredient(ingredientDto);
                }
            }

            if (recipeDto.getSteps() != null && !recipeDto.getSteps().isEmpty()) {
                for (StepDto stepDto : recipeDto.getSteps()) {
                    stepDto.setRecipeId(recipeId);
                    StepDto createdStep = stepService.createStep(stepDto);

                    if (stepDto.getStepIngredients() != null && !stepDto.getStepIngredients().isEmpty()) {
                        for (StepIngredientDto stepIngredientDto : stepDto.getStepIngredients()) {
                            stepIngredientDto.setStepId(createdStep.getId());
                             stepIngredientService.createStepIngredient(stepIngredientDto);
                        }
                    }
                }
            }

            Recipe completeRecipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found after creation"));

            return recipeMapper.toDTO(completeRecipe);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create recipe: " + e.getMessage(), e);
        }
    }

    @Override
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        existingRecipe.setName(recipeDto.getName());
        existingRecipe.setDifficulty(recipeDto.getDifficulty());
        existingRecipe.setTotalDuration(recipeDto.getTotalDuration());
        existingRecipe.setCategory(recipeDto.getCategory());
        existingRecipe.setDescription(recipeDto.getDescription());

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return recipeMapper.toDTO(updatedRecipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDto findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        return recipeMapper.toDTO(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> findByCategory(RecipeCategory category) {
        return recipeRepository.findByCategory(category)
                .stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> findByDifficulty(Difficulty difficulty) {
        return recipeRepository.findByDifficulty(difficulty)
                .stream()
                .map(recipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDto findByIdWithSteps(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        return recipeMapper.toDTO(recipe);
    }
    //TODO:
    // protasi
    // @Override
    // @Transactional(readOnly = true)
    // public List<RecipeDto> findByIngredientName(String ingredientName) {
    //      List<Recipe> recipes = recipeRepository.findAllByIngredientName(ingredientName);
    //      return recipes.stream()
    //                    .map(recipeMapper::toDTO)
    //                    .collect(Collectors.toList());
    // }
}
