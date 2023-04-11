package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.AuthToken;
import com.gymguru.frontend.domain.Role;
import com.gymguru.frontend.domain.dto.PasswordChanger;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.external.app.cllient.AuthClient;
import com.gymguru.frontend.domain.dto.CredentialDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
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
            AuthToken token = authClient.getAuthToken(credentialDto);
            if (token == null) {
                return false;
            } else {
                SessionMemoryDto sessionMemoryDto = new SessionMemoryDto(token.getToken(), token.getRole(), email, token.getUserId());
                VaadinSession.getCurrent().setAttribute(SessionMemoryDto.class, sessionMemoryDto);
                return true;
            }
        } catch (HttpClientErrorException exception) {
            logger.warn("Sign In failed");
            return false;
        }
    }

    public boolean changePassword(String email, String oldPassword, String newPassword) {
        PasswordChanger passwordChanger =  new PasswordChanger(email, oldPassword, newPassword);
        try {
             return authClient.changePassword(passwordChanger).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn("Password change failed");
            return false;
        }
    }

    public void logOut() {
        UI.getCurrent().getPage().setLocation("/gymguru");
        VaadinSession.getCurrent().close();
    }
}
