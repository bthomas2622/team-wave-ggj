package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by bthom on 1/21/2017.
 */

public class gameOverScreen implements Screen {
    final TeamWave game;
    OrthographicCamera camera;
    Texture backgroundImage;
    float backgroundRoller;
    BitmapFont fontOne;
    int score;
    int remaining;

    public gameOverScreen(final TeamWave gam, int score, int remaining) {
        game = gam;
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, 1280, 720);
        camera.setToOrtho(false, 1920, 1080);
        backgroundRoller = MathUtils.random();
        if (backgroundRoller <= 0.25f) {
            backgroundImage = new Texture(Gdx.files.internal("gameoverimages/gameOverBlue.png"));
        } else if (backgroundRoller > 0.25f & backgroundRoller <= 0.5f){
            backgroundImage = new Texture(Gdx.files.internal("gameoverimages/gameOverGreen.png"));
        } else if (backgroundRoller > 0.5f & backgroundRoller <= 0.75f){
            backgroundImage = new Texture(Gdx.files.internal("gameoverimages/gameOverOrange.png"));
        } else {
            backgroundImage = new Texture(Gdx.files.internal("gameoverimages/gameOverPink.png"));
        }
        this.score = score;
        this.remaining = remaining;
        fontOne = new BitmapFont();
        fontOne.setColor(Color.BLACK);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fontOne.draw(game.batch, this.score + " / " + this.remaining, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/1.50f);
        fontOne.draw(game.batch, "Press SPACE to wave once more", Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/1.55f);
        game.batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new gameScreen(game, false));
            dispose();
        }

    }
    @Override
    public void resize(int width, int height){

    }

    @Override
    public void show(){
    }

    @Override
    public void hide(){
    }

    @Override
    public void pause(){
    }

    @Override
    public void resume(){
    }

    @Override
    public void dispose(){
        backgroundImage.dispose();
        fontOne.dispose();
    }
}
