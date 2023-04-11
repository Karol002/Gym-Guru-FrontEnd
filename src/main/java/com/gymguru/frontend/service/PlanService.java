package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.external.app.cllient.PlanClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanClient planClient;

    public Plan getPlan(Long userId) {
        try {
            return planClient.getPlan(userId);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }
}
