package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.User;
import com.gymguru.frontend.domain.UserAccount;
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
public class UserClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public HttpStatus createUser(UserAccount user) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, user, Void.class).getStatusCode();
    }

    public User getUser(Long id) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser() +  "/id/"  + id)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), User.class).getBody();
    }

    public HttpStatus updateUser(User user) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(user), Void.class).getStatusCode();
    }
}
