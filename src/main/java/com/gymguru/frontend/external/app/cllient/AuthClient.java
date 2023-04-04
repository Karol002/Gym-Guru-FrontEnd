package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;
    public String getAuthToken(CredentialDto credentials ) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getLogin())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, cregit dentials, String.class);
    }
}
