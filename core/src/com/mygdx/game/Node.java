package com.mygdx.game;

public class Node
{
	private int xPos, yPos;
	// Flagged true for end nodes, so that "exiting" people know to be removed when they reach it
	private boolean endNode;


	public Node(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	// Methods that allow some math between Nodes
	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public int getXPixelPos() {
		return Map.getNodePixelPosition(this).x;
	}

	public int getYPixelPos() {
		return Map.getNodePixelPosition(this).y;
	}

	public Node[] getNeighborNodes() {
		return null;
	}


}
