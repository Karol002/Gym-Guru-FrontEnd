package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.SessionMemory;
import com.gymguru.frontend.domain.dto.*;
import com.gymguru.frontend.service.PlanService;
import com.gymguru.frontend.service.SubscriptionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;

public class TrainerExtendPlanView extends VerticalLayout {
    private final SubscriptionService subscriptionService;
    private final PlanService planService;
    private final SessionMemory sessionMemory;
    private final VerticalLayout container;
    private Plan plan;

    public TrainerExtendPlanView(SubscriptionService subscriptionService, SessionMemory sessionMemory, PlanService planService) {
        this.subscriptionService = subscriptionService;
        this.planService = planService;
        this.sessionMemory = sessionMemory;
        Grid<SubscriptionWithUser> subscriptionDtoGrid = getSubscriptionGrid();

        this.container = getContainer(subscriptionDtoGrid);
        add(container);
    }

    private VerticalLayout getContainer(Grid<SubscriptionWithUser> subscriptionDtoGrid) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(subscriptionDtoGrid);

        return container;
    }

    private VerticalLayout getContainer(HorizontalLayout trainingLayout, HorizontalLayout dietLayout) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "80vh");
        container.getStyle().set("width", "100%");
        Grid<ExerciseDto> exerciseGrid = getExerciseGird();
        Grid<MealDto> mealGrid = getMealGrid();
        container.add(trainingLayout, exerciseGrid, dietLayout, mealGrid);

        return container;
    }


    private Grid<ExerciseDto> getExerciseGird() {
        Grid<ExerciseDto> exerciseGrid = new Grid<>(ExerciseDto.class);

        exerciseGrid.setColumns("name", "seriesQuantity", "repetitionsQuantity");
        exerciseGrid.getColumnByKey("name").setWidth("20%");
        exerciseGrid.getColumnByKey("seriesQuantity").setWidth("15%");
        exerciseGrid.getColumnByKey("repetitionsQuantity").setWidth("15%");
        exerciseGrid.addColumn(TemplateRenderer.<ExerciseDto>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", ExerciseDto::getDescription))
                .setHeader("Description")
                .setFlexGrow(50);

        exerciseGrid.asSingleSelect().addValueChangeListener(event -> editExercise(exerciseGrid.asSingleSelect().getValue()));
        exerciseGrid.setItems(planService.getExercisesByPlanId(plan.getId()));
        return exerciseGrid;
    }

    private void editExercise(ExerciseDto exercise) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setAlignItems(Alignment.CENTER);
        dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Label infoLabel = new Label("Edit exercise properties");
        infoLabel.setWidthFull();

        TextField exerciseNameField = new TextField();
        exerciseNameField.setWidthFull();
        exerciseNameField.setValue(exercise.getName());

        IntegerField seriesField = new IntegerField();
        seriesField.setHasControls(true);
        seriesField.setStep(1);
        seriesField.setLabel("Series quantity for chosen exercise");
        seriesField.setMax(20);
        seriesField.setMin(1);
        seriesField.setValue(exercise.getSeriesQuantity());
        seriesField.setWidth("400px");
        seriesField.setMaxWidth("100%");


        IntegerField repetitionsField = new IntegerField();
        repetitionsField.setHasControls(true);
        repetitionsField.setStep(1);
        repetitionsField.setLabel("Repetitions quantity for chosen exercise");
        repetitionsField.setMax(50);
        repetitionsField.setMin(1);
        repetitionsField.setValue(exercise.getRepetitionsQuantity());
        repetitionsField.setWidth("400px");
        repetitionsField.setMaxWidth("100%");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setValue(exercise.getDescription());
        descriptionArea.setWidthFull();

        Button confirmButton = new Button("Confirm", event1 -> {
            if (!repetitionsField.isEmpty() && !seriesField.isEmpty()
                    && repetitionsField.getValue() <= repetitionsField.getMax()
                    && seriesField.getValue() <= seriesField.getMax()
                    && !descriptionArea.isEmpty() && !exerciseNameField.isEmpty()) {

                if (planService.updateExercise(new ExerciseDto(
                        exercise.getId(), exerciseNameField.getValue(), descriptionArea.getValue(),
                        repetitionsField.getValue(), seriesField.getValue(), plan.getId()))) {
                    Notification.show("Successful update exercise");
                } else Notification.show("Error update exercise");

                dialog.close();
                getSinglePlan(plan.getUserId());
                Notification.show("Successful update exercise");
            }

        });
        confirmButton.getStyle().set("background-color", "#007bff");
        confirmButton.getStyle().set("color", "#fff");
        confirmButton.setWidthFull();

        Button closeButton = new Button("Close", event2 -> {
            dialog.close();
        });
        closeButton.setWidthFull();

        dialogLayout.add(infoLabel, exerciseNameField, seriesField, repetitionsField, descriptionArea, confirmButton, closeButton);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private Grid<MealDto> getMealGrid() {
        Grid<MealDto> mealGrid = new Grid<>(MealDto.class);

        mealGrid.setColumns("name");
        mealGrid.getColumnByKey("name").setWidth("20%");
        mealGrid.addColumn(TemplateRenderer.<MealDto>of("<div style='white-space: normal'>[[item.cookInstruction]]</div>")
                        .withProperty("cookInstruction", MealDto::getCookInstruction))
                .setHeader("Cook Instruction")
                .setFlexGrow(80);
        mealGrid.asSingleSelect().addValueChangeListener(event -> editMeal(mealGrid.asSingleSelect().getValue()));
        mealGrid.setItems(planService.getMealsByPlanId(plan.getId()));
        return mealGrid;
    }

    private void editMeal(MealDto mealDto) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setAlignItems(Alignment.CENTER);
        dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Label infoLabel = new Label("Edit meal properties");
        infoLabel.setWidthFull();

        TextField mealNameField = new TextField();
        mealNameField.setWidth("400px");
        mealNameField.setValue(mealDto.getName());

        TextArea cookInstructionArea = new TextArea();
        cookInstructionArea.setValue(mealDto.getCookInstruction());
        cookInstructionArea.setWidthFull();

        Button confirmButton = new Button("Confirm", event1 -> {
            if (!cookInstructionArea.isEmpty() && !mealNameField.isEmpty()) {
                if (planService.updateMeal(new MealDto(
                        mealDto.getId(), mealNameField.getValue(), cookInstructionArea.getValue(), plan.getId()))) {
                    Notification.show("Successful update diet");
                } else  Notification.show("Error update diet");

                dialog.close();
                getSinglePlan(plan.getUserId());
                Notification.show("Successful update diet");
            }

        });
        confirmButton.getStyle().set("background-color", "#007bff");
        confirmButton.getStyle().set("color", "#fff");
        confirmButton.setWidthFull();

        Button closeButton = new Button("Close", event2 -> {
            dialog.close();
        });
        closeButton.setWidthFull();

        dialogLayout.add(infoLabel, mealNameField, cookInstructionArea, confirmButton, closeButton);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private Grid<SubscriptionWithUser> getSubscriptionGrid() {
        Grid<SubscriptionWithUser> subscriptionDtoGrid = new Grid<>(SubscriptionWithUser.class);

        subscriptionDtoGrid.setColumns("userFirstName", "userLastName", "startDate", "endDate", "price");
        subscriptionDtoGrid.asSingleSelect().addValueChangeListener(event -> getSinglePlan(subscriptionDtoGrid.asSingleSelect().getValue().getUserId()));
        subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsWithPlanByTrainerId(sessionMemory.getId()));
        return subscriptionDtoGrid;
    }

    private void getSinglePlan(Long userId) {
        plan = planService.getPlan(userId);
        container.removeAll();
        container.add(getContainer(getTrainingLayout(), getDietLayout()));
    }

    private TextArea getTrainingDescriptionArea() {
        TextArea trainingDescriptionArea = new TextArea();
        trainingDescriptionArea.setValue(plan.getExerciseDescription());
        trainingDescriptionArea.setWidthFull();
        trainingDescriptionArea.setReadOnly(true);

        return trainingDescriptionArea;
    }

    private HorizontalLayout getTrainingLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        TextArea trainingArea = getTrainingDescriptionArea();
        Button saveButton = getTrainingSaveButton(plan.getDietDescription(), trainingArea);
        Button trainingButton = getUpdateTrainigDescriptionButton(trainingArea, saveButton);
        horizontalLayout.add(trainingArea, trainingButton, saveButton);

        return horizontalLayout;
    }

    private Button getUpdateTrainigDescriptionButton(TextArea trainingArea, Button saveButton) {
        Button editTriningDescriptionButton = new Button("Edit training description");
        editTriningDescriptionButton.getStyle().set("background-color", "#002d5c");
        editTriningDescriptionButton.getStyle().set("color", "#fff");
        editTriningDescriptionButton.getStyle().set("border", "none");
        editTriningDescriptionButton.getStyle().set("border-radius", "0.25rem");
        editTriningDescriptionButton.setWidth("400px");
        editTriningDescriptionButton.setMaxWidth("100%");
        editTriningDescriptionButton.setHeight("88%");

        editTriningDescriptionButton.addClickListener(event -> {
            editTriningDescriptionButton.setVisible(false);
            saveButton.setVisible(true);
            trainingArea.setReadOnly(false);
        });

        return editTriningDescriptionButton;
    }

    private TextArea getMealDescriptionArea() {
        TextArea mealDescriptionArea = new TextArea();
        mealDescriptionArea.setValue(plan.getDietDescription());
        mealDescriptionArea.setWidthFull();
        mealDescriptionArea.setReadOnly(true);

        return mealDescriptionArea;
    }

    private HorizontalLayout getDietLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        TextArea dietArea = getMealDescriptionArea();
        Button saveButton = getDietSaveButton(dietArea, plan.getExerciseDescription());
        Button dietButton = getUpdateDietDescriptionButton(dietArea, saveButton);
        horizontalLayout.add(dietArea, dietButton, saveButton);

        return horizontalLayout;
    }

    private Button getUpdateDietDescriptionButton(TextArea dietArea, Button saveButton) {
        Button editDietDescriptionButton = new Button("Edit diet description");
        editDietDescriptionButton.getStyle().set("background-color", "#002d5c");
        editDietDescriptionButton.getStyle().set("color", "#fff");
        editDietDescriptionButton.getStyle().set("border", "none");
        editDietDescriptionButton.getStyle().set("border-radius", "0.25rem");
        editDietDescriptionButton.setWidth("400px");
        editDietDescriptionButton.setMaxWidth("100%");
        editDietDescriptionButton.setHeight("88%");

        editDietDescriptionButton.addClickListener(event -> {
            editDietDescriptionButton.setVisible(false);
            saveButton.setVisible(true);
            dietArea.setReadOnly(false);
        });

        return editDietDescriptionButton;
    }

    private Button getTrainingSaveButton(String dietDescription, TextArea planDescription) {
        Button saveButton = new Button("Save changes");
        saveButton.getStyle().set("background-color", "#002d5c");
        saveButton.getStyle().set("color", "#fff");
        saveButton.getStyle().set("border", "none");
        saveButton.getStyle().set("border-radius", "0.25rem");
        saveButton.setWidth("400px");
        saveButton.setMaxWidth("100%");
        saveButton.setHeight("88%");
        saveButton.setVisible(false);

        saveButton.addClickListener(event -> {
            if (planService.updatePlan(new PlanDto(plan.getId(), dietDescription, planDescription.getValue(), plan.getUserId(), plan.getTrainerId()))) {
                Notification.show("Successful update training instructions");
            } else Notification.show("Error update training instructions");
            getSinglePlan(plan.getUserId());
        });

        return saveButton;
    }

    private Button getDietSaveButton(TextArea dietDescription, String planDescription) {
        Button saveButton = new Button("Save changes");
        saveButton.getStyle().set("background-color", "#002d5c");
        saveButton.getStyle().set("color", "#fff");
        saveButton.getStyle().set("border", "none");
        saveButton.getStyle().set("border-radius", "0.25rem");
        saveButton.setWidth("400px");
        saveButton.setMaxWidth("100%");
        saveButton.setHeight("88%");
        saveButton.setVisible(false);

        saveButton.addClickListener(event -> {
            if (planService.updatePlan(new PlanDto(plan.getId(), dietDescription.getValue(), planDescription, plan.getUserId(), plan.getTrainerId()))) {
                Notification.show("Successful update cook instruction");
            } else Notification.show("Error update cook instruction");
            getSinglePlan(plan.getUserId());
        });

        return saveButton;
    }
}
