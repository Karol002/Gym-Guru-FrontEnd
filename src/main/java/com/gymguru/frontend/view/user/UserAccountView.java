package com.gymguru.frontend.view.user;


import com.gymguru.frontend.domain.SubscriptionDto;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.domain.dto.UserDto;
import com.gymguru.frontend.service.SubscriptionService;
import com.gymguru.frontend.service.UserService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;


public class UserAccountView extends VerticalLayout {
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final SessionMemoryDto sessionMemoryDto;
    private final Label accountLabel;
    private final Label subscriptionLabel;
    private final TextField emailField;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private TextField startSub;
    private TextField endSub;
    private TextField priceSub;
    private UserDto userDto;
    private SubscriptionDto subscriptionDto;


    @Autowired
    public UserAccountView(UserService userService, SubscriptionService subscriptionService) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        this.userService = userService;
        this.subscriptionService = subscriptionService;

        sessionMemoryDto = VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class);

        userDto = userService.getUserById(sessionMemoryDto.getId());
        accountLabel = getAccountLabel();
        emailField = getEmailField();
        firstNameField = getFirstNameField();
        lastNameField = getLastNameField();

        add(accountLabel, emailField, firstNameField, lastNameField);

        if (subscriptionService.checkStatus(userDto.getId())) {
            subscriptionDto = subscriptionService.getSubscription(userDto.getId());
            subscriptionLabel = getActiveSubscriptionLabel();
            priceSub = getPriceSub();
            startSub = getStartSub();
            endSub = getEndSub();
            add(accountLabel, emailField, firstNameField, lastNameField, subscriptionLabel, priceSub, startSub, endSub);

        } else {
            subscriptionLabel = getUnActiveSubscriptionLabel();
            add(accountLabel, emailField, firstNameField, lastNameField, subscriptionLabel);
        }
    }

    private TextField getEndSub() {
        TextField endSub = new TextField();
        endSub.setLabel("Subscription end date");
        endSub.setValue(subscriptionDto.getEndDate().toString());
        endSub.setWidth("400px");
        endSub.setMaxWidth("100%");
        endSub.setReadOnly(true);

        return endSub;
    }

    private TextField getStartSub() {
        TextField startSub = new TextField();
        startSub.setLabel("Subscription start date");
        startSub.setValue(subscriptionDto.getStartDate().toString());
        startSub.setWidth("400px");
        startSub.setMaxWidth("100%");
        startSub.setReadOnly(true);

        return startSub;
    }

    private TextField getPriceSub() {
        TextField priceSub = new TextField();
        priceSub.setLabel("Subscription price");
        priceSub.setValue(subscriptionDto.getPrice().toString() + "$");
        priceSub.setWidth("400px");
        priceSub.setMaxWidth("100%");
        priceSub.setReadOnly(true);

        return priceSub;
    }

    private TextField getLastNameField() {
        TextField lastNameField = new TextField();
        lastNameField.setLabel("Last Name");
        lastNameField.setValue(userDto.getLastName());
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
        firstNameField.setValue(userDto.getFirstName());
        firstNameField.setWidth("400px");
        firstNameField.setMaxWidth("100%");
        firstNameField.setReadOnly(true);

        return firstNameField;
    }

    private Label getAccountLabel() {
        Label label = new Label("Your account data");

        return label;
    }

    private Label getActiveSubscriptionLabel() {
        Label label = new Label("Your subscription data");

        return label;
    }

    private Label getUnActiveSubscriptionLabel() {
        Label label = new Label("You don't have active subscription");

        return label;
    }
}
