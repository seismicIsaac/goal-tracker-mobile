package com.iaustin.goaltracker.model;

/**
 * Created by iaustin on 7/15/2015.
 */
public class GoalHistoryRecord {

    private String note;
    private int selfRating;
    private long dateSet;

    public GoalHistoryRecord(String note, int selfRating)
    {
        this.note = note;
        this.selfRating = selfRating;
        this.dateSet = System.currentTimeMillis();
    }

    public GoalHistoryRecord(String note, int selfRating, long dateSet)
    {
        this.note = note;
        this.selfRating = selfRating;
        this.dateSet = dateSet;
    }

    public GoalHistoryRecord(){}

    public int getSelfRating()
    {
        return selfRating;
    }

    public long getDateSet()
    {
        return dateSet;
    }
}
