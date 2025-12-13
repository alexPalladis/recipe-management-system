package com.recipeapp.recipemanagementsystem.repositories;

import com.recipeapp.recipemanagementsystem.entities.StepIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StepIngredientRepository extends JpaRepository<StepIngredient, Long> {

    // Υλικά ενός βήματος
    List<StepIngredient> findByStepId(Long stepId);

    // Βήματα που χρησιμοποιούν συγκεκριμένο υλικό
    List<StepIngredient> findByIngredientId(Long ingredientId);

    // Συγκεκριμένο υλικό σε συγκεκριμένο βήμα
    Optional<StepIngredient> findByStepIdAndIngredientId(Long stepId, Long ingredientId);

    // Έλεγχος αν βήμα χρησιμοποιεί υλικό
    boolean existsByStepIdAndIngredientId(Long stepId, Long ingredientId);

    // Διαγραφή όλων των υλικών ενός βήματος
    void deleteByStepId(Long stepId);

    // Υλικά όλων των βημάτων μιας συνταγής
    @Query("SELECT si FROM StepIngredient si JOIN si.step s WHERE s.recipe.id = :recipeId")
    List<StepIngredient> findByRecipeId(@Param("recipeId") Long recipeId);

    // Αριθμός υλικών ανά βήμα
    @Query("SELECT COUNT(si) FROM StepIngredient si WHERE si.step.id = :stepId")
    Integer countIngredientsByStepId(@Param("stepId") Long stepId);
}
