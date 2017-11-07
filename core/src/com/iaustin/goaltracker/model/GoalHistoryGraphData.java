package com.iaustin.goaltracker.model;

import com.iaustin.goaltracker.view.GraphAxis;

import java.util.ArrayList;

/**
 * Created by iaustin on 8/11/2015.
 */
public class GoalHistoryGraphData implements ILineGraphData {

    public GraphAxis xAxis, yAxis;
    private GoalHistoryRecord[] goalHistory;
    private double[] pointsScaledToLocalAxes;

    private static final int yStart = 0;
    private static final int yEnd = 10;

    public GoalHistoryGraphData(ArrayList<GoalHistoryRecord> goalHistoryRecords)
    {
        goalHistory = goalHistoryRecords.toArray(new GoalHistoryRecord[goalHistoryRecords.size()]);
        setupAxes();
        buildPointsScaledToAxes();
    }

    private void setupAxes()
    {
        double[] xPoints = new double[goalHistory.length];
        double[] yPoints = new double[goalHistory.length];

        for(int i = 0; i < goalHistory.length; i++)
        {
            xPoints[i] = (double) goalHistory[i].getDateSet();
            yPoints[i] = (double) goalHistory[i].getSelfRating();
        }
        setYAxis(yPoints);
        setXAxis(xPoints);
    }

    private void setXAxis(double[] xPoints)
    {
        xAxis = new GraphAxis(xPoints);
    }

    private void setYAxis(double[] yPoints)
    {
        yAxis = new GraphAxis(yStart, yEnd, yPoints);
    }

    private void buildPointsScaledToAxes()
    {
        double[] pointsScaledToAxes = new double[goalHistory.length*2];

        for(int i=0; i < goalHistory.length; i++)
        {
            pointsScaledToAxes[2 * i] = (xAxis.dataPoints[i] - xAxis.start) / (xAxis.end - xAxis.start);
            pointsScaledToAxes[2 * i+1] = (yAxis.dataPoints[i] - yAxis.start) / (yAxis.end - yAxis.start);
        }
        this.pointsScaledToLocalAxes = pointsScaledToAxes;
    }

    @Override
    public double[] getPointsScaledToLocalAxes()
    {
        if(pointsScaledToLocalAxes == null)
        {
            buildPointsScaledToAxes();
        }
        return pointsScaledToLocalAxes;
    }
}
