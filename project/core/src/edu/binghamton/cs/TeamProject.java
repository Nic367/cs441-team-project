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
	final String FITNESS = "sort_minigame";
	public String gameState = DORM;
	Dorm dorm;

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
		dorm = new Dorm();
		dorm.create();

	}

	@Override
	public void render () {
		if(gameState==DORM){
			dorm.render();
			gameState = dorm.gameState;
		}
		else if(gameState==SLEEP){
			ScreenUtils.clear(0,0, 1, 1);
		}
		else if(gameState==HUNGER){
			ScreenUtils.clear(0, 1, 0, 1);
		}
		else if(gameState==STUDY){
			ScreenUtils.clear(1, 1, 0, 1);
		}
		else if(gameState==FITNESS){
			ScreenUtils.clear(1, 0, 1, 1);
		}

	}
	
	@Override
	public void dispose () {
		dorm_batch.dispose();
		img.dispose();
	}
}
