package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.Point;

public class Map {

	// adding some static variables to represent the pixel offsets for nodes
	// Note: we are creating this as static finals now, but we could eventually add new ones
	// to allow us to scale our map
	public static final int INITIAL_NODE_PIXEL_OFFSET_X = 192;
	public static final int INITIAL_NODE_PIXEL_OFFSET_Y = 156;
	public static final int INITIAL_NODE_PIXEL_SIZE = 192;

	// Number of "blocks" aka Nodes on the map
	static final int WIDTH = 5;
	static final int HEIGHT = 3;

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

	}
	
	public void generateNodes() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Node newNode = new Node(x, y);
				nodes[x][y] = newNode;
			}
		}
		/* Legacy code that generates nodes using xPos / yPos as PIXEL locations (rather than nodes
			array indices)
		for (int x = 1; x <= WIDTH; x++) {
			for (int y = 1; y <= HEIGHT; y++) {
				Node newNode = new Node(x * 192, y * 192);
				nodes[x - 1][y - 1] = newNode;
			}
		}
		*/
	}

	// Returns the pixel location of a node based on its position in the node array nodes
	public static Point getNodePixelPosition(Node node) {
		// Refactor this code if we want to allow the map to zoom in and out
		// To refactor: make this method non-static, change the Note getPixelPos methods...
		int xPos = INITIAL_NODE_PIXEL_OFFSET_X + node.getXPos() * INITIAL_NODE_PIXEL_SIZE;
		int yPos = INITIAL_NODE_PIXEL_OFFSET_Y + node.getYPos() * INITIAL_NODE_PIXEL_SIZE;
		return new Point(xPos, yPos);
	}

	public Body createBody(int x, int y, int width, int height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		bodyDef.position.set(x / game.PIXELS_TO_METERS, y / game.PIXELS_TO_METERS);
        System.out.println(bodyDef.position);
		Body body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(width / 2f / game.PIXELS_TO_METERS, height / 2f / game.PIXELS_TO_METERS);

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
