package com.gymguru.frontend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MealDto {
    private Long id;
    private String name;
    private String cookInstruction;
    private Long planId;
}
