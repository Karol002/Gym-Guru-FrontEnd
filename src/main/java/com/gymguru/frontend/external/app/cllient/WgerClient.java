package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.Category;
import com.gymguru.frontend.domain.dto.ExerciseDto;
import com.gymguru.frontend.external.app.config.BackendEndpointConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WgerClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;

    public List<Category> getCategories() {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getWger() + "/categories")
                .build()
                .encode()
                .toUri();

        Category[] categories = restTemplate.getForObject(url, Category[].class);
        return Arrays.asList(categories);
    }

    public List<ExerciseDto> getExecises(Long categoryId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getWger() + "/exercises/" + categoryId)
                .build()
                .encode()
                .toUri();

        ExerciseDto[] exercises = restTemplate.getForObject(url, ExerciseDto[].class);
        return Arrays.asList(exercises);
    }
}
