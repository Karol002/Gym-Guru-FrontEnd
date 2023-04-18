package com.gymguru.frontend.domain.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private String email;
    private String password;
}
