package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Proj_Team8.lwjgl3.managers.EntityManager;

public class GameScene {
    private Texture background;
    private EntityManager entityManager;
    private HighScore highScore;

    public GameScene(EntityManager entityManager, HighScore highScore) {
        this.entityManager = entityManager;
        this.highScore = highScore;
        // Ensure the asset is available
        background = new Texture(Gdx.files.internal("background.jpg"));
    }

    /**
     * Renders the in-game visuals.
     */
    public void render(SpriteBatch batch) {
        batch.begin();
        // Draw the background; if this returns black, check your asset path/name
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        highScore.render(batch);
        entityManager.render(batch);
        batch.end();
    }

    public void dispose() {
        background.dispose();
    }
}
