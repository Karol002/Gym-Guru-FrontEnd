package com.gymguru.frontend.domain.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gymguru.frontend.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthToken {
    @JsonProperty("token")
    private String token;
    @JsonProperty("role")
    private Role role;
    @JsonProperty("userId")
    private Long userId;
}
