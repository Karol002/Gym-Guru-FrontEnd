package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.User;
import com.gymguru.frontend.domain.UserAccount;
import com.gymguru.frontend.external.app.cllient.UserClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean createUser(String email, String password, String firstName, String lastName) {
        try {
            return userClient.createUser(new UserAccount(email, password, firstName, lastName)).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public User getUserById(Long id) {
        try {
            return userClient.getUser(id);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return new User();
        }
    }

    public boolean  updateUser(User user) {
        try {
            return userClient.updateUser(user).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

}
