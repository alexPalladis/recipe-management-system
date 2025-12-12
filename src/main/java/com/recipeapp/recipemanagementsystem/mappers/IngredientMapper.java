package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.IngredientDto;
import com.recipeapp.recipemanagementsystem.entities.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    @Mapping(target = "recipeIngredients", ignore = true)
    @Mapping(target = "stepIngredients", ignore = true)
    IngredientDto toDTO(Ingredient ingredient);

    @Mapping(target = "recipeIngredients", ignore = true)
    @Mapping(target = "stepIngredients", ignore = true)
    Ingredient toEntity(IngredientDto ingredientDTO);
}
