package com.gymguru.frontend.service;

import com.gymguru.frontend.cllient.TrainerClient;
import com.gymguru.frontend.domain.edit.EditTrainer;
import com.gymguru.frontend.domain.enums.Specialization;
import com.gymguru.frontend.domain.save.SaveTrainerAccount;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerClient trainerClient;
    private final Logger logger = LoggerFactory.getLogger(TrainerService.class);

    public boolean createTrainer(String email, String password, String firstName, String lastName,
                                 String education, String decription, Double price, Specialization specialization) {

        try {
            return trainerClient.createTrainerAccount(new SaveTrainerAccount(email, password, firstName,
                    lastName, education, decription, new BigDecimal(price), specialization)).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }

    public Set<EditTrainer> getAllBySpecialization(Specialization specialization) {
        try {
            return new HashSet<>(trainerClient.getAllTrainersBySpecialization(specialization));
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public Set<EditTrainer> getTrainers() {
        try {
            return new HashSet<>(trainerClient.getAllTrainers());
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return Collections.emptySet();
        }
    }

    public EditTrainer getTrainer(Long id) {
        try {
            return trainerClient.getTrainerById(id);
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return new EditTrainer();
        }
    }

    public Boolean updateTrainer(EditTrainer editTrainer) {
        try {
            return trainerClient.updateTrainer(editTrainer).is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            logger.warn(exception.getMessage());
            return false;
        }
    }
}
