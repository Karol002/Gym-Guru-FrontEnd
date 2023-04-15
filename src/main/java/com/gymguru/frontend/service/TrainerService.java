package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.Specialization;
import com.gymguru.frontend.domain.dto.TrainerAccount;
import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.external.app.cllient.TrainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerClient trainerClient;

    public boolean createTrainer(String email, String password, String firstName, String lastName,
                                 String education, String decription, Double price, Specialization specialization) {

        try {
            return trainerClient.createTrainerAccount(new TrainerAccount(email, password, firstName,
                    lastName, education, decription, new BigDecimal(price), specialization)).is2xxSuccessful();
        } catch (ResourceAccessException exception) {
            return false;
        }
    }

    public Set<TrainerDto> getAllBySpecialization(Specialization specialization) {
        try {
            return new HashSet<>(trainerClient.getAllTrainersBySpecialization(specialization));
        } catch (ResourceAccessException exception) {
            return Collections.emptySet();
        }
    }

    public Set<TrainerDto> getTrainers() {
        try {
            return new HashSet<>(trainerClient.getAllTrainers());
        } catch (ResourceAccessException exception) {
            return Collections.emptySet();
        }
    }

    public TrainerDto getTrainer(Long id) {
        return trainerClient.getTrainerById(id);
    }

    public Boolean updateTrainer(TrainerDto trainerDto) {
        return trainerClient.updateTrainer(trainerDto).is2xxSuccessful();
    }
}
