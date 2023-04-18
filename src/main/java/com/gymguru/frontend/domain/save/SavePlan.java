package com.gymguru.frontend.domain.save;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SavePlan {
    private String dietDescription;
    private String exerciseDescription;
    private Long userId;
    private Long trainerId;
    private List<SaveExercise> saveExerciseDtos;
    private List<SaveMeal> saveMealDtos;
}
