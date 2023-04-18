package com.gymguru.frontend.domain.read;


import com.gymguru.frontend.domain.edit.SaveEditSubscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ReadSubscriptionWithUserSave extends SaveEditSubscription {
    private String userFirstName;
    private String userLastName;

    public ReadSubscriptionWithUserSave(BigDecimal price, LocalDate startDate, LocalDate endDate, Long userId, Long trainerId, String userFirstName, String userLastName) {
        super(price, startDate, endDate, userId, trainerId);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }
}
