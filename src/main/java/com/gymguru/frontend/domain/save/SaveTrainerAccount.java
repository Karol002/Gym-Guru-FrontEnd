package com.gymguru.frontend.domain.save;

import com.gymguru.frontend.domain.enums.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class SaveTrainerAccount {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String education;
    private final String description;
    private BigDecimal monthPrice;
    private Specialization specialization;
}
