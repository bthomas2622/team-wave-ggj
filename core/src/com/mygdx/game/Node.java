package com.mygdx.game;

public class Node
{
	public int xPos, yPos;

	// Flagged true for end nodes, so that "exiting" people know to be removed when they reach it
	private boolean endNode;

	public Node(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	// Methods that allow some math between Nodes
	public int getxPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setNeighborNodes(Node left, Node right, Node above, Node below) {

	}

}
