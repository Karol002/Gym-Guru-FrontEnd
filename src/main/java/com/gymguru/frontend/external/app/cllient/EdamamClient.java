package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.dto.MealDto;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.external.app.config.BackendClientConfiguration;

import com.vaadin.flow.server.VaadinSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EdamamClient {
    private final BackendClientConfiguration backendClientConfiguration;
    private final RestTemplate restTemplate;

    public List<MealDto> getMeals(String mealName) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint()
                        + backendClientConfiguration.getEdamam() + "/meals/" + mealName)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), MealDto[].class).getBody()));
    }
}