package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import com.recipeapp.recipemanagementsystem.entities.StepIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepIngredientMapper {

    @Mapping(source = "step.id", target = "stepId")
    @Mapping(source = "ingredient.id", target = "ingredientId")
    StepIngredientDto toDto(StepIngredient stepIngredient);

    @Mapping(source = "stepId", target = "step.id")
    @Mapping(source = "ingredientId", target = "ingredient.id")
    StepIngredient toEntity(StepIngredientDto stepIngredientDto);
}