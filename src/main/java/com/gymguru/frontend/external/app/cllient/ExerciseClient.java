package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.dto.ExerciseDto;
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
public class ExerciseClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public List<ExerciseDto> getExercisesByPlanId(Long planId) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getExercise() + "/plan/" + planId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), ExerciseDto[].class).getBody()));

    }

    public HttpStatus updateExercise(ExerciseDto exercise) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getExercise())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(exercise), Void.class).getStatusCode();
    }
}
