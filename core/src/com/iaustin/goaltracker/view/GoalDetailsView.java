package com.iaustin.goaltracker.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.iaustin.goaltracker.event.ICallback;
import com.iaustin.goaltracker.model.GoalModel;
import com.iaustin.goaltracker.model.GoalTrackerModel;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by iaustin on 7/25/2015.
 */
public class GoalDetailsView extends Table {

    private GoalModel goal;
    private Table goalInfoTable;
    private Label goalNameLabel;
    private TextArea goalNoteTextArea;
    private Label goalRatingLabel;
    private Label goalLastRatedLabel;
    private Button closeButton;
    public LineGraph lineGraph;
    private Skin skin;

    private static final float GOAL_INFO_TABLE_WIDTH = 175;
    private static final float GOAL_INFO_TABLE_HEIGHT = 300;
    private static final float GOAL_LABEL_WIDTH = GOAL_INFO_TABLE_WIDTH - 10;
    private static final float LINE_GRAPH_WIDTH = 325;
    private static final float LINE_GRAPH_HEIGHT = 300;
    private static final int GOAL_NOTE_TEXT_ROWS = 4;

    public void update()
    {
        if(goal != null) {
            goalNameLabel.setText(goal.getName());
            goalNoteTextArea.setText(goal.getNote());
            goalRatingLabel.setText("Current Rating: " + goal.getSelfRating());
            if(goal.getGoalHistory().size() > 0)
            {
                Date date = new Date(goal.getGoalHistory().get(0).getDateSet());
                goalLastRatedLabel.setText("Last Self Evaluation: " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(date));
            }
        }
        else
        {
            goalNameLabel.setText("Unknown Goal");
        }
    }

    public void setGoal(GoalModel goal)
    {
        this.goal = goal;
    }

    public GoalDetailsView(Skin skin, GoalTrackerModel model)
    {
        this.skin = skin;
        model.getGoalSelected().addCallback(goalSelectedCallback);
        setGoalBackground();
        setupGoalInfoTable();
        lineGraph = new LineGraph(this);
        goalInfoTable.debug();
        this.top().left().add(goalInfoTable).width(GOAL_INFO_TABLE_WIDTH).height(GOAL_INFO_TABLE_HEIGHT);
        this.add(lineGraph).width(LINE_GRAPH_WIDTH).height(LINE_GRAPH_HEIGHT).expand().fill();
        update();
    }

    private void setGoalBackground()
    {
        Pixmap pixmap2 = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.BLACK);
        pixmap2.fill();
        Texture texture2 = new Texture(pixmap2);
        pixmap2.dispose();
        Drawable spriteDrawable = new SpriteDrawable(new Sprite(texture2));
        this.setBackground(spriteDrawable);
    }

    private void setupGoalInfoTable()
    {
        goalInfoTable = new Table();

        goalNameLabel = new Label("BLah Cbrh", this.skin);
        goalNameLabel.setWrap(true);

        goalNoteTextArea = new TextArea("", this.skin);
        goalNoteTextArea.setWidth(GOAL_LABEL_WIDTH);
        goalNoteTextArea.setPrefRows(GOAL_NOTE_TEXT_ROWS);
        goalNoteTextArea.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                goal.setNote(textField.getText());
            }
        });

        goalRatingLabel = new Label("", this.skin);

        goalLastRatedLabel = new Label("", this.skin);
        goalLastRatedLabel.setWrap(true);

        closeButton = new TextButton("Close", this.skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideGoalDetailsView();
            }
        });

        goalInfoTable.top().add(goalNameLabel).width(GOAL_LABEL_WIDTH);
        goalInfoTable.row();
        goalInfoTable.row();
        goalInfoTable.add(goalNoteTextArea).width(GOAL_LABEL_WIDTH);
        goalInfoTable.row();
        goalInfoTable.add(goalRatingLabel).width(GOAL_LABEL_WIDTH);
        goalInfoTable.row();
        goalInfoTable.add(goalLastRatedLabel).width(GOAL_LABEL_WIDTH);
        goalInfoTable.row();
        goalInfoTable.bottom().add(closeButton);
    }


    private void hideGoalDetailsView()
    {
        this.setVisible(false);
    }

    private void showGoalDetailsView()
    {
        this.setVisible(true);
    }

    private ICallback goalSelectedCallback = new ICallback() {
        @Override
        public void execute(Object optional) {
            GoalModel goalModel = (GoalModel) optional;
            setGoal(goalModel);
            lineGraph.setDataSource(goal.getGoalHistory());
            showGoalDetailsView();
            update();
        }
    };
}
