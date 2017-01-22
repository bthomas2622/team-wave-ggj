package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class WaveObject implements Collideable {
	Sprite dropSprite;
	Texture wave_drop;
	gameScreen gameScreen;
	Array<Body> bodies = new Array<Body>(20);
	Array<Body> toDelete = new Array<Body>();
	static final float LIFE_TIME = 0.8f;
	float lifeTimer = 0;
	int team;
	
	public WaveObject(gameScreen gs, int team) {
		gameScreen = gs;
		this.team = team;
		if (team == 1) {
			wave_drop = new Texture(Gdx.files.internal("waveProjectileBlue.png"));
		}
		else {
			wave_drop = new Texture(Gdx.files.internal("waveProjectileRed.png"));
		}
	}

	public void tick(float delta) {
		lifeTimer += delta;
		if (lifeTimer >= LIFE_TIME) {
			for (Body body : bodies) {
				body.getWorld().destroyBody(body);
			}
			bodies = new Array<Body>(20);
		}
	}

	@Override
	public void onCollide(Collideable object) {

		
	}

	/**
	 * Methond to get a circle around the object
	 * The drops will be placed at even distance on this circle
	 */
	protected void positionDrops(float centerX, float centerY) {
		dropSprite = new Sprite(wave_drop);
		for (int i = 0; i < 360; i = i + 9) {
			int r = 32;
			double x, y;
			x = centerX + r * cos(i);
			y = centerY + r * sin(i);
			Body body = gameScreen.map.createBody(((int) x) + 8, (int) y + 8, 16, 16);


			float velocity = (float) 1; // Your desired velocity of the car.
			float angle = i; // Body angle in radians.

			float velX = MathUtils.cos(angle) * velocity; // X-component.
			float velY = MathUtils.sin(angle) * velocity; // Y-component.
			body.setLinearVelocity(velX, velY);
			body.setUserData(this);
			bodies.add(body);
		}
	}

	protected void drawWave(Batch batch) {
		for (Body body: bodies) {
			dropSprite.setPosition(((float) body.getPosition().x * gameScreen.PIXELS_TO_METERS) - (8 / 2f), (float) body.getPosition().y * gameScreen.PIXELS_TO_METERS - (8 / 2f));
			batch.draw(dropSprite, dropSprite.getX(), dropSprite.getY(), dropSprite.getOriginX(), dropSprite.getOriginY(), dropSprite.getWidth() / 2, dropSprite.getHeight() / 2, dropSprite.getScaleX(), dropSprite.getScaleY(), dropSprite.getRotation());
		}
	}

	public Body getBody() {
		return bodies.first();
	}
}
