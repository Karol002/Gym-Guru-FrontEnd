package com.gymguru.frontend.view;

import com.gymguru.frontend.service.OpenAiService;
import com.gymguru.frontend.service.TrainerService;
import com.gymguru.frontend.service.UserService;
import com.gymguru.frontend.view.user.UserAccountView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/gymguru/panel/user")
@PageTitle("User Panel")
public class UserView extends AppLayout {
    private final OpenAiService openAiService;
    private final TrainerService trainerService;
    private final UserService userService;
    private final H1 title;
    private final Tabs tabs;

    @Autowired
    public UserView(OpenAiService openAiService, TrainerService trainerService, UserService userService) {
        this.openAiService = openAiService;
        this.trainerService = trainerService;
        this.userService = userService;
        title = new H1("Welcome in GYM-GUR User panel");
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
                new Tab("Informacje o koncie"),
                new Tab("Mój plan treningowy"),
                new Tab("Znajdź swojego trenera"),
                new Tab("Moje subskrypcje"),
                new Tab("Moje dane konta"),
                new Tab("Zmień hasło")
        );
        tabs.setWidthFull();
        tabs.setHeightFull();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);

        // Add click listener to "Moje dane konta" tab
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Moje dane konta")) {
                setContent(new UserAccountView());
            }
        });

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab.getLabel().equals("Strona główna")) {
                setContent(new MainView(openAiService, trainerService));
            }
        });

        return tabs;
    }
}
