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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Dorm {
    String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
    String gameState = states[0];

    //Needs
    public float timer = 0;
    int[] needs = {7, 7, 7, 7, 7, 7}; //Sleep=0, Study=1, bathroom=2, fun=3, hunger=4, fitness=5

    //Status Bars (8 levels. 0=Empty. 8=Full)
    ArrayList<Texture> statusBars = new ArrayList<>();
    Texture sleepStatus;
    Texture studyStatus;
    Texture bathroomStatus;
    Texture happinessStatus;
    Texture hungerStatus;
    Texture fitnessStatus;
    String status0 = "data/dorm/status0.png";
    String status1 = "data/dorm/status1.png";
    String status2 = "data/dorm/status2.png";
    String status3 = "data/dorm/status3.png";
    String status4 = "data/dorm/status4.png";
    String status5 = "data/dorm/status5.png";
    String status6 = "data/dorm/status6.png";
    String status7 = "data/dorm/status7.png";
    String status8 = "data/dorm/status8.png";


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

        //Status Bars
        sleepStatus = new Texture(Gdx.files.internal(status8));
        studyStatus = new Texture(Gdx.files.internal(status8));
        bathroomStatus = new Texture(Gdx.files.internal(status8));
        hungerStatus = new Texture(Gdx.files.internal(status8));
        happinessStatus = new Texture(Gdx.files.internal(status8));
        fitnessStatus = new Texture(Gdx.files.internal(status8));
        statusBars.add(sleepStatus);
        statusBars.add(studyStatus);
        statusBars.add(bathroomStatus);
        statusBars.add(happinessStatus);
        statusBars.add(hungerStatus);
        statusBars.add(fitnessStatus);

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
        int x = 100;
        for(int i=0; i<6; i++){
            batch.draw(statusBars.get(i),x,110,140,350);
            x+=215;
        }
        batch.end();


        int rand = ThreadLocalRandom.current().nextInt(5,10)%Gdx.graphics.getWidth();
        int needIndex = ThreadLocalRandom.current().nextInt(0,6)%Gdx.graphics.getWidth();

        if(timer >= rand){
            timer=0;
            if(needs[needIndex]>0){
                needs[needIndex]--;
                updateStatusBars(needIndex);
            }
        }
        else{
            timer+=Gdx.graphics.getDeltaTime();
        }
    }

    public void updateStatusBars(int index) {
        if (needs[index] == 8) {
            statusBars.set(index, new Texture(Gdx.files.internal(status8)));
        } else if (needs[index] == 7) {
            statusBars.set(index, new Texture(Gdx.files.internal(status7)));
        } else if (needs[index] == 6) {
            statusBars.set(index, new Texture(Gdx.files.internal(status6)));
        } else if (needs[index] == 5) {
            statusBars.set(index, new Texture(Gdx.files.internal(status5)));
        } else if (needs[index] == 4) {
            statusBars.set(index, new Texture(Gdx.files.internal(status4)));
        } else if (needs[index] == 3) {
            statusBars.set(index, new Texture(Gdx.files.internal(status3)));
        } else if (needs[index] == 2) {
            statusBars.set(index, new Texture(Gdx.files.internal(status2)));
        } else if (needs[index] == 1) {
            statusBars.set(index, new Texture(Gdx.files.internal(status1)));
        } else if (needs[index] == 0) {
            statusBars.set(index, new Texture(Gdx.files.internal(status0)));
        }
    }
}