package edu.binghamton.cs;

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

public class Hunger {
    String[] states= {"dorm_loop", "sleep_minigame", "food_minigame", "study_minigame", "sport_minigame"};
    String gameState = states[2];

    // Renderables
    Stage stage;
    SpriteBatch batch;
    BitmapFont font;

    // Timer
    BitmapFont timer_text;
    long timer, initial_time=TimeUtils.nanoTime();;

    // Player
    ShapeRenderer hitboxC;
    Rectangle hitbox;
    String player_facing= "left";
    int player_x, player_y = 0; // x is changed by arrows. y should never change.
    int player_h = 200;
    int player_w = 100;

    // Food
    ArrayList<ShapeRenderer> foodList;
    Food food;


    // Game Background
    Texture bg;
    TextureRegion region;

    public void create(){
        //Player
        hitboxC = new ShapeRenderer();
        hitbox = new Rectangle();

        //Food
        food = new Food();
//        foodRect = new Rectangle();
//        food = new ShapeRenderer();
//        foodList = new ArrayList<ShapeRenderer>();
//        foodList.add(food);
//        food_x = 500;
//        food_y = 1000;

        //Timer
        timer_text = new BitmapFont();
        timer_text.getData().setScale(5f);
        timer = 0;

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(15f);
        stage = new Stage(new ScreenViewport());
    }

    public void render(){
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        /* ====== Text ====== */
        batch.begin();
        //font.draw(batch, "This is the food minigame", 0, Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),10,true);
        if(timer/1_000_000_000 <10){
            timer_text.draw(batch,"0:0"+timer/1_000_000_000,Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),10,true);
        }
        else{
            timer_text.draw(batch,"0:"+timer/1_000_000_000,Gdx.graphics.getWidth()-200, Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),10,true);
        }
        timer = TimeUtils.nanoTime() - initial_time-2000000000;
        batch.end();


        /* ====== PLAYER ====== */
        /* hitbox */
        hitbox.set(player_x+215, player_y+140, player_w, player_h);

        hitboxC.begin(ShapeRenderer.ShapeType.Filled);
        hitboxC.setColor(71/255f,31/255f,177/255f,1);
        hitboxC.rect(player_x+215, player_y+140, player_w, player_h);
        hitboxC.end();

        /* movement */
        if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)){
            player_facing = "right";
            if(player_x<Gdx.graphics.getWidth()-330){ //Keeps player from walking off screen
                player_x += 4;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            player_facing = "left";
            if(player_x>-200){ //Keeps player from walking off screen
                player_x -= 4;
            }
        }


        /* ====== FALLING FOOD ====== */
        /* hitbox */
        if(food!=null){
            food.visiblebox.begin(ShapeRenderer.ShapeType.Filled);
            food.visiblebox.setColor(71/255f,31/255f,0/255f,1);
            food.visiblebox.rect(food.x, food.y, 100, 100);
            food.rect.set(food.x, food.y, 100, 100);
            food.visiblebox.end();
            /* movement */
            food.y -= 2;

            if(hitbox.overlaps(food.rect)){
                food.visiblebox = null;
                food.rect = null;
                food = null;
            }
        }



        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    static class Food{
        ShapeRenderer visiblebox = new ShapeRenderer();
        Rectangle rect = new Rectangle();
        int x = 500;
        int y = 1000;

        Food(){
        }
    }
}
