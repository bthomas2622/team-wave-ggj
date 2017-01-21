package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

public class Mob {
	
	gameScreen game;
	
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
	}
	
	public void tick() {
		
	}
	
	public void render(Batch batch) {
		
	}
	
	public void wave() {
		
	}
	
	public void getTarget() {
		
	}
	
	public void moveTowardTarget() {
		
	}
	
	
	
}
