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
import com.recipeapp.recipemanagementsystem.mappers.StepMapper;

// --- Repositories (SOS: Εδώ είναι και το IngredientRepository) ---
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    // Βασικά Repositories
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository; // <--- Απαραίτητο για τα Υλικά

    // Mappers
    private final RecipeMapper recipeMapper;
    private final StepMapper stepMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;

    // Services (Χρησιμοποιούνται κυρίως στο Create)
    private final RecipeIngredientService recipeIngredientService;
    private final StepService stepService;
    private final StepIngredientService stepIngredientService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository, // <--- Inject εδώ
                             RecipeMapper recipeMapper,
                             StepMapper stepMapper,
                             RecipeIngredientMapper recipeIngredientMapper,
                             RecipeIngredientService recipeIngredientService,
                             StepService stepService,
                             StepIngredientService stepIngredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository; // <--- Αρχικοποίηση
        this.recipeMapper = recipeMapper;
        this.stepMapper = stepMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.recipeIngredientService = recipeIngredientService;
        this.stepService = stepService;
        this.stepIngredientService = stepIngredientService;
    }

    @Override
    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        // Δημιουργία νέας συνταγής (Entity)
        Recipe recipe = new Recipe();

        recipe.setName(recipeDto.getName());
        recipe.setDifficulty(recipeDto.getDifficulty());
        recipe.setTotalDuration(recipeDto.getTotalDuration());
        recipe.setCategory(recipeDto.getCategory());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        // Αποθήκευση της συνταγής πρώτα για να πάρουμε ID
        Recipe savedRecipe = recipeRepository.save(recipe);
        Long recipeId = savedRecipe.getId();

        try {
            // Προσθήκη Υλικών (αν υπάρχουν)
            if (recipeDto.getRecipeIngredients() != null && !recipeDto.getRecipeIngredients().isEmpty()) {
                for (RecipeIngredientDto ingredientDto : recipeDto.getRecipeIngredients()) {
                    ingredientDto.setRecipeId(recipeId);
                    recipeIngredientService.createRecipeIngredient(ingredientDto);
                }
            }

            // Προσθήκη Βημάτων (αν υπάρχουν)
            if (recipeDto.getSteps() != null && !recipeDto.getSteps().isEmpty()) {
                for (StepDto stepDto : recipeDto.getSteps()) {
                    stepDto.setRecipeId(recipeId);
                    StepDto createdStep = stepService.createStep(stepDto);

                    // Προσθήκη Υλικών Βήματος (αν υπάρχουν)
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

            RecipeDto result = recipeMapper.toDTO(completeRecipe);


            List<RecipeIngredientDto> savedIngredients = recipeIngredientService.findByRecipeId(recipeId);
            result.setRecipeIngredients(savedIngredients);

            List<StepDto> savedSteps = stepService.findByRecipeId(recipeId);

            for (StepDto step : savedSteps) {
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

        // 1. Ενημέρωση Βασικών Πεδίων
        existingRecipe.setName(recipeDto.getName());
        existingRecipe.setDifficulty(recipeDto.getDifficulty());
        existingRecipe.setTotalDuration(recipeDto.getTotalDuration());
        existingRecipe.setCategory(recipeDto.getCategory());
        existingRecipe.setDescription(recipeDto.getDescription());

        // 2. Ενημέρωση Υλικών (Ingredients)
        existingRecipe.getRecipeIngredients().clear(); // Διαγραφή παλιών

        if (recipeDto.getRecipeIngredients() != null) {
            for (RecipeIngredientDto dto : recipeDto.getRecipeIngredients()) {
                // Μετατροπή DTO -> Entity (χωρίς ID)
                RecipeIngredient entity = recipeIngredientMapper.toEntity(dto);

                // SOS: Εύρεση του πραγματικού Υλικού από τη βάση
                Ingredient realIngredient = ingredientRepository.findById(dto.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + dto.getIngredientId()));

                // Συνδέσεις
                entity.setIngredient(realIngredient);
                entity.setRecipe(existingRecipe);

                // Προσθήκη
                existingRecipe.getRecipeIngredients().add(entity);
            }
        }

        // 3. Ενημέρωση Βημάτων (Steps)
        existingRecipe.getSteps().clear(); // Διαγραφή παλιών

        if (recipeDto.getSteps() != null) {
            for (StepDto stepDto : recipeDto.getSteps()) {
                // Μετατροπή DTO -> Entity (χωρίς ID)
                Step stepEntity = stepMapper.toEntity(stepDto);

                // Σύνδεση με γονιό
                stepEntity.setRecipe(existingRecipe);

                // Προσθήκη
                existingRecipe.getSteps().add(stepEntity);
            }
        }

        // Δεν χρειάζεται .save() λόγω @Transactional
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
}