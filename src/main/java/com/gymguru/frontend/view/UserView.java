package com.gymguru.frontend.view;

import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.service.*;
import com.gymguru.frontend.view.user.UserAccountView;
import com.gymguru.frontend.view.user.UserBuyView;
import com.gymguru.frontend.view.user.UserPlanView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/gymguru/panel/user")
@PageTitle("User Panel")
public class UserView extends AppLayout {
    private final OpenAiService openAiService;
    private final TrainerService trainerService;
    private final UserService userService;
    private final AuthService authService;
    private final SubscriptionService subscriptionService;
    private final PlanService planService;
    private final H1 title;
    private final Tabs tabs;

    @Autowired
    public UserView(OpenAiService openAiService, TrainerService trainerService, UserService userService, AuthService authService, SubscriptionService subscriptionService, PlanService planService) {
        this.openAiService = openAiService;
        this.trainerService = trainerService;
        this.userService = userService;
        this.authService = authService;
        this.subscriptionService = subscriptionService;
        this.planService = planService;

        title = new H1("Welcome in GYM-GURU user Panel");
        title.setWidthFull();
        title.getStyle().set("text-align", "center"); // wyc
        setPrimarySection(Section.DRAWER);
        tabs = getTabs();

        addToNavbar(title);
        addToDrawer(getVerticalLayout());
    }

    private VerticalLayout getVerticalLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Image image = new Image("images/user.png", "user logo");
        image.setMaxWidth("200px");
        image.setMaxHeight("200px");
        image.getStyle().set("margin", "auto");
        verticalLayout.add(image);
        verticalLayout.add(tabs);
        return verticalLayout;
    }
    private Tabs getTabs() {
        Tabs tabs = new Tabs(
                new Tab("Strona główna"),
                new Tab("Mój plan treningowy"),
                new Tab("Znajdź swojego trenera"),
                new Tab("Informacje o koncie"),
                new Tab("Zmień hasło"),
                new Tab("Log out")
        );
        tabs.setWidthFull();
        tabs.setHeightFull();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Strona główna")) {
                setContent(new MainView(openAiService, trainerService, true));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Log out")) {
                authService.logOut();
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Mój plan treningowy")) {
                setContent(new UserPlanView(subscriptionService, planService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class)));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Informacje o koncie")) {
                setContent(new UserAccountView(userService, subscriptionService));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Znajdź swojego trenera")) {
                setContent(new UserBuyView(trainerService, subscriptionService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class)));
            }
        });



        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Zmień hasło")) {
                setContent(new ChangePaasswordView(authService));
            }
        });

        tabs.setSelectedIndex(1);
        tabs.setSelectedIndex(0);
        return tabs;
    }
}
