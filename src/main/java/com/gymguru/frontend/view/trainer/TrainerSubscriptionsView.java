package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.SubscriptionStatus;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.domain.dto.SubscriptionWithUserDto;
import com.gymguru.frontend.service.SubscriptionService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

public class TrainerSubscriptionsView extends VerticalLayout {
    private final SubscriptionService subscriptionService;
    private final SessionMemoryDto sessionMemoryDto;
    private final Grid<SubscriptionWithUserDto> subscriptionDtoGrid;

    public TrainerSubscriptionsView(SubscriptionService subscriptionService, SessionMemoryDto sessionMemoryDto) {
        this.subscriptionService = subscriptionService;
        this.sessionMemoryDto = sessionMemoryDto;
        Select<SubscriptionStatus> subscriptionStatusSelect = getSpecializationSelect();
        subscriptionDtoGrid = getSubscriptionGrid();

        VerticalLayout container = getContainer(subscriptionDtoGrid, subscriptionStatusSelect);
        add(container);
    }

    private VerticalLayout getContainer(Grid<SubscriptionWithUserDto> subscriptionDtoGrid, Select<SubscriptionStatus> subscriptionStatusSelect) {
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
                subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsByTrainerId(sessionMemoryDto.getId()));
            } else {
                subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsWithOutPlanByTrainerId(sessionMemoryDto.getId()));
            }
        });
        return specializationSelect;
    }

    private Grid<SubscriptionWithUserDto> getSubscriptionGrid() {
        Grid<SubscriptionWithUserDto> subscriptionDtoGrid = new Grid<>(SubscriptionWithUserDto.class);

        subscriptionDtoGrid.setColumns("userFirstName", "userLastName", "startDate", "endDate", "price");
        subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsByTrainerId(sessionMemoryDto.getId()));
        return subscriptionDtoGrid;
    }
}
