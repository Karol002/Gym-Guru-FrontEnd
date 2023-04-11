package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.SubscriptionDto;
import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;

    public HttpStatus subscribe(SubscriptionDto subscriptionDto) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getSubscription())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, subscriptionDto, Void.class).getStatusCode();
    }

    public Boolean checkStatus(Long userId) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getSubscription() + "/active/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, Boolean.class);
    }

    public SubscriptionDto getSubscriptionByUserId(Long userId) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getSubscription() + "/user/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, SubscriptionDto.class);
    }

    public List<SubscriptionDto> getSubscriptionsWithoutPlanByTrainerId(Long trainerId) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getSubscription() + "/without/plan/" + trainerId)
                .build()
                .encode()
                .toUri();

        return List.of(restTemplate.getForObject(url, SubscriptionDto[].class));
    }

    public List<SubscriptionDto> getAllSubscriptionsByTrainerId(Long trainerId) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getSubscription() + "/trainer/" + trainerId)
                .build()
                .encode()
                .toUri();

        return List.of(restTemplate.getForObject(url, SubscriptionDto[].class));
    }
}
