package edu.binghamton.cs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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

public class Dorm {
    String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
    String gameState = states[0];

    //Renderables
    Stage stage;
    SpriteBatch batch;
    Texture img;


    //Game Background
    Texture bg;
    TextureRegion region;

    //Sleep Button
    Texture sleepImg;
    TextureRegion sleepRegion;
    TextureRegionDrawable sleepDrawable;
    ImageButton sleepButton;

    //Study Button
    Texture studyImg;
    TextureRegion studyRegion;
    TextureRegionDrawable studyDrawable;
    ImageButton studyButton;

    //Fitness Button
    Texture fitnessImg;
    TextureRegion fitnessRegion;
    TextureRegionDrawable fitnessDrawable;
    ImageButton fitnessButton;

    //Hunger Button
    Texture hungerImg;
    TextureRegion hungerRegion;
    TextureRegionDrawable hungerDrawable;
    ImageButton hungerButton;

    public void create(){
        batch = new SpriteBatch();


        //Sleep Button
        sleepImg = new Texture(Gdx.files.internal("data/sleepButton.png"));
        sleepRegion = new TextureRegion(sleepImg);
        sleepDrawable = new TextureRegionDrawable(sleepRegion);
        sleepButton = new ImageButton(sleepDrawable);
        sleepButton.setSize(400,400);
        sleepButton.setPosition(Gdx.graphics.getWidth()-500,40);
        sleepButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[1];
            }
        });

        //Study Button
        studyImg = new Texture(Gdx.files.internal("data/studyButton.png"));
        studyRegion = new TextureRegion(studyImg);
        studyDrawable = new TextureRegionDrawable(studyRegion);
        studyButton = new ImageButton(studyDrawable);
        studyButton.setSize(400,400);
        studyButton.setPosition(0,40);
        studyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[3];
            }
        });

        //Fitness Button
        fitnessImg = new Texture(Gdx.files.internal("data/fitnessButton.png"));
        fitnessRegion = new TextureRegion(fitnessImg);
        fitnessDrawable = new TextureRegionDrawable(fitnessRegion);
        fitnessButton = new ImageButton(fitnessDrawable);
        fitnessButton.setSize(200,200);
        fitnessButton.setPosition(Gdx.graphics.getWidth()-300,500);
        fitnessButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[4];
            }
        });

        //Hunger Button
        hungerImg = new Texture(Gdx.files.internal("data/hungerButton.png"));
        hungerRegion = new TextureRegion(hungerImg);
        hungerDrawable = new TextureRegionDrawable(hungerRegion);
        hungerButton = new ImageButton(hungerDrawable);
        hungerButton.setSize(400,400);
        hungerButton.setPosition(0,500);
        hungerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[2];
            }
        });

        //Background
        bg = new Texture(Gdx.files.internal("data/bg.jpeg"));
        region = new TextureRegion(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

        //Staging
        stage = new Stage(new ScreenViewport());
        stage.addActor(sleepButton);
        stage.addActor(hungerButton);
        stage.addActor(fitnessButton);
        stage.addActor(studyButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(){
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(region,0,0);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
