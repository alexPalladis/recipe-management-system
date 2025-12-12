package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.entities.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface RecipeIngredientMapper {

    RecipeIngredientDto toDTO(RecipeIngredient recipeIngredient);

    @Mapping(target = "recipe", ignore = true)
    RecipeIngredient toEntity(RecipeIngredientDto recipeIngredientDTO);
}