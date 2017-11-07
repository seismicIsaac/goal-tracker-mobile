package com.iaustin.goaltracker.view;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.iaustin.goaltracker.controller.GoalTrackerController;
import com.iaustin.goaltracker.model.GoalModel;

/**
 * Created by iaustin on 5/18/2015.
 */
public interface IGoalRendererFactory {
    public GoalRenderer getNewGoalRenderer(GoalModel model);
}
