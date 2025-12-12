package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.StepDto;
import com.recipeapp.recipemanagementsystem.entities.Step;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StepIngredientMapper.class, PhotoMapper.class})
public interface StepMapper {

    @Mapping(target = "recipe", ignore = true)
    StepDto toDTO(Step step);

    @Mapping(target = "recipe", ignore = true)
    Step toEntity(StepDto stepDTO);
}
