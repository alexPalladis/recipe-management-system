package com.recipeapp.recipemanagementsystem.services.impl;

// --- Imports για DTOs και Entities ---
import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto; // <--- ΝΕΟ
import com.recipeapp.recipemanagementsystem.entities.Ingredient;     // <--- ΝΕΟ
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import com.recipeapp.recipemanagementsystem.entities.Step;
import com.recipeapp.recipemanagementsystem.entities.StepIngredient; // <--- ΝΕΟ

// --- Imports για Mappers ---
import com.recipeapp.recipemanagementsystem.mappers.StepIngredientMapper; // <--- ΝΕΟ
import com.recipeapp.recipemanagementsystem.mappers.StepMapper;

// --- Imports για Repositories ---
import com.recipeapp.recipemanagementsystem.repositories.IngredientRepository; // <--- ΝΕΟ
import com.recipeapp.recipemanagementsystem.repositories.RecipeRepository;
import com.recipeapp.recipemanagementsystem.repositories.StepRepository;

// --- Services & Utils ---
import com.recipeapp.recipemanagementsystem.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList; // <--- ΝΕΟ
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository; // <--- ΝΕΟ: Για να βρίσκουμε τα υλικά
    private final StepMapper stepMapper;
    private final StepIngredientMapper stepIngredientMapper; // <--- ΝΕΟ: Για μετατροπή DTO -> Entity

    @Autowired
    public StepServiceImpl(StepRepository stepRepository,
                           RecipeRepository recipeRepository,
                           IngredientRepository ingredientRepository, // <--- Inject εδώ
                           StepMapper stepMapper,
                           StepIngredientMapper stepIngredientMapper) { // <--- Inject εδώ
        this.stepRepository = stepRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.stepMapper = stepMapper;
        this.stepIngredientMapper = stepIngredientMapper;
    }

    @Override
    public StepDto createStep(StepDto stepDto) {
        Recipe recipe = recipeRepository.findById(stepDto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + stepDto.getRecipeId()));

        Step step = stepMapper.toEntity(stepDto);
        step.setRecipe(recipe);

        // --- ΛΟΓΙΚΗ ΓΙΑ ΤΑ ΥΛΙΚΑ ΚΑΤΑ ΤΗ ΔΗΜΙΟΥΡΓΙΑ ---
        if (stepDto.getStepIngredients() != null) {
            List<StepIngredient> ingredients = new ArrayList<>();
            for (StepIngredientDto dto : stepDto.getStepIngredients()) {
                StepIngredient entity = stepIngredientMapper.toEntity(dto);

                Ingredient realIngredient = ingredientRepository.findById(dto.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found"));

                entity.setIngredient(realIngredient);
                entity.setStep(step); // Σύνδεση με το βήμα
                ingredients.add(entity);
            }
            step.setStepIngredients(ingredients);
        }
        // ---------------------------------------------

        Step savedStep = stepRepository.save(step);
        return stepMapper.toDTO(savedStep);
    }

    @Override
    public StepDto updateStep(Long id, StepDto stepDto) {
        // 1. Βρίσκουμε το υπάρχον βήμα
        Step existingStep = stepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Step not found with id: " + id));

        // 2. Ενημερώνουμε τα απλά πεδία
        existingStep.setTitle(stepDto.getTitle());
        existingStep.setDescription(stepDto.getDescription());
        existingStep.setStepOrder(stepDto.getStepOrder());
        existingStep.setDuration(stepDto.getDuration());

        // 3. --- FIX: ΔΙΑΧΕΙΡΙΣΗ ΥΛΙΚΩΝ (STEP INGREDIENTS) ---

        // Α. Καθαρίζουμε την υπάρχουσα λίστα (για να φύγουν τα deleted)
        if (existingStep.getStepIngredients() == null) {
            existingStep.setStepIngredients(new ArrayList<>());
        }
        existingStep.getStepIngredients().clear();

        // Β. Προσθέτουμε τα νέα/ενημερωμένα υλικά
        if (stepDto.getStepIngredients() != null) {
            for (StepIngredientDto dto : stepDto.getStepIngredients()) {
                // Μετατροπή DTO -> Entity
                StepIngredient entity = stepIngredientMapper.toEntity(dto);

                // Βρίσκουμε το πραγματικό Ingredient από τη βάση (π.χ. Ντομάτα ID:5)
                Ingredient realIngredient = ingredientRepository.findById(dto.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + dto.getIngredientId()));

                // Σημαντικό: Συνδέουμε τις σχέσεις (Relations)
                entity.setIngredient(realIngredient);
                entity.setStep(existingStep); // <-- Αυτό έλειπε και έσπαγε τη σύνδεση!

                // Προσθήκη στη λίστα του βήματος
                existingStep.getStepIngredients().add(entity);
            }
        }
        // -----------------------------------------------------

        // 4. Αποθήκευση
        Step updatedStep = stepRepository.save(existingStep);
        return stepMapper.toDTO(updatedStep);
    }

    @Override
    public void deleteStep(Long id) {
        if (!stepRepository.existsById(id)) {
            throw new RuntimeException("Step not found with id: " + id);
        }
        stepRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StepDto findById(Long id) {
        Step step = stepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Step not found with id: " + id));
        return stepMapper.toDTO(step);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StepDto> findByRecipeId(Long recipeId) {
        return stepRepository.findByRecipeIdOrderByStepOrderAsc(recipeId)
                .stream()
                .map(stepMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StepDto findByRecipeIdAndStepOrder(Long recipeId, Integer stepOrder) {
        Step step = stepRepository.findByRecipeIdAndStepOrder(recipeId, stepOrder);
        if (step == null) {
            throw new RuntimeException("Step not found for recipe " + recipeId + " with order " + stepOrder);
        }
        return stepMapper.toDTO(step);
    }
}