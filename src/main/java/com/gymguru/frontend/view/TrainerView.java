package com.gymguru.frontend.view;

import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.service.*;
import com.gymguru.frontend.view.trainer.ExtendPlanView;
import com.gymguru.frontend.view.trainer.TrainerAccountView;
import com.gymguru.frontend.view.trainer.TrainerPlanView;
import com.gymguru.frontend.view.trainer.TrainerSubscriptionsView;
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

@Route(value = "/gymguru/panel/trainer")
@PageTitle("Trainer Panel")
public class TrainerView extends AppLayout {
    private final OpenAiService openAiService;
    private final TrainerService trainerService;
    private final EdamamService edamamService;
    private final AuthService authService;
    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final PlanService planService;
    private final WgerService wgerService;
    private final H1 title;
    private final Tabs tabs;

    @Autowired
    public TrainerView(OpenAiService openAiService, TrainerService trainerService, EdamamService edamamService, AuthService authService, SubscriptionService subscriptionService, UserService userService, PlanService planService, WgerService wgerService) {
        this.openAiService = openAiService;
        this.trainerService = trainerService;
        this.edamamService = edamamService;
        this.authService = authService;
        this.subscriptionService = subscriptionService;
        this.userService = userService;
        this.planService = planService;
        this.wgerService = wgerService;

        title = new H1("Welcome in GYM-GURU trainer Panel");
        title.setWidthFull();
        title.getStyle().set("text-align", "center"); // wyc
        setPrimarySection(AppLayout.Section.DRAWER);
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
                new Tab("Stwórz plan"),
                new Tab("Moje plany"),
                new Tab("Moi subskrybenci"),
                new Tab("Moje dane"),
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
            if (selectedTab.getLabel().equals("Stwórz plan")) {
                setContent(new TrainerPlanView(subscriptionService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class), wgerService, userService, edamamService, planService));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Moje plany")) {
                setContent(new ExtendPlanView(subscriptionService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class), planService));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Moi subskrybenci")) {
                setContent(new TrainerSubscriptionsView(subscriptionService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class)));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Moje dane")) {
                setContent(new TrainerAccountView(trainerService, VaadinSession.getCurrent().getAttribute(SessionMemoryDto.class)));
            }
        });


        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Zmień hasło")) {
                setContent(new ChangePaasswordView(authService));
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Log out")) {
                authService.logOut();
            }
        });

        tabs.setSelectedIndex(3);
        tabs.setSelectedIndex(0);
        return tabs;
    }
}
