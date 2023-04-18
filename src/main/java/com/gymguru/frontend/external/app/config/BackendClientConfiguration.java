package com.gymguru.frontend.external.app.config;

import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.vaadin.flow.server.VaadinSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@Getter
@Configuration
public class BackendClientConfiguration {
    @Value("${backend.server.endpoint}")
    private String endpoint;

    @Value("${backend.endpoint.login}")
    private String login;

    @Value("${backend.endpoint.exercises}")
    private String exercise;

    @Value("${backend.endpoint.meals}")
    private String meal;

    @Value("${backend.endpoint.plans}")
    private String plan;

    @Value("${backend.endpoint.subscriptions}")
    private String subscription;

    @Value("${backend.endpoint.trainers}")
    private String trainer;

    @Value("${backend.endpoint.users}")
    private String user;

    @Value("${backend.endpoint.edamam}")
    private String edamam;

    @Value("${backend.endpoint.wger}")
    private String wger;

    @Value("${backend.endpoint.openai}")
    private String openai;

    public HttpEntity<?> getAuthorizationEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class).getToken());

        return new HttpEntity<>(headers);
    }

    public HttpEntity<?> getAuthorizationEntity(Object entityObject) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class).getToken());

        return new HttpEntity<>(entityObject, headers);
    }
}