package com.gymguru.frontend.view;

import com.gymguru.frontend.domain.dto.TrainerDto;
import com.gymguru.frontend.external.app.cllient.OpenAiClient;
import com.gymguru.frontend.service.TrainerService;
import com.gymguru.frontend.domain.dto.OpenAiMessageDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Gym-Guru")
@Route(value = "/gymguru")
public class MainView extends VerticalLayout {
    @Autowired
    public MainView(OpenAiClient openAiClient, TrainerService trainerService) {
        setId("");
        setWidthFull();
        setHeightFull();
        Image image = getGymImage();
        HorizontalLayout loginButtons = getLoginButtons();
        HorizontalLayout aboutUs = getAboutUs(openAiClient, trainerService);

        add(image, loginButtons, aboutUs);
    }

    private HorizontalLayout getAboutUs(OpenAiClient openAiClient, TrainerService trainerService) {
        HorizontalLayout aboutUs = new HorizontalLayout();
        aboutUs.add(getAiLayout(openAiClient));
        aboutUs.add(getTrainersLayout(trainerService));
        aboutUs.setWidthFull();

        return  aboutUs;
    }

    private VerticalLayout getTrainersLayout(TrainerService trainerService) {
        VerticalLayout trainers = new VerticalLayout();
        trainers.add(getTrainersInfo(getTrainersLabel()));
        trainers.add(getTrainersGird(trainerService));
        trainers.getStyle().set("padding", "0px");
        trainers.getStyle().set("margin-left", "10px");
        trainers.setHeightFull();
        trainers.setWidthFull();

        return trainers;
    }

    private Grid<TrainerDto> getTrainersGird(TrainerService trainerService) {
        Grid<TrainerDto> trainerGrid = new Grid<>(TrainerDto.class);

        trainerGrid.setColumns("firstName", "lastName");
        trainerGrid.addColumn(TemplateRenderer.<TrainerDto>of("<div style='white-space: normal'>[[item.education]]</div>")
                        .withProperty("education", TrainerDto::getEducation))
                .setHeader("Education")
                .setFlexGrow(30);
        trainerGrid.addColumn(TemplateRenderer.<TrainerDto>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", TrainerDto::getDescription))
                .setHeader("Description")
                .setFlexGrow(50);
        trainerGrid.getColumnByKey("firstName").setWidth("10%");
        trainerGrid.getColumnByKey("lastName").setWidth("10%");
        trainerGrid.setItems(trainerService.getTrainers());
        trainerGrid.getStyle().set("border", "2px solid #CC9900");
        trainerGrid.setWidthFull();
        refresh(trainerGrid, trainerService);

        return trainerGrid;
    }

    private VerticalLayout getTrainersInfo(Label trainersInfoLabel) {
        VerticalLayout trainersInfoLayout = new VerticalLayout();
        trainersInfoLayout.add(trainersInfoLabel);
        trainersInfoLayout.getStyle().set("border", "2px solid black");
        trainersInfoLayout.getStyle().set("padding", "4px");
        trainersInfoLayout.setWidthFull();

        return trainersInfoLayout;
    }

    private Label getTrainersLabel() {
        Label trainersLabel = new Label("Meet our trainers team");
        trainersLabel.getStyle().set("font-size", "40px");
        trainersLabel.setHeight("60px");
        trainersLabel.getStyle().set("margin-top", "20px");
        trainersLabel.getStyle().set("margin-bottom", "20px");
        trainersLabel.getStyle().set("text-align", "center");
        trainersLabel.setWidthFull();

        return trainersLabel;
    }

    private FormLayout getAiLayout(OpenAiClient openAiClient) {
        FormLayout aiResponseLayout = talkWithAi(openAiClient);
        FormLayout aiInfoLayout = getAiInfoLayout(getAiLabel());

        return joinAiLayouts(aiResponseLayout, aiInfoLayout);
    }

    private FormLayout joinAiLayouts(FormLayout aiResponseLayout, FormLayout aiInfoLayout) {
        FormLayout aiLayout = new FormLayout();
        aiLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("100", 1));
        aiLayout.add(aiInfoLayout);
        aiLayout.add(aiResponseLayout);
        aiLayout.setColspan(aiResponseLayout, 1);
        aiLayout.setColspan(aiInfoLayout, 1);
        aiLayout.setWidth("40%");
        aiLayout.setHeightFull();

        return  aiLayout;
    }

    private FormLayout getAiInfoLayout(Label aiInfoLabel) {
        FormLayout aiLayout = new FormLayout();
        aiLayout.add(aiInfoLabel);
        aiLayout.setWidthFull();
        aiLayout.getStyle().set("border", "2px solid black");
        aiLayout.getStyle().set("padding", "4px");

        return aiLayout;
    }

