package com.obstacleavoid.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.config.GameConfig;

public class ViewportUtils {

    private static final Logger log = new Logger(ViewportUtils.class.getName(), Logger.DEBUG);
    private static final int DEFAULT_CELL_SIZE = 1;


    private ViewportUtils() {

    }

    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer) {
        drawGrid(viewport, shapeRenderer, DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer, int cellSize) {
        if (viewport == null) {
            throw new IllegalArgumentException("viewport param is required.");
        }
        if (shapeRenderer == null) {
            throw new IllegalArgumentException("shapeRenderer param is required.");
        }
        if (cellSize < DEFAULT_CELL_SIZE) {
            cellSize = DEFAULT_CELL_SIZE;
        }


        Color oldColor = new Color(shapeRenderer.getColor());

        shapeRenderer.setColor(Color.WHITE);
        int worldWidth = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        for (int x = -doubleWorldWidth; x < doubleWorldWidth; x += cellSize) {
            shapeRenderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }


        for (int y = -doubleWorldHeight; y < doubleWorldHeight; y += cellSize) {
            shapeRenderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }


        //Draw Axis
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        shapeRenderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);

        //Draw Bounds
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, 0, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        shapeRenderer.end();
        shapeRenderer.setColor(oldColor);
    }

    public static void debugPixelPerUnit(Viewport viewport) {
        if (viewport == null) {
            throw new IllegalArgumentException("viewport param is required.");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getWorldHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("x PPU= " + xPPU + " yPPU= " + yPPU);
    }

}
