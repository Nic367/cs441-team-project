package edu.binghamton.cs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Dorm {
    String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
    String gameState = states[0];

    //Needs
    int sleepNeed;
    int studyNeed;
    int hungerNeed;
    int bathroomNeed;
    int fitnessNeed;
    int funNeed;
    int[] needs;


    //Renderables
    Stage stage;
    SpriteBatch batch;
    Texture img;

    //Game Background
    Texture bg;
    TextureRegion region;

    //Entertainment Button
    Texture entertainmentImg;
    TextureRegion entertainmentRegion;
    TextureRegionDrawable entertainmentDrawable;
    ImageButton entertainmentButton;

    //Sleep Button
    Texture sleepImg;
    TextureRegion sleepRegion;
    TextureRegionDrawable sleepDrawable;
    ImageButton sleepButton;

    //Sleep Button 2
    Texture sleepImg2;
    TextureRegion sleepRegion2;
    TextureRegionDrawable sleepDrawable2;
    ImageButton sleepButton2;

    //Door Button
    Texture doorImg;
    TextureRegion doorRegion;
    TextureRegionDrawable doorDrawable;
    ImageButton doorButton;

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
    Table hungerTable;

    public void create(){
        batch = new SpriteBatch();

        //Setting up needs
        sleepNeed = 4;
        studyNeed = 4;
        hungerNeed = 4;
        bathroomNeed = 4;
        fitnessNeed = 4;
        funNeed = 4;


        //Sleep Button
        sleepImg = new Texture(Gdx.files.internal("data/dorm/sleepButton.png"));
        sleepRegion = new TextureRegion(sleepImg);
        sleepDrawable = new TextureRegionDrawable(sleepRegion);
        sleepButton = new ImageButton(sleepDrawable);
        sleepButton.getImage().setScale(1.5f);
        sleepButton.setPosition(Gdx.graphics.getWidth()-1250,Gdx.graphics.getHeight()-1150);
        sleepButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[1];
            }
        });

        //Sleep Button Second half
        sleepImg2 = new Texture(Gdx.files.internal("data/dorm/sleepButton2.png"));
        sleepRegion2 = new TextureRegion(sleepImg2);
        sleepDrawable2 = new TextureRegionDrawable(sleepRegion2);
        sleepButton2 = new ImageButton(sleepDrawable2);
        sleepButton2.getImage().setScale(1.6f);
        sleepButton2.setPosition(Gdx.graphics.getWidth()-880,Gdx.graphics.getHeight()-920);
        sleepButton2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[1];
            }
        });

        //Study Button
        studyImg = new Texture(Gdx.files.internal("data/dorm/studyButton.png"));
        studyRegion = new TextureRegion(studyImg);
        studyDrawable = new TextureRegionDrawable(studyRegion);
        studyButton = new ImageButton(studyDrawable);
        studyButton.getImage().setScale(1.5f);
        studyButton.setPosition(Gdx.graphics.getWidth()-275,Gdx.graphics.getHeight()-890);
        studyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[3];
            }
        });

        //Fitness Button
        fitnessImg = new Texture(Gdx.files.internal("data/dorm/fitnessButton.png"));
        fitnessRegion = new TextureRegion(fitnessImg);
        fitnessDrawable = new TextureRegionDrawable(fitnessRegion);
        fitnessButton = new ImageButton(fitnessDrawable);
        fitnessButton.getImage().setScale(1.6f);
        fitnessButton.setPosition(Gdx.graphics.getWidth()-1020,Gdx.graphics.getHeight()-310);
        fitnessButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[4];
            }
        });

        //Hunger Button
        hungerImg = new Texture(Gdx.files.internal("data/dorm/hungerButton.png"));
        hungerRegion = new TextureRegion(hungerImg);
        hungerDrawable = new TextureRegionDrawable(hungerRegion);
        hungerButton = new ImageButton(hungerDrawable);
        hungerButton.getImage().setScale(1.6f);
        hungerButton.setPosition(Gdx.graphics.getWidth()/2-105,Gdx.graphics.getHeight()/2-30);
        hungerButton.addListener(   new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[2];
            }
        });

        //Door Button
        doorImg = new Texture(Gdx.files.internal("data/dorm/doorButton.png"));
        doorRegion = new TextureRegion(doorImg);
        doorDrawable = new TextureRegionDrawable(doorRegion);
        doorButton = new ImageButton(doorDrawable);
        doorButton.getImage().setScale(1.5f);
        doorButton.setPosition(0,Gdx.graphics.getHeight()-1440);
        doorButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                //Change bool inBathroom to true. In render make char walk to door and vanish for 5 secs
            }
        });

        //Entertainment Button
        entertainmentImg = new Texture(Gdx.files.internal("data/dorm/entertainmentButton.png"));
        entertainmentRegion = new TextureRegion(entertainmentImg);
        entertainmentDrawable = new TextureRegionDrawable(entertainmentRegion);
        entertainmentButton = new ImageButton(entertainmentDrawable);
        entertainmentButton.getImage().setScale(1.5f);
        entertainmentButton.setPosition(Gdx.graphics.getWidth()-375,Gdx.graphics.getHeight()-635);
        entertainmentButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                //Change bool inBathroom to true. In render make char walk to door and vanish for 5 secs
            }
        });

        //Background
        bg = new Texture(Gdx.files.internal("data/dorm/dormBG.png"));
        region = new TextureRegion(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

        //Staging
        stage = new Stage(new ScreenViewport());
        stage.addActor(sleepButton);
        stage.addActor(hungerButton);
        stage.addActor(fitnessButton);
        stage.addActor(studyButton);
        stage.addActor(entertainmentButton);
        stage.addActor(doorButton);
        stage.addActor(sleepButton2);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(){
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        batch.draw(bg,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }
}