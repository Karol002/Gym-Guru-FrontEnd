package com.gymguru.frontend.view.user;

import com.gymguru.frontend.domain.SessionMemory;
import com.gymguru.frontend.domain.edit.EditTrainer;
import com.gymguru.frontend.domain.enums.Specialization;
import com.gymguru.frontend.service.SubscriptionService;
import com.gymguru.frontend.service.TrainerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserBuyView extends VerticalLayout {
    private final TrainerService trainerService;
    private final SubscriptionService subscriptionService;
    private final SessionMemory sessionMemory;
    private final VerticalLayout container;
    private final Select<Specialization> specializationSelect;
    private Grid<EditTrainer> trainerGrid;

    public UserBuyView(TrainerService trainerService, SubscriptionService subscriptionService, SessionMemory sessionMemory) {
        this.trainerService = trainerService;
        this.subscriptionService = subscriptionService;
        this.sessionMemory = sessionMemory;

        trainerGrid = selectGrid();
        specializationSelect = getSpecializationSelect();
        container = getContainer(trainerGrid, specializationSelect);
        add(container);
    }

    private VerticalLayout getContainer(Grid<EditTrainer> trainerGrid, Select<Specialization> specializationSelect) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(specializationSelect, trainerGrid);

        return container;
    }

    private Grid<EditTrainer> selectGrid() {
        if (subscriptionService.checkStatus(sessionMemory.getId())) {
            return  getTrainersGridWithActiveSubscribe();
        } else  return getTrainersGridWithInactiveSubscription();
    }

    private Select<Specialization> getSpecializationSelect() {
        Select<Specialization> specializationSelect = new Select<>();
        specializationSelect.setItems(Specialization.values());
        specializationSelect.setLabel("Specialization");
        specializationSelect.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                trainerGrid.setItems(trainerService.getAllBySpecialization(specializationSelect.getValue()));
            } else {
                trainerGrid.setItems(trainerService.getTrainers());
            }
        });
        return specializationSelect;
    }

    private Grid<EditTrainer> getTrainersGridWithInactiveSubscription() {
        Grid<EditTrainer> trainerGrid = new Grid<>(EditTrainer.class);
        trainerGrid.setColumns("firstName", "specialization");
        trainerGrid.setItems(trainerService.getTrainers());
        trainerGrid.getColumnByKey("firstName").setWidth("5%");
        trainerGrid.getColumnByKey("specialization").setWidth("10%");

        trainerGrid.addColumn(TemplateRenderer.<EditTrainer>of("<div style='white-space: normal'>[[item.education]]</div>")
                        .withProperty("education", EditTrainer::getEducation))
                .setHeader("Education")
                .setWidth("25%")
                .setFlexGrow(0);

        trainerGrid.addColumn(TemplateRenderer.<EditTrainer>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", EditTrainer::getDescription))
                .setHeader("Description")
                .setWidth("40%")
                .setFlexGrow(0);

        trainerGrid.addColumn(new ComponentRenderer<>(this::createBuyButton)).setHeader("Subscribe").setFlexGrow(2);

        return trainerGrid;
    }


    private Button createBuyButton(EditTrainer editTrainer) {
        Button buyButton = new Button("Subscribe", event -> {
            Dialog dialog = new Dialog();
            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.setAlignItems(Alignment.CENTER);
            dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            Label infoLabel = new Label("Subscription costs for chosen trainer");
            infoLabel.setWidthFull();
            Label costLabel = new Label("For month: " + editTrainer.getMonthPrice().toString() + "$");
            costLabel.setWidthFull();

            Select<Integer> subscriptionLengthSelect = new Select<>();
            subscriptionLengthSelect.setItems(1, 2, 3, 4, 5, 6);
            subscriptionLengthSelect.setLabel("Subscription Length in month");
            subscriptionLengthSelect.setWidth("100%");

            Label priceLabel = new Label();
            priceLabel.setWidthFull();

            Button confirmButton = new Button("Confirm", event1 -> {
                if (!subscriptionLengthSelect.isEmpty()) {
                    if (subscriptionService.subscribe(subscriptionLengthSelect.getValue() , editTrainer.getMonthPrice(),
                            LocalDate.now(), LocalDate.now().plusMonths(subscriptionLengthSelect.getValue()),
                            sessionMemory.getId(), editTrainer.getId())) {
                        Notification.show("Successfully bought");
                        changeGridAfterBuy();
                    }
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

            subscriptionLengthSelect.addValueChangeListener(event2 -> {
                int subscriptionLength = event2.getValue();
                BigDecimal price = editTrainer.getMonthPrice().multiply(BigDecimal.valueOf(subscriptionLength));
                priceLabel.setText("Full price: " + price.toPlainString() + "$");
            });

            Label singleTrainerLabel = new Label("Remember you can subscribe only one trainer");
            singleTrainerLabel.setWidthFull();
            singleTrainerLabel.getStyle().set("color", "#FF0000");

            dialogLayout.add(singleTrainerLabel, infoLabel, costLabel, priceLabel, subscriptionLengthSelect, confirmButton, closeButton);
            dialog.add(dialogLayout);
            dialog.open();
        });

        buyButton.getStyle()
                .set("width", "100%")
                .set("height", "100px")
                .set("background-color", "#002d5c")
                .set("color", "white")
                .set("font-size", "25px");

        return buyButton;
    }

    private Grid<EditTrainer> getTrainersGridWithActiveSubscribe() {
        Grid<EditTrainer> trainerGrid = new Grid<>(EditTrainer.class);

        trainerGrid.setColumns("firstName", "specialization");
        trainerGrid.setItems(trainerService.getTrainers());
        trainerGrid.getColumnByKey("firstName").setWidth("5%");
        trainerGrid.getColumnByKey("specialization").setWidth("10%");

        trainerGrid.addColumn(TemplateRenderer.<EditTrainer>of("<div style='white-space: normal'>[[item.education]]</div>")
                        .withProperty("education", EditTrainer::getEducation))
                .setHeader("Education")
                .setWidth("25%")
                .setFlexGrow(0);
        trainerGrid.addColumn(TemplateRenderer.<EditTrainer>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", EditTrainer::getDescription))
                .setHeader("Description")
                .setWidth("40%")
                .setFlexGrow(0);

        return trainerGrid;
    }

    private void changeGridAfterBuy() {
        container.remove(trainerGrid);
        trainerGrid = getTrainersGridWithActiveSubscribe();
        container.add(trainerGrid);
        refreshGrid();
    }

    private void refreshGrid() {
        trainerGrid.setItems(trainerService.getTrainers());
    }

}
