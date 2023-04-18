package com.gymguru.frontend.cllient;

import com.gymguru.frontend.config.BackendClientConfiguration;
import com.gymguru.frontend.domain.read.ReadWgerCategory;
import com.gymguru.frontend.domain.read.ReadWgerExercise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WgerClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public List<ReadWgerCategory> getCategories() throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getWger() + "/categories")
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull
                (restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), ReadWgerCategory[].class).getBody()));
    }

    public List<ReadWgerExercise> getExercises(Long categoryId) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getWger() + "/exercises/" + categoryId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), ReadWgerExercise[].class).getBody()));
    }
}
