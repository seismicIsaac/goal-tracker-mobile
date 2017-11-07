package com.iaustin.goaltracker.controller;

import com.iaustin.goaltracker.event.ICallback;
import com.iaustin.goaltracker.model.GoalHistoryRecord;
import com.iaustin.goaltracker.model.GoalTrackerModel;
import com.iaustin.goaltracker.model.GoalModel;

/**
 * Created by iaustin on 7/24/2015.
 */
public class GoalHistoryDelegate {
    private GoalTrackerModel model;

    public GoalHistoryDelegate(GoalTrackerModel model){
        this.model = model;
        initialize();
    }

    public GoalHistoryDelegate(){}

    private void initialize() {
        model.getGoalAdded().addCallback(addGoalToHistoryMapCallback);

        for(GoalModel goalModel : model.getGoals())
        {
            addRatingAndNoteChangedListeners(goalModel);
        }
    }

    private void addRatingAndNoteChangedListeners(GoalModel goal)
    {
        goal.getSelfRatingChanged().addCallback(createGoalHistoryRecord);
        goal.getNoteChanged().addCallback(createGoalHistoryRecord);
    }

    private ICallback createGoalHistoryRecord = new ICallback() {
        @Override
        public void execute(Object optional) {
            GoalModel goal = (GoalModel) optional;
            goal.getGoalHistory().add(new GoalHistoryRecord(goal.getNote(), goal.getSelfRating()));
        }
    };

    private ICallback addGoalToHistoryMapCallback = new ICallback() {
        @Override
        public void execute(Object optional) {
            addRatingAndNoteChangedListeners((GoalModel) optional);
        }
    };
}
