package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.common.GameManager;

public class HighScoreScreen extends MenuScreenBase {

    private static final Logger logger = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);


    public HighScoreScreen(ObstacleAvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUI() {

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        Skin skinUI = assetManager.get(AssetDescriptors.SKIN_ATLAS);


        //highScore
        Label highScoreText = new Label("HIGH SCORE", skinUI);

        //highScore label
        Label highScoreLabel = new Label(GameManager.INSTANCE.getHighScoreString(), skinUI);

        //back button
        TextButton backButton = new TextButton("BACK", skinUI);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });


        //Content Table
        Table scoreTable = new Table(skinUI);
        scoreTable.defaults().pad(20);
        scoreTable.setBackground(RegionNames.PANEL);
        scoreTable.center();
        scoreTable.add(highScoreText).row();
        scoreTable.add(highScoreLabel).row();
        scoreTable.add(backButton);

        //Background Table
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(backgroundRegion));
        backgroundTable.center();
        backgroundTable.setFillParent(true);
        backgroundTable.pack();
        backgroundTable.add(scoreTable);
        return backgroundTable;
    }


    private void back() {
        logger.debug("back");
        game.setScreen(new MenuScreen(game));
    }

}
