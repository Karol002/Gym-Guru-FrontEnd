package com.gymguru.frontend.service;

import com.gymguru.frontend.cllient.EdamamClient;
import com.gymguru.frontend.domain.read.ReadEdamamMeal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EdamamService {
    private final EdamamClient edamamClient;

    public List<ReadEdamamMeal> getMeals(String mealName) {
        return edamamClient.getMeals(mealName);
    }
}
