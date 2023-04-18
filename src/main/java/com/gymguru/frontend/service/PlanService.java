package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.Meal;
import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.*;
import com.gymguru.frontend.external.app.cllient.AuthClient;
import com.gymguru.frontend.external.app.cllient.ExerciseClient;
import com.gymguru.frontend.external.app.cllient.MealClient;
import com.gymguru.frontend.external.app.cllient.PlanClient;
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

    public Plan getPlan(Long userId) {
        try {
            return planClient.getPlan(userId);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public boolean createPlan(String dietDescription, String exerciseDescription, Long userId, Long trainerId, List<Exercise> exerciseDtos, List<Meal> mealDtos) {
        try {
            Plan plan = new Plan(0L, dietDescription, exerciseDescription, userId, trainerId, exerciseDtos, mealDtos);
            return planClient.createPlan(plan).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public Set<ExerciseWithId> getExercisesByPlanId(Long planId) {
        try {
            return new HashSet<>(exerciseClient.getExercisesByPlanId(planId));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public Set<MealWithId> getMealsByPlanId(Long planId) {
        try {
            return new HashSet<>(mealClient.getMealsByPlanId(planId));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public boolean updateExercise(ExerciseWithId exercise) {
        try {
            return exerciseClient.updateExercise(exercise).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public boolean updateMeal(MealWithId mealWithId) {
        try {
            return mealClient.updateMeal(mealWithId).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public boolean updatePlan(PlanDto planDto) {
        try {
            return planClient.updatePlan(planDto).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }
}
