package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

import box2dLight.RayHandler;

/**
 * Created by bthom on 1/20/2017.
 */

public class gameScreen implements Screen {
    final static float PIXELS_TO_METERS = 100f;
    final TeamWave game;
    Map map;
    Array<Mob> mobs;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    Array<Sprite> buildings;

    public gameScreen(final TeamWave gam) {
        game = gam;
        map = new Map(this);
        mobs = new Array<Mob>();
        buildings = new Array<Sprite>();

        map.generate();
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        camera.zoom = 5;
    }

    //@Override
    public void create() {
        /*
        The code to override the ApplicationAdapter goes here
         */
    }

    @Override
    public void render (float delta) {
    	if (camera.zoom > 1) {
    		camera.zoom -= 0.1;
    		camera.position.x = camera.viewportWidth/2;
    		camera.position.y = camera.viewportHeight/2;
    	}
    	else {
    		camera.zoom = 1;
    	}
    	map.world.step(delta, 6, 2);
        camera.update();

    	map.tick();
    	for (Mob mob:mobs) {
            mob.tick(delta);
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
        for (Sprite building : buildings) {
            game.batch.draw(building, building.getX(), building.getY(), building.getOriginX(), building.getOriginY(), building.getWidth(), building.getHeight(), building.getScaleX(), building.getScaleY(), building.getRotation());
        }
        game.batch.end();
        debugRenderer.render(map.world, debugMatrix);
        map.rayHandler.setCombinedMatrix(debugMatrix, 0, 0, camera.viewportWidth / PIXELS_TO_METERS, camera.viewportHeight / PIXELS_TO_METERS);
        ((RayHandler) map.rayHandler).updateAndRender();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new gameScreen(game));
            dispose();
        }


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
        map.dispose();
    }



    // Experimental Code
    public static void renderColorTrails(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
