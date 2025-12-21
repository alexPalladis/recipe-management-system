package com.recipeapp.recipemanagementsystem.config;

import com.recipeapp.recipemanagementsystem.entities.Photo;
import com.recipeapp.recipemanagementsystem.entities.Recipe;
import com.recipeapp.recipemanagementsystem.repositories.PhotoRepository;
import com.recipeapp.recipemanagementsystem.repositories.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RecipePhotoSeeder implements CommandLineRunner {

    private final PhotoRepository photoRepository;
    private final RecipeRepository recipeRepository;

    public RecipePhotoSeeder(PhotoRepository photoRepository, RecipeRepository recipeRepository) {
        this.photoRepository = photoRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<Recipe> recipes = recipeRepository.findAll();
        System.out.println("[RecipePhotoSeeder] recipes found: " + recipes.size());

        for (Recipe recipe : recipes) {
            Long id = recipe.getId();

            if (photoRepository.existsByRecipeId(id)) continue;

            String fileName = id + ".jpg";
            ClassPathResource res = new ClassPathResource("seed-photos/recipes/" + fileName);

            if (!res.exists()) {
                System.out.println("[RecipePhotoSeeder] missing photo: " + fileName + " (skip recipeId=" + id + ")");
                continue;
            }

            byte[] bytes = res.getInputStream().readAllBytes();

            Photo p = new Photo();
            p.setFileName(fileName);
            p.setMimeType("image/jpeg");
            p.setImageData(bytes);
            p.setDescription(recipe.getName());
            p.setRecipe(recipe);

            photoRepository.save(p);
            System.out.println("[RecipePhotoSeeder] inserted photo for recipeId=" + id);
        }
    }
}

