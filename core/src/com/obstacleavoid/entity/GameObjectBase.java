package com.obstacleavoid.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase {

    private final Circle bounds;

    private float xPos;
    private float yPos;
    private float width = 1;
    private float height = 1;

    public GameObjectBase(float boundRadius) {

        bounds = new Circle(xPos, yPos, boundRadius);

    }



    public void setPosition(float newXPos, float newYPos) {
        this.xPos = newXPos;
        this.yPos = newYPos;
        updateBounds();
    }

    public void updateBounds() {
        float margin = width / 2f;
        this.bounds.setPosition(xPos + margin, yPos + margin);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.x(bounds.x, bounds.y, 0.05f);
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public Circle getBounds() {
        return bounds;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
        updateBounds();
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }


}
