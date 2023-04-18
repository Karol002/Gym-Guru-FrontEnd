package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.PlanDto;
import com.gymguru.frontend.external.app.config.BackendClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PlanClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public Plan getPlan(Long userId) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint()
                        + backendClientConfiguration.getPlan() + "/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), Plan.class).getBody();
    }

    public HttpStatus createPlan(Plan plan) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getPlan())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, backendClientConfiguration.getAuthorizationEntity(plan), Void.class).getStatusCode();
    }

    public HttpStatus updatePlan(PlanDto planDto) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getPlan())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(planDto), Void.class).getStatusCode();
    }
}
