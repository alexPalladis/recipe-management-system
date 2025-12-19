package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import com.recipeapp.recipemanagementsystem.entities.Ingredient;
import com.recipeapp.recipemanagementsystem.entities.Step;
import com.recipeapp.recipemanagementsystem.entities.StepIngredient;
import com.recipeapp.recipemanagementsystem.mappers.StepIngredientMapper;
import com.recipeapp.recipemanagementsystem.repositories.IngredientRepository;
import com.recipeapp.recipemanagementsystem.repositories.StepIngredientRepository;
import com.recipeapp.recipemanagementsystem.repositories.StepRepository;
import com.recipeapp.recipemanagementsystem.services.StepIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepIngredientServiceImpl implements StepIngredientService {

    private final StepIngredientRepository stepIngredientRepository;
    private final StepIngredientMapper stepIngredientMapper;
    private final StepRepository stepRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public StepIngredientServiceImpl(StepIngredientRepository stepIngredientRepository,
                                     StepIngredientMapper stepIngredientMapper,
                                     StepRepository stepRepository,
                                     IngredientRepository ingredientRepository) {
        this.stepIngredientRepository = stepIngredientRepository;
        this.stepIngredientMapper = stepIngredientMapper;
        this.stepRepository = stepRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public StepIngredientDto createStepIngredient(StepIngredientDto stepIngredientDto) {
        Step step = stepRepository.findById(stepIngredientDto.getStepId())
                .orElseThrow(() -> new RuntimeException("Step not found with id: " + stepIngredientDto.getStepId()));

        Ingredient ingredient = ingredientRepository.findById(stepIngredientDto.getIngredientId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + stepIngredientDto.getIngredientId()));

        StepIngredient stepIngredient = stepIngredientMapper.toEntity(stepIngredientDto);

        stepIngredient.setStep(step);
        stepIngredient.setIngredient(ingredient);

        StepIngredient savedStepIngredient = stepIngredientRepository.save(stepIngredient);
        return stepIngredientMapper.toDto(savedStepIngredient);
    }

    @Override
    public StepIngredientDto updateStepIngredient(Long id, StepIngredientDto stepIngredientDto) {
        StepIngredient existingStepIngredient = stepIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StepIngredient not found with id: " + id));

        existingStepIngredient.setQuantity(stepIngredientDto.getQuantity());
        existingStepIngredient.setMeasurementUnit(stepIngredientDto.getMeasurementUnit());

        StepIngredient updatedStepIngredient = stepIngredientRepository.save(existingStepIngredient);
        return stepIngredientMapper.toDto(updatedStepIngredient);
    }

    @Override
    public void deleteStepIngredient(Long id) {
        stepIngredientRepository.deleteById(id);
    }

    @Override
    public StepIngredientDto findById(Long id) {
        StepIngredient stepIngredient = stepIngredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StepIngredient not found with id: " + id));
        return stepIngredientMapper.toDto(stepIngredient);
    }

    @Override
    public List<StepIngredientDto> findByStepId(Long stepId) {
        List<StepIngredient> stepIngredients = stepIngredientRepository.findByStepId(stepId);
        return stepIngredients.stream()
                .map(stepIngredientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StepIngredientDto> findByIngredientId(Long ingredientId) {
        List<StepIngredient> stepIngredients = stepIngredientRepository.findByIngredientId(ingredientId);
        return stepIngredients.stream()
                .map(stepIngredientMapper::toDto)
                .collect(Collectors.toList());
    }
}
