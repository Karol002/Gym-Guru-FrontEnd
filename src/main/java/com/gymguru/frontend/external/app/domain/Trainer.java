package com.gymguru.frontend.external.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
public class Trainer {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private List<Subscription> subscriptions;

    public Trainer(Long id, String email, String password, String firstName, String lastName, String description, String education) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
    }
}
