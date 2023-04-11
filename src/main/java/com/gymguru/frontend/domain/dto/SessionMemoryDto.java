package com.gymguru.frontend.domain.dto;

import com.gymguru.frontend.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionMemoryDto {
    private String token;
    private Role role;
    private String email;
    private Long id;

}
