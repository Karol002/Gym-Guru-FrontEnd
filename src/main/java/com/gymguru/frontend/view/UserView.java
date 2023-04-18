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
    private final Tabs tabs;

    @Autowired
    public UserView(OpenAiService openAiService, TrainerService trainerService, UserService userService, AuthService authService, SubscriptionService subscriptionService, PlanService planService) {
        this.openAiService = openAiService;
        this.trainerService = trainerService;
        this.userService = userService;
        this.authService = authService;
        this.subscriptionService = subscriptionService;
        this.planService = planService;

        H1 title = new H1("Welcome in GYM-GURU user Panel");
        title.setWidthFull();
        title.getStyle().set("text-align", "center");
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
                new Tab("Home"),
                new Tab("My training plan"),
                new Tab("Find your trainer"),
                new Tab("Account Information"),
                new Tab("Change Password"),
                new Tab("Log out")
        );
        tabs.setWidthFull();
        tabs.setHeightFull();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Home")) {
                setContent(new MainView(openAiService, trainerService, true));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("My training plan")) {
                setContent(new UserPlanView(subscriptionService, planService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class)));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Account Information")) {
                setContent(new UserAccountView(userService, subscriptionService));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Find your trainer")) {
                setContent(new UserBuyView(trainerService, subscriptionService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class)));
            }
        });


        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Change Password")) {
                setContent(new ChangePaasswordView(authService));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Log out")) {
                authService.logOut();
            }
        });

        tabs.setSelectedIndex(2);
        tabs.setSelectedIndex(0);
        return tabs;
    }
}
