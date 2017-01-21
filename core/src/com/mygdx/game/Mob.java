package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class Mob {
	
	gameScreen game;
	Texture mobImage;
	Texture wave_drop = new Texture(Gdx.files.internal("blue_drop.png"));
    Sprite mobSprite;
    float MobDice;
	
	static final int BODY_WIDTH = 80;
	static final int BODY_HEIGHT = 50;
	
	boolean controlled;		// Has been waved at
	boolean waved;			// Has performed a wave
	
	Node target;			// Node with the position the mob wants to move to
	Body body;				// Mob body

	// Amount moved by the mob per tick (in pixels)
	private static final double MOVE_SPEED = 2;

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


	}

	// Method that gets called whenever the game is "updating"
	public void tick() {

		move();

	}

	// Moves the Mob one "MOVE_SPEED" closer to its target node
	private void move() {

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
		return (body.getPosition().x == target.xPos); // Update with collision code
	}

	public void moveTowardTarget() {
		
	}

	public void dispose(){
		mobImage.dispose();
	}

	public void onCollide(Object entity) {
		
	}

}
