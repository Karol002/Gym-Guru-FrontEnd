package com.gymguru.frontend.cllient;

import com.gymguru.frontend.config.BackendClientConfiguration;
import com.gymguru.frontend.domain.edit.EditPlan;
import com.gymguru.frontend.domain.save.SavePlan;
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

    public EditPlan getPlan(Long userId) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint()
                        + backendClientConfiguration.getPlan() + "/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), EditPlan.class).getBody();
    }

    public HttpStatus createPlan(SavePlan savePlan) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getPlan())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, backendClientConfiguration.getAuthorizationEntity(savePlan), Void.class).getStatusCode();
    }

    public HttpStatus updatePlan(EditPlan editPlan) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getPlan())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(editPlan), Void.class).getStatusCode();
    }
}
