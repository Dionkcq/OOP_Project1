package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.Proj_Team8.lwjgl3.managers.SceneManager;

public class StartMenuScene {
    private Stage stage;
    private TextButton startButton, exitButton;
    private Label titleLabel;
    private Texture backgroundTexture;
    private Skin skin;
    private boolean active; // Track scene state
    private SceneManager sceneManager;

    // Accept the shared SpriteBatch from EndlessRunner.
    public StartMenuScene(SpriteBatch batch) {
        // Create the Stage with a ScreenViewport and the shared batch.
        stage = new Stage(new ScreenViewport(), batch);
        setActive(true); // Activate input processor

        backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));

        try {
            skin = new Skin(Gdx.files.internal("ui/cloud-form-ui.json"));
        } catch (Exception e) {
            skin = new Skin();
        }
        
        float viewportWidth = stage.getViewport().getWorldWidth();
        float viewportHeight = stage.getViewport().getWorldHeight();

        titleLabel = new Label("START MENU", skin);
        //titleLabel.setPosition(Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() - 50);
        titleLabel.setPosition(viewportWidth / 2f - 50, viewportHeight / 2f + 100);

        startButton = new TextButton("Start Game", skin);
        startButton.setSize(200, 50);
        //startButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f + 50);
        startButton.setPosition(viewportWidth / 2f - 100, viewportHeight / 2f + 50);

        exitButton = new TextButton("Exit", skin);
        exitButton.setSize(200, 50);
        //exitButton.setPosition(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 50);
        exitButton.setPosition(viewportWidth / 2f - 100, viewportHeight / 2f - 50);
        
   
   
        
        
       

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disableButtons(); // Disable buttons before switching scenes
                if (sceneManager != null) {
                    sceneManager.setState(SceneManager.GameState.GAMEPLAY);
                }
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disableButtons(); // Disable buttons before exiting
                Gdx.app.exit();
            }
        });

        stage.addActor(titleLabel);
        stage.addActor(startButton);
        stage.addActor(exitButton);
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        startButton.setPosition(stage.getViewport().getWorldWidth() / 2f - 100, stage.getViewport().getWorldHeight() / 2f + 50);
        exitButton.setPosition(stage.getViewport().getWorldWidth() / 2f - 100, stage.getViewport().getWorldHeight() / 2f - 50);
        titleLabel.setPosition(stage.getViewport().getWorldWidth() / 2f - 50, stage.getViewport().getWorldHeight() / 2f + 100);
    }

    // Render the start menu if active.
     
    public void render(SpriteBatch batch) {
        if (active) {
            batch.begin();
            batch.draw(backgroundTexture, 0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
            batch.end();

            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

    // Enable/Disable input processor when entering/exiting scene.
    private void setActive(boolean state) {
        active = state;
        if (active) {
            Gdx.input.setInputProcessor(stage);
        } else {
            Gdx.input.setInputProcessor(null);
        }
    }

    // Remove buttons when transitioning scenes.
   
    private void disableButtons() {
        startButton.remove();
        exitButton.remove();
        setActive(false); // Disable input processor
    }

    public void dispose() {
        disableButtons();
        stage.dispose();
        backgroundTexture.dispose();
        skin.dispose();
    }
}


