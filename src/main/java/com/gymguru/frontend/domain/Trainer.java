package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Trainer {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String description;
    private String education;

}
/*private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String description;
    private final String education;
    private List<Subscription> subscriptions;

    public static class TrainerBuilder {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String description;
        private String education;
        private List<Subscription> subscriptions;

        public TrainerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public TrainerBuilder password(String password) {
            this.password = password;
            return this;
        }

        public TrainerBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TrainerBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TrainerBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrainerBuilder education(String education) {
            this.education = education;
            return this;
        }

        public TrainerBuilder subscriptions(List<Subscription> subscriptions) {
            this.subscriptions = subscriptions;
            return this;
        }

        public Trainer build() {
            return new Trainer(email,password, firstName, lastName, description, education);
        }
    }

    public Trainer(String email, String password, String firstName, String lastName, String description, String education) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return description;
    }

    public String getEducation() {
        return education;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }*/