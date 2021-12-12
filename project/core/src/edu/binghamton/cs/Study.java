package edu.binghamton.cs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    final int BLOCK_SIZE = 100;//24;
    final int NUM_BLOCKS = 14;
    final int SCREEN_SIZE = NUM_BLOCKS * BLOCK_SIZE;//1400
    final int MAX_GHOSTS = 12;
    final int PLAYER_SPEED = 6;
    int num_ghosts = 6;
    int health, hygiene, sleep, study, health_f, hygiene_f, sleep_f, study_f;   //affected stats
    int [] dx, dy;
    int [] ghost_x, ghost_y, ghost_dx, ghost_dy, ghost_speed;//POSSIBLY ADD GHOST NAME TO AFFECT STATS TODO
    Texture player_up, player_down, player_left, player_right;                  //player
    Texture netflix, hulu, game_controller, alcohol;                            //distractions
    Texture book, pencil, book2, pencil2, book3;                                //studying
    Texture blocks;
    private int player_x, player_y, player_dx, player_dy;                       //for actual movement
    private int req_dx, req_dy;                                                 //for image direction
    final int valid_speeds[] = {1, 2, 3, 4, 6, 8};
    final int max_speed = 6;
    int current_speed = 2;
    private Timer timer;
    final int [][] screenData2 = new int[NUM_BLOCKS][NUM_BLOCKS];
    final int [][] level_data2 ={//rotated 90 degrees clockwise
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 16, 0,0, 0,  0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 0},
            {1, 0, 16, 0, 0, 0, 0,0, 0,  0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };


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
        blocks = new Texture(Gdx.files.internal("badlogic.jpg"));
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
        upImg = new Texture(Gdx.files.internal("study/upButton.png"));//155x155
        upRegion = new TextureRegion(upImg);
        upDrawable = new TextureRegionDrawable(upRegion);
        upButton = new ImageButton(upDrawable);
        upButton.setSize(400,400);
        upButton.setPosition(Gdx.graphics.getWidth()/2 - 200,298);
        upButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                System.out.println("UP CLICKED HERE");
                req_dx = 0;
                req_dy = 1;
                player_dx = 0;
                player_dy = 3;
            }
        });
        downImg = new Texture(Gdx.files.internal("study/downButton.png"));
        downRegion = new TextureRegion(downImg);
        downDrawable = new TextureRegionDrawable(downRegion);
        downButton = new ImageButton(downDrawable);
        downButton.setSize(400,400);
        downButton.setPosition(Gdx.graphics.getWidth()/2 - 200,-20);
        downButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                System.out.println("DOWN CLICKED HERE");
                req_dx = 0;
                req_dy = -1;
                player_dx = 0;
                player_dy = -3;
            }
        });
        leftImg = new Texture(Gdx.files.internal("study/leftButton.png"));
        leftRegion = new TextureRegion(leftImg);
        leftDrawable = new TextureRegionDrawable(leftRegion);
        leftButton = new ImageButton(leftDrawable);
        leftButton.setSize(200,200);
        leftButton.setPosition((Gdx.graphics.getWidth()/2) - 410,227);
        leftButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                System.out.println("LEFT CLICKED HERE");
                req_dx = -1;
                req_dy = 0;
                player_dx = -3;
                player_dy = 0;
            }
        });
        rightImg = new Texture(Gdx.files.internal("study/rightButton.png"));
        rightRegion = new TextureRegion(rightImg);
        rightDrawable = new TextureRegionDrawable(rightRegion);
        rightButton = new ImageButton(rightDrawable);
        rightButton.setSize(400,400);
        rightButton.setPosition(Gdx.graphics.getWidth()/2 + 113,140);
        rightButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                System.out.println("RIGHT CLICKED HERE");
                req_dx = 1;
                req_dy = 0;
                player_dx = 3;
                player_dy = 0;
            }
        });
        //BackButton
        backImg = new Texture(Gdx.files.internal("study/backButton.png"));
        backRegion = new TextureRegion(backImg);
        backDrawable = new TextureRegionDrawable(backRegion);
        backButton = new ImageButton(backDrawable);
        backButton.getImage().setScale(4);
        backButton.setPosition(0, 0);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                System.out.println("BACK CLICKED HERE");
                gameState = states[0];
            }
        });

        for(int i = 0; i<NUM_BLOCKS; i++){
            for(int j = 0; j<NUM_BLOCKS; j++){
                screenData2[i][j]=level_data2[i][j];
            }
        }
        health = 0;//5 max
        hygiene = 0;
        sleep = 0;
        study = 0;

        //START POS: 8x8
        player_x = 820;
        player_y = 1490;
        player_dx = 0;
        player_dy = 0;
        dead = false;

        timer = new Timer();    //milliseconds for redrawing - for animation
        timer.start();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(10f);

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
            Gdx.input.setInputProcessor(stage2);
            health_f = health;
            hygiene_f = hygiene;
            sleep_f = sleep;
            study_f = study;

            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            font.draw(batch,"Health, Hygiene, sleep, and study stats are as follows: "+health_f+" "+hygiene_f+" "+sleep_f+" "+study_f,0,Gdx.graphics.getHeight(),Gdx.graphics.getWidth(), 10,true);
            batch.end();

            stage2.act(Gdx.graphics.getDeltaTime());
            stage2.draw();
        }else{
            Gdx.input.setInputProcessor(stage);
            Gdx.gl.glClearColor(1,1,1,1);
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
            //CHECK FRUIT STATUS
            if(study == 5){
                dead = true;
            }
            //MOVE PACMAN: SCREEN_SIZE = NUM_BLOCKS * BLOCK_SIZE = 14 * 100
            if(player_x >= 20+100 && player_x<=1220){//START + BOXES - (2*BOXSIZE) = 20+1300-100 = 1220
                player_x +=player_dx;
            }else{
                player_dx = 0;
            }
            if(player_y >= 990+100 && player_y<=990+100+1300-100){
                player_y +=player_dy;
            }else{
                player_dy = 0;
            }

            int pos_x;
            int pos_y;
            /*if(player_x%BLOCK_SIZE == 0 && player_y%BLOCK_SIZE == 0){
                pos_x;
                pos_y;
            }





            if(player_x%BLOCK_SIZE == 0 && player_y%BLOCK_SIZE == 0){ // find pos of player
                //      (7*200)/200 + 7 * (11*200)/200 = 84
                pos = (player_x) / BLOCK_SIZE + NUM_BLOCKS * (int) ((player_y) / BLOCK_SIZE);
                //System.out.println(pos);

                ch = screenData[pos];

                //10 x 10 POSITIONS: 0 = BLOCKS; 1 = LEFT; 2 = TOP; 4 = RIGHT; 8 = BOTTOM;
                if(req_dx !=0 || req_dy !=0){
                    if(!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0 )
                    || (req_dy == 1 && req_dy == 0 && (ch & 4) != 0)
                    || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                    || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))){
                        player_x = req_dx;
                        player_y = req_dy;
                    }
                }

                if((player_x == -1 && player_y == 0 && (ch & 1) != 0)
                || (player_x == 1 && player_y == 0 && (ch & 4) != 0)
                || (player_x == 0 && player_y == -1 && (ch & 2) != 0)
                || (player_x == 0 && player_y == 1 && (ch & 8) != 0)){//checks for standstill?
                    player_x = 0;
                    player_y = 0;
                }
            }
            player_x = player_x + current_speed * player_x;
            player_y = player_y + current_speed * player_y;
            */


















            batch.begin();
            //MOVE GHOSTS: TODO

            //DRAW PACMAN
            if(req_dx == -1){
                batch.draw(player_left, player_x,player_y,BLOCK_SIZE,BLOCK_SIZE);
            }else if(req_dx == 1){
                batch.draw(player_right, player_x,player_y, BLOCK_SIZE,BLOCK_SIZE);
            } else if(req_dy == 1){
                batch.draw(player_up, player_x,player_y,BLOCK_SIZE,BLOCK_SIZE);
            }else{
                batch.draw(player_down, player_x,player_y,BLOCK_SIZE,BLOCK_SIZE);
            }
            //DRAW MAZE:
            int i = 0;
            int j = 0;
            int x, y;
            for(y = 990; y<SCREEN_SIZE+990; y+=BLOCK_SIZE){
                for(x = 20; x<SCREEN_SIZE+20; x+= BLOCK_SIZE){
                    if(i <NUM_BLOCKS && j <NUM_BLOCKS){
                        if((screenData2[i][j] & 1)!= 0){
                            batch.draw(blocks, x,y,BLOCK_SIZE,BLOCK_SIZE);
                        }
                        if((screenData2[i][j] & 2)!= 0){
                            batch.draw(netflix, x,y,BLOCK_SIZE,BLOCK_SIZE);
                        }/*
                        if(screenData2[i][j] == 8){
                            //System.out.println(x+" "+y);
                            if(req_dx == -1){
                                batch.draw(player_left, x,y,BLOCK_SIZE,BLOCK_SIZE);
                            }else if(req_dx == 1){
                                batch.draw(player_right, x,y,BLOCK_SIZE,BLOCK_SIZE);
                            } else if(req_dy == 1){
                                batch.draw(player_up, x,y,BLOCK_SIZE,BLOCK_SIZE);
                            }else{
                                batch.draw(player_down, x,y,BLOCK_SIZE,BLOCK_SIZE);
                            }
                        }*/
                        if((screenData2[i][j] & 16) !=0){
                            batch.draw(book, x,y,BLOCK_SIZE,BLOCK_SIZE);
                        }
                    }
                    i++;
                }
                i=0;
                j++;
            }

            batch.end();
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }
}
/*IN CREATE
        //screenData = new short[NUM_BLOCKS*NUM_BLOCKS];
        dx= new int [4];
        dy= new int [4];
        ghost_x = new int [MAX_GHOSTS];
        ghost_y = new int [MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_dy = new int [MAX_GHOSTS];
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
*/
//MOVE PACMAN
            /*
            int pos;
            short ch;
            //player_x=7*BLOCK_SIZE(=200); player_y = 11*BLOCK_SIZE;
            if(player_x%BLOCK_SIZE == 0 && player_y%BLOCK_SIZE == 0){ // find pos of player
                //      (7*200)/200 + 7 * (11*200)/200 = 84
                pos = (player_x) / BLOCK_SIZE + NUM_BLOCKS * (int) ((player_y) / BLOCK_SIZE);
                //System.out.println(pos);

                /*ch = screenData[pos];

                //10 x 10 POSITIONS: 0 = BLOCKS; 1 = LEFT; 2 = TOP; 4 = RIGHT; 8 = BOTTOM;
                if(req_dx !=0 || req_dy !=0){
                    if(!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0 )
                    || (req_dy == 1 && req_dy == 0 && (ch & 4) != 0)
                    || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                    || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))){
                        player_x = req_dx;
                        player_y = req_dy;
                    }
                }

                if((player_x == -1 && player_y == 0 && (ch & 1) != 0)
                || (player_x == 1 && player_y == 0 && (ch & 4) != 0)
                || (player_x == 0 && player_y == -1 && (ch & 2) != 0)
                || (player_x == 0 && player_y == 1 && (ch & 8) != 0)){//checks for standstill?
                    player_x = 0;
                    player_y = 0;
                }
            }
            player_x = player_x + current_speed * player_x;
            player_y = player_y + current_speed * player_y;
*/
//MOVE GHOSTS
            /*
            int pos2;
            int count;
            for(int i = 0; i <num_ghosts; i++){
                if(ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0){
                    //pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE); TODO
                    pos2 = ghost_x[i] / BLOCK_SIZE + NUM_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                    count = 0;
                    if((screenData[pos2] & 1) == 0 && ghost_dx[i] != 1){
                        dx[count] = -1;
                        dy[count] = 0;
                        count++;
                    }
                    if((screenData[pos2] & 2) == 0 && ghost_dx[i] != 1){
                        dx[count] = 0;
                        dy[count] = -1;
                        count++;
                    }if((screenData[pos2] & 4) == 0 && ghost_dx[i] != -1){
                        dx[count] = 1;
                        dy[count] = 0;
                        count++;
                    }if((screenData[pos2] & 1) == 0 && ghost_dx[i] != -1){
                        dx[count] = 0;
                        dy[count] = 1;
                        count++;
                    }

                    if(count == 0){
                        if((screenData[pos2] & 15) == 15){
                            ghost_dx[i] = 0;
                            ghost_dy[i] = 0;
                        }else{
                            ghost_dx[i] = -ghost_dx[i];
                            ghost_dy[i] = -ghost_dy[i];
                        }
                    }else{
                        count = (int)(Math.random()*count);
                        if(count>3){
                            count = 3;
                        }

                        ghost_dy[i] = dy[count];
                        ghost_dx[i] = dx[count];
                    }
                }
                ghost_x[i] = ghost_x[i]+(ghost_dx[i]*ghost_speed[i]);
                ghost_y[i] = ghost_y[i]+(ghost_dy[i]*ghost_speed[i]);
                //DRAW GHOST
                batch.draw(netflix, player_x +1, player_y +1, BLOCK_SIZE,BLOCK_SIZE);//DIFF PICS TODO
                //IF PLAYER TOUCHES DISTRACTIONS
                if(player_x > (ghost_x[i] -12) && player_x < (ghost_x[i] +12)
                        && player_y > (ghost_y[i] -12) && player_y < (ghost_y[i] +12)){//if pacman
                    dead = true;
                }
            }*/
//DRAW PACMAN
            /*if(req_dx == -1){
                batch.draw(player_left, player_x +1, player_y +1, BLOCK_SIZE,BLOCK_SIZE);
            }else if(req_dx == 1){
                batch.draw(player_right, player_x +1, player_y +1, BLOCK_SIZE,BLOCK_SIZE);
            } else if(req_dy == -1){
                batch.draw(player_up, player_x +1, player_y +1, BLOCK_SIZE,BLOCK_SIZE);
            }else{
                batch.draw(player_down, player_x +1, player_y +1, BLOCK_SIZE,BLOCK_SIZE);
            }*/