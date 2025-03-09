package io.github.Proj_Team8.lwjgl3.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Proj_Team8.lwjgl3.classes.StartMenuScene;
import io.github.Proj_Team8.lwjgl3.classes.GameScene;
import io.github.Proj_Team8.lwjgl3.classes.GameOverScene;

public class SceneManager {
    public enum GameState { MENU, GAMEPLAY, GAMEOVER;}

    private GameState currentState;
    private StartMenuScene startMenuScene;
    private GameScene gameScene;
    private GameOverScene gameOverScene;
    private SpriteBatch batch; // shared batch reference

    // Constructor: scenes are created externally and injected, plus the shared batch.
    public SceneManager(SpriteBatch batch, StartMenuScene startMenuScene, GameScene gameScene, GameOverScene gameOverScene) {
        this.batch = batch;
        this.startMenuScene = startMenuScene;
        this.gameScene = gameScene;
        this.gameOverScene = gameOverScene;
        this.currentState = GameState.MENU;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setState(GameState state) {
        currentState = state;
    }

    /**
     * Renders the active scene based on the current game state.
     */
    public void render(SpriteBatch batch) {
        switch (currentState) {
            case MENU:
                startMenuScene.render(batch);
                break;
            case GAMEPLAY:
                gameScene.render(batch);
                break;
            case GAMEOVER:
                gameOverScene.render(batch);
                break;
        }
    }

    /**
     * Resets the Start Menu scene by disposing it and recreating it
     * with the shared SpriteBatch.
     */
    public void resetStartMenu() {
        startMenuScene.dispose();
        startMenuScene = new StartMenuScene(batch);
        startMenuScene.setSceneManager(this);
    }
    
    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public void dispose() {
        startMenuScene.dispose();
        gameScene.dispose();
        gameOverScene.dispose();
    }

    public void resize(int width, int height) {
        switch (currentState) {
            case MENU:
                startMenuScene.resize(width, height);
                break;
            case GAMEPLAY:
                gameScene.resize(width, height);
                break;
            case GAMEOVER:
                gameOverScene.resize(width, height);
                break;
        }
    }
}
