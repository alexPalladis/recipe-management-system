package com.recipeapp.recipemanagementsystem.mappers;


import com.recipeapp.recipemanagementsystem.dtos.StepIngredientDto;
import com.recipeapp.recipemanagementsystem.entities.StepIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface StepIngredientMapper {

    @Mapping(target = "step", ignore = true)
    StepIngredientDto toDTO(StepIngredient stepIngredient);

    @Mapping(target = "step", ignore = true)
    StepIngredient toEntity(StepIngredientDto stepIngredientDTO);
}
