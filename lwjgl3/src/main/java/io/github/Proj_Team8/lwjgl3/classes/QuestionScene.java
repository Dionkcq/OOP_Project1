package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.Proj_Team8.lwjgl3.managers.SceneManager;
import com.badlogic.gdx.utils.Array;

public class QuestionScene {
    private BitmapFont font;
    private Texture backgroundTexture;
    private SceneManager sceneManager;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Virtual resolution for the viewport
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;
    
    // Questions list to hold question-answer pairs
    private Array<String[]> questions;
    private String[] currentQuestion;
    
    // Flag to track if we need to load a new question
    private boolean isAnswerCorrect;

    public QuestionScene(BitmapFont font, SceneManager sceneManager) {
        this.font = font;
        this.sceneManager = sceneManager;
        backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        
    
        // Initialize the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
        
        // Load questions from the file
        questions = loadQuestions();
        // Randomly select the first question
        selectRandomQuestion();
        isAnswerCorrect = false;
    }
    
    // Load questions from the questions.txt file located in the assets folder
    private Array<String[]> loadQuestions() {
        Array<String[]> questionsList = new Array<>();
        FileHandle file = Gdx.files.internal("questions.txt");
        
        // Read each line from the file and split into question-answer pair
        for (String line : file.readString().split("\n")) {
            String[] questionAnswer = line.split(";");
            questionsList.add(questionAnswer);
        }
        
        return questionsList;
    }
    
    // Select a random question from the list
    private void selectRandomQuestion() {
        // Randomly pick a question from the list
        currentQuestion = questions.get(MathUtils.random(questions.size - 1));
        isAnswerCorrect = false;
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
        font.getData().setScale(2f);
        font.draw(batch, "QUESTION TIME!",300,370);
        
        //from questions.text
        font.draw(batch, currentQuestion[0], 150, 310);
        
        font.draw(batch, "True - T",250,245);
        font.draw(batch, "False - F",450,245);
        
        font.draw(batch, "Press ESC to Return To Menu",220,170);
        font.getData().setScale(1f);
        batch.end();
        
        // Check if user pressed T or F
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            // Check if the correct answer is True (with trimming and case-insensitive comparison)
            if (currentQuestion[1].trim().equalsIgnoreCase("True")) {
                // If True is correct, switch to gameplay
            	isAnswerCorrect = true;
                sceneManager.setState(SceneManager.GameState.GAMEPLAY);
                selectRandomQuestion();
            } else {
                // If True is not correct, stay in the question scene or go to game over
                sceneManager.setState(SceneManager.GameState.GAMEOVER);
            }
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            // Check if the correct answer is False (with trimming and case-insensitive comparison)
            if (currentQuestion[1].trim().equalsIgnoreCase("False")) {
                // If False is correct, switch to gameplay
            	isAnswerCorrect = true;
                sceneManager.setState(SceneManager.GameState.GAMEPLAY);
                selectRandomQuestion();
            } else {
                // If False is not correct, stay in the question scene or go to game over
                sceneManager.setState(SceneManager.GameState.GAMEOVER);
            }
        }
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




