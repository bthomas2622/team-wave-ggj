package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Map {

	static final int WIDTH = 5;
	static final int HEIGHT = 3;
	static final int MOB_NUMBERS = 20;
	
	gameScreen game;

	World world;
	
	Node[][] nodes;
	
    Texture backgroundImage;

	public Map(gameScreen game) {
		this.game = game;
		world = new World(new Vector2(0, 0), false);
		nodes = new Node[WIDTH][HEIGHT];
        backgroundImage = new Texture(Gdx.files.internal("grid.png"));
	}
	
	public void tick() {
		
	}
	
	public void render(Batch batch) {
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void generate() {
		generateNodes();
		Mob startingPlayer = new Mob(game, createBody(960, 540,Mob.BODY_WIDTH, Mob.BODY_HEIGHT), nodes[2][1]);
		game.mobs.add(startingPlayer);

		for (int x = 0; x < MOB_NUMBERS; x++) {
			int startingX = MathUtils.random(WIDTH);
			int startingY = MathUtils.random(HEIGHT);
			
//			Mob newMob = new Mob(game, createBody(), nodes[]);
		}
	}
	
	public void generateNodes() {
		for (int x = 1; x <= WIDTH; x++) {
			for (int y = 1; y <= HEIGHT; y++) {
				Node newNode = new Node(x * 192, y * 192);
				nodes[x - 1][y - 1] = newNode;
			}
		}
	}

	public Body createBody(int x, int y, int width, int height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		bodyDef.position.set(x, y);

		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(width, height);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;

		body.createFixture(fixtureDef);

		shape.dispose();

		return body;
	}

	public Node[][] getNodes(){
		return nodes;
	}
}
