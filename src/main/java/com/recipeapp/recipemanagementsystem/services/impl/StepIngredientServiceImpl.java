package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import com.recipeapp.recipemanagementsystem.entities.StepIngredient;
import com.recipeapp.recipemanagementsystem.mappers.StepIngredientMapper;
import com.recipeapp.recipemanagementsystem.repositories.StepIngredientRepository;
import com.recipeapp.recipemanagementsystem.services.StepIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StepIngredientServiceImpl implements StepIngredientService {

    private final StepIngredientRepository stepIngredientRepository;
    private final StepIngredientMapper stepIngredientMapper;

    @Autowired
    public StepIngredientServiceImpl(StepIngredientRepository stepIngredientRepository,
                                     StepIngredientMapper stepIngredientMapper) {
        this.stepIngredientRepository = stepIngredientRepository;
        this.stepIngredientMapper = stepIngredientMapper;
    }

    @Override
    public StepIngredientDto createStepIngredient(StepIngredientDto stepIngredientDto) {
        StepIngredient stepIngredient = stepIngredientMapper.toEntity(stepIngredientDto);
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
