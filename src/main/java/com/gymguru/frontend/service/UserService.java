package com.gymguru.frontend.service;

import com.gymguru.frontend.cllient.UserClient;
import com.gymguru.frontend.domain.edit.EditUser;
import com.gymguru.frontend.domain.save.SaveUserAccount;
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
            return userClient.createUser(new SaveUserAccount(email, password, firstName, lastName)).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public EditUser getUserById(Long id) {
        try {
            return userClient.getUser(id);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return new EditUser();
        }
    }

    public boolean  updateUser(EditUser editUser) {
        try {
            return userClient.updateUser(editUser).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

}
