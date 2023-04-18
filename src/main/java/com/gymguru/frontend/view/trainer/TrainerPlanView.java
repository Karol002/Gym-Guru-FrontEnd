package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.*;
import com.gymguru.frontend.domain.dto.*;
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
    private final List<WgerCategory> categories;
    private final Select<String> exerciseCategory;
    private final Button finishButton;
    private final Button exerciseButton;
    private final List<Exercise> exercises = new ArrayList<>();
    private final List<Meal> meals = new ArrayList<>();
    private final TextField mealNameField;
    private final Button findMealButton;
    private final HorizontalLayout findMealLayout;
    private final Grid<WgerExercise> exerciseDtoGrid;
    private final Grid<EdamamMeal> mealDtoGrid;
    private final VerticalLayout mainContainer;
    private Grid<SubscriptionWithUser> subscriptionDtoGrid;
    private User user;
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

    private void selectUser(SubscriptionWithUser subscriptionWithUserDto) {
        user = userService.getUserById(subscriptionWithUserDto.getUserId());
        mainContainer.removeAll();
        mainContainer.add(getMealContainer(mealDtoGrid, exerciseButton, findMealLayout));
    }

    private VerticalLayout getExerciseContainer(Grid<WgerExercise> exercisesDtoGrid, Select<String> specializationSelect, Button finishButton) {
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
                    if (planService.createPlan(dietDescription, trainingDescription, user.getId(), sessionMemory.getId(), exercises, meals)) {
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

                categorySelect.setItems(categories.stream().map(WgerCategory::getName).collect(Collectors.toList()));
        categorySelect.addValueChangeListener(event -> {
            String selectedCategoryName = event.getValue();
            Long selectedCategoryId = categories.stream()
                    .filter(wgerCategory -> wgerCategory.getName().equals(selectedCategoryName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Not found category:" + selectedCategoryName))
                    .getId();
            exerciseDtoGrid.setItems(wgerService.getExercises(selectedCategoryId));
        });
        categorySelect.setPlaceholder("Chose");
        return categorySelect;
    }

    private Grid<WgerExercise> getExerciseGrid() {
        Grid<WgerExercise> exerciseDtoGrid = new Grid<>(WgerExercise.class);

        exerciseDtoGrid.setColumns("name");
        exerciseDtoGrid.getColumnByKey("name").setWidth("20%");
        exerciseDtoGrid.addColumn(TemplateRenderer.<WgerExercise>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", WgerExercise::getDescription))
                .setHeader("Description")
                .setFlexGrow(60);
        exerciseDtoGrid.asSingleSelect().addValueChangeListener(event -> selectExercise(exerciseDtoGrid.asSingleSelect().getValue()));

        exerciseDtoGrid.setItems(wgerService.getExercises(EXAMPLE_CATEGORY));

        return exerciseDtoGrid;
    }

    private void selectExercise(WgerExercise wgerExercise) {
        Dialog dialog = trainerDialogCreator.getExerciseDialog(exercises, wgerExercise, finishButton);
        dialog.open();
    }

    private VerticalLayout getMealContainer(Grid<EdamamMeal> mealDtoGrid, Button exerciseButton, HorizontalLayout horizontalLayout) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(horizontalLayout, mealDtoGrid, exerciseButton);

        return container;
    }

    private Grid<EdamamMeal> getMealDtoGrid() {
        Grid<EdamamMeal> mealDtoGrid = new Grid<>(EdamamMeal.class);

        mealDtoGrid.setColumns("label");
        mealDtoGrid.getColumnByKey("label").setWidth("20%");
        mealDtoGrid.addColumn(TemplateRenderer.<EdamamMeal>of("<div style='white-space: normal'>[[item.ingredientLines]]</div>")
                        .withProperty("ingredientLines", EdamamMeal::getIngredientLines))
                .setHeader("Ingredient Lines")
                .setFlexGrow(60);

        mealDtoGrid.asSingleSelect().addValueChangeListener(event -> selectMeal(mealDtoGrid.asSingleSelect().getValue()));
        mealDtoGrid.setItems(edmamService.getMeals("chicken"));
        return mealDtoGrid;
    }

    private void selectMeal(EdamamMeal edamamMeal) {
        Dialog dialog = trainerDialogCreator.getMealDialog(meals, edamamMeal, exerciseButton);
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
        meals.clear();
        exercises.clear();
        mainContainer.removeAll();
        subscriptionDtoGrid = getSubscriptionGrid();
        mainContainer.add(getUsersContainer(subscriptionDtoGrid));
    }
}
