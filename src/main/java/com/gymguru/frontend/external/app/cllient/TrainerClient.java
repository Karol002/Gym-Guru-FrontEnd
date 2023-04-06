package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.dto.TrainerAccount;
import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;

    public HttpStatus createTrainerAccount(TrainerAccount trainer) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, trainer, Void.class).getStatusCode();
    }

    public List<TrainerDto> getAllTrainers() throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getTrainer())
                .build()
                .encode()
                .toUri();

        TrainerDto[] trainers = restTemplate.getForObject(url, TrainerDto[].class);
        return Arrays.asList(trainers);
    }
}
