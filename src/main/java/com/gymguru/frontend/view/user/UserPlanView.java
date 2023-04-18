package com.gymguru.frontend.view.user;

import com.gymguru.frontend.domain.edit.EditExercise;
import com.gymguru.frontend.domain.edit.EditMeal;
import com.gymguru.frontend.domain.edit.EditPlan;
import com.gymguru.frontend.domain.authorization.SessionMemory;
import com.gymguru.frontend.service.PlanService;
import com.gymguru.frontend.service.SubscriptionService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.TemplateRenderer;


public class UserPlanView extends VerticalLayout {
    private final SubscriptionService subscriptionService;
    private final PlanService planService;
    private final SessionMemory sessionMemory;
    private final EditPlan plan;

    public UserPlanView(SubscriptionService subscriptionService, PlanService planService, SessionMemory sessionMemory) {
        this.subscriptionService = subscriptionService;
        this.planService = planService;
        this.sessionMemory = sessionMemory;

        plan = planService.getPlan(sessionMemory.getId());

        if (plan != null) {
            Grid<EditExercise> exerciseGrid = getExerciseGird();
            Grid<EditMeal> mealGrid = getMealGrid();
            TextArea trainingDescriptionArea = getTrainingDescriptionArea();
            TextArea mealDescriptionArea = getMealDescriptionArea();

            VerticalLayout container = getContainer(mealGrid, mealDescriptionArea, exerciseGrid, trainingDescriptionArea);
            add(container);
        } else {
            H1 title = getTitle();
            add(title);
        }
    }

    private TextArea getTrainingDescriptionArea() {
        TextArea trainingDescriptionArea = new TextArea();
        trainingDescriptionArea.setValue(plan.getExerciseDescription());
        trainingDescriptionArea.setWidthFull();
        trainingDescriptionArea.setLabel("Training description");
        trainingDescriptionArea.setReadOnly(true);

        return trainingDescriptionArea;
    }

    private TextArea getMealDescriptionArea() {
        TextArea mealDescriptionArea = new TextArea();
        mealDescriptionArea.setValue(plan.getDietDescription());
        mealDescriptionArea.setWidthFull();
        mealDescriptionArea.setLabel("Diet description");
        mealDescriptionArea.setReadOnly(true);

        return mealDescriptionArea;
    }

    private H1 getTitle() {
        H1 title = new H1(getTitleContent());
        title.getStyle().set("text-align", "center");
        title.setWidthFull();
        return title;
    }

    private String getTitleContent() {
        if (subscriptionService.checkStatus(sessionMemory.getId())) {
            if (plan != null) return "Your plan";
            else return "Wait for plan";
        } else return "You dont have available subscription";
    }

    private VerticalLayout getContainer(Grid<EditMeal> mealGrid, TextArea mealDescription, Grid<EditExercise> exerciseGrid, TextArea exerciseDescription) {
        VerticalLayout container = new VerticalLayout();
        container.getStyle().set("height", "80vh");
        container.getStyle().set("width", "100%");
        container.add(exerciseDescription, exerciseGrid, mealDescription, mealGrid);

        return container;
    }

    private Grid<EditExercise> getExerciseGird() {
        Grid<EditExercise> exerciseGrid = new Grid<>(EditExercise.class);

        exerciseGrid.setColumns("name", "seriesQuantity", "repetitionsQuantity");
        exerciseGrid.getColumnByKey("name").setWidth("20%");
        exerciseGrid.getColumnByKey("seriesQuantity").setWidth("15%");
        exerciseGrid.getColumnByKey("repetitionsQuantity").setWidth("15%");
        exerciseGrid.addColumn(TemplateRenderer.<EditExercise>of("<div style='white-space: normal'>[[item.description]]</div>")
                .withProperty("description", EditExercise::getDescription))
                .setHeader("Description")
                .setFlexGrow(50);
        exerciseGrid.setItems(planService.getExercisesByPlanId(plan.getId()));
        return exerciseGrid;
    }

    private Grid<EditMeal> getMealGrid() {
        Grid<EditMeal> mealGrid = new Grid<>(EditMeal.class);

        mealGrid.setColumns("name");
        mealGrid.getColumnByKey("name").setWidth("20%");
        mealGrid.addColumn(TemplateRenderer.<EditMeal>of("<div style='white-space: normal'>[[item.cookInstruction]]</div>")
                        .withProperty("cookInstruction", EditMeal::getCookInstruction))
                .setHeader("Cook Instruction")
                .setFlexGrow(80);
        mealGrid.setItems(planService.getMealsByPlanId(plan.getId()));
        return mealGrid;
    }
}
