package com.obstacleavoid.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

public class Obstacle extends GameObjectBase implements Pool.Poolable {

    private static final float BOUND_RADIUS = 0.3f;
    public static final float DIAMETER = 2 * BOUND_RADIUS;
    private float ySpeed = 0.05f;
    private boolean hit;

    public Obstacle() {
        super(BOUND_RADIUS);
    }

    public void update() {

        setPosition(getXPos(), getYPos() - ySpeed);
        updateBounds();
        setSize(DIAMETER, DIAMETER);
    }

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();
        boolean overlaps = Intersector.overlaps(playerBounds, getBounds());

        hit = overlaps;

        return overlaps;
    }

    public boolean isNotHit() {
        return !hit;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void reset() {
        hit = false;
    }
}
