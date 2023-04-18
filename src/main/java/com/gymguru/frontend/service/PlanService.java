package com.gymguru.frontend.service;

import com.gymguru.frontend.cllient.ExerciseClient;
import com.gymguru.frontend.cllient.MealClient;
import com.gymguru.frontend.cllient.PlanClient;
import com.gymguru.frontend.domain.edit.EditExercise;
import com.gymguru.frontend.domain.edit.EditMeal;
import com.gymguru.frontend.domain.edit.EditPlan;
import com.gymguru.frontend.domain.save.SaveExercise;
import com.gymguru.frontend.domain.save.SaveMeal;
import com.gymguru.frontend.domain.save.SavePlan;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanClient planClient;
    private final ExerciseClient exerciseClient;
    private final MealClient mealClient;
    private final Logger logger = LoggerFactory.getLogger(PlanService.class);

    public EditPlan getPlan(Long userId) {
        try {
            return planClient.getPlan(userId);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public boolean createPlan(String dietDescription, String exerciseDescription, Long userId, Long trainerId, List<SaveExercise> saveExerciseDtos, List<SaveMeal> saveMealDtos) {
        try {
            SavePlan savePlan = new SavePlan(dietDescription, exerciseDescription, userId, trainerId, saveExerciseDtos, saveMealDtos);
            return planClient.createPlan(savePlan).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public Set<EditExercise> getExercisesByPlanId(Long planId) {
        try {
            return new HashSet<>(exerciseClient.getExercisesByPlanId(planId));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public Set<EditMeal> getMealsByPlanId(Long planId) {
        try {
            return new HashSet<>(mealClient.getMealsByPlanId(planId));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public boolean updateExercise(EditExercise exercise) {
        try {
            return exerciseClient.updateExercise(exercise).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public boolean updateMeal(EditMeal editMeal) {
        try {
            return mealClient.updateMeal(editMeal).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public boolean updatePlan(EditPlan editPlan) {
        try {
            return planClient.updatePlan(editPlan).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }
}
