package com.gymguru.frontend.cllient;

import com.gymguru.frontend.config.BackendClientConfiguration;
import com.gymguru.frontend.domain.edit.EditTrainer;
import com.gymguru.frontend.domain.enums.Specialization;
import com.gymguru.frontend.domain.save.SaveTrainerAccount;
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

    public HttpStatus createTrainerAccount(SaveTrainerAccount trainer) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, trainer, Void.class).getStatusCode();
    }

    public List<EditTrainer> getAllTrainers() throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.getForObject(url, EditTrainer[].class)));
    }

    public List<EditTrainer> getAllTrainersBySpecialization(Specialization specialization) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer() + "/specialization/" + specialization)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), EditTrainer[].class).getBody()));
    }

    public EditTrainer getTrainerById(Long id) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer() + "/single/" + id)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), EditTrainer.class).getBody();
    }

    public HttpStatus updateTrainer(EditTrainer editTrainer) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(editTrainer), Void.class).getStatusCode();
    }
}
