package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    private String dietDescription;
    private String exerciseDescription;
    private Long userId;
    private Long trainerId;
    private List<Exercise> exercises;
    private List<Meal> meals;
}
