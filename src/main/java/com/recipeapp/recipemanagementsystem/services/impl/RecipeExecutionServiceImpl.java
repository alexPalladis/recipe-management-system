package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import com.recipeapp.recipemanagementsystem.entities.Step;
import com.recipeapp.recipemanagementsystem.mappers.RecipeMapper;
import com.recipeapp.recipemanagementsystem.mappers.StepMapper;
import com.recipeapp.recipemanagementsystem.repositories.RecipeRepository;
import com.recipeapp.recipemanagementsystem.repositories.StepRepository;
import com.recipeapp.recipemanagementsystem.services.RecipeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeExecutionServiceImpl implements RecipeExecutionService {

    private final RecipeRepository recipeRepository;
    private final StepRepository stepRepository;
    private final RecipeMapper recipeMapper;
    private final StepMapper stepMapper;

    // Απλά Maps για tracking
    private final Map<Long, Long> activeExecutions = new HashMap<>(); // executionId -> recipeId
    private final Map<Long, Set<Long>> completedSteps = new HashMap<>(); // executionId -> completedStepIds
    private Long executionIdCounter = 1L;

    @Autowired
    public RecipeExecutionServiceImpl(RecipeRepository recipeRepository,
                                      StepRepository stepRepository,
                                      RecipeMapper recipeMapper,
                                      StepMapper stepMapper) {
        this.recipeRepository = recipeRepository;
        this.stepRepository = stepRepository;
        this.recipeMapper = recipeMapper;
        this.stepMapper = stepMapper;
    }

    @Override
    public Long startExecution(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));

        List<Step> steps = stepRepository.findByRecipeIdOrderByStepOrderAsc(recipeId);
        if (steps.isEmpty()) {
            throw new RuntimeException("Recipe has no steps to execute");
        }

        Long executionId = executionIdCounter++;
        activeExecutions.put(executionId, recipeId);
        completedSteps.put(executionId, new HashSet<>());

        return executionId;
    }

    @Override
    public void markStepCompleted(Long executionId, Long stepId) {
        if (!activeExecutions.containsKey(executionId)) {
            throw new RuntimeException("Recipe execution not found with id: " + executionId);
        }
        completedSteps.get(executionId).add(stepId);
    }

    @Override
    public void completeExecution(Long executionId) {
        if (!activeExecutions.containsKey(executionId)) {
            throw new RuntimeException("Recipe execution not found with id: " + executionId);
        }
        // Μπορούμε να αφαιρέσουμε από memory ή να το κρατήσουμε
        // activeExecutions.remove(executionId);
        // completedSteps.remove(executionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateProgress(Long executionId) {
        Long recipeId = getRecipeIdForExecution(executionId);
        List<Step> allSteps = stepRepository.findByRecipeIdOrderByStepOrderAsc(recipeId);
        Set<Long> completed = completedSteps.get(executionId);

        int totalSteps = allSteps.size();
        int completedCount = completed != null ? completed.size() : 0;

        return totalSteps > 0 ? (double) completedCount / totalSteps * 100.0 : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCurrentStepOrder(Long executionId) {
        Long recipeId = getRecipeIdForExecution(executionId);
        List<Step> allSteps = stepRepository.findByRecipeIdOrderByStepOrderAsc(recipeId);
        Set<Long> completed = completedSteps.get(executionId);

        for (Step step : allSteps) {
            if (completed == null || !completed.contains(step.getId())) {
                return step.getStepOrder();
            }
        }
        // Όλα τα βήματα ολοκληρώθηκαν
        return allSteps.isEmpty() ? 1 : allSteps.get(allSteps.size() - 1).getStepOrder() + 1;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StepDto> getCompletedSteps(Long executionId) {
        Long recipeId = getRecipeIdForExecution(executionId);
        List<Step> allSteps = stepRepository.findByRecipeIdOrderByStepOrderAsc(recipeId);
        Set<Long> completed = completedSteps.get(executionId);

        return allSteps.stream()
                .filter(step -> completed != null && completed.contains(step.getId()))
                .map(stepMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StepDto> getRemainingSteps(Long executionId) {
        Long recipeId = getRecipeIdForExecution(executionId);
        List<Step> allSteps = stepRepository.findByRecipeIdOrderByStepOrderAsc(recipeId);
        Set<Long> completed = completedSteps.get(executionId);

        return allSteps.stream()
                .filter(step -> completed == null || !completed.contains(step.getId()))
                .map(stepMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDto getCurrentExecutingRecipe(Long executionId) {
        Long recipeId = getRecipeIdForExecution(executionId);
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        return recipeMapper.toDTO(recipe);
    }

    private Long getRecipeIdForExecution(Long executionId) {
        Long recipeId = activeExecutions.get(executionId);
        if (recipeId == null) {
            throw new RuntimeException("Recipe execution not found with id: " + executionId);
        }
        return recipeId;
    }
}
