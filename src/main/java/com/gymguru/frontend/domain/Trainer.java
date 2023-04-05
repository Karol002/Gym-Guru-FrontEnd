package com.gymguru.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Trainer {
    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String education;
    private List<Subscription> subscriptions;
    private List<Plan> plans;
}