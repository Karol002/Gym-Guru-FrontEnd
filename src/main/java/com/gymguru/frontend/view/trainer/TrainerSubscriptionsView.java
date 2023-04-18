package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.enums.SubscriptionStatus;
import com.gymguru.frontend.domain.authorization.SessionMemory;
import com.gymguru.frontend.domain.read.ReadSubscriptionWithUserSave;
import com.gymguru.frontend.service.SubscriptionService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

public class TrainerSubscriptionsView extends VerticalLayout {
    private final SubscriptionService subscriptionService;
    private final SessionMemory sessionMemory;
    private final Grid<ReadSubscriptionWithUserSave> subscriptionDtoGrid;

    public TrainerSubscriptionsView(SubscriptionService subscriptionService, SessionMemory sessionMemory) {
        this.subscriptionService = subscriptionService;
        this.sessionMemory = sessionMemory;
        Select<SubscriptionStatus> subscriptionStatusSelect = getSpecializationSelect();
        subscriptionDtoGrid = getSubscriptionGrid();

        VerticalLayout container = getContainer(subscriptionDtoGrid, subscriptionStatusSelect);
        add(container);
    }

    private VerticalLayout getContainer(Grid<ReadSubscriptionWithUserSave> subscriptionDtoGrid, Select<SubscriptionStatus> subscriptionStatusSelect) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(subscriptionStatusSelect, subscriptionDtoGrid);

        return container;
    }

    private Select<SubscriptionStatus> getSpecializationSelect() {
        Select<SubscriptionStatus> specializationSelect = new Select<>();
        specializationSelect.setItems(SubscriptionStatus.values());
        specializationSelect.setLabel("Subscriptions");
        specializationSelect.addValueChangeListener(event -> {
            if (event.getValue() == null || event.getValue() == SubscriptionStatus.All) {
                subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsByTrainerId(sessionMemory.getId()));
            } else {
                subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsWithOutPlanByTrainerId(sessionMemory.getId()));
            }
        });
        return specializationSelect;
    }

    private Grid<ReadSubscriptionWithUserSave> getSubscriptionGrid() {
        Grid<ReadSubscriptionWithUserSave> subscriptionDtoGrid = new Grid<>(ReadSubscriptionWithUserSave.class);

        subscriptionDtoGrid.setColumns("userFirstName", "userLastName", "startDate", "endDate", "price");
        subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsByTrainerId(sessionMemory.getId()));
        return subscriptionDtoGrid;
    }
}
