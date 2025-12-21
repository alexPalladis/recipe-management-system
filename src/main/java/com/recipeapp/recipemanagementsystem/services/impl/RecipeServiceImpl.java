package com.recipeapp.recipemanagementsystem.services.impl;

// --- DTOs & Entities ---
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

    // Mappers
    private final RecipeMapper recipeMapper;
    private final StepMapper stepMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final StepIngredientMapper stepIngredientMapper; // <--- 2. ΝΕΟ FIELD

    // Services
    private final RecipeIngredientService recipeIngredientService;
    private final StepService stepService;
    private final StepIngredientService stepIngredientService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository,
                             RecipeMapper recipeMapper,
                             StepMapper stepMapper,
                             RecipeIngredientMapper recipeIngredientMapper,
                             StepIngredientMapper stepIngredientMapper, // <--- INJECT ΕΔΩ
                             RecipeIngredientService recipeIngredientService,
                             StepService stepService,
                             StepIngredientService stepIngredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
        this.stepMapper = stepMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.stepIngredientMapper = stepIngredientMapper; // <--- ASSIGN ΕΔΩ
        this.recipeIngredientService = recipeIngredientService;
        this.stepService = stepService;
        this.stepIngredientService = stepIngredientService;
    }

    @Override
    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        // ... (Ο κώδικας του createRecipe παραμένει ίδιος καθώς δούλευε σωστά) ...
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
            // ... (rest of create logic) ...
            return findByIdWithSteps(recipeId);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create recipe: " + e.getMessage(), e);
        }
    }

    @Override
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // 1. Ενημέρωση Βασικών Πεδίων
        existingRecipe.setName(recipeDto.getName());
        existingRecipe.setDifficulty(recipeDto.getDifficulty());
        existingRecipe.setTotalDuration(recipeDto.getTotalDuration());
        existingRecipe.setCategory(recipeDto.getCategory());
        existingRecipe.setDescription(recipeDto.getDescription());
        existingRecipe.setUpdatedAt(LocalDateTime.now()); // Καλό είναι να ενημερώνουμε και αυτό

        // 2. Ενημέρωση Υλικών Συνταγής (Recipe Ingredients)
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

        // 3. Ενημέρωση Βημάτων (Steps) - SOS FIX ΕΔΩ!
        if (existingRecipe.getSteps() == null) {
            existingRecipe.setSteps(new ArrayList<>());
        }
        existingRecipe.getSteps().clear();

        if (recipeDto.getSteps() != null) {
            for (StepDto stepDto : recipeDto.getSteps()) {
                // Μετατροπή Step DTO -> Entity
                Step stepEntity = stepMapper.toEntity(stepDto);
                stepEntity.setRecipe(existingRecipe);

                // --- FIX: ΧΕΙΡΟΚΙΝΗΤΗ ΠΡΟΣΘΗΚΗ ΥΛΙΚΩΝ ΒΗΜΑΤΟΣ ---
                // Επειδή ο StepMapper τα αγνοεί (ignore=true), τα προσθέτουμε εμείς τώρα.
                if (stepDto.getStepIngredients() != null) {
                    List<StepIngredient> stepIngredients = new ArrayList<>();

                    for (StepIngredientDto siDto : stepDto.getStepIngredients()) {
                        // 1. Μετατροπή DTO -> Entity
                        StepIngredient siEntity = stepIngredientMapper.toEntity(siDto);

                        // 2. Εύρεση του πραγματικού Ingredient από τη βάση
                        Ingredient realIng = ingredientRepository.findById(siDto.getIngredientId())
                                .orElseThrow(() -> new RuntimeException("Ingredient not found in step: " + siDto.getIngredientId()));

                        // 3. Ρύθμιση συσχετίσεων
                        siEntity.setIngredient(realIng);
                        siEntity.setStep(stepEntity);

                        stepIngredients.add(siEntity);
                    }
                    // 4. Ανάθεση στη λίστα του βήματος
                    stepEntity.setStepIngredients(stepIngredients);
                }
                // ------------------------------------------------

                existingRecipe.getSteps().add(stepEntity);
            }
        }

        // Το @Transactional θα κάνει commit τις αλλαγές αυτόματα
        return recipeMapper.toDTO(existingRecipe);
    }

    // ... (Οι υπόλοιπες μέθοδοι delete, findAll κλπ παραμένουν ίδιες) ...
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