package com.gymguru.frontend.service;

import com.gymguru.frontend.domain.dto.TrainerAccount;
import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.external.app.cllient.TrainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerClient trainerClient;

    public boolean createTrainer(String email, String password, String firstName, String lastName, String education, String decription) {
        try {
            return trainerClient.createTrainer(new TrainerAccount(email, password, firstName, lastName, education, decription)).is2xxSuccessful();
        } catch (ResourceAccessException exception) {
            return false;
        }
    }

    public Set<TrainerDto> getTrainers() {
        try {
            return new HashSet<>(trainerClient.getAllTrainers());
        } catch (ResourceAccessException exception) {
            return Collections.emptySet();
        }
    }

    /*public Set<Trainer> getTrainers() {
        Set<Trainer> trainers;
        trainers = new HashSet<>();
        trainers.add(new Trainer("john.doe@gmail.com", "password123", "John", "Doe", "I am a certified personal trainer with over 10 years of experience in the fitness industry. My passion for fitness started when I was young and I've been dedicated to helping others achieve their goals ever since. I specialize in strength and conditioning training, as well as nutrition coaching to help my clients achieve a well-rounded healthy lifestyle.", "B.S. in Exercise Science from University of California, Los Angeles"));
        trainers.add(new Trainer("jane.smith@gmail.com", "password456", "Jane", "Smith", "As a former athlete turned trainer, I have a unique understanding of what it takes to achieve success in the fitness industry. My training philosophy focuses on functional movements that can be applied to everyday life, as well as strength and conditioning training to help clients reach their goals. I also offer nutrition coaching to help clients fuel their bodies for optimal performance.", "B.S. in Kinesiology from University of Michigan"));
        trainers.add(new Trainer("jason.nguyen@gmail.com", "password789", "Jason", "Nguyen", "I am a certified personal trainer with a passion for helping clients achieve their goals through customized fitness plans. My approach to training is a combination of strength and conditioning, as well as functional movements to improve overall mobility and flexibility. I also specialize in injury prevention and post-rehabilitation training.", "B.S. in Exercise Science from San Francisco State University"));
        trainers.add(new Trainer("katie.chen@gmail.com", "password1234", "Katie", "Chen", "As a former gymnast and cheerleader, I have a deep understanding of the importance of strength and flexibility in achieving optimal performance. My training philosophy focuses on functional movements that improve overall strength and mobility, as well as nutrition coaching to help clients achieve a well-rounded healthy lifestyle. I also offer specialized training for athletes looking to improve their performance in their respective sports.", "B.S. in Kinesiology from University of California, Davis"));
        trainers.add(new Trainer("mike.jackson@gmail.com", "password5678", "Mike", "Jackson", "I am a certified personal trainer with over 5 years of experience in the fitness industry. My approach to training is a combination of strength and conditioning, as well as functional movements to improve overall mobility and flexibility. I also specialize in high-intensity interval training (HIIT) to help clients achieve their weight loss goals.", "B.S. in Exercise Science from University of Texas at Austin"));
        return trainers;
    }*/

    /*public static TrainerService getInstance() {
        if (trainerService == null) {
            trainerService = new TrainerService();
        }
        return trainerService;
    }

    public TrainerService() {
        trainers = new HashSet<>();
        trainers.add(new Trainer("john.doe@gmail.com", "password123", "John", "Doe", "I am a certified personal trainer with over 10 years of experience in the fitness industry. My passion for fitness started when I was young and I've been dedicated to helping others achieve their goals ever since. I specialize in strength and conditioning training, as well as nutrition coaching to help my clients achieve a well-rounded healthy lifestyle.", "B.S. in Exercise Science from University of California, Los Angeles"));
        trainers.add(new Trainer("jane.smith@gmail.com", "password456", "Jane", "Smith", "As a former athlete turned trainer, I have a unique understanding of what it takes to achieve success in the fitness industry. My training philosophy focuses on functional movements that can be applied to everyday life, as well as strength and conditioning training to help clients reach their goals. I also offer nutrition coaching to help clients fuel their bodies for optimal performance.", "B.S. in Kinesiology from University of Michigan"));
        trainers.add(new Trainer("jason.nguyen@gmail.com", "password789", "Jason", "Nguyen", "I am a certified personal trainer with a passion for helping clients achieve their goals through customized fitness plans. My approach to training is a combination of strength and conditioning, as well as functional movements to improve overall mobility and flexibility. I also specialize in injury prevention and post-rehabilitation training.", "B.S. in Exercise Science from San Francisco State University"));
        trainers.add(new Trainer("katie.chen@gmail.com", "password1234", "Katie", "Chen", "As a former gymnast and cheerleader, I have a deep understanding of the importance of strength and flexibility in achieving optimal performance. My training philosophy focuses on functional movements that improve overall strength and mobility, as well as nutrition coaching to help clients achieve a well-rounded healthy lifestyle. I also offer specialized training for athletes looking to improve their performance in their respective sports.", "B.S. in Kinesiology from University of California, Davis"));
        trainers.add(new Trainer("mike.jackson@gmail.com", "password5678", "Mike", "Jackson", "I am a certified personal trainer with over 5 years of experience in the fitness industry. My approach to training is a combination of strength and conditioning, as well as functional movements to improve overall mobility and flexibility. I also specialize in high-intensity interval training (HIIT) to help clients achieve their weight loss goals.", "B.S. in Exercise Science from University of Texas at Austin"));
    }

    public Set<Trainer> getTrainers() {
        return trainers;
    }*/
}
