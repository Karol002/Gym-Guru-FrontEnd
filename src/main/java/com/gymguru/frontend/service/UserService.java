package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.dto.UserToSaveDto;
import com.gymguru.frontend.domain.dto.UserDto;
import com.gymguru.frontend.external.app.cllient.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;

    public boolean createUser(String email, String password, String firstName, String lastName) {
        try {
            return userClient.createUser(new UserToSaveDto(email, password, firstName, lastName)).is2xxSuccessful();
        } catch (ResourceAccessException exception) {
            return false;
        }
    }

    public UserDto getUserById(Long id) {
        return userClient.getUser(id);
    }

    public List<String> getImpossibleEmails() {
        return userClient.getImpossibleEmails();
    }

  /*  public void  updateUser(Long id, String firstName, String lastName, Long credentialId, String email) {
        UserDto userDto = new UserDto(id, firstName, lastName, credentialId);
        userClient.updateUser(userDto, email);
    }*/
}
