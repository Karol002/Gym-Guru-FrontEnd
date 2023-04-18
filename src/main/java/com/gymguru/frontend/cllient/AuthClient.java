package com.gymguru.frontend.cllient;

import com.gymguru.frontend.config.BackendClientConfiguration;
import com.gymguru.frontend.domain.read.ReadAuthToken;
import com.gymguru.frontend.domain.Credential;
import com.gymguru.frontend.domain.PasswordChanger;
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
    public ReadAuthToken getAuthToken(Credential credentials ) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getLogin())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, credentials, ReadAuthToken.class);
    }

    public HttpStatus changePassword(PasswordChanger passwordChanger) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getUser() + "/password")
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(passwordChanger), Void.class).getStatusCode();
    }
}
