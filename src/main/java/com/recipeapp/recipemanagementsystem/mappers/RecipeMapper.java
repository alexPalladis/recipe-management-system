package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.RecipeDto;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StepMapper.class, RecipeIngredientMapper.class, PhotoMapper.class})
public interface RecipeMapper {


    @Mapping(source = "steps", target = "steps")
    @Mapping(source = "recipeIngredients", target = "recipeIngredients")
    @Mapping(source = "photos", target = "photos")
    RecipeDto toDTO(Recipe recipe);

    Recipe toEntity(RecipeDto recipeDTO);
}
