package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.AuthToken;
import com.gymguru.frontend.domain.dto.CredentialDto;
import com.gymguru.frontend.domain.dto.PasswordChanger;
import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;
import lombok.RequiredArgsConstructor;
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
    private final BackendEndpointConfiguration backendEndpointConfiguration;
    public AuthToken getAuthToken(CredentialDto credentials ) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getLogin())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, credentials, AuthToken.class);
    }

    public HttpStatus changePassword(PasswordChanger passwordChanger) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getUser() + "/password")
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, passwordChanger, Void.class).getStatusCode();
    }
}
