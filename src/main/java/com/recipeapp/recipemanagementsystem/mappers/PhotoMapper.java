package com.recipeapp.recipemanagementsystem.mappers;

import com.recipeapp.recipemanagementsystem.dtos.PhotoDto;
import com.recipeapp.recipemanagementsystem.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "step", ignore = true)
    PhotoDto toDTO(Photo photo);

    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "step", ignore = true)
    Photo toEntity(PhotoDto photoDTO);
}
