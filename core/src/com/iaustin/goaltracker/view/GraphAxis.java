package com.iaustin.goaltracker.view;

/**
 * Created by iaustin on 8/2/2015.
 */
public class GraphAxis {

    public double start;
    public double end;
    public double[] dataPoints;

    public GraphAxis(double start, double end, double[] points)
    {
        this.start = start;
        this.end = end;
        this.dataPoints = points;
    }

    public GraphAxis(double[] points)
    {
        double min = Float.MAX_VALUE;
        double max = 0;

        for(int i = 0; i<points.length; i++)
        {
            if(points[i] > max)
                max = points[i];
            if(points[i] < min)
                min = points[i];
        }
        start = min;
        end = max;
        this.dataPoints = points;
    }
}
