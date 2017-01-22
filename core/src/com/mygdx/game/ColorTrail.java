package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by zacha on 1/21/2017.
 */

// Class designed to add transparent color trails to the game where people who are waving go
public class ColorTrail {
    public static Color default_trail_color = new Color(1, 0, 1, (float)(.3));
    public Color trailColor;
    private int xPos, yPos;

    public ColorTrail(int xPosition, int yPosition, Color color) {
        xPos = xPosition;
        yPos = yPosition;
        trailColor = color;
    }

    public void drawTrail(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(trailColor);
        shapeRenderer.circle(10, 10, 100);
    }

}
