package com.gymguru.frontend.service;

import com.gymguru.frontend.cllient.AuthClient;
import com.gymguru.frontend.domain.authorization.AuthToken;
import com.gymguru.frontend.domain.authorization.Credential;
import com.gymguru.frontend.domain.authorization.PasswordChanger;
import com.gymguru.frontend.domain.authorization.SessionMemory;
import com.gymguru.frontend.domain.enums.Role;
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
        Credential credential = new Credential(email, password);
        try {
            AuthToken token = authClient.getAuthToken(credential);
            if (token == null) {
                return false;
            } else {
                SessionMemory sessionMemory = new SessionMemory(token.getToken(), token.getRole(), email, token.getUserId());
                VaadinSession.getCurrent().setAttribute(SessionMemory.class, sessionMemory);
                return true;
            }
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public boolean changePassword(String email, String oldPassword, String newPassword) {
        PasswordChanger passwordChanger =  new PasswordChanger(email, oldPassword, newPassword);
        try {
             return authClient.changePassword(passwordChanger).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public boolean checkAuth(Role role) {
        SessionMemory sessionMemory = VaadinSession.getCurrent().getAttribute(SessionMemory.class);
        if (sessionMemory == null || sessionMemory.getRole() != role) {
            clearSession();
            return true;
        } else return false;
    }

    public void clearSession() {
        UI.getCurrent().getPage().setLocation("/gymguru");
        VaadinSession.getCurrent().close();
    }
}
