package com.iaustin.goaltracker.model;

import com.badlogic.gdx.utils.Json;
import com.iaustin.goaltracker.event.*;

import java.util.ArrayList;

/**
 * Created by iaustin on 5/18/2015.
 */
public class GoalTrackerModel {

    private GoalModel selectedGoal;
    private Signal goalSelected = new Signal();

    private ArrayList<GoalModel> goals;
    private Signal goalAdded = new Signal();
    private Signal goalRemoved = new Signal();

    public void addGoal(GoalModel model)
    {
        goals.add(model);
        goalAdded.dispatch(model);
    }

    public void removeGoal(GoalModel model)
    {
        goals.remove(model);
        goalRemoved.dispatch(model);
    }

    public ISignal getGoalAdded()
    {
        return goalAdded;
    }

    public ISignal getGoalRemoved() { return goalRemoved; }

    public ArrayList<GoalModel> getGoals()
    {
        return goals;
    }

    public void setSelectedGoal(GoalModel model)
    {
        this.selectedGoal = model;
        goalSelected.dispatch(model);
    }

    public ISignal getGoalSelected()
    {
        return goalSelected;
    }

    public GoalTrackerModel()
    {
        goals = new ArrayList<GoalModel>();
        goals.add(new GoalModel("Get Better at programming", 4));
        goals.add(new GoalModel("Talk to people more often", 8));
    }

    @Override
    public String toString()
    {
        Json json = new Json();
        return json.prettyPrint(this);
    }

}
