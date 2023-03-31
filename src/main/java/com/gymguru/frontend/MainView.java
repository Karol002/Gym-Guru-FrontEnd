package com.gymguru.frontend;

import com.gymguru.frontend.external.app.cllient.OpenAiClient;
import com.gymguru.frontend.external.app.domain.Trainer;
import com.gymguru.frontend.service.TrainerService;
import com.gymguru.frontend.test.OpenAiResponseDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Gym-Guru")
@Route(value = "/gymguru")
public class MainView extends VerticalLayout {
    private TrainerService trainerService = TrainerService.getInstance();
    private Grid<Trainer> grid = new Grid<>(Trainer.class);
    @Autowired
    public MainView(OpenAiClient openAiClient) {
        setId("");
        setWidthFull();
        setHeightFull();

        Image gymImage = new Image("images/bodybuilder.png", "Main Gym-Guru image");
        gymImage.setWidthFull();
        add(gymImage);

        HorizontalLayout loginButtons = new HorizontalLayout();

        Button trainerLoginButton = new Button("Trainer Login/Register");
        trainerLoginButton.setWidth("100%");
        trainerLoginButton.setHeight("100px");
        trainerLoginButton.getStyle().set("background-color", "#660000");
        trainerLoginButton.getStyle().set("font-size", "40px");
        trainerLoginButton.getStyle().set("color", "#f2f2ff");
        loginButtons.add(trainerLoginButton);

        Button userLoginButton = new Button("User Login/Register");
        userLoginButton.setWidth("100%");
        userLoginButton.setHeight("100px");
        userLoginButton.getStyle().set("background-color", "#003366");
        userLoginButton.getStyle().set("font-size", "40px");
        userLoginButton.getStyle().set("color", "#f2f2ff");

        loginButtons.setWidthFull();
        loginButtons.add(userLoginButton);
        add(loginButtons);

        FormLayout trainerTeamLayout = getTrainerTeamLayout(openAiClient);
        add(trainerTeamLayout);

        grid.setColumns("firstName", "lastName", "education");
        grid.addColumn(TemplateRenderer.<Trainer>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", Trainer::getDescription))
                .setHeader("Description")
                .setFlexGrow(50);
        grid.getColumnByKey("firstName").setWidth("10%");
        grid.getColumnByKey("lastName").setWidth("10%");
        grid.getColumnByKey("education").setWidth("27%");
        grid.setWidthFull();
        grid.setItems(trainerService.getTrainers());


        add(grid);
        setWidthFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(trainerService.getTrainers());
    }

    public FormLayout getTrainerTeamLayout(OpenAiClient openAiClient) {

        TextField userInput = new TextField();
        userInput.setPlaceholder("Ask our virtual trainer Jeff");
        userInput.getStyle().set("padding", "1px");
        userInput.setWidthFull();

        Button aiButton = new Button("Ask our virtual trainer Jeff");
        aiButton.setWidth("100%");
        aiButton.setHeight("50px");
        aiButton.getStyle().set("background-color", "#CC5500");
        aiButton.getStyle().set("font-size", "20px");
        aiButton.getStyle().set("color", "#f2f2ff");


        Label label = new Label(openAiClient.getEndpoint(new OpenAiResponseDto("Write day plan")).getText());

        FormLayout formLayout = new FormLayout();
        formLayout.add(userInput);
        formLayout.add(aiButton);
        formLayout.add(label);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setColspan(userInput, 1);
        formLayout.setColspan(aiButton, 1);
        formLayout.getStyle().set("border", "2px solid black");
        formLayout.setWidthFull();

        return formLayout;
    }
}
