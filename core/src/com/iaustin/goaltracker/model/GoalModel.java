package com.iaustin.goaltracker.model;

import com.iaustin.goaltracker.event.ISignal;
import com.iaustin.goaltracker.event.Signal;

import java.util.ArrayList;

/**
 * Created by iaustin on 5/18/2015.
 */
public class GoalModel {

    private String name;
    private Signal nameChanged = new Signal();

    private String note;
    private Signal noteChanged = new Signal();

    private int selfRating;
    private Signal selfRatingChanged = new Signal();

    private ArrayList<GoalHistoryRecord> goalHistory = new ArrayList<GoalHistoryRecord>();

    public GoalModel(String name, int selfRating)
    {
        this.name = name;
        this.selfRating = selfRating;
        this.note = "";
    }

    public GoalModel(){}

    public int getSelfRating()
    {
        return selfRating;
    }

    public void setSelfRating(int selfRating)
    {
        if(this.selfRating != selfRating)
        {
            this.selfRating = selfRating;
            selfRatingChanged.dispatch(this);
        }
    }

    public String getNote(){
        return note;
    }

    public ISignal getNoteChanged(){
        return noteChanged;
    }

    public void setNote(String value){
        if(!note.equals(value))
        {
            note=value;
            noteChanged.dispatch(this);
        }
    }

    public ISignal getSelfRatingChanged()
    {
        return selfRatingChanged;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        if(!this.name.equals(name))
        {
            this.name = name;
            nameChanged.dispatch(this);
        }
    }

    public ISignal getNameChanged()
    {
        return nameChanged;
    }

    public ArrayList<GoalHistoryRecord> getGoalHistory()
    {
        return goalHistory;
    }
}
