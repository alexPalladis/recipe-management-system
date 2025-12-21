package com.recipeapp.recipemanagementsystem.config;

import com.recipeapp.recipemanagementsystem.entities.Photo;
import com.recipeapp.recipemanagementsystem.entities.Step;
import com.recipeapp.recipemanagementsystem.repositories.PhotoRepository;
import com.recipeapp.recipemanagementsystem.repositories.StepRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StepPhotoSeeder implements CommandLineRunner {

    private final PhotoRepository photoRepository;
    private final StepRepository stepRepository;

    public StepPhotoSeeder(PhotoRepository photoRepository, StepRepository stepRepository) {
        this.photoRepository = photoRepository;
        this.stepRepository = stepRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<Step> steps = stepRepository.findAll();
        System.out.println("[StepPhotoSeeder] steps found: " + steps.size());

        for (Step step : steps) {
            Long stepId = step.getId();

            if (photoRepository.existsByStepId(stepId)) continue;

            String fileName = "step-" + stepId + ".jpg";
            ClassPathResource res = new ClassPathResource("seed-photos/steps/" + fileName);

            if (!res.exists()) {
                System.out.println("[StepPhotoSeeder] missing " + fileName + " for stepId=" + stepId);
                continue;
            }

            byte[] bytes = res.getInputStream().readAllBytes();

            Photo p = new Photo();
            p.setFileName(fileName);
            p.setMimeType("image/jpeg");
            p.setImageData(bytes);
            p.setStep(step);

            photoRepository.save(p);
        }
    }
}

