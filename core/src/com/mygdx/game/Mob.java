package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class Mob implements Collideable {
	
	gameScreen game;
	public static Texture mobImage;
	public static Texture wave_drop = new Texture(Gdx.files.internal("blue_drop.png"));

    Sprite mobSprite;
    float MobDice;

	static final int BODY_WIDTH = 80;
	static final int BODY_HEIGHT = 50;

	boolean controlled;		// Has been waved at
	boolean waved;			// Has performed a wave

	Node target;			// Node with the position the mob wants to move to
	Body body;				// Mob body

	// Amount moved by the mob per tick (in pixels)
	private static final float MOVE_SPEED = 2;

	public Mob(gameScreen game, Body body, Node target) {
		this.game = game;
		this.body = body;
		this.target = target;
        MobDice = MathUtils.random();
        if (MobDice > .5f){
            mobImage = new Texture(Gdx.files.internal("badPedestrian.png"));
            mobSprite = new Sprite(mobImage);
        } else {
            mobImage = new Texture(Gdx.files.internal("neutralPedestrian.png"));
            mobSprite = new Sprite(mobImage);
        }
        mobSprite.setPosition(body.getPosition().x, body.getPosition().y);
        mobSprite.setOriginCenter();
        mobSprite.setRotation(0f);

        body.setUserData(this);
	}

	// Method that gets called whenever the game is "updating"
	public void tick() {

		moveTowardTarget();

	}

	public void render(Batch batch) {
        batch.draw(mobSprite, mobSprite.getX(), mobSprite.getY(), mobSprite.getOriginX(), mobSprite.getOriginY(), mobSprite.getWidth(), mobSprite.getHeight(), mobSprite.getScaleX(), mobSprite.getScaleY(), mobSprite.getRotation());
	}

	/**
	 * Code to generate wave
	 * Place the projectiles around the person object in a circular fashion
	 * Each projectile will be a 2x2 blue rectangle travelling for some 'd' distance
	 * Number of projectiles = 360/18 = 20
	 * Need to calculate each projectiles center location (x/y) around the particular person object
	 * To be decided: 1. Velocity of the projectile
	 * 2. TTL = Time to live. The projectile will expire after 's' seconds
	 */
	public void wave() {


	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node newTarget) {
		target = newTarget;
	}

	// returns true when the mob is at the target node
	private boolean atTarget() {
		return (body.getPosition().x == target.getYPos()) && (body.getPosition().y == target.getYPos()); // Update with collision code
	}

	// Moves the target one "MOVE_SPEED" towards the target Node
	public void moveTowardTarget() {
		// Note: this code is based on the assumption that there is a straight line between
		// the mob and its target node. This does come with the advantage that it allows for
		// diagonal movement paths

		float dX = MOVE_SPEED * (float) Math.cos(target.getxPos() / getXPos());
		float dY = MOVE_SPEED * (float) Math.sin(target.getYPos() / getYPos());

		setPos(getXPos() + dX, getYPos() + dY);
	}

	// Moving the position access calls into a helper to increase code readability
	public float getXPos() {
		return body.getPosition().x;
	}

	public float getYPos() {
		return body.getPosition().y;
	}

	public void setPos(float newXPos, float newYPos) {
		body.getPosition().set(newXPos, newYPos);
	}

	public void dispose(){
		mobImage.dispose();
	}

	public void onCollide(Object entity) {
		}
	@Override
	public void onCollide(Collideable object) {
		if (object instanceof WaveObject) {
			controlled = true;
		}	
	}
}
