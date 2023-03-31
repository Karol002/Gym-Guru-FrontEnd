package com.gymguru.frontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/gymguru/user/login")
@PageTitle("Login user")
public class LoginView extends VerticalLayout {

    public LoginView() {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        H1 title = new H1("Please log in to your account");
        add(title);

        Label errorLabel = new Label();
        errorLabel.getStyle().set("color", "red");

        TextField usernameField = new TextField();
        usernameField.setLabel("Username");
        usernameField.setPlaceholder("Enter your username");
        usernameField.setRequired(true);
        usernameField.setWidth("400px");
        usernameField.setMaxWidth("100%");
        add(usernameField);

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setPlaceholder("Enter your password");
        passwordField.setRequired(true);
        passwordField.setWidth("400px");
        passwordField.setMaxWidth("100%");
        add(passwordField);

        Button loginButton = new Button("Log in");
        loginButton.getStyle().set("background-color", "#007bff");
        loginButton.getStyle().set("color", "#fff");
        loginButton.getStyle().set("border", "none");
        loginButton.getStyle().set("border-radius", "0.25rem");
        loginButton.setWidth("400px");
        loginButton.setMaxWidth("100%");
        loginButton.setHeight("60px");
        loginButton.addClickListener(event -> {
            if (usernameField.getValue().equals("admin") && passwordField.getValue().equals("password")) {
                Notification.show("Successfully logged in!");
            } else {
                errorLabel.setText("Invalid username or password");
            }
        });

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
            /////
            ///////
        });


        add(errorLabel, loginButton, registerButton);
    }

}