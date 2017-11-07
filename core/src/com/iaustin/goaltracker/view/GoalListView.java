package com.iaustin.goaltracker.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.iaustin.goaltracker.controller.GoalTrackerController;
import com.iaustin.goaltracker.event.ICallback;
import com.iaustin.goaltracker.model.GoalModel;
import com.iaustin.goaltracker.model.GoalTrackerModel;

import java.util.HashMap;

/**
 * Created by iaustin on 5/18/2015.
 */
public class GoalListView extends Table {

    private Stage stage;
    private Skin skin;
    private Table headerTable;
    private Label headerLabel;
    private TextButton createGoalButton;
    private VerticalGroup goalListTable;
    private ScrollPane scrollPane;
    private IGoalRendererFactory rendererFactory;
    private GoalTrackerController goalTrackerController;
    private GoalTrackerModel goalTrackerModel;
    private HashMap<GoalModel, GoalRenderer> goalModelToGoalRenderer = new HashMap<GoalModel, GoalRenderer>();

    private static final float CREATE_GOAL_BUTTON_RIGHT_PADDING = 10f;
    private static final float HEADER_HEIGHT = 100f;

    public GoalListView(Skin skin, GoalTrackerModel model, IGoalRendererFactory rendererFactory, GoalTrackerController goalTrackerController, Stage stage)
    {
        this.skin = skin;
        this.rendererFactory = rendererFactory;
        this.goalTrackerController = goalTrackerController;
        this.goalTrackerModel = model;
        this.stage = stage;
        create();
    }

    private void create()
    {
        headerTable = new Table();
        headerLabel = new Label("List of My Favorite Goals", skin, "default");
        createGoalButton = new TextButton(" + ", skin, "default");
        addCreateGoalButtonListener();
        headerTable.add(headerLabel).expandX().fillX();
        headerTable.add(createGoalButton).padRight(CREATE_GOAL_BUTTON_RIGHT_PADDING);
        goalListTable = new VerticalGroup();
        goalListTable.left();
        scrollPane = new ScrollPane(goalListTable, skin);
        this.add(headerTable).prefHeight(HEADER_HEIGHT).expandX().fillX();
        this.row();
        this.add(scrollPane).expand().fill();

        goalTrackerModel.getGoalAdded().addCallback(new ICallback() {
            @Override
            public void execute(Object optional) {
                addGoalRendererToGoalListView((GoalModel)optional);
            }
        });
        goalTrackerModel.getGoalRemoved().addCallback(new ICallback() {
            @Override
            public void execute(Object optional) {
                removeGoalFromList((GoalModel)optional);
            }
        });

        initializeGoalsList();
    }

    private void removeGoalFromList(GoalModel model) {
        removeGoalRendererFromList(goalModelToGoalRenderer.get(model));
        goalModelToGoalRenderer.remove(model);
    }

    private GoalRenderer requestGoalRenderer(GoalModel goal)
    {
        return rendererFactory.getNewGoalRenderer(goal);
    }

    private void initializeGoalsList()
    {
        for(GoalModel goal : goalTrackerModel.getGoals())
        {
            if(!goalModelToGoalRenderer.containsKey(goal))
                addGoalRendererToGoalListView(goal);
        }
    }

    private void removeGoalRendererFromList(GoalRenderer renderer) {
        goalListTable.removeActor(renderer);
    }

    private void addGoalRendererToGoalListView(GoalModel goal)
    {
        GoalRenderer goalRenderer = requestGoalRenderer(goal);
        goalModelToGoalRenderer.put(goal, goalRenderer);
        goalListTable.addActor(goalRenderer);
        stage.setKeyboardFocus(goalRenderer.getGoalNameTextField());
        Gdx.input.setOnscreenKeyboardVisible(true);
    }

    private void addCreateGoalButtonListener()
    {
        createGoalButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                goalTrackerController.createNewGoal();
            }
        });
    }
}
