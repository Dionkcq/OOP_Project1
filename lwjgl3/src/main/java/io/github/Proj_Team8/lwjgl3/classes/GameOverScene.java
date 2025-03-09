package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.Proj_Team8.lwjgl3.managers.SceneManager;

public class GameOverScene {
    private BitmapFont font;
    private Texture backgroundTexture;
    private SceneManager sceneManager;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Virtual resolution for the viewport
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;

    public GameOverScene(BitmapFont font, SceneManager sceneManager) {
        this.font = font;
        this.sceneManager = sceneManager;
        backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));

        // Initialize the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }
    
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void render(SpriteBatch batch) {
        // Update camera and set the projection matrix
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Draw the background to fill the entire viewport
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // Draw texts at positions relative to the viewport's virtual dimensions
        font.draw(batch, "GAME OVER", viewport.getWorldWidth() / 2f - 50, viewport.getWorldHeight() / 2f + 50);
        font.draw(batch, "Press ESC to Return To Menu", viewport.getWorldWidth() / 2f - 100, viewport.getWorldHeight() / 2f - 10);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneManager.resetStartMenu();
            sceneManager.setState(SceneManager.GameState.MENU);
        }
    }

    public void dispose() {
        backgroundTexture.dispose();
        // Do not dispose the font here since it is shared.
    }

    // The resize method now updates the viewport instead of relying on Gdx.graphics methods.
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
