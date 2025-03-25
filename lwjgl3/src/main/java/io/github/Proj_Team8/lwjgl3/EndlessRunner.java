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
    private QuestionScene questionScene;
    private GameOverScene gameOverScene;

    // Scene Manager (for scene switching)
    private SceneManager sceneManager;

    // Flag to indicate if a new game should be started.
    private boolean newGame = true;
    
    // NEW: Flag to track if returning from question scene
    private boolean returningFromQuestion = false;
    // NEW: Variable to store score when going to question scene
    private float savedScore = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sharedFont = new BitmapFont(); // Create a shared BitmapFont

        soundManager = new SoundManager();

        inputOutputManager = new InputOutputManager(soundManager);

        // Create initial scenes.
        // Pass the shared batch to StartMenuScene so its Stage uses it.
        startMenuScene = new StartMenuScene(batch);
        gameScene = null;// Will be (re)created when starting gameplay.
        questionScene = new QuestionScene(sharedFont,null);
        gameOverScene = new GameOverScene(sharedFont, null); // SceneManager will be set later.

        // Create SceneManager with the shared batch and pre-created scenes.
        sceneManager = new SceneManager(batch, startMenuScene, gameScene, questionScene, gameOverScene);

        // Inject the SceneManager into scenes that require it.
        startMenuScene.setSceneManager(sceneManager);
        questionScene.setSceneManager(sceneManager);
        gameOverScene.setSceneManager(sceneManager);

        // Create highScore only once so that it persists across sessions.
        highScore = new HighScore(sharedFont);

        // Start background music.
        inputOutputManager.playBackgroundMusic();
    }

    // Resets game logic and creates a new GameScene while resetting the current score.
    public void resetGame() {
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
        inputOutputManager.playBackgroundMusic();

        newGame = false;
    }
    
    // NEW: Method to reset game but preserve the score
    private void resetGamePreserveScore() {
        if (entityManager != null) {
            entityManager.dispose();
        }
        // Reinitialize game logic objects.
        entityManager = new EntityManager();
        movementManager = new MovementManager(entityManager.getPlayer());
        collisionManager = new CollisionManager();
        
        // Instead of resetting score, restore the saved score
        highScore.setScore(savedScore);

        if (gameScene != null) {
            gameScene.dispose();
        }
        gameScene = new GameScene(entityManager, highScore);
        sceneManager.setGameScene(gameScene);
        inputOutputManager.playBackgroundMusic();

        newGame = false;
    }
    
    // NEW: Method to save game state before going to question scene
    private void saveGameState() {
        savedScore = highScore.getCurrentScore();
        returningFromQuestion = true;
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // When the state is GAMEPLAY and a new game is required, reset game logic.
        if (sceneManager.getCurrentState() == SceneManager.GameState.GAMEPLAY && newGame) {
            // MODIFIED: Check if returning from question or starting new game
            if (!returningFromQuestion) {
                // Full reset for new game
                resetGame();
            } else {
                // Partial reset preserving score when returning from question
                resetGamePreserveScore();
                returningFromQuestion = false;
            }
        }

        // Update game logic only in GAMEPLAY.
        if (sceneManager.getCurrentState() == SceneManager.GameState.GAMEPLAY) {
            inputOutputManager.checkJumpInput(movementManager, entityManager.getPlayer());
            entityManager.update(deltaTime);
            movementManager.update(deltaTime);
            highScore.updateScore(deltaTime);

            Entity collidedEntity = collisionManager.getCollidedEntity(entityManager.getPlayer(), entityManager.getEntities());

            if (collidedEntity != null) {
                if (collidedEntity instanceof Obstacle || collidedEntity instanceof Bird) {
                    highScore.checkAndSaveHighScore();
                    // MODIFIED: Save game state before switching to question scene
                    saveGameState();
                    inputOutputManager.playGameOverMusic();
                    System.out.println("Switching to QUESTION state...");
                    sceneManager.setState(SceneManager.GameState.QUESTION);
                    newGame = true;
                } else if (collidedEntity instanceof HealthyFood1 || collidedEntity instanceof HealthyFood2) {
                    highScore.addScore(1000); 
                    inputOutputManager.scoreSound();
                    System.out.println("Collected Healthy Food! Score: " + highScore.getCurrentScore());
                    entityManager.removeEntity(collidedEntity); 
                }
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
        questionScene.dispose();
        if (entityManager != null) {
            entityManager.dispose();
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
    
    @Override
    public void resize(int width, int height) {
        sceneManager.resize(width, height);
    }
}