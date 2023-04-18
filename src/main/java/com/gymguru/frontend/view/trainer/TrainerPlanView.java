package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.SessionMemory;
import com.gymguru.frontend.domain.SubscriptionWithUser;
import com.gymguru.frontend.domain.edit.EditUser;
import com.gymguru.frontend.domain.read.ReadEdamamMeal;
import com.gymguru.frontend.domain.read.ReadWgerCategory;
import com.gymguru.frontend.domain.read.ReadWgerExercise;
import com.gymguru.frontend.domain.save.SaveExercise;
import com.gymguru.frontend.domain.save.SaveMeal;
import com.gymguru.frontend.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainerPlanView extends VerticalLayout {
    private final static Long EXAMPLE_CATEGORY = 11L;
    private final SubscriptionService subscriptionService;
    private final SessionMemory sessionMemory;
    private final WgerService wgerService;
    private final UserService userService;
    private final EdamamService edmamService;
    private final PlanService planService;
    private final TrainerDialogCreator trainerDialogCreator;
    private final List<ReadWgerCategory> categories;
    private final Select<String> exerciseCategory;
    private final Button finishButton;
    private final Button exerciseButton;
    private final List<SaveExercise> saveExercises = new ArrayList<>();
    private final List<SaveMeal> saveMeals = new ArrayList<>();
    private final TextField mealNameField;
    private final Button findMealButton;
    private final HorizontalLayout findMealLayout;
    private final Grid<ReadWgerExercise> exerciseDtoGrid;
    private final Grid<ReadEdamamMeal> mealDtoGrid;
    private final VerticalLayout mainContainer;
    private Grid<SubscriptionWithUser> subscriptionDtoGrid;
    private EditUser editUser;
    private String dietDescription;
    private String trainingDescription;

    public TrainerPlanView(SubscriptionService subscriptionService, SessionMemory sessionMemory, WgerService wgerService,
                           UserService userService, EdamamService edmamService, PlanService planService) {

        this.planService = planService;
        this.subscriptionService = subscriptionService;
        this.sessionMemory = sessionMemory;
        this.wgerService = wgerService;
        this.userService = userService;
        this.edmamService = edmamService;
        trainerDialogCreator = new TrainerDialogCreator();

        subscriptionDtoGrid = getSubscriptionGrid();
        mealDtoGrid = getMealDtoGrid();
        exerciseButton = getExerciseButton();

        categories = wgerService.getCategories();
        exerciseCategory = getSpecializationSelect();
        exerciseDtoGrid = getExerciseGrid();
        finishButton = getFinishButton();

        mealNameField = getMealNameField();
        findMealButton = getFindMealButton();
        findMealLayout = getFindMealLayout();


        this.mainContainer = getUsersContainer(subscriptionDtoGrid);
        add(mainContainer);
    }

    private VerticalLayout getUsersContainer(Grid<SubscriptionWithUser> subscriptionDtoGrid) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(subscriptionDtoGrid);
        finishButton.setVisible(false);
        exerciseButton.setVisible(false);

        return container;
    }

    private Grid<SubscriptionWithUser> getSubscriptionGrid() {
        Grid<SubscriptionWithUser> subscriptionDtoGrid = new Grid<>(SubscriptionWithUser.class);

        subscriptionDtoGrid.setColumns("userFirstName", "startDate", "endDate", "price");
        subscriptionDtoGrid.getColumnByKey("userFirstName").setWidth("20%");
        subscriptionDtoGrid.getColumnByKey("startDate").setWidth("20%");
        subscriptionDtoGrid.getColumnByKey("endDate").setWidth("20%");
        subscriptionDtoGrid.getColumnByKey("price").setWidth("20%");
        subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsWithOutPlanByTrainerId(sessionMemory.getId()));
        subscriptionDtoGrid.asSingleSelect().addValueChangeListener(event -> selectUser(subscriptionDtoGrid.asSingleSelect().getValue()));
        return subscriptionDtoGrid;

    }

    private void selectUser(SubscriptionWithUser subscriptionWithUser) {
        editUser = userService.getUserById(subscriptionWithUser.getUserId());
        mainContainer.removeAll();
        mainContainer.add(getMealContainer(mealDtoGrid, exerciseButton, findMealLayout));
    }

    private VerticalLayout getExerciseContainer(Grid<ReadWgerExercise> exercisesDtoGrid, Select<String> specializationSelect, Button finishButton) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(specializationSelect, exercisesDtoGrid, finishButton);

        return container;
    }

    private Button getFinishButton() {
        Button finish = new Button("Finish", event -> {
            Dialog dialog = new Dialog();
            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.setAlignItems(Alignment.CENTER);
            dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            TextArea descriptionArea = new TextArea();
            descriptionArea.setLabel("Describe your training");
            descriptionArea.setPlaceholder("Describe");
            descriptionArea.setWidth("400px");
            descriptionArea.setMaxWidth("100%");

            Button confirmButton = new Button("Confirm", event1 -> {
                if(!descriptionArea.isEmpty()) {
                    dialog.close();
                    trainingDescription = descriptionArea.getValue();
                    if (planService.createPlan(dietDescription, trainingDescription, editUser.getId(), sessionMemory.getId(), saveExercises, saveMeals)) {
                        Notification.show("Successful create plan");
                    } else Notification.show("Plan create error");
                    refresh();
                    dialog.close();
                }
            });

            confirmButton.getStyle().set("background-color", "#007bff");
            confirmButton.getStyle().set("color", "#fff");
            confirmButton.setWidthFull();
            Button closeButton = new Button("Close", event2 -> {
                dialog.close();
            });
            closeButton.setWidthFull();

            dialogLayout.add(descriptionArea, confirmButton, closeButton);
            dialog.add(dialogLayout);
            dialog.open();
        });

        finish.setWidthFull();
        finish.setHeight("100px");
        finish.getStyle().set("background-color", "#003366");
        finish.getStyle().set("font-size", "40px");
        finish.getStyle().set("color", "#f2f2ff");
        finish.setVisible(false);

        return finish;
    }

    private Select<String> getSpecializationSelect() {
        Select<String> categorySelect = new Select<>();
        categorySelect.setLabel("Exercise categories");

                categorySelect.setItems(categories.stream().map(ReadWgerCategory::getName).collect(Collectors.toList()));
        categorySelect.addValueChangeListener(event -> {
            String selectedCategoryName = event.getValue();
            Long selectedCategoryId = categories.stream()
                    .filter(readWgerCategory -> readWgerCategory.getName().equals(selectedCategoryName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Not found category:" + selectedCategoryName))
                    .getId();
            exerciseDtoGrid.setItems(wgerService.getExercises(selectedCategoryId));
        });
        categorySelect.setPlaceholder("Chose");
        return categorySelect;
    }

    private Grid<ReadWgerExercise> getExerciseGrid() {
        Grid<ReadWgerExercise> exerciseDtoGrid = new Grid<>(ReadWgerExercise.class);

        exerciseDtoGrid.setColumns("name");
        exerciseDtoGrid.getColumnByKey("name").setWidth("20%");
        exerciseDtoGrid.addColumn(TemplateRenderer.<ReadWgerExercise>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", ReadWgerExercise::getDescription))
                .setHeader("Description")
                .setFlexGrow(60);
        exerciseDtoGrid.asSingleSelect().addValueChangeListener(event -> selectExercise(exerciseDtoGrid.asSingleSelect().getValue()));

        exerciseDtoGrid.setItems(wgerService.getExercises(EXAMPLE_CATEGORY));

        return exerciseDtoGrid;
    }

    private void selectExercise(ReadWgerExercise readWgerExercise) {
        Dialog dialog = trainerDialogCreator.getExerciseDialog(saveExercises, readWgerExercise, finishButton);
        dialog.open();
    }

    private VerticalLayout getMealContainer(Grid<ReadEdamamMeal> mealDtoGrid, Button exerciseButton, HorizontalLayout horizontalLayout) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(horizontalLayout, mealDtoGrid, exerciseButton);

        return container;
    }

    private Grid<ReadEdamamMeal> getMealDtoGrid() {
        Grid<ReadEdamamMeal> mealDtoGrid = new Grid<>(ReadEdamamMeal.class);

        mealDtoGrid.setColumns("label");
        mealDtoGrid.getColumnByKey("label").setWidth("20%");
        mealDtoGrid.addColumn(TemplateRenderer.<ReadEdamamMeal>of("<div style='white-space: normal'>[[item.ingredientLines]]</div>")
                        .withProperty("ingredientLines", ReadEdamamMeal::getIngredientLines))
                .setHeader("Ingredient Lines")
                .setFlexGrow(60);

        mealDtoGrid.asSingleSelect().addValueChangeListener(event -> selectMeal(mealDtoGrid.asSingleSelect().getValue()));
        mealDtoGrid.setItems(edmamService.getMeals("chicken"));
        return mealDtoGrid;
    }

    private void selectMeal(ReadEdamamMeal readEdamamMeal) {
        Dialog dialog = trainerDialogCreator.getMealDialog(saveMeals, readEdamamMeal, exerciseButton);
        dialog.open();
    }

    private Button getExerciseButton() {
        Button exerciseButton = new Button("Finish diet and go to training", event -> {
            Dialog dialog = new Dialog();
            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.setAlignItems(Alignment.CENTER);
            dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            TextArea descriptionArea = new TextArea();
            descriptionArea.setLabel("Describe your diet");
            descriptionArea.setPlaceholder("Describe");
            descriptionArea.setWidth("400px");
            descriptionArea.setMaxWidth("100%");

            Button confirmButton = new Button("Confirm", event1 -> {
                if(!descriptionArea.isEmpty()) {
                    dietDescription = descriptionArea.getValue();
                    mainContainer.removeAll();
                    mainContainer.add(getExerciseContainer(exerciseDtoGrid, exerciseCategory, finishButton));
                    dialog.close();
                }
            });

            confirmButton.getStyle().set("background-color", "#007bff");
            confirmButton.getStyle().set("color", "#fff");
            confirmButton.setWidthFull();
            Button closeButton = new Button("Close", event2 -> {
                dialog.close();
            });
            closeButton.setWidthFull();

            dialogLayout.add(descriptionArea, confirmButton, closeButton);
            dialog.add(dialogLayout);
            dialog.open();
        });

        exerciseButton.setWidthFull();
        exerciseButton.setHeight("100px");
        exerciseButton.getStyle().set("background-color", "#003366");
        exerciseButton.getStyle().set("font-size", "40px");
        exerciseButton.getStyle().set("color", "#f2f2ff");
        exerciseButton.setVisible(false);

        return exerciseButton;
    }

    private HorizontalLayout getFindMealLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(mealNameField, findMealButton);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return horizontalLayout;
    }

    private TextField getMealNameField() {
        TextField mealField = new TextField();
        mealField.setPlaceholder("Enter meal name");
        mealField.setRequired(true);
        mealField.setWidth("400px");
        mealField.setMaxWidth("100%");

        return mealField;
    }

    private Button getFindMealButton() {
        Button mealButton = new Button("Find");
        mealButton.getStyle().set("background-color", "#007bff");
        mealButton.getStyle().set("color", "#fff");
        mealButton.getStyle().set("border", "none");
        mealButton.getStyle().set("border-radius", "0.25rem");

        mealButton.addClickListener(event -> {
            if (!mealNameField.isEmpty()) {
                mealDtoGrid.setItems(edmamService.getMeals(mealNameField.getValue()));
            }
        });
        return mealButton;
    }

    private void refresh() {
        saveMeals.clear();
        saveExercises.clear();
        mainContainer.removeAll();
        subscriptionDtoGrid = getSubscriptionGrid();
        mainContainer.add(getUsersContainer(subscriptionDtoGrid));
    }
}
