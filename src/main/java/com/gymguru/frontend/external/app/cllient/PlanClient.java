package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;

    public Plan getPlan(Long userId) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint()
                        + backendEndpointConfiguration.getPlan() + "/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, Plan.class);
    }
}
