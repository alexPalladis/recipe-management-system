package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDTO;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StepMapper.class, RecipeIngredientMapper.class, PhotoMapper.class})
public interface RecipeMapper {

    RecipeDTO toDTO(Recipe recipe);

    Recipe toEntity(RecipeDTO recipeDTO);
}
