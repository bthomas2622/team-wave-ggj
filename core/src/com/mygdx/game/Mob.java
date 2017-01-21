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
    Sprite mobSprite;
    float MobDice;
	
	static final int BODY_WIDTH = 80;
	static final int BODY_HEIGHT = 50;
	
	boolean controlled;		// Has been waved at
	boolean waved;			// Has performed a wave
	
	Node target;			// Node with the position the mob wants to move to
	Body body;				// Mob body
	
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
	
	public void tick() {
		
	}
	
	public void render(Batch batch) {
        batch.draw(mobSprite, mobSprite.getX(), mobSprite.getY(), mobSprite.getOriginX(), mobSprite.getOriginY(), mobSprite.getWidth(), mobSprite.getHeight(), mobSprite.getScaleX(), mobSprite.getScaleY(), mobSprite.getRotation());
	}
	
	public void wave() {
		
	}
	
	public void getTarget() {
		
	}
	
	public void moveTowardTarget() {
		
	}

	public void dispose(){
		mobImage.dispose();
	}
	
}
