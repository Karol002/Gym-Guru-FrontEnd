package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.edit.EditTrainer;
import com.gymguru.frontend.domain.authorization.SessionMemory;
import com.gymguru.frontend.service.TrainerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;

public class TrainerAccountView extends VerticalLayout {
    private final TrainerService trainerService;
    private final SessionMemory sessionMemory;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextArea educationArea;
    private final TextArea descriptionArea;
    private final NumberField monthPriceField;
    private final Button editButton;
    private final Button saveButton;
    private EditTrainer editTrainer;

    public TrainerAccountView(TrainerService trainerService, SessionMemory sessionMemory) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        this.trainerService = trainerService;
        this.sessionMemory = sessionMemory;
        editTrainer = trainerService.getTrainer(sessionMemory.getId());

        TextField emailField = getEmailField();
        firstNameField = getFirstNameField();
        lastNameField = getLastNameField();
        educationArea = getEducationArea();
        descriptionArea = getDescriptionArea();
        monthPriceField = getPriceField();
        TextField specialiaztionField = getSpecialzationField();
        editButton = getEditButton();
        saveButton = getSaveButton();

        add(emailField, specialiaztionField, firstNameField, lastNameField, monthPriceField, educationArea, descriptionArea, editButton, saveButton);
    }

    private Button getEditButton() {
        Button editButton = new Button("Edit account data");
        editButton.getStyle().set("background-color", "#007bff");
        editButton.getStyle().set("color", "#fff");
        editButton.getStyle().set("border", "none");
        editButton.getStyle().set("border-radius", "0.25rem");
        editButton.setWidth("400px");
        editButton.setMaxWidth("100%");
        editButton.setHeight("60px");

        editButton.addClickListener(event -> {
            editButton.setVisible(false); saveButton.setVisible(true);
            firstNameField.setReadOnly(false); lastNameField.setReadOnly(false);
            descriptionArea.setReadOnly(false); educationArea.setReadOnly(false);
            monthPriceField.setReadOnly(false);
        });

        return editButton;
    }

    private Button getSaveButton() {
        Button saveButton = new Button("Save data");
        saveButton.getStyle().set("background-color", "#007bff");
        saveButton.getStyle().set("color", "#fff");
        saveButton.getStyle().set("border", "none");
        saveButton.getStyle().set("border-radius", "0.25rem");
        saveButton.setWidth("400px");
        saveButton.setMaxWidth("100%");
        saveButton.setHeight("60px");
        saveButton.setVisible(false);

        saveButton.addClickListener(event -> {
            if (updateUser()) {
                editButton.setVisible(true); saveButton.setVisible(false);
                firstNameField.setReadOnly(true); lastNameField.setReadOnly(true);
                descriptionArea.setReadOnly(true); educationArea.setReadOnly(true);
                monthPriceField.setReadOnly(true);
                editTrainer = trainerService.getTrainer(sessionMemory.getId());
                Notification.show("Successful change data");
            }
        });
        return saveButton;
    }

    private boolean updateUser() {
        if (monthPriceField.getValue() >= monthPriceField.getMin() && monthPriceField.getValue() <= monthPriceField.getMax()) {
            editTrainer.setFirstName(firstNameField.getValue());
            editTrainer.setLastName(lastNameField.getValue());
            editTrainer.setDescription(descriptionArea.getValue());
            editTrainer.setEducation(educationArea.getValue());
            editTrainer.setMonthPrice(BigDecimal.valueOf(monthPriceField.getValue()));
            return trainerService.updateTrainer(editTrainer);
        } else return false;
    }

    private TextField getSpecialzationField() {
        TextField specializationField = new TextField();
        specializationField.setLabel("Specialization");
        specializationField.setValue(editTrainer.getSpecialization().toString());
        specializationField.setWidth("400px");
        specializationField.setMaxWidth("100%");
        specializationField.setReadOnly(true);

        return specializationField;
    }

    private NumberField getPriceField() {
        NumberField priceSub = new NumberField();
        priceSub.setLabel("Your monthly price in $");
        priceSub.setMax(100);
        priceSub.setMin(20);
        priceSub.setValue(editTrainer.getMonthPrice().doubleValue());
        priceSub.setReadOnly(true);
        priceSub.setWidth("400px");
        priceSub.setMaxWidth("100%");

        return priceSub;
    }

    private TextField getLastNameField() {
        TextField lastNameField = new TextField();
        lastNameField.setLabel("Last Name");
        lastNameField.setValue(editTrainer.getLastName());
        lastNameField.setWidth("400px");
        lastNameField.setMaxWidth("100%");
        lastNameField.setReadOnly(true);

        return lastNameField;
    }

    private TextField getEmailField() {
        TextField emailField = new TextField();
        emailField.setLabel("Email");
        emailField.setValue(sessionMemory.getEmail());
        emailField.setWidth("400px");
        emailField.setMaxWidth("100%");
        emailField.setReadOnly(true);
        return emailField;
    }

    private TextField getFirstNameField() {
        TextField firstNameField = new TextField();
        firstNameField.setLabel("First Name");
        firstNameField.setValue(editTrainer.getFirstName());
        firstNameField.setWidth("400px");
        firstNameField.setMaxWidth("100%");
        firstNameField.setReadOnly(true);

        return firstNameField;
    }

    private TextArea getEducationArea() {
        TextArea educationArea = new TextArea();
        educationArea.setLabel("Education");
        educationArea.setValue(editTrainer.getEducation());
        educationArea.setWidth("400px");
        educationArea.setMaxWidth("100%");
        educationArea.setReadOnly(true);

        return educationArea;
    }

    private TextArea getDescriptionArea() {
        TextArea descriptionArea = new TextArea();
        descriptionArea.setLabel("Description");
        descriptionArea.setValue(editTrainer.getDescription());
        descriptionArea.setWidth("400px");
        descriptionArea.setMaxWidth("100%");
        descriptionArea.setReadOnly(true);

        return descriptionArea;
    }
}
