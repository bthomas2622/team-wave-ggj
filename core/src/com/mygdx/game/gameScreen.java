package com.mygdx.game;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by bthom on 1/20/2017.
 */

public class gameScreen implements Screen {
    final TeamWave game;
    final float PIXELS_TO_METERS = 100f;
    Map map;
    Array<Mob> mobs;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;

    public gameScreen(final TeamWave gam) {
        game = gam;
        map = new Map(this);
        mobs = new Array<Mob>();

        map.generate();
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }

    //@Override
    public void create() {
        /*
        The code to override the ApplicationAdapter goes here
         */
    }

    @Override
    public void render (float delta) {
    	map.world.step(delta, 6, 2);
        camera.update();
    	
    	map.tick();
    	for (Mob mob:mobs) {
    		mob.tick();
    	}
    	
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        debugMatrix = game.batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
        game.batch.begin();
        map.render(game.batch);
        for (Mob mob:mobs) {
            mob.render(game.batch);
        }
        game.batch.end();
        debugRenderer.render(map.world, debugMatrix);
        map.rayHandler.setCombinedMatrix(debugMatrix, 0, 0, camera.viewportWidth / PIXELS_TO_METERS, camera.viewportHeight / PIXELS_TO_METERS);
        ((RayHandler) map.rayHandler).updateAndRender();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
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
        debugRenderer.dispose();
    }

}
