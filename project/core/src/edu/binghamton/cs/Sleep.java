package edu.binghamton.cs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Sleep extends Game {
    String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
    String gameState = states[1];

    //Renderables
    Stage stage;
    SpriteBatch batch;
    BitmapFont font;

    //Game Background
    Texture bg;
    TextureRegion region;

    public void create(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(15f);
        stage = new Stage(new ScreenViewport());
        setScreen(new SleepGame(this));

    }

    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
    }
}
