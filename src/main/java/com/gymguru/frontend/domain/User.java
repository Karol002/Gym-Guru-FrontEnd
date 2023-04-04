package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
/*  private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    public static class UserBuilder {
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public User build() {
            return new User(email, password, firstName, lastName);
        }
    }

    private User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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
    }*/
