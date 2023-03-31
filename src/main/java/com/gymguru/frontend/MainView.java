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
        trainerLoginButton.setWidthFull();
        trainerLoginButton.setHeight("100px");
        trainerLoginButton.getStyle().set("background-color", "#660000");
        trainerLoginButton.getStyle().set("font-size", "40px");
        trainerLoginButton.getStyle().set("color", "#f2f2ff");
        loginButtons.add(trainerLoginButton);

        Button userLoginButton = new Button("User Login/Register");
        userLoginButton.setWidthFull();
        userLoginButton.setHeight("100px");
        userLoginButton.getStyle().set("background-color", "#003366");
        userLoginButton.getStyle().set("font-size", "40px");
        userLoginButton.getStyle().set("color", "#f2f2ff");

        loginButtons.setWidthFull();
        loginButtons.add(userLoginButton);
        add(loginButtons);

        /////////////////////////////////////////////////

        FormLayout jeffLayout = talkWithAi(openAiClient);
        jeffLayout.getStyle().set("margin-top", "16px");
        jeffLayout.setWidthFull();

        Label jeffLabel = new Label("Meet virtual trainer");
        jeffLabel.getStyle().set("font-size", "40px");
        jeffLabel.setHeight("60px");
        jeffLabel.getStyle().set("margin-top", "20px");
        jeffLabel.getStyle().set("margin-bottom", "20px");
        jeffLabel.getStyle().set("text-align", "center");

        FormLayout aiLayout = new FormLayout();
        aiLayout.add(jeffLabel);
        aiLayout.setWidthFull();
        aiLayout.getStyle().set("border", "2px solid black");
        aiLayout.getStyle().set("padding", "4px");

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("100", 1));
        formLayout.add(aiLayout);
        formLayout.add(jeffLayout);
        formLayout.setColspan(jeffLayout, 1);
        formLayout.setColspan(aiLayout, 1);
        formLayout.setWidth("40%");
        formLayout.setHeightFull();
        /////////////////////////////////////////////////

        Label trainersLabel = new Label("Meet our trainers team");
        trainersLabel.getStyle().set("font-size", "40px");
        trainersLabel.setHeight("60px");
        trainersLabel.getStyle().set("margin-top", "20px");
        trainersLabel.getStyle().set("margin-bottom", "20px");
        trainersLabel.getStyle().set("text-align", "center");
        trainersLabel.setWidthFull();

        VerticalLayout meetTrainers = new VerticalLayout();
        meetTrainers.add(trainersLabel);
        meetTrainers.getStyle().set("border", "2px solid black");
        meetTrainers.getStyle().set("padding", "4px");
        meetTrainers.setWidthFull();

        grid.setColumns("firstName", "lastName", "education");
        grid.addColumn(TemplateRenderer.<Trainer>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", Trainer::getDescription))
                .setHeader("Description")
                .setFlexGrow(50);
        grid.getColumnByKey("firstName").setWidth("10%");
        grid.getColumnByKey("lastName").setWidth("10%");
        grid.getColumnByKey("education").setWidth("27%");
        grid.setItems(trainerService.getTrainers());
        grid.getStyle().set("border", "2px solid #CC9900");
        grid.setWidthFull();
        refresh();

        VerticalLayout trainers = new VerticalLayout();
        trainers.add(meetTrainers);
        trainers.add(grid);
        trainers.getStyle().set("padding", "0px");
        trainers.getStyle().set("margin-left", "10px");
        trainers.setHeightFull();
        trainers.setWidthFull();

        HorizontalLayout aboutUs = new HorizontalLayout();
        aboutUs.add(formLayout);
        //aboutUs.add(grid);
        aboutUs.add(trainers);
        aboutUs.setWidthFull();

        add(aboutUs);
        setWidthFull();
    }

    public void refresh() {
        grid.setItems(trainerService.getTrainers());
    }

    public FormLayout talkWithAi(OpenAiClient openAiClient) {

        TextField userInput = new TextField();
        userInput.setPlaceholder("Ask our virtual trainer Jeff");
        userInput.getStyle().set("padding", "2px");
        userInput.setWidth("180%");


        Button aiButton = new Button("Ask");
        aiButton.setWidth("20%");
        aiButton.setHeight(userInput.getHeight());
        aiButton.getStyle().set("padding", "2px");
        aiButton.getStyle().set("margin", "2px");
        aiButton.getStyle().set("background-color", "#CC5500");
        aiButton.getStyle().set("font-size", "20px");
        aiButton.getStyle().set("color", "#f2f2ff");

        HorizontalLayout userInputLayout = new HorizontalLayout();
        userInputLayout.add(userInput);
        userInputLayout.add(aiButton);
        userInputLayout.setWidthFull();


        FormLayout conversationLayout = new FormLayout();
        Label aiLabel = new Label("Jeff response");
        conversationLayout.add(aiLabel);
        aiLabel.setHeight("350px");
        aiButton.addClickListener(event -> {
           aiLabel.setText(getAiOutput(userInput, openAiClient));
        });

        conversationLayout.add(userInputLayout);

        conversationLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("100", 1));
        conversationLayout.setColspan(userInput, 1);
        conversationLayout.setColspan(aiButton, 1);
        conversationLayout.getStyle().set("border", "2px solid #CC5500");
        conversationLayout.getStyle().set("padding", "4px");
        conversationLayout.setWidthFull();
        conversationLayout.getStyle().set("background-color", "#F5F5F5");

        return conversationLayout;
    }

    public String getAiOutput(TextField userInput, OpenAiClient openAiClient) {
        return openAiClient.getEndpoint(new OpenAiResponseDto(userInput.getValue())).getText();
    }
}
