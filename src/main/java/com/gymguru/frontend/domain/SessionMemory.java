package com.gymguru.frontend.domain;

import com.gymguru.frontend.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionMemory {
    private String token;
    private Role role;
    private String email;
    private Long id;
}
