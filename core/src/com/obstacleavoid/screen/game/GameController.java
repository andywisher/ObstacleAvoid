package com.obstacleavoid.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.common.GameManager;
import com.obstacleavoid.config.DifficultyLevel;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.entity.Background;
import com.obstacleavoid.entity.Obstacle;
import com.obstacleavoid.entity.Player;

public class GameController {

    //Logger
    private final static Logger logger = new Logger(GameController.class.getName(), Logger.DEBUG);
    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private Player player;
    private int lives = GameConfig.LIVES_START;
    private float startPlayerX;
    private float startPlayerY;

    private final Array<Obstacle> obstacleArray = new Array<>();
    private float obstacleTimer;
    private Pool<Obstacle> obstaclePool;

    private int score;
    private float scoreTimer;
    private int displayScore;

    private Background background;

    private Sound hit;

    public GameController(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        init();
    }

    private void init() {
        //Player Init
        player = new Player();
        this.startPlayerX = (GameConfig.WORLD_WIDTH - player.getWidth()) / 2f;
        this.startPlayerY = 1 - player.getHeight() / 2f;
        player.setPosition(startPlayerX, startPlayerY);

        obstaclePool = Pools.get(Obstacle.class, 40);

        //create background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        //load hit sound
        hit = assetManager.get(AssetDescriptors.SOUND_DESCRIPTOR);

    }

    public void update(float delta) {
        if (isGameOver()) {
            logger.debug("Game Over");
            return;
        }

        updatePlayer();
        updateObstacles(delta);

        updateScore(delta);
        updateDisplayScore(delta);

        if (isPlayerCollidingWithObstacle()) {
            lives--;
            if (isGameOver()) {
                logger.debug("Game Over!!!");
                GameManager.INSTANCE.updateHighScore(score);

            } else {
                restartGame();
            }

        }

    }

    private void restartGame() {
        obstaclePool.freeAll(obstacleArray);
        obstacleArray.clear();
        player.setPosition(startPlayerX, startPlayerY);
    }

    public boolean isGameOver() {
        return lives <= 0;
//        return false;
    }


    private boolean isPlayerCollidingWithObstacle() {

        for (Obstacle obstacle : obstacleArray) {
            if (obstacle.isNotHit() && obstacle.isPlayerColliding(player)) {
                hit.play();
                return true;
            }
        }


        return false;
    }

    private void updatePlayer() {
        float xSpeed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

        player.setXPos(player.getXPos() + xSpeed);

        blockPlayerFromLeavingTheWorld();
    }

    private void updateScore(float delta) {
        scoreTimer += delta;
        if (scoreTimer > GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(
                    score,
                    displayScore + (int) (60 * delta));
        }
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerX = MathUtils.clamp(player.getXPos(),
                0, GameConfig.WORLD_WIDTH - player.getWidth());

        player.setPosition(playerX, player.getYPos());
    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacleArray) {
            obstacle.update();
        }

        createNewObstacle(delta);
        removePassedObstacles();

    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;
        if (obstacleTimer > GameConfig.OBSTACLE_SPAWN_TIME) {
            float max = GameConfig.WORLD_WIDTH - Obstacle.DIAMETER;
            float obstacleX = MathUtils.random(0, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = obstaclePool.obtain();
            DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
            obstacle.setPosition(obstacleX, obstacleY);
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacleArray.add(obstacle);

            obstacleTimer = 0f;
        }

    }

    private void removePassedObstacles() {
        if (obstacleArray.size > 0) {
            Obstacle first = obstacleArray.first();

            float minObstacleY = -Obstacle.DIAMETER;

            if (first.getYPos() < minObstacleY) {
                obstacleArray.removeValue(first, true);
                obstaclePool.free(first);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public int getLives() {
        return lives;
    }

    public Array<Obstacle> getObstacleArray() {
        return obstacleArray;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public Background getBackground() {
        return this.background;
    }
}
