package com.iaustin.goaltracker.controller;

import com.iaustin.goaltracker.model.GoalModel;
import com.iaustin.goaltracker.model.GoalTrackerModel;

/**
 * Created by iaustin on 5/18/2015.
 */
public class GoalTrackerController {

    private GoalTrackerModel goalTrackerModel;

    public static int goalNumber;

    public void createNewGoal()
    {
        GoalModel goal = new GoalModel("Goal #" + goalNumber, 1);
        goalNumber++;
        goalTrackerModel.addGoal(goal);
    }

    public void deleteGoal(GoalModel model)
    {
        goalTrackerModel.removeGoal(model);
    }

    public void displayGoalHistory(GoalModel model)
    {
        goalTrackerModel.setSelectedGoal(model);
    }

    public GoalTrackerController(GoalTrackerModel model)
    {
        this.goalTrackerModel = model;
    }

}
