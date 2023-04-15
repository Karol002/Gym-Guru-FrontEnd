package com.gymguru.frontend.view.user;


import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.SubscriptionDto;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.domain.dto.UserDto;
import com.gymguru.frontend.service.SubscriptionService;
import com.gymguru.frontend.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class UserAccountView extends VerticalLayout {
    private final static long MAX_SUB_TIME_IN_MONTH = 6L;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final SessionMemoryDto sessionMemoryDto;
    private final Label accountLabel;
    private final Label subscriptionLabel;
    private final TextField emailField;
    private final TextField firstNameField;
    private final TextField lastNameField;
    private Button editButton;
    private Button saveButton;
    private Button extendButton;
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
            editButton = getEditButton();
            saveButton = getSaveButton();
            extendButton = getExtendButton();
            add(accountLabel, emailField, firstNameField, lastNameField, subscriptionLabel, priceSub, startSub, endSub, saveButton, editButton, extendButton);
        } else {
            subscriptionLabel = getUnActiveSubscriptionLabel();
            add(accountLabel, emailField, firstNameField, lastNameField, subscriptionLabel, saveButton, editButton);
        }
    }

    private Button getExtendButton() {
        Button extendButton = new Button("Extend your subscription");
        extendButton.getStyle().set("background-color", "#007bff");
        extendButton.getStyle().set("color", "#fff");
        extendButton.getStyle().set("border", "none");
        extendButton.getStyle().set("border-radius", "0.25rem");
        extendButton.setWidth("400px");
        extendButton.setMaxWidth("100%");
        extendButton.setHeight("60px");

        extendButton.addClickListener(event -> {
            extendSubscriptionLayout();
        });

        return extendButton;
    }

    private void extendSubscriptionLayout() {
        long mothDifference = monthsBetween(subscriptionDto.getStartDate(), subscriptionDto.getEndDate());
        long maxExtend =  (MAX_SUB_TIME_IN_MONTH - mothDifference);
        if (maxExtend > 0) getSubLengthLayout(maxExtend);
        else getMaxSubLengthLayout();
    }

    private void getSubLengthLayout(long maxExtend) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setWidth("300px");
        dialogLayout.setAlignItems(Alignment.CENTER);
        dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Label infoLabel = new Label("Extend your subscription");
        infoLabel.setWidthFull();

        List<Long> subscriptionLengthList = new ArrayList<>();
        for (long i = 1; i <= maxExtend; i++) {
            subscriptionLengthList.add(i);
        }

        Select<Long> subscriptionExtendSelect = new Select<>();
        subscriptionExtendSelect.setItems(subscriptionLengthList);
        subscriptionExtendSelect.setLabel("Add month to your subscription");
        subscriptionExtendSelect.setWidthFull();

        Button confirmButton = new Button("Confirm", event1 -> {
            if (subscriptionExtendSelect.getValue() != null) {
                subscriptionService.extendSubscription(sessionMemoryDto.getId(), subscriptionExtendSelect.getValue());
                refresh();
                dialog.close();
            }
        });
        confirmButton.getStyle().set("background-color", "#007bff");
        confirmButton.getStyle().set("color", "#fff");
        confirmButton.setWidthFull();

        Button closeButton = new Button("Close", event2 -> {
            dialog.close();
        });
        closeButton.setWidthFull();



        dialogLayout.add(infoLabel, subscriptionExtendSelect, confirmButton, closeButton);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private void getMaxSubLengthLayout() {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setAlignItems(Alignment.CENTER);
        dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Label infoLabel = new Label("Max subscription time, can not extend");
        infoLabel.setWidthFull();

        Button closeButton = new Button("Close", event2 -> {
            dialog.close();
        });
        closeButton.setWidthFull();

        dialogLayout.add(infoLabel, closeButton);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private long monthsBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.MONTHS.between(date1.withDayOfMonth(1), date2.withDayOfMonth(1));
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
            editButton.setVisible(false);
            saveButton.setVisible(true);
            firstNameField.setReadOnly(false);
            lastNameField.setReadOnly(false);
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
            if (prepareEditData()) {
                editButton.setVisible(true); saveButton.setVisible(false);
                firstNameField.setReadOnly(true); lastNameField.setReadOnly(true);
                userDto = userService.getUserById(sessionMemoryDto.getId());
            }
        });
        return saveButton;
    }

    private boolean prepareEditData() {
        userDto.setFirstName(firstNameField.getValue());
        userDto.setLastName(lastNameField.getValue());
        return userService.updateUser(userDto);
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

    private void refresh() {
        subscriptionDto = subscriptionService.getSubscription(userDto.getId());
        priceSub = getPriceSub();
        startSub = getStartSub();
        endSub = getEndSub();
        editButton = getEditButton();
        saveButton = getSaveButton();
        extendButton = getExtendButton();
        removeAll();
        add(accountLabel, emailField, firstNameField, lastNameField, subscriptionLabel, priceSub, startSub, endSub, saveButton, editButton, extendButton);

    }
}
