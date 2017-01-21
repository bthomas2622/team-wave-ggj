package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class Mob implements Collideable {

    gameScreen game;
    public static Texture mobImage;
    WaveObject wave;


    Sprite mobSprite;
    float MobDice;

    static final int BODY_WIDTH = 50;
    static final int BODY_HEIGHT = 80;

    boolean controlled;        // Has been waved at
    boolean waved;            // Has performed a wave

    Node target;            // Node with the position the mob wants to move to
    Body body;                // Mob body

    // Amount moved by the mob per tick (in pixels)
    private static final float MOVE_SPEED = 2;

    public Mob(gameScreen game, Body body, Node target) {
        this.game = game;
        this.body = body;
        this.target = target;
        MobDice = MathUtils.random();
        if (MobDice > .5f) {
            mobImage = new Texture(Gdx.files.internal("badPedestrian.png"));
            mobSprite = new Sprite(mobImage);
        } else {
            mobImage = new Texture(Gdx.files.internal("neutralPedestrian.png"));
            mobSprite = new Sprite(mobImage);
        }
        mobSprite.setPosition(body.getPosition().x * game.PIXELS_TO_METERS - (BODY_WIDTH / 2f), body.getPosition().y * game.PIXELS_TO_METERS - (BODY_HEIGHT / 2f));
        System.out.println(mobSprite.getX());
        mobSprite.setOriginCenter();
        mobSprite.setRotation(0f);
        //wave();
        body.setUserData(this);
    }

    // Method that gets called whenever the game is "updating"
    public void tick() {

		moveTowardTarget();

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (controlled && !waved) {
				wave();
			}
		}

    }

	public void render(Batch batch) {
		mobSprite.setPosition(body.getPosition().x * game.PIXELS_TO_METERS - (BODY_WIDTH/2f), body.getPosition().y * game.PIXELS_TO_METERS - (BODY_HEIGHT/2f));
        batch.draw(mobSprite, mobSprite.getX(), mobSprite.getY(), mobSprite.getOriginX(), mobSprite.getOriginY(), mobSprite.getWidth(), mobSprite.getHeight(), mobSprite.getScaleX(), mobSprite.getScaleY(), mobSprite.getRotation());
		if (waved) {
			wave.drawWave(batch);
		}
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
        wave = new WaveObject();
        wave.positionDrops(mobSprite.getX() + 25, mobSprite.getY() + 40);
		waved = true;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node newTarget) {
        target = newTarget;
    }

	// returns true when the mob is at the target node
	private boolean atTarget() {
		// Note, we have to adjust the xPos because it is not a pixel position
		float dX = Math.abs(this.getXPixelPos() - target.getXPixelPos());
		float dY = Math.abs(this.getYPixelPos() - target.getYPixelPos());
		System.out.println("Mob x: " + this.getXPixelPos() + " target X: " + target.getXPixelPos());
		System.out.println("Mob y: " + this.getYPixelPos() + " target y: " + target.getYPixelPos());

		System.out.println("DX: " + dX + ", DY " + dY);
		System.out.println("atTarget: " + (dX <= 96 && dY <= 96));
		return (dX <= 96 && dY <= 96);
	}

    // Moves the target one "MOVE_SPEED" towards the target Node
    public void moveTowardTarget() {
        // Note: this code is based on the assumption that there is a straight line between
        // the mob and its target node. This does come with the advantage that it allows for
        // diagonal movement paths


		if (atTarget()) {
			setTarget(target.getRandomNeighborNode());
		}

		float deltaX = target.getXPixelPos() - this.getXPixelPos();
		float deltaY = target.getYPixelPos() - this.getYPixelPos();

		float theta = (float)Math.tan(deltaY / deltaX);

		float Vx =  (float) Math.cos(theta) * MOVE_SPEED;
		float Vy = (float) Math.sin(theta) * MOVE_SPEED;

		this.body.setLinearVelocity(Vx, Vy);
	}

	public float getPixelDistanceToTarget() {
		double dX2 = Math.pow((target.getXPixelPos() - this.getXPixelPos()), 2);
		double dY2 = Math.pow((target.getYPixelPos() - this.getYPixelPos()), 2);
		return (float) Math.sqrt(dX2 + dY2);
	}

	// Moving the position access calls into a helper to increase code readability
	// Note these are not pixel positions
	public float getXPos() {
		return body.getPosition().x;
	}

    public float getYPos() {
        return body.getPosition().y;
    }

    public void setPos(float newXPos, float newYPos) {
        body.getPosition().set(newXPos, newYPos);
    }

	// These methods are pixel positions
	public float getXPixelPos() {
		return body.getPosition().x * game.PIXELS_TO_METERS;
	}

	public float getYPixelPos() {
		return body.getPosition().y * game.PIXELS_TO_METERS;
	}
	public void dispose(){
		mobImage.dispose();
	}

	@Override
	public void onCollide(Collideable object) {
		if (object instanceof WaveObject) {
			controlled = true;
		}
	}
}
