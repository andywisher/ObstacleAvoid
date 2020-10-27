package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.screen.game.GameScreen;

public class MenuScreen extends MenuScreenBase {

    public static final Logger logger = new Logger(MenuScreen.class.getName(), Logger.DEBUG);


    public MenuScreen(ObstacleAvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUI() {
        Skin uiSkin = assetManager.get(AssetDescriptors.SKIN_ATLAS);
        Table backgroundTable = new Table();
        Table buttonTable = new Table(uiSkin);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);


        TextButton playButton = new TextButton("PLAY", uiSkin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        TextButton highScoreButton = new TextButton("HIGH SCORE", uiSkin);
        ;
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });

        TextButton optionsButton = new TextButton("OPTIONS", uiSkin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptions();
            }
        });

        TextButton quitButton = new TextButton("QUIT", uiSkin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });


        buttonTable.setBackground(RegionNames.PANEL);
        buttonTable.defaults().pad(20);
        buttonTable.center();
        backgroundTable.add(buttonTable);

        buttonTable.add(playButton);
        buttonTable.row();
        buttonTable.add(highScoreButton);
        buttonTable.row();
        buttonTable.add(optionsButton);
        buttonTable.row();
        buttonTable.add(quitButton);

        backgroundTable.setBackground(new TextureRegionDrawable(backgroundRegion));
        backgroundTable.center();
        backgroundTable.setFillParent(true);
        backgroundTable.pack();
        return backgroundTable;
    }

    private void quit() {
        logger.debug("quitting");
        Gdx.app.exit();
    }

    private void play() {
        logger.debug("play");
        game.setScreen(new GameScreen(game));
    }

    private void showHighScore() {
        logger.debug("highScore");
        game.setScreen(new HighScoreScreen(game));
    }

    private void showOptions() {
        logger.debug("options");
        game.setScreen(new OptionsScreen(game));
    }

}
