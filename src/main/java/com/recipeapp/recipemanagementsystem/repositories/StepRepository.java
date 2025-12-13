package com.recipeapp.recipemanagementsystem.repositories;

import com.recipeapp.recipemanagementsystem.entities.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

    // Βήματα μιας συνταγής ταξινομημένα
    List<Step> findByRecipeIdOrderByStepOrderAsc(Long recipeId);

    // Βήμα με συγκεκριμένη σειρά σε συνταγή
    Step findByRecipeIdAndStepOrder(Long recipeId, Integer stepOrder);

    // Συνολικός χρόνος βημάτων μιας συνταγής
    @Query("SELECT SUM(s.duration) FROM Step s WHERE s.recipe.id = :recipeId")
    Integer calculateTotalDurationByRecipeId(@Param("recipeId") Long recipeId);

    // Αριθμός βημάτων ανά συνταγή
    @Query("SELECT COUNT(s) FROM Step s WHERE s.recipe.id = :recipeId")
    Integer countStepsByRecipeId(@Param("recipeId") Long recipeId);

    // Επόμενο stepOrder για νέο βήμα
    @Query("SELECT COALESCE(MAX(s.stepOrder), 0) + 1 FROM Step s WHERE s.recipe.id = :recipeId")
    Integer getNextStepOrder(@Param("recipeId") Long recipeId);
}
