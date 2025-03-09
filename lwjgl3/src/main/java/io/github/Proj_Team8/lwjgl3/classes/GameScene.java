package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.Proj_Team8.lwjgl3.managers.EntityManager;

public class GameScene {
    private Texture background;
    private EntityManager entityManager;
    private HighScore highScore;
    private OrthographicCamera camera;
    private Viewport viewport;

    // Virtual resolution for the viewport
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;

    public GameScene(EntityManager entityManager, HighScore highScore) {
        this.entityManager = entityManager;
        this.highScore = highScore;
        // Ensure the asset is available
        background = new Texture(Gdx.files.internal("background.jpg"));
        
        // Initialize the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
        

    }

    /**
     * Renders the in-game visuals.
     */
    public void render(SpriteBatch batch) {
        // Update camera and set the projection matrix
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Draw the background; if this returns black, check your asset path/name
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        highScore.render(batch);
        entityManager.render(batch);
        batch.end();
    }

    public void dispose() {
        background.dispose();
    }

	public void resize(int width, int height) {
		viewport.update(width, height, true);
		
	}
}
