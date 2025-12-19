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

    @Mapping(target = "step", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    @Mapping(target = "id", ignore = true)
    StepIngredient toEntity(StepIngredientDto stepIngredientDto);
}