    private Label getAiLabel() {
        Label aiLabel = new Label("Meet virtual trainer");
        aiLabel.getStyle().set("font-size", "40px");
        aiLabel.setHeight("60px");
        aiLabel.getStyle().set("margin-top", "20px");
        aiLabel.getStyle().set("margin-bottom", "20px");
        aiLabel.getStyle().set("text-align", "center");

        return aiLabel;
    }

    private HorizontalLayout getLoginButtons() {
        HorizontalLayout loginButtons = new HorizontalLayout();
        loginButtons.setWidthFull();
        loginButtons.add(getTrainerLoginButton());
        loginButtons.add(getUserLoginButton());

        return loginButtons;
    }

    private Button getTrainerLoginButton() {
        Button trainerLoginButton = new Button("Create new account", event -> {
            UI.getCurrent().navigate("gymguru/reqister");
        });
        trainerLoginButton.setWidthFull();
        trainerLoginButton.setHeight("100px");
        trainerLoginButton.getStyle().set("background-color", "#660000");
        trainerLoginButton.getStyle().set("font-size", "40px");
        trainerLoginButton.getStyle().set("color", "#f2f2ff");

        return  trainerLoginButton;
    }

    private Button getUserLoginButton() {
        Button userLoginButton = new Button("Login to your account", event -> {
            UI.getCurrent().navigate("gymguru/login");
        });
        userLoginButton.setWidthFull();
        userLoginButton.setHeight("100px");
        userLoginButton.getStyle().set("background-color", "#003366");
        userLoginButton.getStyle().set("font-size", "40px");
        userLoginButton.getStyle().set("color", "#f2f2ff");

        return userLoginButton;
    }

    private Image getGymImage() {
        Image gymImage = new Image("images/bodybuilder.png", "Main Gym-Guru image");
        gymImage.setWidthFull();
        return gymImage;
    }

    private void refresh(Grid<TrainerDto> trainerGrid, TrainerService trainerService) {
        trainerGrid.setItems(trainerService.getTrainers());
    }

    private FormLayout talkWithAi(OpenAiClient openAiClient) {

        TextField userInput = getUserInput();
        Label aiResponseLabel = getAiResponseLabel();
        Button sendButton = getSendButton(userInput.getHeight(), aiResponseLabel, userInput, openAiClient);
        HorizontalLayout sendLayout = getSendLayout(userInput, sendButton);

        FormLayout conversationLayout = new FormLayout();
        conversationLayout.add(aiResponseLabel);
        conversationLayout.add(sendLayout);
        conversationLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("100", 1));
        conversationLayout.setColspan(userInput, 1);
        conversationLayout.setColspan(sendButton, 1);
        conversationLayout.getStyle().set("border", "2px solid #CC5500");
        conversationLayout.getStyle().set("padding", "4px");
        conversationLayout.setWidthFull();
        conversationLayout.getStyle().set("background-color", "#F5F5F5");
        conversationLayout.getStyle().set("margin-top", "16px");

        return conversationLayout;
    }

    private HorizontalLayout getSendLayout(TextField userInput, Button sendButton) {
        HorizontalLayout sendLayout = new HorizontalLayout();
        sendLayout.add(userInput);
        sendLayout.add(sendButton);
        sendLayout.setWidthFull();

        return sendLayout;
    }

    private Label getAiResponseLabel() {
        Label aiLabel = new Label("Jeff response");
        aiLabel.setHeight("350px");
        return aiLabel;
    }

    private Button getSendButton(String height, Label aiLabel, TextField userInput, OpenAiClient openAiClient) {
        Button sendButton = new Button("Ask");
        sendButton.setWidth("20%");
        sendButton.setHeight(height);
        sendButton.getStyle().set("padding", "2px");
        sendButton.getStyle().set("margin", "2px");
        sendButton.getStyle().set("background-color", "#CC5500");
        sendButton.getStyle().set("font-size", "20px");
        sendButton.getStyle().set("color", "#f2f2ff");

        sendButton.addClickListener(event -> {
            aiLabel.setText(getAiOutput(userInput, openAiClient));
        });

        return sendButton;
    }

    private TextField getUserInput() {
        TextField userInput = new TextField();
        userInput.setPlaceholder("Ask our virtual trainer Jeff");
        userInput.getStyle().set("padding", "2px");
        userInput.setWidth("180%");

        return userInput;
    }

    public String getAiOutput(TextField userInput, OpenAiClient openAiClient) {
        return openAiClient.getEndpoint(new OpenAiMessageDto(userInput.getValue())).getContent();
    }
}
