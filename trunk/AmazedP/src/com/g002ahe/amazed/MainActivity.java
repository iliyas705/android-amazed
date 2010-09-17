package com.g002ahe.amazed;

import android.widget.TextView;

import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;

public class MainActivity extends RokonActivity {

	public static final int GAME_WIDTH = 30;
	public static final int GAME_HEIGHT = 20;
	public static final int NUM_BALLS = 2;
	public static final float INITIAL_BALL_SPEED = 5;
	

	private GameScene scene;

	public void onCreate() {
		debugMode();
		forceFullscreen();
		forceLandscape();
		setGameSize(GAME_WIDTH, GAME_HEIGHT);
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		setGraphicsPath("textures/");
		createEngine(true); 
	}

	public void onLoadComplete() {
		Textures.load();
		setScene(scene = new GameScene());

	}

}
