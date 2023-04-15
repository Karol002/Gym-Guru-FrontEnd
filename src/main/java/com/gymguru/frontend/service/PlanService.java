package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.Meal;
import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.*;
import com.gymguru.frontend.external.app.cllient.PlanClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanClient planClient;

    public Plan getPlan(Long userId) {
        try {
            return planClient.getPlan(userId);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public boolean createPlan(String dietDescription, String exerciseDescription, Long userId, Long trainerId, List<Exercise> exerciseDtos, List<Meal> mealDtos) {
        Plan plan = new Plan(0L, dietDescription, exerciseDescription, userId, trainerId, exerciseDtos, mealDtos);
        return planClient.createPlan(plan).is2xxSuccessful();
    }

    public Set<ExerciseWithId> getExercisesByPlanId(Long planId) {
        return new HashSet<>(planClient.getExercisesByPlanId(planId));
    }

    public Set<MealWithId> getMealsByPlanId(Long planId) {
        return new HashSet<>(planClient.getMealsByPlanId(planId));
    }

    public boolean updateExercise(ExerciseWithId exercise) {
        return planClient.updateExercise(exercise).is2xxSuccessful();
    }

    public boolean updateMeal(MealWithId mealWithId) {
        return planClient.updateMeal(mealWithId).is2xxSuccessful();
    }

    public boolean updatePlan(PlanDto planDto) {
        return planClient.updatePlan(planDto).is2xxSuccessful();
    }
}
