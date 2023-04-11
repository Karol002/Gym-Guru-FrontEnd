package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Exercise {
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
    private Plan plan;
}
