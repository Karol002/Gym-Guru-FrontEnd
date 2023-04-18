package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.SubscriptionDto;
import com.gymguru.frontend.domain.dto.SubscriptionWithUserDto;
import com.gymguru.frontend.domain.dto.UserDto;
import com.gymguru.frontend.external.app.cllient.AuthClient;
import com.gymguru.frontend.external.app.cllient.SubscriptionClient;
import com.gymguru.frontend.external.app.cllient.UserClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final static long MAX_SUB_TIME_IN_MONTH = 6L;
    private final SubscriptionClient subscriptionClient;
    private final UserClient userClient;
    private final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    public boolean subscribe(Integer sublength, BigDecimal price, LocalDate startDate, LocalDate endDate, Long userId, Long trainerId) {
        try {
            return subscriptionClient.subscribe(new SubscriptionDto(price.multiply(BigDecimal.valueOf(sublength)), startDate, endDate, userId, trainerId)).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public Set<SubscriptionWithUserDto> getSubscriptionsWithPlanByTrainerId(Long trainerId) {
        try {
            List<SubscriptionDto> subscriptions = subscriptionClient.getSubscriptionsWithPlanByTrainerId(trainerId);
            return new HashSet<>(mapToSubscriptionWithUsers(subscriptions));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }


    public Set<SubscriptionWithUserDto> getSubscriptionsWithOutPlanByTrainerId(Long trainerId) {
        try {
            List<SubscriptionDto> subscriptions = subscriptionClient.getSubscriptionsWithoutPlanByTrainerId(trainerId);
            return new HashSet<>(mapToSubscriptionWithUsers(subscriptions));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public Set<SubscriptionWithUserDto> getSubscriptionsByTrainerId(Long trainerId) {
        try {
            List<SubscriptionDto> subscriptions = subscriptionClient.getAllSubscriptionsByTrainerId(trainerId);
            return new HashSet<>(mapToSubscriptionWithUsers(subscriptions));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public boolean checkStatus(Long userId) {
        try {
            return subscriptionClient.checkStatus(userId);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public void extendSubscription(Long userId, Long monthQuantity) {
        try {
            subscriptionClient.extendSubscription(userId, monthQuantity);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
        }
    }

    public SubscriptionDto getSubscription(Long userId) {
        try {
            return subscriptionClient.getSubscriptionByUserId(userId);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return new SubscriptionDto();
        }
    }

    public long getMaxExtendSubscription(SubscriptionDto subscriptionDto) {
        long mothDifference = monthsBetween(subscriptionDto.getStartDate(), subscriptionDto.getEndDate());
        return (MAX_SUB_TIME_IN_MONTH - mothDifference);
    }

    private long monthsBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.MONTHS.between(date1.withDayOfMonth(1), date2.withDayOfMonth(1));
    }

    public List<Long> getSubscriptionLengthList(Long maxExtend) {
        List<Long> subscriptionLengthList = new ArrayList<>();
        for (long i = 1; i <= maxExtend; i++) subscriptionLengthList.add(i);
        return subscriptionLengthList;
    }

    private List<SubscriptionWithUserDto> mapToSubscriptionWithUsers(List<SubscriptionDto> subscriptions) {
        List<SubscriptionWithUserDto> subscriptionsWithUser = new ArrayList<>();
        for (SubscriptionDto subscriptionDto : subscriptions) {
            UserDto userDto = userClient.getUser(subscriptionDto.getUserId());
            subscriptionsWithUser.add(new SubscriptionWithUserDto(
                    subscriptionDto.getPrice(),
                    subscriptionDto.getStartDate(),
                    subscriptionDto.getEndDate(),
                    subscriptionDto.getUserId(),
                    subscriptionDto.getTrainerId(),
                    userDto.getFirstName(),
                    userDto.getLastName()
            ));
        }

        return subscriptionsWithUser;
    }

}
