package com.recipeapp.recipemanagementsystem.repositories;

import com.recipeapp.recipemanagementsystem.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByRecipeId(Long recipeId);

    List<Photo> findByStepId(Long stepId);

    List<Photo> findByMimeType(String mimeType);

    boolean existsByRecipeId(Long recipeId);

    boolean existsByStepId(Long stepId);

    @Query("SELECT COUNT(p) FROM Photo p WHERE p.recipe.id = :recipeId")
    Integer countPhotosByRecipeId(@Param("recipeId") Long recipeId);

    @Query("SELECT COUNT(p) FROM Photo p WHERE p.step.id = :stepId")
    Integer countPhotosByStepId(@Param("stepId") Long stepId);
}
