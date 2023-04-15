package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.dto.UserToSaveDto;
import com.gymguru.frontend.domain.dto.UserDto;
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
public class UserClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public HttpStatus createUser(UserToSaveDto user) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, user, Void.class).getStatusCode();
    }

    public UserDto getUser(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser() +  "/id/"  + id)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), UserDto.class).getBody();
    }

    public List<String> getImpossibleEmails() {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser() + "/emails")
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.getForObject(url, String[].class)));
    }

    public HttpStatus updateUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(userDto), Void.class).getStatusCode();
    }

}
