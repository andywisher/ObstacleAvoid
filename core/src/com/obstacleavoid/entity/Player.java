package com.obstacleavoid.entity;

public class Player extends GameObjectBase {

    private static final float BOUND_RADIUS = 0.4f;
    private static final float DIAMETER = 2 * BOUND_RADIUS;


    public Player() {
        super(BOUND_RADIUS);
        setSize(DIAMETER, DIAMETER);
    }

    @Override
    public float getHeight() {
        return DIAMETER;
    }
}
