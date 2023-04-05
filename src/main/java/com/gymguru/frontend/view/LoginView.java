package com.gymguru.frontend.view;

import com.gymguru.frontend.service.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/gymguru/login")
@PageTitle("Login user")
public class LoginView extends VerticalLayout {

    @Autowired
    public LoginView(AuthService authService) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        H1 title = new H1("Please log in to your account");
        Label errorLabel = getErrorLabel();

        TextField emailField = getEmailField();
        PasswordField passwordField = getPasswordField();

        Button loginButton = getLoginButton(emailField, passwordField, errorLabel, authService);
        Button registerButton = getRegisterButton();

        add(title, errorLabel, emailField, passwordField, loginButton, registerButton);
    }

    private Button getRegisterButton() {
        Button registerButton = new Button("Register");
        registerButton.getStyle().set("background-color", "#6c757d");
        registerButton.getStyle().set("color", "#fff");
        registerButton.getStyle().set("border", "none");
        registerButton.getStyle().set("border-radius", "0.25rem");
        registerButton.setWidth("400px");
        registerButton.setMaxWidth("100%");
        registerButton.setHeight("60px");

        registerButton.addClickListener(event -> {
            Notification.show("Redirecting to registration page...");
            UI.getCurrent().navigate("gymguru/reqister");
        });

        return registerButton;
    }

    private Button getLoginButton(TextField emailField, PasswordField passwordField, Label errorLabel, AuthService authService) {
        Button loginButton = new Button("Log in");
        loginButton.getStyle().set("background-color", "#007bff");
        loginButton.getStyle().set("color", "#fff");
        loginButton.getStyle().set("border", "none");
        loginButton.getStyle().set("border-radius", "0.25rem");
        loginButton.setWidth("400px");
        loginButton.setMaxWidth("100%");
        loginButton.setHeight("60px");

        loginButton.addClickListener(event -> {
            if (authService.signIn(emailField.getValue(), passwordField.getValue())) Notification.show("Successfully logged in!");
            else errorLabel.setText("Invalid username or password");
        });

        return loginButton;
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

    private TextField getEmailField() {
        TextField email = new TextField();
        email.setLabel("Email");
        email.setPlaceholder("Enter your email");
        email.setRequired(true);
        email.setWidth("400px");
        email.setMaxWidth("100%");

        return email;
    }

    private Label getErrorLabel() {
        Label errorLabel = new Label();
        errorLabel.getStyle().set("color", "red");

        return errorLabel;
    }
}