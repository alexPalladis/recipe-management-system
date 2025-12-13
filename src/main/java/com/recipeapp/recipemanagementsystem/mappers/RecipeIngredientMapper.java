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

    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    @Mapping(target = "id", ignore = true)
    RecipeIngredient toEntity(RecipeIngredientDto recipeIngredientDto);
}