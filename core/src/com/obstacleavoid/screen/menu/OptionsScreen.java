package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.common.GameManager;
import com.obstacleavoid.config.DifficultyLevel;

public class OptionsScreen extends MenuScreenBase {

    private static final Logger logger = new Logger(OptionsScreen.class.getName(), Logger.DEBUG);

    private ButtonGroup<CheckBox> checkBoxButtonGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public OptionsScreen(ObstacleAvoidGame game) {
        super(game);
    }


    protected Actor createUI() {
        Skin uiSkin = assetManager.get(AssetDescriptors.SKIN_ATLAS);
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        Table table = new Table();
        table.defaults().pad(15);
        table.center();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Label header = new Label("DIFFICULTY", uiSkin);

        easy = createCheckBox(DifficultyLevel.EASY.name(), uiSkin);
        medium = createCheckBox(DifficultyLevel.MEDIUM.name(), uiSkin);
        hard = createCheckBox(DifficultyLevel.HARD.name(), uiSkin);
        checkBoxButtonGroup = new ButtonGroup<>(easy, medium, hard);

        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        checkBoxButtonGroup.setChecked(difficultyLevel.name());

        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(back());

        Table contentTable = new Table(uiSkin);
        contentTable.defaults().pad(10);
        contentTable.setBackground(RegionNames.PANEL);
        contentTable.add(header).row();
        contentTable.add(easy).row();
        contentTable.add(medium).row();
        contentTable.add(hard).row();
        contentTable.add(backButton).row();

        table.add(contentTable);
        table.pack();
        return table;
    }

    private CheckBox createCheckBox(String diff, Skin uiSkin) {

        CheckBox checkBox = new CheckBox(diff, uiSkin);
        checkBox.left().pad(8);
        checkBox.getLabelCell().pad(7);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        });

        return checkBox;
    }

    private void difficultyChanged() {
        CheckBox checked = checkBoxButtonGroup.getChecked();

        if (checked == easy) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
            logger.debug("easy");
        } else if (checked == medium) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
            logger.debug("medium");
        } else if (checked == hard) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
            logger.debug("hard");
        }
    }

    private ChangeListener back() {
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        };
        return changeListener;
    }
}
