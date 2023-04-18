package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.AuthToken;
import com.gymguru.frontend.domain.dto.Credential;
import com.gymguru.frontend.domain.dto.PasswordChanger;
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
public class AuthClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;
    public AuthToken getAuthToken(Credential credentials ) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getLogin())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, credentials, AuthToken.class);
    }

    public HttpStatus changePassword(PasswordChanger passwordChanger) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser() + "/password")
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(passwordChanger), Void.class).getStatusCode();
    }
}
