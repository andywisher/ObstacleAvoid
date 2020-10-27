package com.obstacleavoid.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.config.DifficultyLevel;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGH_SCORE_KEY = "highScore";
    public static final String DIFFICULTY_KEY = "difficulty";

    private final Preferences PREFS;
    private int highScore;
    private DifficultyLevel difficultyLevel;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(ObstacleAvoidGame.class.getSimpleName());
        highScore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
        String difficultyName = PREFS.getString(DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name());
        difficultyLevel = DifficultyLevel.valueOf(difficultyName);
    }

    public String getHighScoreString() {
        return String.valueOf(highScore);
    }

    public void updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            PREFS.putInteger(HIGH_SCORE_KEY, score);
            PREFS.flush();
        }
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void updateDifficulty(DifficultyLevel newDifficultyLevel) {
        if (this.difficultyLevel != newDifficultyLevel) {
            this.difficultyLevel = newDifficultyLevel;
            PREFS.putString(DIFFICULTY_KEY, difficultyLevel.name());
            System.out.println(difficultyLevel.name());
            PREFS.flush();
        }
    }
}
