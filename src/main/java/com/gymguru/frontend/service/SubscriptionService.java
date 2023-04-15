package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.SubscriptionDto;
import com.gymguru.frontend.domain.dto.SubscriptionWithUserDto;
import com.gymguru.frontend.domain.dto.UserDto;
import com.gymguru.frontend.external.app.cllient.SubscriptionClient;
import com.gymguru.frontend.external.app.cllient.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionClient subscriptionClient;
    private final UserClient userClient;

    public boolean subscribe(Integer sublength, BigDecimal price, LocalDate startDate, LocalDate endDate, Long userId, Long trainerId) {
        try {
            return subscriptionClient.subscribe(new SubscriptionDto(price.multiply(BigDecimal.valueOf(sublength)), startDate, endDate, userId, trainerId)).is2xxSuccessful();
        } catch (ResourceAccessException exception) {
            return false;
        }
    }

    public List<SubscriptionWithUserDto> getSubscriptionsWithPlanByTrainerId(Long trainerId) {
        List<SubscriptionDto> subscriptions = subscriptionClient.getSubscriptionsWithPlanByTrainerId(trainerId);
        return mapToSubscriptionWithUsers(subscriptions);
    }


    public List<SubscriptionWithUserDto> getSubscriptionsWithOutPlanByTrainerId(Long trainerId) {
        List<SubscriptionDto> subscriptions = subscriptionClient.getSubscriptionsWithoutPlanByTrainerId(trainerId);
        return mapToSubscriptionWithUsers(subscriptions);
    }

    public List<SubscriptionWithUserDto> getSubscriptionsByTrainerId(Long trainerId) {
        List<SubscriptionDto> subscriptions = subscriptionClient.getAllSubscriptionsByTrainerId(trainerId);
        return mapToSubscriptionWithUsers(subscriptions);
    }

    public boolean checkStatus(Long userId) {
        try {
            return subscriptionClient.checkStatus(userId);
        } catch (ResourceAccessException exception) {
            return false;
        }
    }

    public boolean extendSubscription(Long userId, Long monthQuantity) {
        return subscriptionClient.extendSubscription(userId, monthQuantity).is2xxSuccessful();
    }

    public SubscriptionDto getSubscription(Long userId) {
            return subscriptionClient.getSubscriptionByUserId(userId);
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
