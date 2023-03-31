package com.gymguru.frontend.external.app.domain;

import java.util.List;

public class Plan {
    private Long id;
    private String description;
    private List<Meal> meals;
    private List<Exercise> exercises;
    private User user;
}
