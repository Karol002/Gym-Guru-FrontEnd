package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.dto.UserToSaveDto;
import com.gymguru.frontend.domain.dto.UserDto;
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
public class UserClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;

    public HttpStatus createUser(UserToSaveDto user) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getUser())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, user, Void.class).getStatusCode();
    }

    public UserDto getUser(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getUser() +  "/id/"  + id)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, UserDto.class);
    }

    public List<String> getImpossibleEmails() {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getUser() + "/emails")
                .build()
                .encode()
                .toUri();

        String[] emails = restTemplate.getForObject(url, String[].class);
        return Arrays.asList(emails);
    }

    public void updateUser(UserDto userDto, String email) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getUser() + "/email/" + email)
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, userDto);
    }

}
