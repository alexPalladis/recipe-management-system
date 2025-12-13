package com.recipeapp.recipemanagementsystem.repositories;

import com.recipeapp.recipemanagementsystem.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    // Φωτογραφίες μιας συνταγής
    List<Photo> findByRecipeId(Long recipeId);

    // Φωτογραφίες ενός βήματος
    List<Photo> findByStepId(Long stepId);

    // Φωτογραφίες βάσει MIME type
    List<Photo> findByMimeType(String mimeType);

    // Έλεγχος αν υπάρχει φωτογραφία για συνταγή
    boolean existsByRecipeId(Long recipeId);

    // Έλεγχος αν υπάρχει φωτογραφία για βήμα
    boolean existsByStepId(Long stepId);

    // Αριθμός φωτογραφιών ανά συνταγή
    @Query("SELECT COUNT(p) FROM Photo p WHERE p.recipe.id = :recipeId")
    Integer countPhotosByRecipeId(@Param("recipeId") Long recipeId);

    // Αριθμός φωτογραφιών ανά βήμα
    @Query("SELECT COUNT(p) FROM Photo p WHERE p.step.id = :stepId")
    Integer countPhotosByStepId(@Param("stepId") Long stepId);
}
