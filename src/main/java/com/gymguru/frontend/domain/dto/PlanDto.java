package com.gymguru.frontend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanDto {
    private Long id;
    private String description;
    private Long userId;
}
