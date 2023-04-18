package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.dto.MealDto;
import com.gymguru.frontend.external.app.cllient.EdamamClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EdamamService {
    private final EdamamClient edamamClient;

    public List<MealDto> getMeals(String mealName) {
        return edamamClient.getMeals(mealName);
    }
}
