package com.obstacleavoid.screen.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.entity.Obstacle;
import com.obstacleavoid.entity.Player;
import com.obstacleavoid.util.GdxUtils;
import com.obstacleavoid.util.ViewportUtils;
import com.obstacleavoid.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    //Game Screen
    private OrthographicCamera mainCamera;
    private ShapeRenderer shapeRenderer;
    private Viewport mainViewport;

    //Hud Screen
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private final SpriteBatch spriteBatch;
    private BitmapFont font;
    private final GlyphLayout glyphLayout = new GlyphLayout();

    //Debug Camera
    private DebugCameraController debugCameraController;


    //GameController
    private final GameController gameController;

    //Asset Manager
    private final AssetManager assetManager;

    //Player Texture
    private TextureRegion playerRegion;

    //Obstacle Texture
    private TextureRegion obstacleRegion;

    //Background Texture
    private TextureRegion backgroundRegion;

    public GameRenderer(SpriteBatch spriteBatch, AssetManager assetManager, GameController gameController) {
        this.gameController = gameController;
        this.assetManager = assetManager;
        this.spriteBatch = spriteBatch;
        init();

    }

    private void init() {
        mainCamera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();
        mainViewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, mainCamera);

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);


        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        font = assetManager.get(AssetDescriptors.FONT);
        playerRegion = gamePlayAtlas.findRegion(RegionNames.PLAYER);
        obstacleRegion = gamePlayAtlas.findRegion(RegionNames.OBSTACLE);
        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
    }

    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(mainCamera);

        if (Gdx.input.isTouched() && !gameController.isGameOver()) {
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = mainViewport.unproject(new Vector2(screenTouch));

            Player player = gameController.getPlayer();

            worldTouch.x = MathUtils.clamp(worldTouch.x, 0, GameConfig.WORLD_WIDTH - player.getWidth());

            if (Math.abs(worldTouch.x - player.getXPos()) > 0.1) {
                if (worldTouch.x > player.getXPos()) {
                    player.setXPos(player.getXPos() + (7f * delta));
                } else if (worldTouch.x < player.getXPos()) {
                    player.setXPos(player.getXPos() - (7f * delta));
                }
            }


        }

        //Clear Screen
        GdxUtils.clearScreen();

        //Render GamePlay
        renderGamePlay();

        //Render ui/hud
        renderUi();

        //Render debug
        //renderDebug();
    }


    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }


    public void resize(int width, int height) {
        mainViewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(mainViewport);
    }

    private void renderUi() {

        hudViewport.apply();
        mainViewport.apply();

        spriteBatch.setProjectionMatrix(hudCamera.combined);

        spriteBatch.begin();


        //Lives
        String livesText = "LIVES: " + gameController.getLives();
        glyphLayout.setText(font, livesText);
        font.draw(spriteBatch, livesText,
                20,
                GameConfig.HUD_HEIGHT - glyphLayout.height);

        //Score
        String scoreText = "SCORE: " + gameController.getDisplayScore();
        glyphLayout.setText(font, scoreText);
        font.draw(spriteBatch, scoreText,
                GameConfig.HUD_WIDTH - 240.0f,
                GameConfig.HUD_HEIGHT - glyphLayout.height);


        spriteBatch.end();
    }

    private void renderGamePlay() {
        mainViewport.apply();

        spriteBatch.setProjectionMatrix(mainCamera.combined);

        spriteBatch.begin();

        spriteBatch.draw(backgroundRegion, gameController.getBackground().getX(), gameController.getBackground().getY(),
                gameController.getBackground().getWidth(), gameController.getBackground().getHeight());

        Player player = gameController.getPlayer();
        spriteBatch.draw(playerRegion,
                player.getXPos(), player.getYPos(),
                player.getWidth(), player.getHeight()
        );

        for (Obstacle obstacle : gameController.getObstacleArray()) {
            spriteBatch.draw(obstacleRegion,
                    obstacle.getXPos(), obstacle.getYPos(),
                    obstacle.getWidth(), obstacle.getHeight());
        }

        spriteBatch.end();
    }

    private void renderDebug() {
        shapeRenderer.setProjectionMatrix(mainCamera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawDebug();
        shapeRenderer.end();

        ViewportUtils.drawGrid(mainViewport, shapeRenderer);
    }

    private void drawDebug() {
        gameController.getPlayer().drawDebug(shapeRenderer);
        for (Obstacle obstacle : gameController.getObstacleArray()) {
            obstacle.drawDebug(shapeRenderer);
        }
    }
}
