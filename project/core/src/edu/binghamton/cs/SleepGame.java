package edu.binghamton.cs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class SleepGame implements Screen {
    Sleep game;

    Texture tileImage;
    Texture sleeperImage;
    Rectangle sleeper;

    int screenWidth = 0;
    int screenHeight = 0;

    Rectangle tile;
    Array<Rectangle> tiles;
    long tileTime;

    OrthographicCamera camera;

    ShapeRenderer shapeRenderer;

    int r;

    Random random;

    long advancedTime;

    Integer timer;
    float timecount;
    float tilecount;

    Boolean over;


    public SleepGame(Sleep game){
        this.game = game;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        game.batch = new SpriteBatch();
        tileImage = new Texture("study/img08.png");
        sleeperImage = new Texture("data/sleep/bed.png");

        sleeper = new Rectangle();
        sleeper.x = 0.5f*screenWidth-150;
        sleeper.y = 0.5f*screenHeight-250;
        sleeper.width = 300;
        sleeper.height = 500;

        random = new Random();

        shapeRenderer = new ShapeRenderer();

        tiles = new Array<Rectangle>();
        createTiles();

        //TIMER
        timer = 40;
        timecount = 0;
        tilecount = 0;

        over = false;

        Dorm.needs[0] = 7;
    }

    public void createTiles(){
        Random random = new Random();
        r = random.nextInt(4);
        Rectangle tile = new Rectangle();
        switch (r){
            case 0:
                tile.x = screenWidth*0f;
                tile.y = screenHeight*0.5f;
                break;
            case 1:
                tile.x = screenWidth*0.5f;
                tile.y = screenHeight*1f;
                break;
            case 2:
                tile.x = screenWidth*0.5f;
                tile.y = screenHeight*0f;
                break;
            case 3:
                tile.x = screenWidth*1f;
                tile.y = screenHeight*0.5f;
                break;
            default:
                break;
        }
        tile.width = 200;
        tile.height = 200;
        tiles.add(tile);
        tileTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    public void update(float dt){
        if(timer > 0) {
            tilecount += 1.75 * dt;
            if (tilecount >= 1) {
                createTiles();
                tilecount = 0;
            }
            timecount += dt;
            if (timecount >= 1) {
                timer--;
                timecount = 0;
            }
        }

    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0,0,0.4f,1);


        game.batch.begin();
        if(over)
            game.font.draw(game.batch, "Tap to continue", 500, 500);
        game.batch.draw(sleeperImage, 0.5f*screenWidth-150,0.5f*screenHeight-250,300,500);
        for(Rectangle i: tiles){
            game.batch.draw(tileImage, i.x, i.y, 200, 200);
        }
        game.font.draw(game.batch, "Time: " + timer, screenWidth-350, screenHeight-100);
        game.font.draw(game.batch,"Missed: " + game.missed, 50, screenHeight-100);
        game.batch.end();


        for(Iterator<Rectangle> iterator = tiles.iterator(); iterator.hasNext();){
            Rectangle tile = iterator.next();
            //Left to center
            if(tile.x < screenWidth*0.5f) {
                tile.x += 800 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
                    iterator.remove();
            }
            //Right to center
            else if(tile.x > screenWidth*0.5f) {
                tile.x -= 900 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                    iterator.remove();
            }
            //Top to center
            else if(tile.y > screenHeight*0.5f){
                tile.y -= 1300 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    iterator.remove();
            }
            //Bottom to center
            else {
                tile.y += 1300 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                    iterator.remove();
            }
            if(tile.overlaps(sleeper)) {
                if(iterator == null)
                    continue;
                else iterator.remove();
                game.missed++;
            }


            game.status = (game.missed > 64) ? 0 : (8 - (Math.floorDiv(game.missed,8)));

        }

        // END OF GAME
        if(timer == 0){
            over = true;
            Dorm.needs[0] = game.status;
            Dorm.updateStatusBars(0);
            if(Gdx.input.isTouched()) {
                TeamProject.gameState = game.states[0];
                timer = 40;
                game.missed = 0;
                over = false;
                //dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        tileImage.dispose();
        sleeperImage.dispose();
    }
}