package com.gymguru.frontend.domain.save;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveUserAccount {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
}
