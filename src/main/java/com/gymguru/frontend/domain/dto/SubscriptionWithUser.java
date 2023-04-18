package com.gymguru.frontend.domain.dto;


import com.gymguru.frontend.domain.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionWithUser extends Subscription {
    private String userFirstName;
    private String userLastName;

    public SubscriptionWithUser(BigDecimal price, LocalDate startDate, LocalDate endDate, Long userId, Long trainerId, String userFirstName, String userLastName) {
        super(price, startDate, endDate, userId, trainerId);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }
}
