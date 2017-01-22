package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

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
    boolean menuScreen;
    static Texture pressSpace = new Texture(Gdx.files.internal("pressSpace.png"));
    Sprite pressSpaceSprite;
    static Texture playerAmount = new Texture(Gdx.files.internal("playerCount.png"));
    Sprite playerAmountSprite;
    
    int TEAMS = 2;
    int[] teamScores;
    int[] teamRemaining;
    int teamTurn;
    boolean updateTeamTurn;
    
    AssetManager assetManager;
    Music backgroundMusic;
    boolean loaded = false;

    public gameScreen(final TeamWave gam, boolean isMainMenu, int playerCount) {
        game = gam;
        map = new Map(this);
        mobs = new Array<Mob>();
        buildings = new Array<Sprite>();
        menuScreen = isMainMenu;
        TEAMS = playerCount;

        map.generate();
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        if (!menuScreen){
            camera.zoom = 5;
        } else {
            pressSpaceSprite = new Sprite(pressSpace);
            pressSpaceSprite.setPosition(0f, 0f);
            playerAmountSprite = new Sprite(playerAmount);
            playerAmountSprite.setPosition(0f, 0f);
            camera.zoom = 1;
        }
        teamScores = new int[TEAMS];
        teamRemaining = new int[TEAMS];
        teamTurn = 1;

        assetManager = new AssetManager();
        assetManager.load("backgroundMusic.mp3", Music.class);
        assetManager.finishLoading();
    }
    
    public void useTeamTurn() {
    	teamTurn++;
    	
    	if (teamTurn > TEAMS) {
    		teamTurn = 1;
    	}
    	int count = 0;
    	while (teamRemaining[teamTurn-1] <= 0) {
    		teamTurn++;
    		if (teamTurn > TEAMS) {
        		teamTurn = 1;
        	}
    		if (count > 5) {
    			System.out.println("GOT HERE");
    			game.setScreen(new gameOverScreen(game, teamScores, map.MOB_NUMBERS, TEAMS));
    			break;
    		}
    		count++;
    	}
    	updateTeamTurn = false; 
    }
    
    public void updateTeamScores() {
    	for (int x = 0; x < TEAMS; x++) {
    		teamScores[x] = 0;
    	}
    	for (Mob mob : mobs) {
    		if (mob.controlled) {
    			teamScores[mob.team-1]++;
    		}
    	}
    }
    
    public void countTeamRemaining() {
    	for (int x = 0; x < TEAMS; x++) {
    		teamRemaining[x] = 0;
    	}
    	for (Mob mob : mobs) {
    		if (mob.controlled && !mob.waved) {
    			teamRemaining[mob.team-1]++;
    		}
    	}
    }

    //@Override
    public void create() {
        /*
        The code to override the ApplicationAdapter goes here
         */
    }

    @Override
    public void render (float delta) {
        //make sure music is loaded
    	if (updateTeamTurn) {
    		useTeamTurn();
    	}
        if (loaded == false){
            loaded = startMusic();
        }
        if (!menuScreen){
            if (camera.zoom > 1) {
                camera.zoom -= 0.08;
                camera.position.x = camera.viewportWidth/2;
                camera.position.y = camera.viewportHeight/2;
            }
            else {
                camera.zoom = 1;
            }
        }
    	countTeamRemaining();
    	
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
        if (menuScreen){
            game.batch.draw(pressSpaceSprite, pressSpaceSprite.getX(), pressSpaceSprite.getY(), pressSpaceSprite.getOriginX(), pressSpaceSprite.getOriginY(), pressSpaceSprite.getWidth(), pressSpaceSprite.getHeight(), pressSpaceSprite.getScaleX(), pressSpaceSprite.getScaleY(), pressSpaceSprite.getRotation());
            game.batch.draw(playerAmountSprite, playerAmountSprite.getX(), playerAmountSprite.getY(), playerAmountSprite.getOriginX(), playerAmountSprite.getOriginY(), playerAmountSprite.getWidth(), playerAmountSprite.getHeight(), playerAmountSprite.getScaleX(), playerAmountSprite.getScaleY(), playerAmountSprite.getRotation());
        }
        game.batch.end();
        //debugRenderer.render(map.world, debugMatrix);

        if (menuScreen){
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                menuScreen = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                game.setScreen(new gameScreen(game, false, 1));
                dispose();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                menuScreen = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                game.setScreen(new gameScreen(game, false, 3));
                dispose();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                game.setScreen(new gameScreen(game, false, 4));
                dispose();
            }
        }

//        if (!menuScreen) {
//            ((RayHandler) map.rayHandler).updateAndRender();
//        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new gameScreen(game, false, TEAMS));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new gameOverScreen(game, teamScores, map.MOB_NUMBERS, TEAMS));
            dispose();
        }


    }

    public boolean startMusic() {
        if(assetManager.isLoaded("backgroundMusic.mp3")) {
            backgroundMusic = assetManager.get("backgroundMusic.mp3", Music.class);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.5f);
            backgroundMusic.play();
            return true;
        }else {
            //System.out.println("not loaded yet");
            return false;
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
        assetManager.dispose();
        backgroundMusic.dispose();
//        try{
//            pressSpace.dispose();
//        }
//        catch (Exception e){
//        }

    }


}
