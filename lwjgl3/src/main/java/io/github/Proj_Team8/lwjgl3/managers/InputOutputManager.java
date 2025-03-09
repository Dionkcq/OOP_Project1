package io.github.Proj_Team8.lwjgl3.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import io.github.Proj_Team8.lwjgl3.classes.Player;

public class InputOutputManager {
    private SoundManager soundManager;

    public InputOutputManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void checkJumpInput(MovementManager movementManager, Player player) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.canJump()) {
                player.performJump();
                soundManager.playSound("jump");
            }
        }
    }
    
    public void scoreSound() {
    	soundManager.playSound("collect");
    }

    public void playBackgroundMusic() {
        soundManager.getBackgroundMusic().play();
    }

    public void stopBackgroundMusic() {
        soundManager.getBackgroundMusic().stop();
    }

    public void playGameOverMusic() {
        stopBackgroundMusic();
        soundManager.getGameOverMusic().play();
    }
}
