package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HighScore {
    private int currentScore;
    private int highScore;
    private BitmapFont font;

    public HighScore(BitmapFont font) {
        this.font = font;
        currentScore = 0;
        highScore = 0;
    }

    public void updateScore(float deltaTime) {
        currentScore += (int) (deltaTime * 100);
    }

    public void resetScore() {
        currentScore = 0;
    }

    public void checkAndSaveHighScore() {
        if (currentScore > highScore) {
            highScore = currentScore;
            System.out.println("🔥 New High Score: " + highScore);
        }
    }

    public void render(SpriteBatch batch) {
        font.draw(batch, "Score: " + currentScore, 20, 460);
        font.draw(batch, "High Score: " + highScore, 20, 440);
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }
}
