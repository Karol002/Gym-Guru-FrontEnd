package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.Meal;
import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.TrainerDto;
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
public class PlanClient {
    private final RestTemplate restTemplate;
    private final BackendEndpointConfiguration backendEndpointConfiguration;

    public Plan getPlan(Long userId) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint()
                        + backendEndpointConfiguration.getPlan() + "/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, Plan.class);
    }

    public HttpStatus createPlan(Plan plan) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getPlan())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForEntity(url, plan, Void.class).getStatusCode();
    }

    public List<Meal> getMealsByPlanId(Long planId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getMeal() + "/plan/" + planId)
                .build()
                .encode()
                .toUri();

        return List.of(restTemplate.getForObject(url, Meal[].class));
    }

    public List<Exercise> getExercisesByPlanId(Long planId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendEndpointConfiguration.getEndpoint() + backendEndpointConfiguration.getExercise() + "/plan/" + planId)
                .build()
                .encode()
                .toUri();
        System.out.println(url);
        return List.of(restTemplate.getForObject(url, Exercise[].class));
    }
}
