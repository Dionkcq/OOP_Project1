package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Proj_Team8.lwjgl3.managers.SceneManager;

public class GameOverScene {
    private BitmapFont font;
    private Texture backgroundTexture;
    private SceneManager sceneManager;

    public GameOverScene(BitmapFont font, SceneManager sceneManager) {
        this.font = font;
        this.sceneManager = sceneManager;
        backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
    }
    
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f + 50);
        font.draw(batch, "Press ESC to Return To Menu", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 10);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneManager.resetStartMenu();
            sceneManager.setState(SceneManager.GameState.MENU);
        }
    }

    public void dispose() {
        backgroundTexture.dispose();
        // Do not dispose font here since it is shared.
    }
}
