package com.recipeapp.recipemanagementsystem.services.impl;

import com.recipeapp.recipemanagementsystem.dtos.IngredientDto;
import com.recipeapp.recipemanagementsystem.entities.Ingredient;
import com.recipeapp.recipemanagementsystem.mappers.IngredientMapper;
import com.recipeapp.recipemanagementsystem.repositories.IngredientRepository;
import com.recipeapp.recipemanagementsystem.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientMapper.toDTO(savedIngredient);
    }

    @Override
    public IngredientDto updateIngredient(Long id, IngredientDto ingredientDto) {
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));

        existingIngredient.setName(ingredientDto.getName());
        existingIngredient.setDescription(ingredientDto.getDescription());

        Ingredient updatedIngredient = ingredientRepository.save(existingIngredient);
        return ingredientMapper.toDTO(updatedIngredient);
    }

    @Override
    public void deleteIngredient(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new RuntimeException("Ingredient not found with id: " + id);
        }
        ingredientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientDto findById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
        return ingredientMapper.toDTO(ingredient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDto> findAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDto> searchByName(String name) {
        return ingredientRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ingredientMapper::toDTO)
                .collect(Collectors.toList());
    }
}