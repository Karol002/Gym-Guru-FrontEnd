package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;
import com.gymguru.frontend.test.OpenAiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class UserClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;


}
