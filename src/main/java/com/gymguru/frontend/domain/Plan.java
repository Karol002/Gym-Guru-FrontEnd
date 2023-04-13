package com.gymguru.frontend.domain;

import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    private Long id;
    private String dietDescription;
    private String exerciseDescription;
    private Long userId;
    private Long trainerId;
    private List<Exercise> saveExerciseDtos;
    private List<Meal> saveMealDtos;
}
