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
    //String gameState = states[3];
    Stage stage, stage2;
    SpriteBatch batch;
    BitmapFont font, font2;


    private Dimension d;
    //boolean in_game = true;
    boolean dead;
    final int BLOCK_SIZE = 100;//24;
    final int NUM_BLOCKS = 14;
    final int SCREEN_SIZE = NUM_BLOCKS * BLOCK_SIZE;//1400
    final int MAX_GHOSTS = 12;
    final int PLAYER_SPEED = 6;
    int num_prizes = 4;
    int health, hygiene, sleep, study, health_f, hygiene_f, sleep_f, study_f;   //affected stats
    int fun_f = 0;
    int hunger_f = 0;                                                           //other stats
    int [] dx, dy;
    int [] ghosts_x, ghosts_y, ghosts_dx, ghosts_dy, ghosts_speed;//POSSIBLY ADD GHOST NAME TO AFFECT STATS TODO
    Texture player_up, player_down, player_left, player_right;                  //player
    Texture netflix, hulu, game_controller, alcohol;                            //distractions
    Texture book, pencil, book2, pencil2, book3;                                //studying
    Texture blocks;
    private int ghost_x, ghost_y, ghost_dx, ghost_dy;
    private int player_x, player_y, player_dx, player_dy;                       //for actual movement
    private int req_dx, req_dy;                                                 //for image direction
    final int valid_speeds[] = {1, 2, 3, 4, 6, 8};
    final int max_speed = 6;
    int current_speed = 5;
    private Timer timer;
    int [][] screenData2 = new int[NUM_BLOCKS][NUM_BLOCKS];
    final int [][] level_data2 ={//rotated 90 degrees clockwise: ex [1][3] = 16
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0,0, 0,  0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0},
            {1, 0, 0, 0, 0, 0, 0,0, 0,  0, 0, 0, 0, 0},
            {1, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };//1 = border; 2 = enemy; 8 = player; 16 = prize

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

    int max = 7;
    int min = 0;
    int range = max - min + 1;
    int rand = (int)(Math.random() * range) + min;

    int max_f = 3;
    int min_f = 0;
    int range_f = max_f - min_f + 1;

    Texture bg;
    TextureRegion region;

    public void create(){
        bg = new Texture(Gdx.files.internal("study/deadbg.png"));
        blocks = new Texture(Gdx.files.internal("study/blocks.png"));
        //player images
        player_up = new Texture(Gdx.files.internal("data/hunger/head.png"));
        player_down = new Texture(Gdx.files.internal("data/hunger/head.png"));
        player_left = new Texture(Gdx.files.internal("data/hunger/head.png"));
        player_right = new Texture(Gdx.files.internal("data/hunger/head.png"));
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
                player_dy = current_speed;
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
                player_dy = -current_speed;
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
                player_dx = -current_speed;
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
                player_dx = current_speed;
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
                for(int i = 0; i<NUM_BLOCKS; i++){
                    for(int j = 0; j<NUM_BLOCKS; j++){
                        screenData2[i][j]=level_data2[i][j];
                    }
                }
                health = (int)(Math.random() * range_f) + min_f;
                hygiene = (int)(Math.random() * range_f) + min_f;
                sleep = (int)(Math.random() * range_f) + min_f;
                study = 0;
                player_x = 820;
                player_y = 1490;
                player_dx = 0;
                player_dy = 0;
                dead = false;
                ghost_x = 120;
                ghost_y = 990 + 1400 - 100;//2290
                if(rand == 0){//RIGHT
                    ghost_dx = current_speed;
                    ghost_dy = 0;
                }else if(rand == 1){//DOWN
                    ghost_dx = 0;
                    ghost_dy = -current_speed;
                }else if(rand == 2){//LEFT
                    ghost_dx = -current_speed;
                    ghost_dy = 0;
                }else if(rand == 3){//UP
                    ghost_dx = 0;
                    ghost_dy = current_speed;
                }else if(rand == 4){//NE
                    ghost_dx = current_speed;
                    ghost_dy = current_speed;
                }else if(rand == 5){//SW
                    ghost_dx = -current_speed;
                    ghost_dy = -current_speed;
                }else if(rand == 6){//NW
                    ghost_dx = -current_speed;
                    ghost_dy = current_speed;
                }else{//NE
                    ghost_dx = current_speed;
                    ghost_dy = -current_speed;
                }
                study = 0;
                num_prizes = 0;
                TeamProject.gameState = states[0];
            }
        });

        for(int i = 0; i<NUM_BLOCKS; i++){
            for(int j = 0; j<NUM_BLOCKS; j++){
                screenData2[i][j]=level_data2[i][j];
            }
        }
        health = (int)(Math.random() * range_f) + min_f;
        hygiene = (int)(Math.random() * range_f) + min_f;
        sleep = (int)(Math.random() * range_f) + min_f;
        study = 0;//max 8

        //START POS: 8x8
        player_x = 820;
        player_y = 1490;
        player_dx = 0;
        player_dy = 0;
        dead = false;
        ghost_x = 120;
        ghost_y = 990 + 1400 - 100;//2290

        if(rand == 0){//RIGHT
            ghost_dx = current_speed;
            ghost_dy = 0;
        }else if(rand == 1){//DOWN
            ghost_dx = 0;
            ghost_dy = -current_speed;
        }else if(rand == 2){//LEFT
            ghost_dx = -current_speed;
            ghost_dy = 0;
        }else if(rand == 3){//UP
            ghost_dx = 0;
            ghost_dy = current_speed;
        }else if(rand == 4){//NE
            ghost_dx = current_speed;
            ghost_dy = current_speed;
        }else if(rand == 5){//SW
            ghost_dx = -current_speed;
            ghost_dy = -current_speed;
        }else if(rand == 6){//NW
            ghost_dx = -current_speed;
            ghost_dy = current_speed;
        }else{//NE
            ghost_dx = current_speed;
            ghost_dy = -current_speed;
        }

        timer = new Timer();    //milliseconds for redrawing - for animation
        timer.start();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(9f);
        font2 = new BitmapFont();
        font2.getData().setScale(7f);

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
            health_f = -health;//needs[5] DONE
            hygiene_f = -hygiene;//needs[2] DONE
            sleep_f = -sleep;//needs[0] DONE
            study_f = study;//needs[1] DONE
            
            while(Dorm.needs[1]<8 && study!=0){
                Dorm.needs[1]++;
                study--;
            }
            while(Dorm.needs[0]>0 && sleep!=0){
                Dorm.needs[0]--;
                sleep--;
            }
            while(Dorm.needs[2]>0 && hygiene!=0){
                Dorm.needs[2]--;
                hygiene--;
            }
            while(Dorm.needs[5]>0 && health!=0){
                Dorm.needs[5]--;
                health--;
            }

            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            batch.draw(bg,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            font.draw(batch,"Health, Hygiene, sleep, and study stats are as follows: "+health_f+" "+hygiene_f+" "+sleep_f+" "+study_f,10,Gdx.graphics.getHeight()-50,Gdx.graphics.getWidth(), 10,true);
            font2.draw(batch,"TAP SCREEN TO STOP STUDYING",10, 300,Gdx.graphics.getWidth(),5, true);
            batch.end();

            stage2.act(Gdx.graphics.getDeltaTime());
            stage2.draw();
        }else{
            Gdx.input.setInputProcessor(stage);
            Gdx.gl.glClearColor(1,1,1,1);
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
            //CHECK FRUIT STATUS
            if(study == 8 || num_prizes == 0){
                dead = true;
            }
            //MOVE PACMAN: SCREEN_SIZE = NUM_BLOCKS * BLOCK_SIZE = 14 * 100
            int poss_x = player_x + player_dx;//POSSIBLE POSITIONS
            int poss_y = player_y + player_dy;
            int ghostposs_x = ghost_x + ghost_dx;
            int ghostposs_y = ghost_y + ghost_dy;

            int pos_x = (player_x - 20)/(BLOCK_SIZE);//ACTUAL POSITIONS PLAYER
            //^^ 0 = 20; 1 = 120; 2 = 220;
            int pos_y = (player_y - 990)/(BLOCK_SIZE);
            //^^ 0 = 990; 1 = 1990; 2 = 1290;
            int ghostpos_x = (ghost_x - 20)/(BLOCK_SIZE);//120, 125, 130, 135 -> 125 - 20 / 100 = 105/100
            int ghostpos_y = (ghost_y - 990)/(BLOCK_SIZE);//ghost_y = 990 + 1400 - 100;//2290
            //GETTING PRIZES: 120 x 1290 = block [1][3] = 16

            int tempRand = (int)(Math.random() * range) + min;
            //System.out.println("GHOST WALK: RAND = "+rand+" AND TEMP RAND = "+tempRand);
            for(int x = 0; x<NUM_BLOCKS; x++){
                for(int y = 0; y<NUM_BLOCKS; y++){
                    if (screenData2[x][y] == 2) {
                        screenData2[x][y] = 0;
                    }
                    if (screenData2[x][y] == 8) {
                        screenData2[x][y] = 0;
                    }
                }
            }

            if(ghostposs_x >= 20 && ghostposs_x<=1320){
                ghost_x +=ghost_dx;
                if(screenData2[ghostpos_x][ghostpos_y] != 1){//border exception for diagonals (2)
                    if(screenData2[ghostpos_x][ghostpos_y] == 16){
                        num_prizes--;
                    }
                    screenData2[ghostpos_x][ghostpos_y] = 2;
                }
            }else{
                while(rand == tempRand){//MAKE SURE IT IS NOT THE SAME DIRECTION
                    tempRand = (int)(Math.random() * range) + min;
                }
                rand = tempRand;
                //USE THAT DIRECTION
                if(rand == 0){//RIGHT
                    ghost_dx = current_speed;
                    ghost_dy = 0;
                }else if(rand == 1){//DOWN
                    ghost_dx = 0;
                    ghost_dy = -current_speed;
                }else if(rand == 2){//LEFT
                    ghost_dx = -current_speed;
                    ghost_dy = 0;
                }else if(rand == 3){//UP
                    ghost_dx = 0;
                    ghost_dy = current_speed;
                }else if(rand == 4){//NE
                    ghost_dx = current_speed;
                    ghost_dy = current_speed;
                }else if(rand == 5){//SW
                    ghost_dx = -current_speed;
                    ghost_dy = -current_speed;
                }else if(rand == 6){//NW
                    ghost_dx = -current_speed;
                    ghost_dy = current_speed;
                }else{//NE
                    ghost_dx = current_speed;
                    ghost_dy = -current_speed;
                }
            }
            if(ghostposs_y >= 990+100 && ghostposs_y<=990+100+1300-100){//COPY ABOVE IF FOR "Y"
                ghost_y +=ghost_dy;
                if(screenData2[ghostpos_x][ghostpos_y] != 1){//border exception for diagonals
                    if(screenData2[ghostpos_x][ghostpos_y] == 16){
                        num_prizes--;
                    }
                    screenData2[ghostpos_x][ghostpos_y] = 2;
                }
            }else{
                while(rand == tempRand){//MAKE SURE IT IS NOT THE SAME DIRECTION
                    tempRand = (int)(Math.random() * range) + min;
                }
                rand = tempRand;
                //USE THAT DIRECTION
                if(rand == 0){//RIGHT
                    ghost_dx = current_speed;
                    ghost_dy = 0;
                }else if(rand == 1){//DOWN
                    ghost_dx = 0;
                    ghost_dy = -current_speed;
                }else if(rand == 2){//LEFT
                    ghost_dx = -current_speed;
                    ghost_dy = 0;
                }else if(rand == 3){//UP
                    ghost_dx = 0;
                    ghost_dy = current_speed;
                }else if(rand == 4){//NE
                    ghost_dx = current_speed;
                    ghost_dy = current_speed;
                }else if(rand == 5){//SW
                    ghost_dx = -current_speed;
                    ghost_dy = -current_speed;
                }else if(rand == 6){//NW
                    ghost_dx = -current_speed;
                    ghost_dy = current_speed;
                }else{//NE
                    ghost_dx = current_speed;
                    ghost_dy = -current_speed;
                }
            }
            //System.out.println("BEFORE PLAYER ["+player_x+"="+pos_x+"]["+player_y+"="+pos_y+"] GHOST ["+ghost_x+"="+ghostpos_x+"]["+ghost_y+"="+ghostpos_y+"]");
            //[124=1][2288=12] GHOST [120=1][2290=13]
            if(poss_x >= 20 && poss_x<=1320){//AS LONG AS POSSIBLE POSITION IS WITHIN BOUNDARIES
                //START + BOXES - (2 * BOX_SIZE) = 20 + 1300-100 = 1220
                if(screenData2[pos_x][pos_y] == 16){//CHECK NEXT SPOT FOR PRIZE
                    //0 = NOTHING; 1 = BORDER; 2 = GHOST; 8 = PLAYER; 16 = PRIZE
                    study+=2;
                    num_prizes--;
                }
                if(screenData2[pos_x][pos_y] == 2){//CHECK NEXT SPOT FOR GHOST
                    //System.out.println("X1 == "+hygiene);
                    dead = true;
                }
                screenData2[pos_x][pos_y] = 0;//OLD POS = 0 = NOTHING
                player_x +=player_dx;//UPDATE POS
                if(screenData2[pos_x][pos_y] == 16){
                    study+=2;
                    num_prizes--;
                }
                if(screenData2[pos_x][pos_y] == 2){
                    //System.out.println("X2 == "+hygiene);
                    dead = true;
                }
                screenData2[pos_x][pos_y] = 8;//NEW POS = 8 = PLAYER
            }else{//IF POSS POS OUT OF BOUNDARY STOP PLAYER
                player_dx = 0;
            }
            System.out.println("STUDY == "+study);
            if(poss_y >= 990+100 && poss_y<=990+100+1300-100){//COPY ABOVE IF FOR "Y"
                if(screenData2[pos_x][pos_y] == 16){
                    study+=2;
                    num_prizes--;
                }
                if(screenData2[pos_x][pos_y] == 2){//CHECK NEXT SPOT FOR GHOST
                    //System.out.println("Y1 == "+hygiene);
                    dead = true;
                }
                screenData2[pos_x][pos_y] = 0;
                player_y +=player_dy;
                if(screenData2[pos_x][pos_y] == 16){
                    study+=2;
                    num_prizes--;
                }
                if(screenData2[pos_x][pos_y] == 2){
                    //System.out.println("Y2 == "+hygiene);
                    dead = true;
                }
                screenData2[pos_x][pos_y] = 8;
            }else{
                player_dy = 0;
            }

            batch.begin();
            //DRAW GHOSTS
            //batch.draw(hulu, ghost_x,ghost_y,BLOCK_SIZE,BLOCK_SIZE);
            //DRAW PACMAN
            /*if(req_dx == -1){
                batch.draw(player_left, player_x,player_y,BLOCK_SIZE,BLOCK_SIZE);
            }else if(req_dx == 1){
                batch.draw(player_right, player_x,player_y, BLOCK_SIZE,BLOCK_SIZE);
            } else if(req_dy == 1){
                batch.draw(player_up, player_x,player_y,BLOCK_SIZE,BLOCK_SIZE);
            }else{
                batch.draw(player_down, player_x,player_y,BLOCK_SIZE,BLOCK_SIZE);
            }*/
            //DRAW MAZE:
            int i = 0;
            int j = 0;
            int x, y;
            for(y = 990; y<SCREEN_SIZE+990; y+=BLOCK_SIZE){
                for(x = 20; x<SCREEN_SIZE+20; x+= BLOCK_SIZE){
                    if(i <NUM_BLOCKS && j <NUM_BLOCKS){
                        if(screenData2[i][j] == 1){
                            batch.draw(blocks, x,y,BLOCK_SIZE,BLOCK_SIZE);
                        }
                        if(screenData2[i][j] == 2){
                            batch.draw(netflix, x,y,BLOCK_SIZE,BLOCK_SIZE);
                        }
                        if(screenData2[i][j] == 8){
                            if(req_dx == -1){
                                batch.draw(player_left, x,y,BLOCK_SIZE,BLOCK_SIZE);
                            }else if(req_dx == 1){
                                batch.draw(player_right, x,y, BLOCK_SIZE,BLOCK_SIZE);
                            } else if(req_dy == 1){
                                batch.draw(player_up, x,y,BLOCK_SIZE,BLOCK_SIZE);
                            }else{
                                batch.draw(player_down, x,y,BLOCK_SIZE,BLOCK_SIZE);
                            }
                        }
                        if(screenData2[i][j] == 16){
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

            /*
            int max = 3;
            int min = 0;
            int range = max - min + 1;
            int rand = (int)(Math.random() * range) + min;
            if(rand == 0){//RIGHT
                ghost_dx = current_speed;
                ghost_dy = 0;
            }else if(rand == 1){//DOWN
                ghost_dx = 0;
                ghost_dy = -current_speed;
            }else if(rand == 2){//LEFT
                ghost_dx = -current_speed;
                ghost_dy = 0;
            }else{//UP
                ghost_dx = 0;
                ghost_dy = current_speed;
            }
            //=======================
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
