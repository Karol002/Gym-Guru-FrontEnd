package com.gymguru.frontend.service;

import com.gymguru.frontend.external.app.domain.User;
import lombok.Getter;
import org.atmosphere.config.service.Get;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
public class UserService {
    private Set<User> users;
    private static  UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public UserService() {
        users = new HashSet<>();
        users.add(new User(1L,"karo","karol1","karol2", "karol3"));
        users.add(new User(3L,"karaao","karolas1","karol2", "karol3"));
        users.add(new User(4L,"karso","karolas1","karol2", "karol3"));
        users.add(new User(5L,"kardo","karasol1","karol2", "karol3"));
        users.add(new User(6L,"karaso","karol1","karol2", "karol3"));
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUsers(User user) {
        users.add(user);
    }
}
