package edu.binghamton.cs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    static String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
//    String gameState = TeamProject.gameState;

    //Needs
    public float timer = 0;
    public float bathroomTimer = 0;
    static public int[] needs = {4, 4, 1, 1, 4, 4}; //Sleep=0, Study=1, hygiene=2, fun=3, hunger=4, fitness=5

    //Status Bars (8 levels. 0=Empty. 8=Full)
    static ArrayList<Texture> statusBars = new ArrayList<>();
    Texture sleepStatus;
    Texture studyStatus;
    Texture bathroomStatus;
    Texture happinessStatus;
    Texture hungerStatus;
    Texture fitnessStatus;
    static String status0 = "data/dorm/status0.png";
    static String status1 = "data/dorm/status1.png";
    static String status2 = "data/dorm/status2.png";
    static String status3 = "data/dorm/status3.png";
    static String status4 = "data/dorm/status4.png";
    static String status5 = "data/dorm/status5.png";
    static String status6 = "data/dorm/status6.png";
    static String status7 = "data/dorm/status7.png";
    static String status8 = "data/dorm/status8.png";


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
    int watchingTV = 0;
    int atTV = 0;
    int tvBG = 0;

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
    int inBathoom = 0;
    int atBathoom = 0;

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


    // Character
    int[][] positions = new int[6][2]; //A list of positions the character can walk to
    int[] mc_pos = new int[]{300,640}; //Where the character is currently standing
    int new_pos_index = 2;
    Animation<TextureRegion> leftWalkAnimation;
    Animation<TextureRegion> rightWalkAnimation;
    TextureRegion currentFrame;
    SpriteBatch mc_batch;
    float stateTime;
    String dir = "left";

    public void create(){
        batch = new SpriteBatch();
        mc_batch = new SpriteBatch();
        positions[1] = new int[]{1100, 1100};
        positions[0] = new int[]{200, 1100};
        positions[2] = new int[]{80, 500};
        positions[3] = new int[]{1120, 600};
        positions[4] = new int[]{500, 900};
        animateWalk();

        //Sleep Button
        sleepImg = new Texture(Gdx.files.internal("data/dorm/sleepButton.png"));
        sleepRegion = new TextureRegion(sleepImg);
        sleepDrawable = new TextureRegionDrawable(sleepRegion);
        sleepButton = new ImageButton(sleepDrawable);
        sleepButton.getImage().setScale(1.5f);
        sleepButton.setPosition(Gdx.graphics.getWidth()-1250,Gdx.graphics.getHeight()-1150);
        sleepButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                if(inBathoom==0 && watchingTV == 0) {
                    TeamProject.gameState = "sleep_minigame";
                }
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
                if(inBathoom==0 && watchingTV == 0) {
                    TeamProject.gameState = "sleep_minigame";
                }
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
                if(inBathoom==0 && watchingTV == 0) {
                    TeamProject.gameState = "study_minigame";
                }
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
                if(inBathoom==0 && watchingTV == 0) {
                    TeamProject.gameState = "sport_minigame";
                }
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
                if(inBathoom==0 && watchingTV == 0){
                    TeamProject.gameState = "food_minigame";
                }
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
                if(inBathoom==0 && watchingTV == 0){
                    inBathoom = 1;
                }
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
                if(inBathoom==0 && watchingTV == 0){
                    watchingTV = 1;
                }
            }
        });

        //Background
        bg = new Texture(Gdx.files.internal("data/dorm/dormBG.png"));

        //Status Bars
        sleepStatus = new Texture(Gdx.files.internal(status4));
        studyStatus = new Texture(Gdx.files.internal(status4));
        bathroomStatus = new Texture(Gdx.files.internal(status4));
        hungerStatus = new Texture(Gdx.files.internal(status4));
        happinessStatus = new Texture(Gdx.files.internal(status4));
        fitnessStatus = new Texture(Gdx.files.internal(status4));
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
    }


    public void render(){

        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        //Drawing buttons and background
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        //Drawing and updating status bars
        batch.begin();
        batch.draw(bg,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        int x = 100;
        for(int i=0; i<6; i++){
            batch.draw(statusBars.get(i),x,110,140,350);
            x+=215;
        }
        batch.end();


        //Drawing the character to the screen
        if(dir=="left"){
            currentFrame = leftWalkAnimation.getKeyFrame(stateTime, true);
        }else{
            currentFrame = rightWalkAnimation.getKeyFrame(stateTime, true);
        }
        mc_batch.begin();
        mc_batch.draw(currentFrame, mc_pos[0], mc_pos[1], 286,720);
        mc_batch.end();

        for(int i=0;i<5;i++){
            if(mc_pos[0] == positions[i][0] && mc_pos[1] == positions[i][1]){
                //Do a random idle animation for 5 seconds (display speech bubble)
                //Then set a new position to walk towards
                new_pos_index = ThreadLocalRandom.current().nextInt(0,4)%Gdx.graphics.getWidth();
                if(mc_pos[0] < positions[new_pos_index][0]){ //Current x is less than destination x
                    dir = "right";
                }
                else{ //Current x is greater than destination x
                    dir = "left";
                }
            }
        }

        if(inBathoom==0 && watchingTV == 0){
            if(mc_pos[0] < positions[new_pos_index][0]){ //Current x is less than destination x
                mc_pos[0]+=2;
            }
            else{ //Current x is greater than destination x
                mc_pos[0]-=2;
            }
            if(mc_pos[1] < positions[new_pos_index][1]){ //Current y is less than destination y
                mc_pos[1]+=2;
            }
            else{ //Current y is greater than destination y
                mc_pos[1]-=2;
            }
        }
        else if (inBathoom==1){
            if(atBathoom==0) {
                dir = "left";
                int[] dest = {-400, 1000};
                if (mc_pos[0] < dest[0]) { //Current x is less than destination x
                    mc_pos[0] += 2;
                } else { //Current x is greater than destination x
                    mc_pos[0] -= 2;
                }
                if (mc_pos[1] < dest[1]) { //Current y is less than destination y
                    mc_pos[1] += 2;
                } else { //Current y is greater than destination y
                    mc_pos[1] -= 2;
                }
                if(mc_pos[0]==dest[0] && mc_pos[1]==dest[1]){
                    atBathoom = 1;
                }
            }
            if(atBathoom == 1){
                if(bathroomTimer >= 1){
                    bathroomTimer=0;
                    if (Dorm.needs[2] < 8) {
                        Dorm.needs[2]++; //fun up
                        Dorm.updateStatusBars(2);
                    }
                }
                else{
                    bathroomTimer+=Gdx.graphics.getDeltaTime();
                }

                if(Dorm.needs[2] >= 8){
                    atBathoom=0;
                    dir = "right";
                    inBathoom=0;
                }
            }
        }
        else if (watchingTV == 1){
            dir = "right";
            int[] dest = {1200, 1000};
            if (mc_pos[0] < dest[0]) { //Current x is less than destination x
                mc_pos[0] += 2;
            } else { //Current x is greater than destination x
                mc_pos[0] -= 2;
            }
            if (mc_pos[1] < dest[1]) { //Current y is less than destination y
                mc_pos[1] += 2;
            } else { //Current y is greater than destination y
                mc_pos[1] -= 2;
            }
            if(mc_pos[0]==dest[0] && mc_pos[1]==dest[1]){
                atTV = 1;
            }
        }
        if(atTV == 1){
            if(bathroomTimer >= 1){
                bathroomTimer=0;
                if (Dorm.needs[3] < 8) {
                    Dorm.needs[3]++; //fun up
                    Dorm.updateStatusBars(3);
                    if(tvBG==0){
                        bg = new Texture(Gdx.files.internal("data/dorm/tvBG1.jpg"));
                        tvBG = 1;
                    }
                    else{
                        bg = new Texture(Gdx.files.internal("data/dorm/tvBG2.jpg"));
                        tvBG = 0;
                    }
                }
            }
            else{
                bathroomTimer+=Gdx.graphics.getDeltaTime();
            }

            if(Dorm.needs[3] >= 8){
                watchingTV = 0;
                dir = "left";
                atTV = 0;
                bg = new Texture(Gdx.files.internal("data/dorm/dormBG.png"));
            }
        }

        // Dropping a random need every 5-10 seconds
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

    public static void updateStatusBars(int index) {
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

    public void animateWalk(){
        Texture walk1 = new Texture(Gdx.files.internal("data/dorm/mc-walk-1.png"));
        Texture walk2 = new Texture(Gdx.files.internal("data/dorm/mc-walk-2.png"));
        Texture walk3 = new Texture(Gdx.files.internal("data/dorm/mc-walk-3.png"));
        Texture walk4 = new Texture(Gdx.files.internal("data/dorm/mc-walk-4.png"));
        Texture walk5 = new Texture(Gdx.files.internal("data/dorm/mc-walk-5.png"));
        TextureRegion walkRegion1 = new TextureRegion(walk1);
        TextureRegion walkRegion2 = new TextureRegion(walk2);
        TextureRegion walkRegion3 = new TextureRegion(walk3);
        TextureRegion walkRegion4 = new TextureRegion(walk4);
        TextureRegion walkRegion5 = new TextureRegion(walk5);
        TextureRegion[] leftWalkFrames = new TextureRegion[5];
        leftWalkFrames[0] = walkRegion1;
        leftWalkFrames[1] = walkRegion2;
        leftWalkFrames[2] = walkRegion3;
        leftWalkFrames[3] = walkRegion4;
        leftWalkFrames[4] = walkRegion5;


        Texture rightWalk1 = new Texture(Gdx.files.internal("data/dorm/mc-walk-right-1.png"));
        Texture rightWalk2 = new Texture(Gdx.files.internal("data/dorm/mc-walk-right-2.png"));
        Texture rightWalk3 = new Texture(Gdx.files.internal("data/dorm/mc-walk-right-3.png"));
        Texture rightWalk4 = new Texture(Gdx.files.internal("data/dorm/mc-walk-right-4.png"));
        Texture rightWalk5 = new Texture(Gdx.files.internal("data/dorm/mc-walk-right-5.png"));
        TextureRegion walkRightRegion1 = new TextureRegion(rightWalk1);
        TextureRegion walkRightRegion2 = new TextureRegion(rightWalk2);
        TextureRegion walkRightRegion3 = new TextureRegion(rightWalk3);
        TextureRegion walkRightRegion4 = new TextureRegion(rightWalk4);
        TextureRegion walkRightRegion5 = new TextureRegion(rightWalk5);
        TextureRegion[] rightWalkFrames = new TextureRegion[5];
        rightWalkFrames[0] = walkRightRegion1;
        rightWalkFrames[1] = walkRightRegion2;
        rightWalkFrames[2] = walkRightRegion3;
        rightWalkFrames[3] = walkRightRegion4;
        rightWalkFrames[4] = walkRightRegion5;


        this.leftWalkAnimation = new Animation<TextureRegion>((float)0.225, leftWalkFrames);
        this.rightWalkAnimation = new Animation<TextureRegion>((float)0.225, rightWalkFrames);
        this.stateTime = (float)0;

    }
}