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
    private String description;
    private UserDto user;
    private TrainerDto trainer;
    private List<Exercise> exercises;
    private List<Meal> meals;
}
