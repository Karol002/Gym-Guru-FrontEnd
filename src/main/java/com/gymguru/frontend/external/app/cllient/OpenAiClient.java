package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.dto.OpenAiMessageDto;
import com.gymguru.frontend.external.app.config.BackendClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class OpenAiClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public String getResponse(OpenAiMessageDto openAiMessageDto) throws HttpClientErrorException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getOpenai())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, openAiMessageDto, OpenAiMessageDto.class).getContent();
    }
}
