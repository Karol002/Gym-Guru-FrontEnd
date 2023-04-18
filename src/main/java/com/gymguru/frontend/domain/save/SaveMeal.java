package com.gymguru.frontend.domain.save;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveMeal {
    private String name;
    private String cookInstruction;
}
