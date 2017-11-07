package com.iaustin.goaltracker.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.iaustin.goaltracker.controller.GoalTrackerController;
import com.iaustin.goaltracker.model.GoalModel;

/**
 * Created by iaustin on 5/18/2015.
 */
public class GoalRenderer extends Table {

    private GoalModel model;
    private GoalTrackerController controller;
    private Skin skin;
    private TextField goalNameTextField;
    private SelectBox<String> selfRatingSelectionBox;
    private Button viewHistoryButton;
    private Button deleteGoalButton;

    private static final int NUMBER_OF_BUTTONS = 2;
    private static final float BUTTON_WIDTH = 32f;
    private static final float SELF_RATING_SELECTION_BOX_WIDTH = 48f;
    private static final float RIGHT_SIDE_PADDING_FOR_SCROLLBAR = 24f;
    private static final float PAD_RIGHT_BETWEEN_BUTTONS = 5f;

    public void create(){
        goalNameTextField = new TextField(model.getName(), skin, "default");
        deleteGoalButton = new TextButton(" X ", skin, "default");
        viewHistoryButton = new TextButton(" H ", skin, "default");
        setupSelfRatingBox();
        addListeners();
        this.add(goalNameTextField).width(Gdx.graphics.getWidth()-(BUTTON_WIDTH * NUMBER_OF_BUTTONS + SELF_RATING_SELECTION_BOX_WIDTH + RIGHT_SIDE_PADDING_FOR_SCROLLBAR + PAD_RIGHT_BETWEEN_BUTTONS * NUMBER_OF_BUTTONS));
        this.add(viewHistoryButton).padRight(PAD_RIGHT_BETWEEN_BUTTONS);
        this.add(deleteGoalButton).padRight(PAD_RIGHT_BETWEEN_BUTTONS);
        this.add(selfRatingSelectionBox).padRight(RIGHT_SIDE_PADDING_FOR_SCROLLBAR);
    }

    public void setupSelfRatingBox()
    {
        String[] labels = new String[10];
        for(int i =1; i<11;i++)
        {
            String numLabel = new String("" + i);
            labels[i-1]= numLabel;
        }
        selfRatingSelectionBox = new SelectBox<String>(skin, "default");
        selfRatingSelectionBox.setItems(labels);
    }

    public void addListeners()
    {
        selfRatingSelectionBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.setSelfRating(Integer.parseInt(selfRatingSelectionBox.getSelected()));
            }
        });

        deleteGoalButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                controller.deleteGoal(model);
            }
        });

        viewHistoryButton.addListener((new ClickListener (){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                controller.displayGoalHistory(model);
            }
        }));

        goalNameTextField.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.input.setOnscreenKeyboardVisible(true);
            }
        });

        goalNameTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                model.setName(textField.getText());
            }
        });
    }

    public void setSkin(Skin skin)
    {
        this.skin = skin;
    }

    public GoalRenderer(Skin skin, GoalModel model, GoalTrackerController controller)
    {
        this.skin = skin;
        this.model = model;
        this.controller = controller;
        create();
    }

    public TextField getGoalNameTextField()
    {
        return goalNameTextField;
    }

    public GoalModel getModel()
    {
        return model;
    }
}
