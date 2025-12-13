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

    // Αναζήτηση βάσει ονόματος (ακριβές match)
    Optional<Ingredient> findByName(String name);

    // Αναζήτηση βάσει ονόματος (case insensitive)
    Optional<Ingredient> findByNameIgnoreCase(String name);

    // Αναζήτηση που περιέχει το όνομα (like search)
    List<Ingredient> findByNameContainingIgnoreCase(String name);

    // Έλεγχος ύπαρξης βάσει ονόματος
    boolean existsByNameIgnoreCase(String name);

    // Custom query - αναζήτηση στο όνομα ή στην περιγραφή
    @Query("SELECT i FROM Ingredient i WHERE " +
            "LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Ingredient> searchByNameOrDescription(@Param("searchTerm") String searchTerm);
}