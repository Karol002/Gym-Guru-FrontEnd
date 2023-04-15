package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.Specialization;
import com.gymguru.frontend.domain.dto.TrainerAccount;
import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.external.app.config.BackendClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TrainerClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public HttpStatus createTrainerAccount(TrainerAccount trainer) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, trainer, Void.class).getStatusCode();
    }

    public List<TrainerDto> getAllTrainers() throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        TrainerDto[] trainers = restTemplate.getForObject(url, TrainerDto[].class);
        assert trainers != null;
        return Arrays.asList(trainers);
    }


    public List<TrainerDto> getAllTrainersBySpecialization(Specialization specialization) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer() + "/specialization/" + specialization)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), TrainerDto[].class).getBody()));
    }

    public TrainerDto getTrainerById(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer() + "/single/" + id)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), TrainerDto.class).getBody();
    }

    public HttpStatus updateTrainer(TrainerDto trainerDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(trainerDto), Void.class).getStatusCode();
    }
}
