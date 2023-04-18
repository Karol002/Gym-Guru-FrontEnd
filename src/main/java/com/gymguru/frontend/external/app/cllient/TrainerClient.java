package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.enums.Specialization;
import com.gymguru.frontend.domain.dto.TrainerAccount;
import com.gymguru.frontend.domain.Trainer;
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
public class TrainerClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public HttpStatus createTrainerAccount(TrainerAccount trainer) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, trainer, Void.class).getStatusCode();
    }

    public List<Trainer> getAllTrainers() throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.getForObject(url, Trainer[].class)));
    }

    public List<Trainer> getAllTrainersBySpecialization(Specialization specialization) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer() + "/specialization/" + specialization)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), Trainer[].class).getBody()));
    }

    public Trainer getTrainerById(Long id) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer() + "/single/" + id)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), Trainer.class).getBody();
    }

    public HttpStatus updateTrainer(Trainer trainer) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(trainer), Void.class).getStatusCode();
    }
}
