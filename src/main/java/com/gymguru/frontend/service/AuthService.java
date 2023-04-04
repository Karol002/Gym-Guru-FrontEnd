package com.gymguru.frontend.service;

import com.gymguru.frontend.external.app.cllient.AuthClient;
import com.gymguru.frontend.external.app.cllient.CredentialDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthClient authClient;
    private final Logger logger = LoggerFactory.getLogger(AuthClient.class);

    public boolean signIn(String email, String password) {
        CredentialDto credentialDto = new CredentialDto(email, password);
        try {
            return !authClient.getAuthToken(credentialDto).isEmpty();
        } catch (HttpClientErrorException exception) {
            logger.warn("Sign In failed");
            return false;
        }
    }
}
