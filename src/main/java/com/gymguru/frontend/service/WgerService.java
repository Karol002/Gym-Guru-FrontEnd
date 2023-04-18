package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.WgerCategory;
import com.gymguru.frontend.domain.WgerExercise;
import com.gymguru.frontend.external.app.cllient.WgerClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WgerService {
    private final WgerClient wgerClient;
    private final Logger logger = LoggerFactory.getLogger(WgerService.class);

    public List<WgerCategory> getCategories() {
        try {
            return wgerClient.getCategories();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Set<WgerExercise> getExercises(Long categoryId) {
        try {
            return new HashSet<>(wgerClient.getExercises(categoryId));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }
}
