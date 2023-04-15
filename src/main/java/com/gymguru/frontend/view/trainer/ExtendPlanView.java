package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.ExerciseWithId;
import com.gymguru.frontend.domain.dto.MealWithId;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.domain.dto.SubscriptionWithUserDto;
import com.gymguru.frontend.service.PlanService;
import com.gymguru.frontend.service.SubscriptionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;

public class ExtendPlanView extends VerticalLayout {
    private final SubscriptionService subscriptionService;
    private final PlanService planService;
    private final SessionMemoryDto sessionMemoryDto;
    private Grid<SubscriptionWithUserDto> subscriptionDtoGrid;
    private Grid<ExerciseWithId> exerciseGrid;
    private Grid<MealWithId> mealGrid;
    private Plan plan;
    private VerticalLayout container;

    public ExtendPlanView(SubscriptionService subscriptionService, SessionMemoryDto sessionMemoryDto, PlanService planService) {
        this.subscriptionService = subscriptionService;
        this.planService = planService;
        this.sessionMemoryDto = sessionMemoryDto;
        subscriptionDtoGrid = getSubscriptionGrid();

        this.container = getContainer(subscriptionDtoGrid);
        add(container);
    }

    private VerticalLayout getContainer(Grid<SubscriptionWithUserDto> subscriptionDtoGrid) {
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
        exerciseGrid = getExerciseGird();
        mealGrid = getMealGrid();
        container.add(trainingLayout, exerciseGrid, dietLayout, mealGrid);

        return container;
    }


    private Grid<ExerciseWithId> getExerciseGird() {
        Grid<ExerciseWithId> exerciseGrid = new Grid<>(ExerciseWithId.class);

        exerciseGrid.setColumns("name", "seriesQuantity", "repetitionsQuantity");
        exerciseGrid.getColumnByKey("name").setWidth("20%");
        exerciseGrid.getColumnByKey("seriesQuantity").setWidth("15%");
        exerciseGrid.getColumnByKey("repetitionsQuantity").setWidth("15%");
        exerciseGrid.addColumn(TemplateRenderer.<ExerciseWithId>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", ExerciseWithId::getDescription))
                .setHeader("Description")
                .setFlexGrow(50);
        exerciseGrid.asSingleSelect().addValueChangeListener(event -> editExercise(exerciseGrid.asSingleSelect().getValue()));
        exerciseGrid.setItems(planService.getExercisesByPlanId(plan.getId()));
        return exerciseGrid;
    }

    private void editExercise(ExerciseWithId exercise) {
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

                planService.updateExercise(new ExerciseWithId(
                        exercise.getId(), exerciseNameField.getValue(), descriptionArea.getValue(), repetitionsField.getValue(), seriesField.getValue(), plan.getId()));
                dialog.close();
                getSinglePlan(plan.getUserId());
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

    private Grid<MealWithId> getMealGrid() {
        Grid<MealWithId> mealGrid = new Grid<>(MealWithId.class);

        mealGrid.setColumns("name");
        mealGrid.getColumnByKey("name").setWidth("20%");
        mealGrid.addColumn(TemplateRenderer.<MealWithId>of("<div style='white-space: normal'>[[item.cookInstruction]]</div>")
                        .withProperty("cookInstruction", MealWithId::getCookInstruction))
                .setHeader("Cook Instruction")
                .setFlexGrow(80);
        mealGrid.asSingleSelect().addValueChangeListener(event -> editMeal(mealGrid.asSingleSelect().getValue()));
        mealGrid.setItems(planService.getMealsByPlanId(plan.getId()));
        return mealGrid;
    }

    private void editMeal(MealWithId mealWithId) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setAlignItems(Alignment.CENTER);
        dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Label infoLabel = new Label("Edit meal properties");
        infoLabel.setWidthFull();

        TextField mealNameField = new TextField();
        mealNameField.setWidth("400px");
        mealNameField.setValue(mealWithId.getName());

        TextArea cookInstructionArea = new TextArea();
        cookInstructionArea.setValue(mealWithId.getCookInstruction());
        cookInstructionArea.setWidthFull();

        Button confirmButton = new Button("Confirm", event1 -> {
            if (!cookInstructionArea.isEmpty() && !mealNameField.isEmpty()) {
                planService.updateMeal(new MealWithId(
                        mealWithId.getId(), mealNameField.getValue(), cookInstructionArea.getValue(), plan.getId()));
                dialog.close();
                getSinglePlan(plan.getUserId());
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

    private Grid<SubscriptionWithUserDto> getSubscriptionGrid() {
        Grid<SubscriptionWithUserDto> subscriptionDtoGrid = new Grid<>(SubscriptionWithUserDto.class);

        subscriptionDtoGrid.setColumns("userFirstName", "userLastName", "startDate", "endDate", "price");
        subscriptionDtoGrid.asSingleSelect().addValueChangeListener(event -> getSinglePlan(subscriptionDtoGrid.asSingleSelect().getValue().getUserId()));
        subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsWithPlanByTrainerId(sessionMemoryDto.getId()));
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
        trainingDescriptionArea.setLabel("Training description");
        trainingDescriptionArea.setReadOnly(true);

        return trainingDescriptionArea;
    }

    private HorizontalLayout getTrainingLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setHeightFull();
        Button trainingButton = getUpdateTrainigDescriptionButton();
        TextArea trainingArea = getTrainingDescriptionArea();
        horizontalLayout.add(trainingArea, trainingButton);

        return horizontalLayout;
    }

    private Button getUpdateTrainigDescriptionButton() {
        Button editTriningDescriptionButton = new Button("Edit training description");
        editTriningDescriptionButton.getStyle().set("background-color", "#002d5c");
        editTriningDescriptionButton.getStyle().set("color", "#fff");
        editTriningDescriptionButton.getStyle().set("border", "none");
        editTriningDescriptionButton.getStyle().set("border-radius", "0.25rem");
        editTriningDescriptionButton.setWidth("400px");
        editTriningDescriptionButton.setMaxWidth("100%");
        editTriningDescriptionButton.getStyle().set("margin-top", "20px");

        return editTriningDescriptionButton;
    }

    private TextArea getMealDescriptionArea() {
        TextArea mealDescriptionArea = new TextArea();
        mealDescriptionArea.setValue(plan.getDietDescription());
        mealDescriptionArea.setWidthFull();
        mealDescriptionArea.setLabel("Diet description");
        mealDescriptionArea.setReadOnly(true);

        return mealDescriptionArea;
    }

    private HorizontalLayout getDietLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setHeightFull();
        Button dietButton = getUpdateDietDescriptionButton();
        TextArea dietArea = getMealDescriptionArea();
        horizontalLayout.add(dietArea, dietButton);

        return horizontalLayout;
    }

    private Button getUpdateDietDescriptionButton() {
        Button editDietDescriptionButton = new Button("Edit diet description");
        editDietDescriptionButton.getStyle().set("background-color", "#002d5c");
        editDietDescriptionButton.getStyle().set("color", "#fff");
        editDietDescriptionButton.getStyle().set("border", "none");
        editDietDescriptionButton.getStyle().set("border-radius", "0.25rem");
        editDietDescriptionButton.setWidth("400px");
        editDietDescriptionButton.setMaxWidth("100%");
        editDietDescriptionButton.getStyle().set("margin-top", "20px");

        return editDietDescriptionButton;
    }
}
