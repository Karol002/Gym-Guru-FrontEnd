package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.SubscriptionDto;
import com.gymguru.frontend.domain.dto.MealDto;
import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EdamamClient {
    private final BackendEndpointConfiguration backendEndpointConfiguration;
    private final RestTemplate restTemplate;

    public List<MealDto> getMeals(String mealName) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint()
                        + backendEndpointConfiguration.getEdamam() + "/meals/" + mealName)
                .build()
                .encode()
                .toUri();

        return List.of(restTemplate.getForObject(url, MealDto[].class));
    }
}
