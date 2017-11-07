package com.iaustin.goaltracker.view;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.iaustin.goaltracker.model.GoalHistoryRecord;
import com.iaustin.goaltracker.model.GoalHistoryGraphData;
import com.iaustin.goaltracker.model.ILineGraphData;

import java.util.ArrayList;

/**
 * Created by iaustin on 8/2/2015.
 */
public class LineGraph extends Actor {

    public ILineGraphData graphData;
    private float scale = 1;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Actor goalDetailsView;

    public LineGraph(Actor goalDetailsView)
    {
        this.goalDetailsView = goalDetailsView;
    }

    public void drawLineGraph() {
        if(graphData != null && getScaledPoints().length > 3)
        {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 1, 0, 1);
            shapeRenderer.polyline(getScaledPoints());
            shapeRenderer.end();
        }
    }

    public float[] getScaledPoints()
    {
        double[] scaledToLocalAxes = graphData.getPointsScaledToLocalAxes();
        float[] scaledPoints = new float[scaledToLocalAxes.length];
        for(int i=0; i<scaledToLocalAxes.length; i++)
        {
            float parentCoord;
            if(i % 2 == 0)
            {
                parentCoord = this.getX() + goalDetailsView.getX();
                scaledPoints[i] = (float)(scaledToLocalAxes[i] * scale * this.getWidth() + parentCoord);
            }
            else
            {
                parentCoord = this.getY() + goalDetailsView.getY();
                scaledPoints[i] = (float)(scaledToLocalAxes[i] * scale * this.getHeight() + parentCoord);
            }
        }

        return scaledPoints;
    }

    public void setDataSource(ArrayList<GoalHistoryRecord> historyRecords)
    {
        graphData = new GoalHistoryGraphData(historyRecords);
    }
}
