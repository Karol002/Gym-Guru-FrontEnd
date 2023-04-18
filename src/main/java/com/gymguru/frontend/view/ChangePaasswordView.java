package com.gymguru.frontend.view;


import com.gymguru.frontend.domain.authorization.SessionMemory;
import com.gymguru.frontend.service.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.server.VaadinSession;

public class ChangePaasswordView extends VerticalLayout {
    private final AuthService authService;
    private final PasswordField oldPassword;
    private final PasswordField newPassword;
    private final PasswordField secondNewPassoword;
    private final Label errorLabel;

    public ChangePaasswordView(AuthService authService) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        this.authService = authService;

        oldPassword = getOldPasswordField();
        newPassword = getNewPasswordField();
        secondNewPassoword = getSecondNewPasswordField();
        Button confirmButton = getConfirmButton();
        errorLabel = getErrorLabel();

        add(errorLabel, oldPassword, newPassword, secondNewPassoword, confirmButton);
    }

    private PasswordField getOldPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setPlaceholder("Enter your password");
        passwordField.setRequired(true);
        passwordField.setWidth("400px");
        passwordField.setMaxWidth("100%");

        return passwordField;
    }

    private PasswordField getNewPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("New password");
        passwordField.setPlaceholder("Enter new password");
        passwordField.setRequired(true);
        passwordField.setWidth("400px");
        passwordField.setMaxWidth("100%");

        return passwordField;
    }

    private PasswordField getSecondNewPasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Repeat password");
        passwordField.setPlaceholder("Repeat password");
        passwordField.setRequired(true);
        passwordField.setWidth("400px");
        passwordField.setMaxWidth("100%");

        return passwordField;
    }

    private Button getConfirmButton() {
        Button loginButton = new Button("Confirm");
        loginButton.getStyle().set("background-color", "#007bff");
        loginButton.getStyle().set("color", "#fff");
        loginButton.getStyle().set("border", "none");
        loginButton.getStyle().set("border-radius", "0.25rem");
        loginButton.setWidth("400px");
        loginButton.setMaxWidth("100%");
        loginButton.setHeight("60px");

        loginButton.addClickListener(event -> {
            if (validateData()) {
                if (authService.changePassword(VaadinSession.getCurrent().getAttribute(SessionMemory.class).getEmail(),
                        oldPassword.getValue(), newPassword.getValue())) {
                    Notification.show("Successful");
                    UI.getCurrent().getPage().setLocation("/gymguru");
                    VaadinSession.getCurrent().close();
                }
            } else {
                errorLabel.setVisible(true);
            }
        });

        return loginButton;
    }

    private Label getErrorLabel() {
        Label errorLabel = new Label("Passwords are different");
        errorLabel.getStyle().set("color", "red");
        errorLabel.setVisible(false);

        return errorLabel;
    }

    private boolean validateData() {
        return (newPassword.getValue().equals(secondNewPassoword.getValue()));
    }
}
