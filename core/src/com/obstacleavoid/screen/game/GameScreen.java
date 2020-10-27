package com.obstacleavoid.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.screen.menu.MenuScreen;

public class GameScreen implements Screen {


    private GameController gameController;
    private GameRenderer gameRenderer;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;


    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = this.game.getAssetManager();
    }

    @Override
    public void show() {


        gameController = new GameController(game);
        gameRenderer = new GameRenderer(game.getSpriteBatch(), assetManager, gameController);
    }


    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);


        if (gameController.isGameOver()) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }


}
