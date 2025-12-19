package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import com.recipeapp.recipemanagementsystem.entities.Step;
import com.recipeapp.recipemanagementsystem.mappers.StepMapper;
import com.recipeapp.recipemanagementsystem.repositories.RecipeRepository;
import com.recipeapp.recipemanagementsystem.repositories.StepRepository;
import com.recipeapp.recipemanagementsystem.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private final RecipeRepository recipeRepository;
    private final StepMapper stepMapper;

    @Autowired
    public StepServiceImpl(StepRepository stepRepository,
                           RecipeRepository recipeRepository,
                           StepMapper stepMapper) {
        this.stepRepository = stepRepository;
        this.recipeRepository = recipeRepository;
        this.stepMapper = stepMapper;
    }

    @Override
    public StepDto createStep(StepDto stepDto) {
        Recipe recipe = recipeRepository.findById(stepDto.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + stepDto.getRecipeId()));

        Step step = stepMapper.toEntity(stepDto);

        step.setRecipe(recipe);

        Step savedStep = stepRepository.save(step);
        return stepMapper.toDTO(savedStep);
    }

    @Override
    public StepDto updateStep(Long id, StepDto stepDto) {
        Step existingStep = stepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Step not found with id: " + id));

        existingStep.setTitle(stepDto.getTitle());
        existingStep.setDescription(stepDto.getDescription());
        existingStep.setStepOrder(stepDto.getStepOrder());
        existingStep.setDuration(stepDto.getDuration());

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
