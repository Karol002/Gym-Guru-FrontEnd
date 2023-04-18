package com.gymguru.frontend.service;

import com.gymguru.frontend.cllient.SubscriptionClient;
import com.gymguru.frontend.cllient.UserClient;
import com.gymguru.frontend.domain.edit.SaveEditSubscription;
import com.gymguru.frontend.domain.edit.EditUser;
import com.gymguru.frontend.domain.read.ReadSubscriptionWithUserSave;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
            return subscriptionClient.subscribe(new SaveEditSubscription(price.multiply(BigDecimal.valueOf(sublength)), startDate, endDate, userId, trainerId)).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public Set<ReadSubscriptionWithUserSave> getSubscriptionsWithPlanByTrainerId(Long trainerId) {
        try {
            List<SaveEditSubscription> editSubscriptions = subscriptionClient.getSubscriptionsWithPlanByTrainerId(trainerId);
            return new HashSet<>(mapToSubscriptionWithUsers(editSubscriptions));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }


    public Set<ReadSubscriptionWithUserSave> getSubscriptionsWithOutPlanByTrainerId(Long trainerId) {
        try {
            List<SaveEditSubscription> editSubscriptions = subscriptionClient.getSubscriptionsWithoutPlanByTrainerId(trainerId);
            return new HashSet<>(mapToSubscriptionWithUsers(editSubscriptions));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public Set<ReadSubscriptionWithUserSave> getSubscriptionsByTrainerId(Long trainerId) {
        try {
            List<SaveEditSubscription> editSubscriptions = subscriptionClient.getAllSubscriptionsByTrainerId(trainerId);
            return new HashSet<>(mapToSubscriptionWithUsers(editSubscriptions));
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

    public SaveEditSubscription getSubscription(Long userId) {
        try {
            return subscriptionClient.getSubscriptionByUserId(userId);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return new SaveEditSubscription();
        }
    }

    public long getMaxExtendSubscription(SaveEditSubscription editSubscription) {
        long mothDifference = monthsBetween(editSubscription.getStartDate(), editSubscription.getEndDate());
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

    private List<ReadSubscriptionWithUserSave> mapToSubscriptionWithUsers(List<SaveEditSubscription> editSubscriptions) {
        List<ReadSubscriptionWithUserSave> subscriptionsWithUser = new ArrayList<>();
        for (SaveEditSubscription editSubscription : editSubscriptions) {
            EditUser editUser = userClient.getUser(editSubscription.getUserId());
            subscriptionsWithUser.add(new ReadSubscriptionWithUserSave(
                    editSubscription.getPrice(),
                    editSubscription.getStartDate(),
                    editSubscription.getEndDate(),
                    editSubscription.getUserId(),
                    editSubscription.getTrainerId(),
                    editUser.getFirstName(),
                    editUser.getLastName()
            ));
        }

        return subscriptionsWithUser;
    }

}
