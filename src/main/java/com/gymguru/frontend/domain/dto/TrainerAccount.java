package com.gymguru.frontend.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gymguru.frontend.domain.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TrainerAccount {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String education;
    private final String description;
    private BigDecimal monthPrice;
    private Specialization specialization;
}

   /* public static class TrainerAccountBuilder {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String description;
        private String education;

        public TrainerAccountBuilder email(String email) {
            this.email = email;
            return this;
        }

        public TrainerAccountBuilder password(String password) {
            this.password = password;
            return this;
        }

        public TrainerAccountBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TrainerAccountBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TrainerAccountBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrainerAccountBuilder education(String education) {
            this.education = education;
            return this;
        }


        public TrainerAccount build() {
            return new TrainerAccount(email,password, firstName, lastName, description, education);
        }
    }

    public TrainerAccount(String email, String password, String firstName, String lastName, String description, String education) {
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
}*/
