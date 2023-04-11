package com.gymguru.frontend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserToSaveDto {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
}
