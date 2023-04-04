package com.gymguru.frontend.external.app.cllient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDto {
    private String email;
    private String password;
}
