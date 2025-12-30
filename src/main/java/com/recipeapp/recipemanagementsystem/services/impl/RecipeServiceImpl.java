package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.*;
import com.recipeapp.recipemanagementsystem.entities.*;

// --- Enums ---
import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;

// --- Mappers ---
import com.recipeapp.recipemanagementsystem.mappers.RecipeIngredientMapper;
import com.recipeapp.recipemanagementsystem.mappers.RecipeMapper;
import com.recipeapp.recipemanagementsystem.mappers.StepIngredientMapper; // <--- 1. ΝΕΟ IMPORT
import com.recipeapp.recipemanagementsystem.mappers.StepMapper;

// --- Repositories ---
import com.recipeapp.recipemanagementsystem.repositories.IngredientRepository;
import com.recipeapp.recipemanagementsystem.repositories.RecipeRepository;

// --- Services ---
import com.recipeapp.recipemanagementsystem.services.*;

// --- Spring Boot Utils ---
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// --- Java Utils ---
import java.time.LocalDateTime;
import java.util.ArrayList; // <--- Χρήσιμο
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    private final RecipeMapper recipeMapper;
    private final StepMapper stepMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final StepIngredientMapper stepIngredientMapper;

    private final RecipeIngredientService recipeIngredientService;
    private final StepService stepService;
    private final StepIngredientService stepIngredientService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository,
                             RecipeMapper recipeMapper,
                             StepMapper stepMapper,
                             RecipeIngredientMapper recipeIngredientMapper,
                             StepIngredientMapper stepIngredientMapper,
                             RecipeIngredientService recipeIngredientService,
                             StepService stepService,
                             StepIngredientService stepIngredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
        this.stepMapper = stepMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.stepIngredientMapper = stepIngredientMapper;
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
            if (recipeDto.getRecipeIngredients() != null) {
                for (RecipeIngredientDto ingredientDto : recipeDto.getRecipeIngredients()) {
                    ingredientDto.setRecipeId(recipeId);
                    recipeIngredientService.createRecipeIngredient(ingredientDto);
                }
            }
            if (recipeDto.getSteps() != null) {
                for (StepDto stepDto : recipeDto.getSteps()) {
                    stepDto.setRecipeId(recipeId);
                    StepDto createdStep = stepService.createStep(stepDto);
                    if (stepDto.getStepIngredients() != null) {
                        for (StepIngredientDto stepIngredientDto : stepDto.getStepIngredients()) {
                            stepIngredientDto.setStepId(createdStep.getId());
                            stepIngredientService.createStepIngredient(stepIngredientDto);
                        }
                    }
                }
            }

            Recipe completeRecipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found."));

            RecipeDto result = recipeMapper.toDTO(completeRecipe);

            List<RecipeIngredientDto> savedIngredients = recipeIngredientService.findByRecipeId(recipeId);

            result.setRecipeIngredients(savedIngredients);

            List<StepDto> savedSteps = stepService.findByRecipeId(recipeId);

            for(StepDto step : savedSteps) {
                List<StepIngredientDto> stepIngredients = stepIngredientService.findByStepId(step.getId());
                step.setStepIngredients(stepIngredients);
            }

            result.setSteps(savedSteps);

            return result;

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
        existingRecipe.setUpdatedAt(LocalDateTime.now()); // Καλό είναι να ενημερώνουμε και αυτό

        if (existingRecipe.getRecipeIngredients() == null) {
            existingRecipe.setRecipeIngredients(new ArrayList<>());
        }
        existingRecipe.getRecipeIngredients().clear();

        if (recipeDto.getRecipeIngredients() != null) {
            for (RecipeIngredientDto dto : recipeDto.getRecipeIngredients()) {
                RecipeIngredient entity = recipeIngredientMapper.toEntity(dto);
                Ingredient realIngredient = ingredientRepository.findById(dto.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found: " + dto.getIngredientId()));

                entity.setIngredient(realIngredient);
                entity.setRecipe(existingRecipe);
                existingRecipe.getRecipeIngredients().add(entity);
            }
        }

        if (existingRecipe.getSteps() == null) {
            existingRecipe.setSteps(new ArrayList<>());
        }
        existingRecipe.getSteps().clear();

        if (recipeDto.getSteps() != null) {
            for (StepDto stepDto : recipeDto.getSteps()) {

                Step stepEntity = stepMapper.toEntity(stepDto);
                stepEntity.setRecipe(existingRecipe);


                if (stepDto.getStepIngredients() != null) {
                    List<StepIngredient> stepIngredients = new ArrayList<>();

                    for (StepIngredientDto siDto : stepDto.getStepIngredients()) {

                        StepIngredient siEntity = stepIngredientMapper.toEntity(siDto);

                        Ingredient realIng = ingredientRepository.findById(siDto.getIngredientId())
                                .orElseThrow(() -> new RuntimeException("Ingredient not found in step: " + siDto.getIngredientId()));

                        siEntity.setIngredient(realIng);
                        siEntity.setStep(stepEntity);

                        stepIngredients.add(siEntity);
                    }
                    stepEntity.setStepIngredients(stepIngredients);
                }

                existingRecipe.getSteps().add(stepEntity);
            }
        }

        return recipeMapper.toDTO(existingRecipe);
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
        return recipeRepository.findAll().stream().map(recipeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name).stream().map(recipeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> findByCategory(RecipeCategory category) {
        return recipeRepository.findByCategory(category).stream().map(recipeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDto> findByDifficulty(Difficulty difficulty) {
        return recipeRepository.findByDifficulty(difficulty).stream().map(recipeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDto findByIdWithSteps(Long id) {
        return findById(id);
    }
}