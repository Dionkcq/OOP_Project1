package io.github.Proj_Team8.lwjgl3.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundManager implements Disposable {
    private ObjectMap<String, Sound> sounds;
    private Music backgroundMusic;
    private Music gameOverMusic;

    public SoundManager() {
        sounds = new ObjectMap<>();
        loadAllSounds();
    }

    private void loadAllSounds() {
        // Load sound effects
        sounds.put("jump", Gdx.audio.newSound(Gdx.files.internal("sound/jump.wav")));

        // Load background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/background.mp3"));
        backgroundMusic.setLooping(true);

        // Load game over music
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/game-over.wav"));
        gameOverMusic.setLooping(false);
        
        // Load sound effects
        sounds.put("collect", Gdx.audio.newSound(Gdx.files.internal("sound/score.wav")));
    }
    
    public void playSound(String soundkey) {
    	Sound sound = sounds.get(soundkey);
    	sound.play();
    }

    public Sound getSound(String soundKey) {
        return sounds.get(soundKey);
    }

    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public Music getGameOverMusic() {
        return gameOverMusic;
    }

    @Override
    public void dispose() {
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
        sounds.clear();
        backgroundMusic.dispose();
        gameOverMusic.dispose();
    }
    
}
