package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.User;
import com.gymguru.frontend.external.app.cllient.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;

    public boolean createUser(String email, String password, String firstName, String lastName) {
        try {
            return userClient.createUser(new User(email, password, firstName, lastName)).is2xxSuccessful();
        } catch (ResourceAccessException exception) {
            return false;
        }
    }
}
