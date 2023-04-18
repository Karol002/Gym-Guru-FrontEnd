package com.gymguru.frontend.view.trainer;

import com.gymguru.frontend.domain.Exercise;
import com.gymguru.frontend.domain.Meal;
import com.gymguru.frontend.domain.WgerExercise;
import com.gymguru.frontend.domain.dto.EdamamMeal;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;

import java.util.List;

public class TrainerDialogCreator {

    public Dialog getMealDialog(List<Meal> meals, EdamamMeal edamamMeal, Button exerciseButton) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        dialogLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Label infoLabel = new Label("Do you want add meal to diet?");
        infoLabel.setWidthFull();

        Button confirmButton = new Button("Confirm", event1 -> {
            meals.add(new Meal(edamamMeal.getLabel(), edamamMeal.getIngredientLines()));
            exerciseButton.setVisible(true);
            dialog.close();
        });
        confirmButton.getStyle().set("background-color", "#007bff");
        confirmButton.getStyle().set("color", "#fff");
        confirmButton.setWidthFull();

        Button closeButton = new Button("Cancel", event2 -> {
            dialog.close();
        });
        closeButton.setWidthFull();

        dialogLayout.add(infoLabel, confirmButton, closeButton);
        dialog.add(dialogLayout);

        return dialog;
    }

    public Dialog getExerciseDialog(List<Exercise> exercises, WgerExercise wgerExercise, Button finishButton) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        dialogLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

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
                exercises.add(new Exercise(wgerExercise.getName(), wgerExercise.getDescription(), seriesField.getValue(), repetitionsField.getValue()));
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

        return dialog;
    }
}
