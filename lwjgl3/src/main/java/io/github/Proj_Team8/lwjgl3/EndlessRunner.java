package io.github.Proj_Team8.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Proj_Team8.lwjgl3.classes.*;
import io.github.Proj_Team8.lwjgl3.managers.*;

public class EndlessRunner extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont sharedFont; // Shared font for HighScore and GameOverScene

    // Game logic managers and components.
    private EntityManager entityManager;
    private MovementManager movementManager;
    private CollisionManager collisionManager;
    private InputOutputManager inputOutputManager;
    private HighScore highScore;
    private SoundManager soundManager;

    // Scenes
    private StartMenuScene startMenuScene;
    private GameScene gameScene;
    private GameOverScene gameOverScene;

    // Scene Manager (for scene switching)
    private SceneManager sceneManager;

    // Flag to indicate if a new game should be started.
    private boolean newGame = true;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sharedFont = new BitmapFont(); // Create a shared BitmapFont

        soundManager = new SoundManager();
        inputOutputManager = new InputOutputManager(soundManager);

        // Create initial scenes.
        // Pass the shared batch to StartMenuScene so its Stage uses it.
        startMenuScene = new StartMenuScene(batch);
        gameScene = null; // Will be (re)created when starting gameplay.
        gameOverScene = new GameOverScene(sharedFont, null); // SceneManager will be set later.

        // Create SceneManager with the shared batch and pre-created scenes.
        sceneManager = new SceneManager(batch, startMenuScene, gameScene, gameOverScene);

        // Inject the SceneManager into scenes that require it.
        startMenuScene.setSceneManager(sceneManager);
        gameOverScene.setSceneManager(sceneManager);

        // Create highScore only once so that it persists across sessions.
        highScore = new HighScore(sharedFont);

        // Start background music.
        inputOutputManager.playBackgroundMusic();
    }

    // Resets game logic and creates a new GameScene while resetting the current score.
    private void resetGame() {
        if (entityManager != null) {
            entityManager.dispose();
        }
        // Reinitialize game logic objects.
        entityManager = new EntityManager();
        movementManager = new MovementManager(entityManager.getPlayer());
        collisionManager = new CollisionManager();
        // Reset the current score (while preserving the overall high score).
        highScore.resetScore();

        if (gameScene != null) {
            gameScene.dispose();
        }
        gameScene = new GameScene(entityManager, highScore);
        sceneManager.setGameScene(gameScene);

        newGame = false;
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // When the state is GAMEPLAY and a new game is required, reset game logic.
        if (sceneManager.getCurrentState() == SceneManager.GameState.GAMEPLAY && newGame) {
            resetGame();
        }

        // Update game logic only in GAMEPLAY.
        if (sceneManager.getCurrentState() == SceneManager.GameState.GAMEPLAY) {
            inputOutputManager.checkJumpInput(movementManager, entityManager.getPlayer());
            entityManager.update(deltaTime);
            movementManager.update(deltaTime);
            highScore.updateScore(deltaTime);

            // On collision, update the high score, play game-over music, switch state, and mark for reset.
            if (collisionManager.checkCollision(entityManager.getPlayer(), entityManager.getEntities())) {
                highScore.checkAndSaveHighScore();
                inputOutputManager.playGameOverMusic();
                sceneManager.setState(SceneManager.GameState.GAMEOVER);
                newGame = true;
            }
        }

        // Delegate rendering to the current scene.
        sceneManager.render(batch);
    }

    @Override
    public void dispose() {
        // Dispose scenes and game logic first.
        startMenuScene.dispose();
        if (gameScene != null) {
            gameScene.dispose();
        }
        gameOverScene.dispose();
        if (entityManager != null) {
            entityManager.dispose();
        }
        soundManager.dispose();
        // Dispose global resources last.
        batch.dispose();
        sharedFont.dispose();
    }
}
