package com.gymguru.frontend.view.user;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "/gymguru/panel/user/account")
public class UserAccountView extends VerticalLayout {

    private final Label errorLabel;
    private final TextField emailField;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private final Button saveButton;

    @Autowired
    public UserAccountView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        H1 title = new H1("Change your account data");
        errorLabel = getErrorLabel();
        emailField = getEmailField();
        firstNameField = getFirstNameField();
        lastNameField = getLastNameField();

        saveButton = getSaveButton();

        add(title, errorLabel, emailField, firstNameField, lastNameField, saveButton);
    }

    private TextField getLastNameField() {
        TextField lastNameField = new TextField();
        lastNameField.setLabel("Last Name");
        lastNameField.setPlaceholder("Enter your last name");
        lastNameField.setRequired(true);
        lastNameField.setWidth("400px");
        lastNameField.setMaxWidth("100%");

        return lastNameField;
    }

    private TextField getEmailField() {
        TextField emailField = new TextField();
        emailField.setLabel("Email");
        emailField.setPlaceholder("Enter your email");
        emailField.setRequired(true);
        emailField.setWidth("400px");
        emailField.setMaxWidth("100%");

        return emailField;
    }

    private TextField getFirstNameField() {
        TextField firstNameField = new TextField();
        firstNameField.setLabel("First Name");
        firstNameField.setPlaceholder("Enter your first name");
        firstNameField.setRequired(true);
        firstNameField.setWidth("400px");
        firstNameField.setMaxWidth("100%");

        return firstNameField;
    }

    private Label getErrorLabel() {
        Label errorLabel = new Label();
        errorLabel.getStyle().set("color", "red");

        return errorLabel;
    }

    private Button getSaveButton() {
        Button saveButton = new Button("Log in");
        saveButton.getStyle().set("background-color", "#007bff");
        saveButton.getStyle().set("color", "#fff");
        saveButton.getStyle().set("border", "none");
        saveButton.getStyle().set("border-radius", "0.25rem");
        saveButton.setWidth("400px");
        saveButton.setMaxWidth("100%");
        saveButton.setHeight("60px");

        saveButton.addClickListener(event -> {
            if (validateData()) Notification.show("Successfully!");
            else errorLabel.setText("Un correct data");
        });

        return saveButton;
    }

    private boolean validateData() {
        return (!(firstNameField.getValue().isEmpty() || lastNameField.getValue().isEmpty()
                || emailField.getValue().isEmpty()));
    }
}
