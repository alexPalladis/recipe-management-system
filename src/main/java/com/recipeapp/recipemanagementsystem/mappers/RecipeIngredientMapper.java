package com.recipeapp.recipemanagementsystem.mappers;
import com.recipeapp.recipemanagementsystem.dtos.RecipeIngredientDto;
import com.recipeapp.recipemanagementsystem.entities.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "ingredient.id", target = "ingredientId")
    RecipeIngredientDto toDto(RecipeIngredient recipeIngredient);


    @Mapping(source = "recipeId", target = "recipe.id")
    @Mapping(source = "ingredientId", target = "ingredient.id")
    RecipeIngredient toEntity(RecipeIngredientDto recipeIngredientDto);
}