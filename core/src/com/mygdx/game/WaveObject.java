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
	Texture wave_drop = new Texture(Gdx.files.internal("waveProjectile.png"));
	gameScreen gameScreen;
	Array<Body> bodies = new Array<Body>(20);

	public WaveObject(gameScreen gs) {
		gameScreen = gs;
	}


	@Override
	public void onCollide(Collideable object) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Methond to get a circle around the object
	 * The drops will be placed at even distance on this circle
	 */
	protected void positionDrops(float centerX, float centerY) {
		dropSprite = new Sprite(wave_drop);
		for (int i = 0; i < 360; i = i + 18) {
			int r = 48;
			double x, y;
			x = centerX + r * cos(i);
			y = centerY + r * sin(i);
			Body body = gameScreen.map.createBody(((int) x) + 8, (int) y + 8, 16, 16);


			float velocity = (float) 0.5; // Your desired velocity of the car.
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
			System.out.println("drawing");
			dropSprite.setPosition(((float) body.getPosition().x * gameScreen.PIXELS_TO_METERS) - (16 / 2f), (float) body.getPosition().y * gameScreen.PIXELS_TO_METERS - (16 / 2f));
			batch.draw(dropSprite, dropSprite.getX(), dropSprite.getY(), dropSprite.getOriginX(), dropSprite.getOriginY(), dropSprite.getWidth(), dropSprite.getHeight(), dropSprite.getScaleX(), dropSprite.getScaleY(), dropSprite.getRotation());
		}
	}

	public Body getBody() {
		return bodies.first();
	}
}
