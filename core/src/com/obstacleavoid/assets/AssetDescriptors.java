package com.obstacleavoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> UI_ATLAS =
            new AssetDescriptor<>(AssetPaths.UI_PATH, TextureAtlas.class);

    public static final AssetDescriptor<Skin> SKIN_ATLAS =
            new AssetDescriptor<>(AssetPaths.UI_SKIN_PATH, Skin.class);

    public static final AssetDescriptor<Sound> SOUND_DESCRIPTOR =
            new AssetDescriptor<>(AssetPaths.HIT, Sound.class);

    private AssetDescriptors() {
    }
}
