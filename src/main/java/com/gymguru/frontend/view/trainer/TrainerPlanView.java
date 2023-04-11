package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.Category;
import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.Plan;
import com.gymguru.frontend.domain.dto.ExerciseDto;
import com.gymguru.frontend.domain.dto.SessionMemoryDto;
import com.gymguru.frontend.domain.dto.SubscriptionWithUserDto;
import com.gymguru.frontend.domain.dto.UserDto;
import com.gymguru.frontend.service.SubscriptionService;
import com.gymguru.frontend.service.UserService;
import com.gymguru.frontend.service.WgerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainerPlanView extends VerticalLayout {
    private final static Long EXAMPLE_CATEGORY = 11L;
    private final SubscriptionService subscriptionService;
    private final SessionMemoryDto sessionMemoryDto;
    private final WgerService wgerService;
    private final UserService userService;
    private final List<Category> categories;
    private Grid<ExerciseDto> exerciseDtoGrid;
    private Grid<SubscriptionWithUserDto> subscriptionDtoGrid;
    private VerticalLayout mainContainer;
    private final Select<String> exerciseCategory;
    private UserDto userDto1;
    private final Plan plan;
    private final Button finishButton;
    List<Exercise> exercises = new ArrayList<>();

    public TrainerPlanView(SubscriptionService subscriptionService, SessionMemoryDto sessionMemoryDto, WgerService wgerService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.sessionMemoryDto = sessionMemoryDto;
        this.wgerService = wgerService;
        this.userService = userService;

        plan = new Plan();
        categories = wgerService.getCategories();
        exerciseCategory = getSpecializationSelect();
        exerciseDtoGrid = getExerciseGrid();
        finishButton = getFinishButton();

        subscriptionDtoGrid = getSubscriptionGrid();

        this.mainContainer = getUsersContainer(subscriptionDtoGrid);
        add(mainContainer);
    }
////////////////////////////////////////////////////////////////////////////////////////////////

    private VerticalLayout getUsersContainer(Grid<SubscriptionWithUserDto> subscriptionDtoGrid) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(subscriptionDtoGrid);

        return container;
    }

    private Grid<SubscriptionWithUserDto> getSubscriptionGrid() {
        Grid<SubscriptionWithUserDto> subscriptionDtoGrid = new Grid<>(SubscriptionWithUserDto.class);

        subscriptionDtoGrid.setColumns("userFirstName", "startDate", "endDate", "price");
        subscriptionDtoGrid.getColumnByKey("userFirstName").setWidth("20%");
        subscriptionDtoGrid.getColumnByKey("startDate").setWidth("20%");
        subscriptionDtoGrid.getColumnByKey("endDate").setWidth("20%");
        subscriptionDtoGrid.getColumnByKey("price").setWidth("20%");
        subscriptionDtoGrid.setItems(subscriptionService.getSubscriptionsWithOutPlanByTrainerId(sessionMemoryDto.getId()));
        subscriptionDtoGrid.addColumn(new ComponentRenderer<>(this::createUserButton)).setHeader("Chose user").setFlexGrow(20);
        return subscriptionDtoGrid;

    }

    private Button createUserButton(SubscriptionWithUserDto subscriptionWithUserDto) {
        Button selectUserButton = new Button("Select User", event -> {
            userDto1 = userService.getUserById(subscriptionWithUserDto.getUserId());
            mainContainer.removeAll();
            mainContainer.add(getExerciseContainer(exerciseDtoGrid, exerciseCategory, finishButton));
        });

        selectUserButton.getStyle()
                .set("width", "100%")
                .set("height", "100px")
                .set("background-color", "#002d5c")
                .set("color", "white")
                .set("font-size", "25px");

        return selectUserButton;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    private VerticalLayout getExerciseContainer(Grid<ExerciseDto> exercisesDtoGrid, Select<String> specializationSelect, Button finishButton) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "83vh");
        container.getStyle().set("width", "100%");
        container.add(specializationSelect, exercisesDtoGrid, finishButton);

        return container;
    }

    private Button getFinishButton() {
        Button finish = new Button("Finish", event -> {
            if (!exercises.isEmpty()) {

            }
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

                categorySelect.setItems(categories.stream().map(Category::getName).collect(Collectors.toList()));
        categorySelect.addValueChangeListener(event -> {
            String selectedCategoryName = event.getValue();
            Long selectedCategoryId = categories.stream()
                    .filter(category -> category.getName().equals(selectedCategoryName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Not found category:" + selectedCategoryName))
                    .getId();
            exerciseDtoGrid.setItems(wgerService.getExercises(selectedCategoryId));
        });
        categorySelect.setPlaceholder("Chose");
        return categorySelect;
    }

    private Grid<ExerciseDto> getExerciseGrid() {
        Grid<ExerciseDto> exerciseDtoGrid = new Grid<>(ExerciseDto.class);

        exerciseDtoGrid.setColumns("name");
        exerciseDtoGrid.getColumnByKey("name").setWidth("20%");
        exerciseDtoGrid.addColumn(TemplateRenderer.<ExerciseDto>of("<div style='white-space: normal'>[[item.description]]</div>")
                        .withProperty("description", ExerciseDto::getDescription))
                .setHeader("Description")
                .setFlexGrow(60);
        exerciseDtoGrid.addColumn(new ComponentRenderer<>(this::createExerciseButton)).setHeader("Add exercise").setFlexGrow(20);

        exerciseDtoGrid.setItems(wgerService.getExercises(EXAMPLE_CATEGORY));

        return exerciseDtoGrid;
    }

    private Button createExerciseButton(ExerciseDto exerciseDto) {
        Button buyButton = new Button("Add Exercise", event -> {
            Dialog dialog = new Dialog();
            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.setAlignItems(Alignment.CENTER);
            dialogLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            Label infoLabel = new Label("Set exercise properties");
            infoLabel.setWidthFull();

            IntegerField seriesField = new IntegerField();
            seriesField.setHasControls(true);
            seriesField.setStep(1);
            seriesField.setLabel("Series quantity for chosen exercise");
            seriesField.setMax(20);
            seriesField.setMin(1);
            seriesField.setPlaceholder("For 1 to 20");
            seriesField.setWidth("400px");
            seriesField.setMaxWidth("100%");


            IntegerField repetitionsField = new IntegerField();
            repetitionsField.setHasControls(true);
            repetitionsField.setStep(1);
            repetitionsField.setLabel("Repetitions quantity for chosen exercise");
            repetitionsField.setMax(50);
            repetitionsField.setMin(1);
            repetitionsField.setPlaceholder("For 1 to 50");
            repetitionsField.setWidth("400px");
            repetitionsField.setMaxWidth("100%");

            Button confirmButton = new Button("Confirm", event1 -> {
                if (!repetitionsField.isEmpty() && !seriesField.isEmpty()
                        && repetitionsField.getValue() <= repetitionsField.getMax()
                        && seriesField.getValue() <= seriesField.getMax()) {
                    dialog.close();
                    exercises.add(new Exercise(exerciseDto.getName(), exerciseDto.getDescription(), seriesField.getValue(), repetitionsField.getValue(), plan));
                    finishButton.setVisible(true);
                }

            });
            confirmButton.getStyle().set("background-color", "#007bff");
            confirmButton.getStyle().set("color", "#fff");
            confirmButton.setWidthFull();

            Button closeButton = new Button("Close", event2 -> {
                dialog.close();
            });
            closeButton.setWidthFull();

            dialogLayout.add(infoLabel, seriesField, repetitionsField, confirmButton, closeButton);
            dialog.add(dialogLayout);
            dialog.open();
        });

        buyButton.getStyle()
                .set("width", "100%")
                .set("height", "100px")
                .set("background-color", "#002d5c")
                .set("color", "white")
                .set("font-size", "25px");

        return buyButton;
    }
}
