package edu.binghamton.cs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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

        random = new Random();

        shapeRenderer = new ShapeRenderer();

        tiles = new Array<Rectangle>();
        createTiles();

        advancedTime = 0;
    }

    public void createTiles(){
        Float[] arr = {0f, 0.25f, 0.5f, 0.75f};
        Random random = new Random();
        r = random.nextInt(arr.length);
        Rectangle tile = new Rectangle();
        tile.x = arr[r]*screenWidth + 50;
        tile.y = screenHeight;
        tile.width = 100;
        tile.height = 100;
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
        for(Rectangle i: tiles){
            game.batch.draw(tileImage, i.x, i.y, 300, 500);
        }
        game.batch.end();

        if(TimeUtils.nanoTime() - tileTime > 550000000) createTiles();

        for(Iterator<Rectangle> iterator = tiles.iterator(); iterator.hasNext();){
            Rectangle tile = iterator.next();
            tile.y -= 3000 * Gdx.graphics.getDeltaTime();
            if(tile.y < screenHeight*0.2f){
                iterator.remove();
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
    }
}
