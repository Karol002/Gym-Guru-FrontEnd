package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAccount {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
}
