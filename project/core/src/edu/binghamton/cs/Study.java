package edu.binghamton.cs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.awt.Dimension;

public class Study {
    String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
    String gameState = states[3];
    Stage stage, stage2;
    SpriteBatch batch;
    BitmapFont font;

    private Dimension d;
    //boolean in_game = true;
    boolean dead;
    final int BLOCK_SIZE = 24;
    final int NUM_BLOCKS = 10;//15
    final int MAX_GHOSTS = 12;
    final int PLAYER_SPEED = 6;
    int num_ghosts = 6;
    int health, hygiene, sleep, study, health_f, hygiene_f, sleep_f, study_f;   //affected stats
    int [] dx, dy;
    int [] ghost_x, ghost_y, ghost_dx, ghost_dy, ghost_speed;
    Texture player_up, player_down, player_left, player_right;                  //player
    Texture netflix, hulu, game_controller, alcohol;                            //distractions
    Texture book, pencil, book2, pencil2, book3;                                //studying
    private int player_x, player_y, player_dx, player_dy;
    private int req_dx, req_dy;
    final int valid_speeds[] = {1, 2, 3, 4, 6, 8};
    final int max_speed = 6;
    int current_speed = 3;
    private short [] screenData;
    private Timer timer;
    final short [] level_data = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };//10 x 10 POSITIONS: 0 = BLOCKS; 1 = LEFT; 2 = TOP; 4 = RIGHT; 8 = BOTTOM;

    //UP Button
    Texture upImg;
    TextureRegion upRegion;
    TextureRegionDrawable upDrawable;
    ImageButton upButton;
    //DOWN Button
    Texture downImg;
    TextureRegion downRegion;
    TextureRegionDrawable downDrawable;
    ImageButton downButton;
    //LEFT Button
    Texture leftImg;
    TextureRegion leftRegion;
    TextureRegionDrawable leftDrawable;
    ImageButton leftButton;
    //RIGHT Button
    Texture rightImg;
    TextureRegion rightRegion;
    TextureRegionDrawable rightDrawable;
    ImageButton rightButton;
    //BACK Button
    Texture backImg;
    TextureRegion backRegion;
    TextureRegionDrawable backDrawable;
    ImageButton backButton;

    public void create(){
        //player images
        player_up = new Texture(Gdx.files.internal("study/img09.png"));
        player_down = new Texture(Gdx.files.internal("study/img01.png"));
        player_left = new Texture(Gdx.files.internal("study/img02.png"));
        player_right = new Texture(Gdx.files.internal("study/img06.jpeg"));
        //distractions images
        netflix = new Texture(Gdx.files.internal("study/img04.png"));
        hulu = new Texture(Gdx.files.internal("study/img05.png"));
        game_controller = new Texture(Gdx.files.internal("study/img07.png"));
        alcohol = new Texture(Gdx.files.internal("study/img05.png"));;
        //studying images
        book = new Texture(Gdx.files.internal("study/img08.png"));
        pencil = new Texture(Gdx.files.internal("study/img01.png"));
        book2 = new Texture(Gdx.files.internal("study/img08.png"));
        pencil2 = new Texture(Gdx.files.internal("study/img01.png"));
        book3 = new Texture(Gdx.files.internal("study/img08.png"));

        //directions
        upImg = new Texture(Gdx.files.internal("study/upButton.jpg"));
        upRegion = new TextureRegion(upImg);
        upDrawable = new TextureRegionDrawable(upRegion);
        upButton = new ImageButton(upDrawable);
        upButton.setSize(400,400);
        upButton.setPosition(Gdx.graphics.getWidth()-500,40);
        upButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                //req_dx = 0;
                //req_dy = -1;
                player_dx = 0;
                player_dy = player_dy;
            }
        });
        downImg = new Texture(Gdx.files.internal("study/downButton.jpg"));
        downRegion = new TextureRegion(downImg);
        downDrawable = new TextureRegionDrawable(downRegion);
        downButton = new ImageButton(downDrawable);
        downButton.setSize(400,400);
        downButton.setPosition(0,40);
        downButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                //req_dx = 0;
                //req_dy = 1;
                player_dx = 0;
                player_dy = -player_dy;
            }
        });
        leftImg = new Texture(Gdx.files.internal("study/leftButton.jpg"));
        leftRegion = new TextureRegion(leftImg);
        leftDrawable = new TextureRegionDrawable(leftRegion);
        leftButton = new ImageButton(leftDrawable);
        leftButton.setSize(200,200);
        leftButton.setPosition(Gdx.graphics.getWidth()-300,500);
        leftButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                //req_dx = -1;
                //req_dy = 0;
                player_dx = -player_dx;
                player_dy = 0;
            }
        });
        rightImg = new Texture(Gdx.files.internal("study/rightButton.jpg"));
        rightRegion = new TextureRegion(rightImg);
        rightDrawable = new TextureRegionDrawable(rightRegion);
        rightButton = new ImageButton(rightDrawable);
        rightButton.setSize(400,400);
        rightButton.setPosition(0,500);
        rightButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                //req_dx = 1;
                //req_dy = 0;
                player_dx = player_dx;
                player_dy = 0;
            }
        });
        //BackButton
        backImg = new Texture(Gdx.files.internal("study/backButton.jpg"));
        backRegion = new TextureRegion(backImg);
        backDrawable = new TextureRegionDrawable(backRegion);
        backButton = new ImageButton(backDrawable);
        backButton.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backButton.setPosition(Gdx.graphics.getWidth()-500,40);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                gameState = states[0];//NOT WORKING BUT A LATER PROBLEM
            }
        });

        screenData = new short[NUM_BLOCKS*NUM_BLOCKS];
        dx= new int [4];
        dy= new int [4];
        ghost_x = new int [MAX_GHOSTS];
        ghost_y = new int [MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_dy = new int [MAX_GHOSTS];
        health = 0;//5 max
        hygiene = 0;
        sleep = 0;
        study = 0;

        ghost_speed = new int[num_ghosts];//added by me
        for(int i = 0; i<NUM_BLOCKS*NUM_BLOCKS; i++){
            screenData[i]=level_data[i];
        }
        int temp_dx = 1;
        int random;
        for(int i = 0; i<num_ghosts; i++){
            ghost_x[i] = 4*BLOCK_SIZE;
            ghost_y[i] = 4*BLOCK_SIZE;
            ghost_dx[i] = temp_dx;
            ghost_dy[i] = 0;
            temp_dx = -temp_dx;
            random = (int) (Math.random()*(current_speed +1));
            if(random > current_speed){
                random = current_speed;
            }
            ghost_speed[i] = valid_speeds[random];
        }
        player_x = 7*BLOCK_SIZE;
        player_y = 11*BLOCK_SIZE;
        player_dx = 0;
        player_dy = 0;
        dead = false;

        timer = new Timer();    //milliseconds for redrawing - for animation
        timer.start();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(15f);
        //Staging
        stage = new Stage(new ScreenViewport());
        stage.addActor(upButton);
        stage.addActor(downButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage2 = new Stage(new ScreenViewport());
        stage2.addActor(backButton);
    }

    public void render(){
        if(dead){
            health_f = health;
            hygiene_f = hygiene;
            sleep_f = sleep;
            study_f = study;

            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            font.draw(batch, "Health, Hygiene, sleep, and study stats are as follows: "+health_f+" "+hygiene_f+" "+sleep_f+" "+study_f, 0, Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),10,true);
            batch.end();

            stage2.act(Gdx.graphics.getDeltaTime());
            stage2.draw();
        }else{
            Gdx.gl.glClearColor(1,1,1,1);
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

            int pos;
            short ch;
            if(player_x%BLOCK_SIZE == 0 && player_y%BLOCK_SIZE == 0){ // find pos of player
                pos = player_x / BLOCK_SIZE + NUM_BLOCKS * (int) (player_y / BLOCK_SIZE);
                //ch = screenData[pos];//====================================================================

                //10 x 10 POSITIONS: 0 = BLOCKS; 1 = LEFT; 2 = TOP; 4 = RIGHT; 8 = BOTTOM;
                if(req_dx !=0 || req_dy !=0){
                    //
                }
            }

            batch.begin();
            //font.draw(batch, "This is the study minigame", 0, Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),10,true);

            //DRAW MAZE
            //DRAW SCORE

            batch.end();
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }
} 
