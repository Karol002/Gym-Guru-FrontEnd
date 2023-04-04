package com.gymguru.frontend.view;

import com.gymguru.frontend.domain.dto.CredentialType;
import com.gymguru.frontend.service.TrainerService;
import com.gymguru.frontend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/gymguru/reqister")
@PageTitle("Register")
public class RegisterView extends VerticalLayout {
    
    @Autowired
    public RegisterView(UserService userService, TrainerService trainerService) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Create a new account");
        Label errorLabel = getErrorLabel();

        TextField emailField = getEmailField();
        PasswordField passwordField = getPasswordField();
        TextField firstNameField = getFirstNameField();
        TextField lastNameField = getLastNameField();

        TextArea educationField = getEducationField();
        TextArea descriptionField = getDescriptionField();
        Select<CredentialType> type = getAccountTypeSelect(educationField, descriptionField);

        Button registerButton = getRegisterButton(errorLabel, type, firstNameField, lastNameField, emailField,
                passwordField, educationField, descriptionField, userService, trainerService);
        Button loginButton = getLoginButton();

        add(title, errorLabel, type,  emailField, passwordField, firstNameField, lastNameField, educationField, descriptionField, registerButton, loginButton);
    }

    private TextArea getEducationField() {
        TextArea educationField = new TextArea();
        educationField.setLabel("Education");
        educationField.setPlaceholder("Enter your education");
        educationField.setRequired(true);
        educationField.setVisible(false);
        educationField.setWidth("400px");
        educationField.setMaxWidth("100%");

        return educationField;
    }

    private TextArea getDescriptionField() {
        TextArea descriptionField = new TextArea();
        descriptionField.setLabel("Description");
        descriptionField.setPlaceholder("Enter your description");
        descriptionField.setRequired(true);
        System.out.println(descriptionField.getHeight());
        descriptionField.setVisible(false);
        descriptionField.setHeight("6em");
        descriptionField.setWidth("400px");
        descriptionField.setMaxWidth("100%");

        return descriptionField;
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

    private PasswordField getPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setPlaceholder("Enter your password");
        passwordField.setRequired(true);
        passwordField.setWidth("400px");
        passwordField.setMaxWidth("100%");

        return passwordField;
    }

    private Button getRegisterButton(Label errorLabel, Select<CredentialType> type, TextField firstNameField, TextField lastNameField,
                                     TextField emailField, PasswordField passwordField, TextArea descriptionAre, TextArea educationArea,
                                     UserService userService, TrainerService trainerService) {
        Button registerButton = new Button("Register");
        registerButton.getStyle().set("background-color", "#007bff");
        registerButton.getStyle().set("color", "#fff");
        registerButton.getStyle().set("border", "none");
        registerButton.getStyle().set("border-radius", "0.25rem");
        registerButton.setWidth("400px");
        registerButton.setMaxWidth("100%");
        registerButton.setHeight("60px");

        registerButton.addClickListener(event -> {
            if (checkData(type, firstNameField, lastNameField, emailField, passwordField, descriptionAre, educationArea)) {
                if (createAccount(type, firstNameField, lastNameField, emailField, passwordField, descriptionAre, educationArea, userService,trainerService)) {
                    Notification.show("Account created!");
                    UI.getCurrent().navigate(LoginView.class);
                } else Notification.show("Something gone wrong!");
            } else {
                errorLabel.setText("Please fill in all fields");
            }
        });

        return registerButton;
    }

    public boolean createAccount(Select<CredentialType> type, TextField firstNameField, TextField lastNameField, TextField emailField,
                                 PasswordField passwordField, TextArea descriptionAre, TextArea educationArea, UserService userService, TrainerService trainerService)  {
        if (type.getValue() == CredentialType.User) {
            return (userService.createUser(emailField.getValue(), passwordField.getValue(), firstNameField.getValue(), lastNameField.getValue()));
        } else {
            return (trainerService.createTrainer(emailField.getValue(), passwordField.getValue(), firstNameField.getValue(),
                    lastNameField.getValue(), educationArea.getValue(), descriptionAre.getValue()));
        }
    }
    
    private boolean checkData(Select<CredentialType> type, TextField firstNameField, TextField lastNameField, TextField emailField, 
                              PasswordField passwordField, TextArea descriptionAre, TextArea educationArea) {
        if (type.getValue() == CredentialType.User) {
            if (firstNameField.getValue().isEmpty() || lastNameField.getValue().isEmpty()
                    || emailField.getValue().isEmpty() || passwordField.getValue().isEmpty()
                    || type.isEmpty()) return false;
        }

        if (type.getValue() == CredentialType.Trainer) {
            if (firstNameField.getValue().isEmpty() || lastNameField.getValue().isEmpty()
                    || emailField.getValue().isEmpty() || passwordField.getValue().isEmpty()
                    || type.isEmpty() || descriptionAre.isEmpty() 
                    || educationArea.isEmpty()) return false;
        }
        
        return true;
    }

    private Button getLoginButton() {
        Button loginButton = new Button("Log in");
        loginButton.getStyle().set("background-color", "#6c757d");
        loginButton.getStyle().set("color", "#fff");
        loginButton.getStyle().set("border", "none");
        loginButton.getStyle().set("border-radius", "0.25rem");
        loginButton.setWidth("400px");
        loginButton.setMaxWidth("100%");
        loginButton.setHeight("60px");

        loginButton.addClickListener(event -> {
            Notification.show("Redirecting to login page...");
            UI.getCurrent().navigate(LoginView.class);
        });

        return loginButton;
    }

    private Label getErrorLabel() {
        Label errorLabel = new Label();
        errorLabel.getStyle().set("color", "red");

        return errorLabel;
    }

    private Select<CredentialType> getAccountTypeSelect(TextArea educationField, TextArea descriptionField) {
        Select<CredentialType> type = new Select<>();
        type.setItems(CredentialType.values());
        type.setLabel("Account type:");
        type.setPlaceholder("Your role");
        type.setRequiredIndicatorVisible(true);
        type.setWidth("400px");
        type.setMaxWidth("100%");

        type.addValueChangeListener(event -> {
            if (event.getValue() == CredentialType.Trainer) {
                educationField.setVisible(true);
                descriptionField.setVisible(true);
            } else if (event.getValue() == CredentialType.User) {
                educationField.setVisible(false);
                descriptionField.setVisible(false);
            }
        });

        return type;
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
}
