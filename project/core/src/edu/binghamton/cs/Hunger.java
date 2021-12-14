package edu.binghamton.cs;

import static edu.binghamton.cs.TeamProject.DORM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Hunger {
    String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
//    String gameState;
    int foodCaught = 0;
    int hygiene=0;
    int hunger=0;

    // Renderables
    Stage stage;
    SpriteBatch batch;
    BitmapFont font, endText, pressToContinue;

    // Timer
    BitmapFont timer_text;
    long timer, initial_time=TimeUtils.nanoTime();

    // Player
    ShapeRenderer hitboxC;
    Texture playerImg;
    Texture playerImgFlipped;
    Rectangle hitbox;
    String player_facing= "left";
    int player_x, player_y = 0; // x is changed by arrows. y should never change.
    int player_h = 400;
    int player_w = 260;

    // Food
    ArrayList<Food> foodList;
    ArrayList<Food> caughtFoodList;
    ArrayList<Food> fallenFoodList;
    Food food;
    float foodTimer=0;

    // Health
    Texture head;
    int hearts = 3;

    // Game Background
    Texture bg;
    TextureRegion region;

    //Back Button
    Texture backImg;
    TextureRegion backRegion;
    TextureRegionDrawable backDrawable;
    ImageButton backButton;

    public void create(){
        //Player
        hitboxC = new ShapeRenderer();
        hitbox = new Rectangle();
        playerImg = new Texture(Gdx.files.internal("data/hunger/player.png"));
        playerImgFlipped = new Texture(Gdx.files.internal("data/hunger/playerFlipped.png"));

        //Food
        foodList = new ArrayList<>();
        caughtFoodList = new ArrayList<>();
        fallenFoodList = new ArrayList<>();
        food = new Food(foodList);

        //Health
        head = new Texture(Gdx.files.internal("data/hunger/head.png"));

        //Timer
        timer_text = new BitmapFont();
        timer_text.getData().setScale(5f);
        timer = 0;

        //Background
        bg = new Texture(Gdx.files.internal("data/hunger/hungerBG.png"));

        //Back Button
        backImg = new Texture(Gdx.files.internal("data/hunger/hungerBG.png"));
        backRegion = new TextureRegion(backImg);
        backDrawable = new TextureRegionDrawable(backRegion);
        backButton = new ImageButton(backDrawable);
        backButton.getImage().setScale(20f);
        backButton.setPosition(0,0);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                TeamProject.gameState = DORM;
                foodCaught=0;
                hearts=3;
                hunger=0;

                foodList.clear();
            }
        });

        //Text
        endText = new BitmapFont();
        endText.getData().setScale(5f);
        pressToContinue = new BitmapFont();
        pressToContinue.getData().setScale(7f);

        batch = new SpriteBatch();

        //Back Button
        backImg = new Texture(Gdx.files.internal("data/hunger/hungerBG.png"));
        backRegion = new TextureRegion(backImg);
        backDrawable = new TextureRegionDrawable(backRegion);
        backButton = new ImageButton(backDrawable);
        backButton.getImage().setScale(20f);
        backButton.setPosition(0,0);
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                if(hearts==0){
                    TeamProject.gameState = DORM;
                    foodCaught=0;
                    hearts=3;
                    hunger=0;
                    foodList.clear();
                }
            }
        });
        stage = new Stage(new ScreenViewport());
        stage.addActor(backButton);
    }

    public void render(){

        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        if(hearts==0){
            stage.draw();
            batch.begin();
            batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Background
            endText.setColor(0, 0, 0, 1);
            endText.draw(batch,"Hunger increased by "+hunger+"\nHygiene decreased by "+hygiene,50,Gdx.graphics.getHeight()-50,Gdx.graphics.getWidth(), 5,true);
            pressToContinue.setColor(0, 0, 0, 1);
            pressToContinue.draw(batch,"Tap screen to continue...",40,150,Gdx.graphics.getWidth(), 5,true);
            batch.end();
        }
        else {
            batch.begin();
            batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Background

            /* ====== Top Health Bar  ====== */
            int x = 20;
            for (int i = 0; i < hearts; i++) {
                batch.draw(head, x, Gdx.graphics.getHeight() - 220, 200, 200);
                x += 220;
            }

            /* ====== Text ====== */

            //font.draw(batch, "This is the food minigame", 0, Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),10,true);
            if (timer / 1_000_000_000 < 10) {
                timer_text.draw(batch, "0:0" + timer / 1_000_000_000, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 10, true);
            } else {
                timer_text.draw(batch, "0:" + timer / 1_000_000_000, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 10, true);
            }
            timer = TimeUtils.nanoTime() - initial_time - 2000000000;

            /* ====== PLAYER ====== */
            /* hitbox */
            hitbox.set(player_x + 215, player_y, player_w, player_h);
            if(player_facing=="right"){
                batch.draw(playerImg, player_x + 215, player_y, player_w, player_h);
            }
            else{
                batch.draw(playerImgFlipped, player_x + 215, player_y, player_w, player_h);
            }

            /* movement */
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
                player_facing = "right";
                if (player_x < Gdx.graphics.getWidth() - 330) { //Keeps player from walking off screen
                    player_x += 8;
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
                player_facing = "left";
                if (player_x > -200) { //Keeps player from walking off screen
                    player_x -= 8;
                }
            }


            /* ====== FALLING FOOD ====== */
            /* spawning */
            int rand = ThreadLocalRandom.current().nextInt(1, 3) % Gdx.graphics.getWidth();

            if (foodTimer >= rand) {
                food = new Food(foodList);
                foodTimer = 0;
            } else {
                foodTimer += Gdx.graphics.getDeltaTime();
            }

            /* hitbox */
            for (Food food : foodList) {
                if (food != null) {
                    if (food.goodOrBad == 2 || food.goodOrBad == 3) { //The food is one you want to catch
                        food.rect.set(food.x, food.y, 200, 200);
                        food.visiblebox.end();

                        food.batch.begin(); //Drawing Book
                        food.batch.draw(food.texture, food.x, food.y, 200, 200);
                        food.batch.end();
                    } else { // The food is rotten/garbage and you don't want to catch it
                        food.rect.set(food.x, food.y, 200, 200);
                        food.batch.begin(); //Drawing Book
                        food.batch.draw(food.texture, food.x, food.y, 200, 200);
                        food.batch.end();

                        //Hygeine down
                        food.visiblebox.end();
                    }

                    /* movement */
                    food.y -= 15;
                }

                if (hitbox.overlaps(food.rect)) { //If the player catches the food
                    caughtFoodList.add(food);
                    foodCaught++;
                }

                if (food.y <= 20) {  //Check if food hit the ground
                    fallenFoodList.add(food);
                }
            }

            for (Food food : fallenFoodList) { //Un-rendering food that hit the ground
                food.visiblebox = null;
                food.rect = null;
                foodList.remove(food);
                //Remove health if good food was dropped
                if (food.goodOrBad == 2 || food.goodOrBad == 3) {
                    hearts--;
                }
            }
            for (Food food : caughtFoodList) { //Un-rendering food that the player caught
                food.visiblebox = null;
                food.rect = null;
                foodList.remove(food);
                if (food.goodOrBad == 4 || food.goodOrBad == 1) {
                    hearts--;
                    if (Dorm.needs[2] > 0) {
                        Dorm.needs[2]--; //hygiene down
                        hygiene++;
                        Dorm.updateStatusBars(2);
                    }
                } else {
                    if (Dorm.needs[4] < 8) {
                        Dorm.needs[4]++;
                        hunger++;
                        Dorm.updateStatusBars(4);
                    }
                }
            }

            caughtFoodList.clear(); //Clearing the temp lists
            fallenFoodList.clear();
            batch.end();
        }
    }

    static class Food{
        ShapeRenderer visiblebox = new ShapeRenderer();
        Rectangle rect = new Rectangle();
        SpriteBatch batch = new SpriteBatch();
        Texture texture;
        String core = "data/hunger/appleCore.png"; //0
        String peel = "data/hunger/bananaPeel.png"; //1
        String bread = "data/hunger/bread.png"; //0
        String cheese = "data/hunger/cheese.png"; //1
        String lettuce = "data/hunger/lettuce.png"; //2
        String meat = "data/hunger/meat.png"; //3
        String tomato = "data/hunger/tomato.png"; //4
        String[] goodImages = {bread, cheese, lettuce, meat, tomato};
        String[] badImages = {core, peel};
        int x;
        int y = 1920;
        int goodOrBad; // 1,2,3 means good  //4 means bad
        int type;

        Food(ArrayList<Food> foodList){
            foodList.add(this);
            x=ThreadLocalRandom.current().nextInt(0,99999)%Gdx.graphics.getWidth();
            goodOrBad=ThreadLocalRandom.current().nextInt(1,4);

            if(goodOrBad==2 || goodOrBad==3){ //
                type = ThreadLocalRandom.current().nextInt(0,99999)%5;
                texture = new Texture(Gdx.files.internal(goodImages[type]));
            }
            else{
                type = ThreadLocalRandom.current().nextInt(0,99999)%2;
                texture = new Texture(Gdx.files.internal(badImages[type]));
            }
        }
    }
}
