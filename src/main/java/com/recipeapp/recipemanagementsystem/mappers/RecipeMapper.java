package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StepMapper.class, RecipeIngredientMapper.class, PhotoMapper.class})
public interface RecipeMapper {

    RecipeDto toDTO(Recipe recipe);

    Recipe toEntity(RecipeDto recipeDTO);
}
