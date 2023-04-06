package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}