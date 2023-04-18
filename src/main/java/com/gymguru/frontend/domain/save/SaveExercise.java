package com.gymguru.frontend.domain.save;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveExercise {
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
}
