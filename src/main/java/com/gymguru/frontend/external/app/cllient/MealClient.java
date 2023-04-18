package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.dto.MealDto;
import com.gymguru.frontend.external.app.config.BackendClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MealClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public List<MealDto> getMealsByPlanId(Long planId) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getMeal() + "/plan/" + planId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), MealDto[].class).getBody()));
    }

    public HttpStatus updateMeal(MealDto mealDto) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getMeal())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(mealDto), Void.class).getStatusCode();
    }
}
