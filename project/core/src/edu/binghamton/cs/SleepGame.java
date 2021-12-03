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

    public SleepGame(Sleep game){
        this.game = game;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        game.batch = new SpriteBatch();
        tileImage = new Texture("badlogic.jpg");
        sleeperImage = new Texture("badlogic.jpg");

        sleeper = new Rectangle();
        sleeper.x = 0.5f*screenWidth-150;
        sleeper.y = 0.5f*screenHeight-250;
        sleeper.width = 300;
        sleeper.height = 500;

        random = new Random();

        shapeRenderer = new ShapeRenderer();

        tiles = new Array<Rectangle>();
        createTiles();

        advancedTime = 0;
    }

    public void createTiles(){
        Random random = new Random();
        r = random.nextInt(4);
        System.out.println(r);
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

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,1,1);
        game.batch.begin();
        game.batch.draw(sleeperImage, 0.5f*screenWidth-150,0.5f*screenHeight-250,300,500);
        for(Rectangle i: tiles){
            game.batch.draw(tileImage, i.x, i.y, 200, 200);
        }
        game.batch.end();

        if(TimeUtils.nanoTime() - tileTime > 550000000) createTiles();

        for(Iterator<Rectangle> iterator = tiles.iterator(); iterator.hasNext();){
            Rectangle tile = iterator.next();
            //Left to center
            if(tile.x < screenWidth*0.5f) {
                tile.x += 1000 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
                    iterator.remove();
            }
            //Right to center
            else if(tile.x > screenWidth*0.5f) {
                tile.x -= 1000 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                    iterator.remove();
            }
            //Top to center
            else if(tile.y > screenHeight*0.5f){
                tile.y -= 1000 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
                    iterator.remove();
            }
            //Bottom to center
            else {
                tile.y += 1000 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                    iterator.remove();
            }
            if(Gdx.input.isTouched()){
                Vector3 touchpos = new Vector3();
                touchpos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchpos);
                if(tile.contains(touchpos.x, touchpos.y))
                    iterator.remove();
            }
            if(tile.overlaps(sleeper))
                iterator.remove();

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
    }
}
