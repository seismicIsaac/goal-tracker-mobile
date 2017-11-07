package com.iaustin.goaltracker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.iaustin.goaltracker.controller.GoalHistoryDelegate;
import com.iaustin.goaltracker.controller.GoalTrackerController;
import com.iaustin.goaltracker.model.GoalTrackerModel;
import com.iaustin.goaltracker.view.GoalDetailsView;
import com.iaustin.goaltracker.view.GoalListView;
import com.iaustin.goaltracker.view.RendererFactory;

public class GoalTracker extends ApplicationAdapter {

	private GoalTrackerModel model;
	private Stage stage;
	private Skin skin;
	private Table rootTable;
	private GoalListView goalListView;
	private GoalTrackerController goalTrackerController;
	private GoalHistoryDelegate goalHistoryDelegate;
	private GoalDetailsView goalDetailsView;
	private RendererFactory rendererFactory;

	private static final float GOAL_DETAILS_VIEW_WIDTH = 500;
	private static final float GOAL_DETAILS_VIEW_HEIGHT = 300;
	private static final float GOAL_DETAILS_X = 100;
	private static final float GOAL_DETAILS_Y = 100;
	private static final String LOCAL_DATA_FILEPATH = "/data/myData.txt";
	private static FileHandle localData;
	private static Json json = new Json();

	@Override
	public void create () {
		localData = Gdx.files.local(LOCAL_DATA_FILEPATH);

		if(localData.exists())
			model = json.fromJson(GoalTrackerModel.class, localData);
		else
			model = new GoalTrackerModel();

		goalHistoryDelegate = new GoalHistoryDelegate(model);
		goalTrackerController = new GoalTrackerController(model);

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		rendererFactory = new RendererFactory(goalTrackerController, skin);

		rootTable = new Table();
		rootTable.setFillParent(true);
		rootTable.debug();

		goalListView = new GoalListView(skin, model, rendererFactory, goalTrackerController, stage);
		rootTable.add(goalListView).expand().fill();
		stage.addActor(rootTable);

		goalDetailsView = new GoalDetailsView(skin, model);
		goalDetailsView.setWidth(GOAL_DETAILS_VIEW_WIDTH);
		goalDetailsView.setHeight(GOAL_DETAILS_VIEW_HEIGHT);
		goalDetailsView.debug();
		goalDetailsView.setX(GOAL_DETAILS_X);
		goalDetailsView.setY(GOAL_DETAILS_Y);
		stage.addActor(goalDetailsView);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(1.0f);
		stage.act();
		stage.draw();

		if(goalDetailsView.isVisible())
		{
			Gdx.gl.glLineWidth(2f);
			goalDetailsView.lineGraph.drawLineGraph();  // this is apparently necessary to do outside of stage.draw()
		}
	}

	@Override
	public void dispose() {
		localData.writeString(model.toString(), false);
	}

	public void loadSavedGoals()
	{

	}
}
