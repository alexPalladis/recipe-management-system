package com.recipeapp.recipemanagementsystem.repositories;

import com.recipeapp.recipemanagementsystem.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String name);

    Optional<Ingredient> findByNameIgnoreCase(String name);

    List<Ingredient> findByNameContainingIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT i FROM Ingredient i WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Ingredient> searchByNameOrDescription(@Param("searchTerm") String searchTerm);
}