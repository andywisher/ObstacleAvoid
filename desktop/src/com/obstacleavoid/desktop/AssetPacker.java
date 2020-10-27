package com.obstacleavoid.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = false;
    private static final String RAW_ASSET_PATH = "desktop/assets-raw/skin";
    private static final String ASSETS_PATH = "android/assets/obstacle-avoid-assets/skin";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
        settings.pot = false;
//        settings.maxHeight = 500;
//        settings.maxWidth = 820;

        TexturePacker.process(settings,
                RAW_ASSET_PATH,
                ASSETS_PATH,
                "uiSkin");
    }

}
