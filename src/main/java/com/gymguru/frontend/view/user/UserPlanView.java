package com.gymguru.frontend.view.user;

import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.service.PlanService;
import com.gymguru.frontend.service.SubscriptionService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

public class UserPlanView extends VerticalLayout {
    private final SubscriptionService subscriptionService;
    private final PlanService planService;
    private final SessionMemoryDto sessionMemoryDto;
    private final Plan plan;
    private final H1 title;

    public UserPlanView(SubscriptionService subscriptionService, PlanService planService, SessionMemoryDto sessionMemoryDto) {
        this.subscriptionService = subscriptionService;
        this.planService = planService;
        this.sessionMemoryDto = sessionMemoryDto;

        plan = planService.getPlan(sessionMemoryDto.getId());
        title = getTitle();
        add(title);
    }

    private H1 getTitle() {
        H1 title = new H1(getTitleContent());
        title.getStyle().set("text-align", "center");
        title.setWidthFull();
        return title;
    }

    private String getTitleContent() {
        if (subscriptionService.checkStatus(sessionMemoryDto.getId())) {
            if (plan != null) return "Your plan";
            else return "Wait for plan";
        } else return "You dont have available subscription";
    }
}
