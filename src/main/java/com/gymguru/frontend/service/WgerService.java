package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.Category;
import com.gymguru.frontend.domain.dto.ExerciseDto;
import com.gymguru.frontend.external.app.cllient.WgerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WgerService {
    private final WgerClient wgerClient;

    public List<Category> getCategories() {
        return wgerClient.getCategories();
    }

    public List<ExerciseDto> getExercises(Long categoryId) {
        return wgerClient.getExecises(categoryId);
    }
}
