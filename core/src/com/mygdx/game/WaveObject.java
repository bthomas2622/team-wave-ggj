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
	Array<Sprite> dropSprites = new Array<Sprite>(20);
	Texture wave_drop = new Texture(Gdx.files.internal("waveProjectile.png"));
	gameScreen gameScreen;

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
		for (int i = 0; i < 360; i = i + 18) {
			Sprite dropSprite = new Sprite(wave_drop);
			int r = 48;
			double x, y;
			x = centerX + r * cos(i);
			y = centerY + r * sin(i);
			dropSprite.setPosition(((float) x), (float) y);
			dropSprites.add(dropSprite);
			Body body = gameScreen.map.createBody(((int) x) + 8, (int) y + 8, 16, 16);


			float velocity = (float) 0.5; // Your desired velocity of the car.
			float angle = i; // Body angle in radians.

			float velX = MathUtils.cos(angle) * velocity; // X-component.
			float velY = MathUtils.sin(angle) * velocity; // Y-component.
			body.setLinearVelocity(velX, velY);
			body.setUserData(this);
		}
	}


	protected void drawWave(Batch batch) {
		for (Sprite thisSprite : dropSprites
			 ) {
			batch.draw(thisSprite, thisSprite.getX(), thisSprite.getY(), thisSprite.getOriginX(), thisSprite.getOriginY(), thisSprite.getWidth(), thisSprite.getHeight(), thisSprite.getScaleX(), thisSprite.getScaleY(), thisSprite.getRotation());
		}
	}
}
