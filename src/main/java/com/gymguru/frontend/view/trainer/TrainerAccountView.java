package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.service.TrainerService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class TrainerAccountView extends VerticalLayout {
    private final TrainerService trainerService;
    private final SessionMemoryDto sessionMemoryDto;
    private TrainerDto trainerDto;
    private final Label accountLabel;
    private final TextField emailField;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextArea educationArea;
    private final TextArea descriptionArea;
    private final TextField monthPriceField;
    private final TextField specialiaztionField;

    public TrainerAccountView(TrainerService trainerService, SessionMemoryDto sessionMemoryDto) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        this.trainerService = trainerService;
        this.sessionMemoryDto = sessionMemoryDto;
        trainerDto = trainerService.getTrainers(sessionMemoryDto.getId());

        accountLabel = getAccountLabel();
        emailField = getEmailField();
        firstNameField = getFirstNameField();
        lastNameField = getLastNameField();
        educationArea = getEducationArea();
        descriptionArea = getDescriptionArea();
        monthPriceField = getPrice();
        specialiaztionField = getSpecialzationField();

        add(accountLabel,emailField, firstNameField, lastNameField, specialiaztionField, monthPriceField, educationArea, descriptionArea);
    }

    private TextField getSpecialzationField() {
        TextField specializationField = new TextField();
        specializationField.setLabel("Specialization");
        specializationField.setValue(trainerDto.getSpecialization().toString());
        specializationField.setWidth("400px");
        specializationField.setMaxWidth("100%");
        specializationField.setReadOnly(true);

        return specializationField;
    }

    private TextField getPrice() {
        TextField priceSub = new TextField();
        priceSub.setLabel("Your monthly price");
        priceSub.setValue(trainerDto.getMonthPrice().toString() + "$");
        priceSub.setWidth("400px");
        priceSub.setMaxWidth("100%");
        priceSub.setReadOnly(true);

        return priceSub;
    }

    private TextField getLastNameField() {
        TextField lastNameField = new TextField();
        lastNameField.setLabel("Last Name");
        lastNameField.setValue(trainerDto.getLastName());
        lastNameField.setWidth("400px");
        lastNameField.setMaxWidth("100%");
        lastNameField.setReadOnly(true);

        return lastNameField;
    }

    private TextField getEmailField() {
        TextField emailField = new TextField();
        emailField.setLabel("Email");
        emailField.setValue(sessionMemoryDto.getEmail());
        emailField.setWidth("400px");
        emailField.setMaxWidth("100%");
        emailField.setReadOnly(true);
        return emailField;
    }

    private TextField getFirstNameField() {
        TextField firstNameField = new TextField();
        firstNameField.setLabel("First Name");
        firstNameField.setValue(trainerDto.getFirstName());
        firstNameField.setWidth("400px");
        firstNameField.setMaxWidth("100%");
        firstNameField.setReadOnly(true);

        return firstNameField;
    }

    private TextArea getEducationArea() {
        TextArea educationArea = new TextArea();
        educationArea.setLabel("Education");
        educationArea.setValue(trainerDto.getEducation());
        educationArea.setWidth("400px");
        educationArea.setMaxWidth("100%");
        educationArea.setReadOnly(true);

        return educationArea;
    }

    private TextArea getDescriptionArea() {
        TextArea descriptionArea = new TextArea();
        descriptionArea.setLabel("Description");
        descriptionArea.setValue(trainerDto.getDescription());
        descriptionArea.setWidth("400px");
        descriptionArea.setMaxWidth("100%");
        descriptionArea.setReadOnly(true);

        return descriptionArea;
    }

    private Label getAccountLabel() {
        Label label = new Label("Your account data");

        return label;
    }
}
