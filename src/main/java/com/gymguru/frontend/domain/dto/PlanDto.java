package com.gymguru.frontend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanDto {
    private Long id;
    private String dietDescription;
    private String exerciseDescription;
    private Long userId;
    private Long trainerId;
}
