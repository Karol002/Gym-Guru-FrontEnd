package com.gymguru.frontend.cllient;

import com.gymguru.frontend.config.BackendClientConfiguration;
import com.gymguru.frontend.domain.edit.EditSubscription;
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
public class SubscriptionClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public HttpStatus subscribe(EditSubscription editSubscription) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getSubscription())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, backendClientConfiguration.getAuthorizationEntity(editSubscription), Void.class).getStatusCode();
    }

    public void extendSubscription(Long userId, Long monthQuantity) throws HttpClientErrorException  {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getSubscription()
                        + "/extend/" + userId + "/" + monthQuantity )
                .build()
                .encode()
                .toUri();

        restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(), Void.class).getStatusCode();
    }

    public Boolean checkStatus(Long userId) throws HttpClientErrorException  {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getSubscription() + "/active/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), Boolean.class).getBody();
    }

    public EditSubscription getSubscriptionByUserId(Long userId) throws HttpClientErrorException  {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getSubscription() + "/user/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), EditSubscription.class).getBody();
    }

    public List<EditSubscription> getSubscriptionsWithoutPlanByTrainerId(Long trainerId) throws HttpClientErrorException  {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getSubscription() + "/without/plan/" + trainerId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), EditSubscription[].class).getBody()));
    }

    public List<EditSubscription> getSubscriptionsWithPlanByTrainerId(Long trainerId) throws HttpClientErrorException  {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getSubscription() + "/with/plan/" + trainerId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), EditSubscription[].class).getBody()));
    }

    public List<EditSubscription> getAllSubscriptionsByTrainerId(Long trainerId) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getSubscription() + "/trainer/" + trainerId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), EditSubscription[].class).getBody()));
    }
}
