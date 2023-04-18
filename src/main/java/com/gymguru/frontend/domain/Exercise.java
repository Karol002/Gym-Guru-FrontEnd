package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    private String name;
    private String description;
    private int seriesQuantity;
    private int repetitionsQuantity;
}
