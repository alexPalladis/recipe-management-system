package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.entities.Step;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StepIngredientMapper.class, PhotoMapper.class})
public interface StepMapper {

    @Mapping(source = "recipe.id", target = "recipeId")
    StepDto toDTO(Step step);

    @Mapping(source = "recipeId", target = "recipe.id")
    Step toEntity(StepDto stepDTO);
}
