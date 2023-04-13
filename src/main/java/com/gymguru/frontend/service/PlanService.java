package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.Meal;
import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.ExerciseDto;
import com.gymguru.frontend.domain.dto.MealDto;
import com.gymguru.frontend.external.app.cllient.PlanClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

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

    public List<Exercise> getExercisesByPlanId(Long planId) {
        return planClient.getExercisesByPlanId(planId);
    }

    public List<Meal> getMealsByPlanId(Long planId) {
        return planClient.getMealsByPlanId(planId);
    }
}
