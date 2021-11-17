package edu.binghamton.cs;

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

public class Sleep {
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
    }

    public void render(){
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "This is the sleep minigame", 0, Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),10,true);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
