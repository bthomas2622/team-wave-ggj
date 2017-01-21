package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Created by bthom on 1/20/2017.
 */

public class gameScreen implements Screen {
    final TeamWave game;
    Texture backgroundImage;
    
    Map map;
    Array<Mob> mobs;

    public gameScreen(final TeamWave gam) {
        game = gam;
        backgroundImage = new Texture(Gdx.files.internal("grid.png"));
        map = new Map(this);
        mobs = new Array<Mob>();
        
        map.generate();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Mob mob:mobs){
            mob.render(game.batch);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
