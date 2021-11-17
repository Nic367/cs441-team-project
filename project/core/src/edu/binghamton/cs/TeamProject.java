package edu.binghamton.cs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class TeamProject extends ApplicationAdapter {
	//Game states
	final String DORM = "dorm_loop";
	final String SLEEP = "sleep_minigame";
	final String HUNGER = "food_minigame";
	final String STUDY = "study_minigame";
	final String FITNESS = "sport_minigame";
	public String gameState = DORM;
	Dorm dorm = new Dorm();
	Study study = new Study();
	Sleep sleep = new Sleep();
	Hunger hunger = new Hunger();
	Fitness fitness = new Fitness();

	//Renderables
	SpriteBatch dorm_batch;
	Texture img;

	//Back Button
	Stage stage;
	Texture backImg;
	TextureRegion region;
	TextureRegionDrawable drawable;
	ImageButton button;

	@Override
	public void create () {
		dorm.create();
		fitness.create();
		hunger.create();
		sleep.create();
		study.create();
	}

	@Override
	public void render () {
		if(gameState==DORM){
			dorm.render();
			gameState = dorm.gameState;
		}
		else if(gameState==SLEEP){
			sleep.render();
			gameState = sleep.gameState;
		}
		else if(gameState==HUNGER){
			hunger.render();
			gameState = hunger.gameState;
		}
		else if(gameState==STUDY){
			study.render();
			gameState = study.gameState;
		}
		else if(gameState==FITNESS){
			fitness.render();
			gameState = fitness.gameState;
		}

	}
	
	@Override
	public void dispose () {
		dorm_batch.dispose();
		img.dispose();
	}
}
