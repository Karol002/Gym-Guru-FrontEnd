package com.gymguru.frontend.external.app.cllient;

import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.Meal;
import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.ExerciseWithId;
import com.gymguru.frontend.domain.dto.MealDto;
import com.gymguru.frontend.domain.dto.MealWithId;
import com.gymguru.frontend.domain.dto.PlanDto;
import com.gymguru.frontend.external.app.config.BackendClientConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlanClient {
    private final RestTemplate restTemplate;
    private final BackendClientConfiguration backendClientConfiguration;

    public Plan getPlan(Long userId) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint()
                        + backendClientConfiguration.getPlan() + "/" + userId)
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), Plan.class).getBody();
    }

    public HttpStatus createPlan(Plan plan) throws ResourceAccessException {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getPlan())
                .build()
                .encode()
                .toUri();
        for (int i = 0; i < plan.getSaveExerciseDtos().size(); i++) {
            System.out.println(plan.getSaveExerciseDtos().get(i).getDescription().length());
        }
        return restTemplate.postForEntity(url, backendClientConfiguration.getAuthorizationEntity(plan), Void.class).getStatusCode();
    }

    public List<MealWithId> getMealsByPlanId(Long planId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getMeal() + "/plan/" + planId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), MealWithId[].class).getBody()));
    }
    public List<ExerciseWithId> getExercisesByPlanId(Long planId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getExercise() + "/plan/" + planId)
                .build()
                .encode()
                .toUri();

        return List.of(Objects.requireNonNull(restTemplate.exchange
                (url, HttpMethod.GET, backendClientConfiguration.getAuthorizationEntity(), ExerciseWithId[].class).getBody()));

    }

    public HttpStatus updateExercise(ExerciseWithId exercise) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getExercise())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(exercise), Void.class).getStatusCode();
    }

    public HttpStatus updateMeal(MealWithId mealWithId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getMeal())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(mealWithId), Void.class).getStatusCode();
    }

    public HttpStatus updatePlan(PlanDto planDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendClientConfiguration.getEndpoint() + backendClientConfiguration.getPlan())
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(url, HttpMethod.PUT, backendClientConfiguration.getAuthorizationEntity(planDto), Void.class).getStatusCode();
    }
}
