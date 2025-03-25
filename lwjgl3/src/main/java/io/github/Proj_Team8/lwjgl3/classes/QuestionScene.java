package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.Proj_Team8.lwjgl3.EndlessRunner;
import io.github.Proj_Team8.lwjgl3.managers.SceneManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Align;

public class QuestionScene {
    private BitmapFont font;
    private Texture backgroundTexture;
    private SceneManager sceneManager;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Virtual resolution for the viewport
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 600;
    
    // Text wrapping constants
    private static final float QUESTION_TEXT_WIDTH = 500f; // Maximum width for wrapped text
    private static final float QUESTION_Y_POSITION = 330f; // Starting Y position for questions
    
    // Questions list to hold question-answer pairs
    private Array<String[]> questions;
    private String[] currentQuestion;
    private int previousQuestionIndex = -1; // Track the last question index to avoid repetition
    
    // Flag to track if we need to load a new question
    private boolean isAnswerCorrect;
    
    // For measuring text
    private GlyphLayout glyphLayout;

    public QuestionScene(BitmapFont font, SceneManager sceneManager) {
        this.font = font;
        this.sceneManager = sceneManager;
        backgroundTexture = new Texture(Gdx.files.internal("background2.jpg"));
        
        // Initialize the glyph layout for text measurement
        this.glyphLayout = new GlyphLayout();
    
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
        // Don't select the same question twice in a row
        if (questions.size <= 1) {
            // If there's only one question, we have no choice
            currentQuestion = questions.get(0);
        } else {
            // Select a different question than the previous one
            int newIndex;
            do {
                newIndex = MathUtils.random(questions.size - 1);
            } while (newIndex == previousQuestionIndex);
            
            currentQuestion = questions.get(newIndex);
            previousQuestionIndex = newIndex; // Remember this question index
        }
        isAnswerCorrect = false;
    }
    
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void render(SpriteBatch batch) {
        // Check if we need a new question when entering this scene
        if (Gdx.input.justTouched() && !Gdx.input.isKeyJustPressed(Input.Keys.T) && 
            !Gdx.input.isKeyJustPressed(Input.Keys.F) && !Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            // This ensures we get a new question when returning to this scene
            selectRandomQuestion();
        }
        
        // Update camera and set the projection matrix
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Draw the background to fill the entire viewport
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        
        // Draw texts at positions relative to the viewport's virtual dimensions
        font.getData().setScale(2f);
        font.draw(batch, "QUESTION TIME!", 300, 370);
        
        // Draw the question with text wrapping
        font.getData().setScale(1.6f);  // Slightly smaller for questions to fit better
        
        // Use GlyphLayout to draw wrapped text
        // The wrapped text is centered with Align.center
        glyphLayout.setText(font, currentQuestion[0], font.getColor(), QUESTION_TEXT_WIDTH, Align.center, true);
        float textX = (viewport.getWorldWidth() - QUESTION_TEXT_WIDTH) / 2f;
        font.draw(batch, glyphLayout, textX, QUESTION_Y_POSITION);
        
        // Reset scale for options
        font.getData().setScale(2f);
        float optionsY = QUESTION_Y_POSITION - glyphLayout.height - 30; // Position options below the question
        font.draw(batch, "True - T", 250, optionsY);
        font.draw(batch, "False - F", 450, optionsY);
        
        font.draw(batch, "Press ESC to Return To Menu", 220, 170);
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
                ((EndlessRunner) Gdx.app.getApplicationListener()).resetGame();
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
                ((EndlessRunner) Gdx.app.getApplicationListener()).resetGame();
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