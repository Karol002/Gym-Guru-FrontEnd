package com.gymguru.frontend.domain.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordChanger {
    private String email;
    private String oldPassword;
    private String newPassword;
}
