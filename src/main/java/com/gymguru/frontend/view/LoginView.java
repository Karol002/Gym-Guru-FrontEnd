package com.gymguru.frontend.view;

import com.gymguru.frontend.domain.Role;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
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
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/gymguru/login")
@PageTitle("Login user")
public class LoginView extends VerticalLayout {
    private final AuthService authService;
    private final Label errorLabel;
    private final TextField emailField;
    private final PasswordField passwordField;

    @Autowired
    public LoginView(AuthService authService) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        this.authService = authService;
        H1 title = new H1("Please log in to your account");
        errorLabel = getErrorLabel();

        emailField = getEmailField();
        passwordField = getPasswordField();

        Button loginButton = getLoginButton();
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

    private Button getLoginButton() {
        Button loginButton = new Button("Log in");
        loginButton.getStyle().set("background-color", "#007bff");
        loginButton.getStyle().set("color", "#fff");
        loginButton.getStyle().set("border", "none");
        loginButton.getStyle().set("border-radius", "0.25rem");
        loginButton.setWidth("400px");
        loginButton.setMaxWidth("100%");
        loginButton.setHeight("60px");

        loginButton.addClickListener(event -> {
            if (authService.signIn(emailField.getValue(), passwordField.getValue())) {
                Notification.show("Successfully logged in!");
                UI.getCurrent().navigate(choseViewAfterLogin());
            }
            else errorLabel.setText("Invalid username or password");
        });

        return loginButton;
    }

    private String choseViewAfterLogin() {
        SessionMemoryDto sessionMemoryDto = VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class);
        if (sessionMemoryDto.getRole() == Role.ROLE_USER) return "gymguru/panel/user";
        else if (sessionMemoryDto.getRole() == Role.ROLE_TRAINER) return "gymguru/panel/trainer";
        else return "gymguru";
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