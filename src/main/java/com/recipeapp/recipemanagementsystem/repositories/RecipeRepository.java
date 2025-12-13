package com.recipeapp.recipemanagementsystem.repositories;

import com.recipeapp.recipemanagementsystem.entities.Recipe;
import com.recipeapp.recipemanagementsystem.enums.Difficulty;
import com.recipeapp.recipemanagementsystem.enums.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Αναζήτηση βάσει ονόματος
    List<Recipe> findByNameContainingIgnoreCase(String name);

    // Αναζήτηση βάσει κατηγορίας
    List<Recipe> findByCategory(RecipeCategory category);

    // Αναζήτηση βάσει δυσκολίας
    List<Recipe> findByDifficulty(Difficulty difficulty);

    // Αναζήτηση βάσει κατηγορίας και δυσκολίας
    List<Recipe> findByCategoryAndDifficulty(RecipeCategory category, Difficulty difficulty);

    // Συνταγές με χρόνο μέχρι X λεπτά
    List<Recipe> findByTotalDurationLessThanEqual(Integer maxDuration);

    // Συνταγές ταξινομημένες βάσει χρόνου
    List<Recipe> findAllByOrderByTotalDurationAsc();
    List<Recipe> findAllByOrderByCreatedAtDesc();

    // Custom query - σύνθετη αναζήτηση
    @Query("SELECT r FROM Recipe r WHERE " +
            "(:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:category IS NULL OR r.category = :category) AND " +
            "(:difficulty IS NULL OR r.difficulty = :difficulty) AND " +
            "(:maxDuration IS NULL OR r.totalDuration <= :maxDuration)")
    List<Recipe> searchRecipes(
            @Param("name") String name,
            @Param("category") RecipeCategory category,
            @Param("difficulty") Difficulty difficulty,
            @Param("maxDuration") Integer maxDuration
    );

    // Συνταγές που περιέχουν συγκεκριμένο υλικό
    @Query("SELECT DISTINCT r FROM Recipe r " +
            "JOIN r.recipeIngredients ri " +
            "WHERE ri.ingredient.id = :ingredientId")
    List<Recipe> findRecipesContainingIngredient(@Param("ingredientId") Long ingredientId);

    // Top recipes (πιο πρόσφατες)
    @Query("SELECT r FROM Recipe r ORDER BY r.createdAt DESC")
    List<Recipe> findTopRecent(@Param("limit") int limit);
}
