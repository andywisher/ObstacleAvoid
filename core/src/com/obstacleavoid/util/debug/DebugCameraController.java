package com.obstacleavoid.util.debug;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;


public class DebugCameraController {

    private static final Logger log = new Logger(DebugCameraController.class.getName(), Logger.DEBUG);

    private static final int DEFAULT_LEFT_KEY = Input.Keys.A;
    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
    private static final int DEFAULT_UP_KEY = Input.Keys.W;
    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;
    private static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.COMMA;
    private static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.PERIOD;
    private static final int DEFAULT_RESET_KEY = Input.Keys.BACKSPACE;
    private static final int DEFAULT_LOG_KEY = Input.Keys.ENTER;


    private static final float DEFAULT_MOVE_SPEED = 20.0f;
    private static final float DEFAULT_ZOOM_SPEED = 2.0f;
    private static final float DEFAULT_MAX_ZOOM_IN = 0.2f;
    private static final float DEFAULT_MAX_ZOOM_OUT = 30f;
    private float zoom = 1.0f;


    private final Vector2 cameraPosition = new Vector2();
    private final Vector2 startPosition = new Vector2();


    public DebugCameraController() {
    }


    private void setPosition(float x, float y) {
        this.cameraPosition.set(x, y);
    }

    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        this.cameraPosition.set(x, y);
    }


    public void applyTo(OrthographicCamera camera) {
        camera.position.set(this.cameraPosition, 0);
        camera.zoom = zoom;
        camera.update();
    }

    public void handleDebugInput(float delta) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float moveSpeed = DEFAULT_MOVE_SPEED * delta;
        float zoomSpeed = DEFAULT_ZOOM_SPEED * delta;

        if (Gdx.input.isKeyPressed(DEFAULT_LEFT_KEY)) {
            moveLeft(moveSpeed);
        } else if (Gdx.input.isKeyPressed(DEFAULT_RIGHT_KEY)) {
            moveRight(moveSpeed);
        } else if (Gdx.input.isKeyPressed(DEFAULT_UP_KEY)) {
            moveUp(moveSpeed);
        } else if (Gdx.input.isKeyPressed(DEFAULT_DOWN_KEY)) {
            moveDown(moveSpeed);
        }

        if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_IN_KEY)) {
            zoomIn(zoomSpeed);
        } else if (Gdx.input.isKeyPressed(DEFAULT_ZOOM_OUT_KEY)) {
            zoomOut(zoomSpeed);
        }

        if (Gdx.input.isKeyPressed(DEFAULT_RESET_KEY)) {
            reset();
        }

        if (Gdx.input.isKeyPressed(DEFAULT_LOG_KEY)) {
            logDebug();
        }

    }

    private void setZoom(float value) {
        zoom = MathUtils.clamp(value, DEFAULT_MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_OUT);
    }

    private void zoomIn(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void zoomOut(float zoomSpeed) {
        setZoom(zoom - zoomSpeed);
    }

    private void reset() {
        cameraPosition.set(startPosition);
        setZoom(1.0f);
    }

    private void logDebug() {
        log.debug("position= " + cameraPosition + " zoom= " + zoom);
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(cameraPosition.x + xSpeed, cameraPosition.y + ySpeed);
    }


    private void moveDown(float moveSpeed) {
        moveCamera(0, -moveSpeed);
    }

    private void moveUp(float moveSpeed) {
        moveCamera(0, moveSpeed);
    }

    private void moveRight(float moveSpeed) {
        moveCamera(moveSpeed, 0);
    }

    private void moveLeft(float moveSpeed) {
        moveCamera(-moveSpeed, 0);
    }


}
