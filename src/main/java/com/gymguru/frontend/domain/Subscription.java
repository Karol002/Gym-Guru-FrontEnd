package com.gymguru.frontend.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Subscription {
    private Long id;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private User user;
    private Trainer trainer;
}
