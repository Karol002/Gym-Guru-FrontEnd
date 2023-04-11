package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meal {
    private String name;
    private String cookInstruction;
    private Plan plan;
}
