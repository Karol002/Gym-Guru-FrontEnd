package com.gymguru.frontend.view;

import com.gymguru.frontend.domain.CredentialType;
import com.gymguru.frontend.domain.Specialization;
import com.gymguru.frontend.service.TrainerService;
import com.gymguru.frontend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/gymguru/reqister")
@PageTitle("Register")
public class RegisterView extends VerticalLayout {
    private final UserService userService;
    private final TrainerService trainerService;
    private final H1 title;
    private final Label errorLabel;
    private final TextField emailField;
    private final PasswordField passwordField;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextArea educationArea;
    private final TextArea descriptionArea;
    private final Select<CredentialType> type;
    private final Select<Specialization> specializationSelect;
    private final NumberField priceField;
    private final Button registerButton;
    private final Button loginButton;

    @Autowired
    public RegisterView(UserService userService, TrainerService trainerService) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        this.trainerService = trainerService;
        this.userService = userService;

        title = new H1("Create a new account");
        errorLabel = getErrorLabel();

        emailField = getEmailField();
        passwordField = getPasswordField();
        firstNameField = getFirstNameField();
        lastNameField = getLastNameField();

        educationArea = getEducationArea();
        descriptionArea = getDescriptionArea();
        type = getAccountTypeSelect();
        specializationSelect = getSpecializationSelect();
        priceField = getPriceField();

        registerButton = getRegisterButton();
        loginButton = getLoginButton();

        add(title, errorLabel, type, emailField, passwordField, specializationSelect, firstNameField, lastNameField, priceField, educationArea, descriptionArea, registerButton, loginButton);
    }

    private TextArea getEducationArea() {
        TextArea educationField = new TextArea();
        educationField.setLabel("Education");
        educationField.setPlaceholder("Enter your education");
        educationField.setRequired(true);
        educationField.setVisible(false);
        educationField.setWidth("400px");
        educationField.setMaxWidth("100%");

        return educationField;
    }

    private TextArea getDescriptionArea() {
        TextArea descriptionField = new TextArea();
        descriptionField.setLabel("Description");
        descriptionField.setPlaceholder("Enter your description");
        descriptionField.setRequired(true);
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

    private Button getRegisterButton() {

        Button registerButton = new Button("Register");
        registerButton.getStyle().set("background-color", "#007bff");
        registerButton.getStyle().set("color", "#fff");
        registerButton.getStyle().set("border", "none");
        registerButton.getStyle().set("border-radius", "0.25rem");
        registerButton.setWidth("400px");
        registerButton.setMaxWidth("100%");
        registerButton.setHeight("60px");

        registerButton.addClickListener(event -> {
            if (validateData()) {
                if (createAccount()) {
                    Notification.show("Account created!");
                    UI.getCurrent().navigate(LoginView.class);
                } else Notification.show("Error creating account!");
            } else {
                errorLabel.setText("Please fill in all fields");
            }
        });

        return registerButton;
    }

    private boolean createAccount()  {
        if (type.getValue() == CredentialType.User) {
            return (userService.createUser(emailField.getValue(), passwordField.getValue(),
                    firstNameField.getValue(), lastNameField.getValue()));
        } else {
            return (trainerService.createTrainer(emailField.getValue(), passwordField.getValue(), firstNameField.getValue(),
                    lastNameField.getValue(), educationArea.getValue(), descriptionArea.getValue(),
                    priceField.getValue(), specializationSelect.getValue()));
        }
    }

    private boolean validateData() {
        if (type.isEmpty()) return false;

        if (type.getValue() == CredentialType.User) {
            if (firstNameField.getValue().isEmpty() || lastNameField.getValue().isEmpty()
                    || emailField.getValue().isEmpty() || passwordField.getValue().isEmpty()) return false;
        }

        if (type.getValue() == CredentialType.Trainer) {
            if (firstNameField.getValue().isEmpty() || lastNameField.getValue().isEmpty()
                    || emailField.getValue().isEmpty() || passwordField.getValue().isEmpty()
                    || type.isEmpty() || descriptionArea.isEmpty() || priceField.isEmpty()
                    || specializationSelect.isEmpty() || educationArea.isEmpty()) return false;
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

    private Select<CredentialType> getAccountTypeSelect() {
        Select<CredentialType> type = new Select<>();
        type.setItems(CredentialType.values());
        type.setLabel("Account type:");
        type.setPlaceholder("Your role");
        type.setWidth("400px");
        type.setMaxWidth("100%");

        type.addValueChangeListener(event -> {
            if (event.getValue() == CredentialType.Trainer) {
                educationArea.setVisible(true); educationArea.setRequired(true);
                descriptionArea.setVisible(true); descriptionArea.setRequired(true);
                specializationSelect.setVisible(true); specializationSelect.setRequiredIndicatorVisible(true);
                priceField.setVisible(true); priceField.setRequiredIndicatorVisible(true);
            } else if (event.getValue() == CredentialType.User) {
                educationArea.setVisible(false); educationArea.setRequired(false);
                descriptionArea.setVisible(false); descriptionArea.setRequired(false);
                specializationSelect.setVisible(false); specializationSelect.setRequiredIndicatorVisible(false);
                priceField.setVisible(false); priceField.setRequiredIndicatorVisible(false);
            }
        });

        return type;
    }

    private Select<Specialization> getSpecializationSelect() {
        Select<Specialization> type = new Select<>();
        type.setItems(Specialization.values());
        type.setLabel("Your specialization:");
        type.setPlaceholder("Specialization");
        type.setVisible(false);
        type.setWidth("400px");
        type.setMaxWidth("100%");

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

    private NumberField getPriceField() {
        NumberField priceField = new NumberField();
        priceField.setLabel("Your monthly price in $");
        priceField.setMax(100);
        priceField.setMin(20);
        priceField.setVisible(false);
        priceField.setPlaceholder("For 20.0 to 100.0");
        priceField.setWidth("400px");
        priceField.setMaxWidth("100%");

        return priceField;
    }
}
