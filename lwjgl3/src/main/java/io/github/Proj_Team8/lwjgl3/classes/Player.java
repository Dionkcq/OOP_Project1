package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {
    private Movement movement;
    private Texture[] playerFrames;
    private float animationTimer;
    private int currentFrame;
    private final float FRAME_DURATION = 0.1f; // Change frame every 0.1 seconds
    private int jumpCount;
    private final int MAX_JUMPS = 3; // Limit to 3 jumps

    public Player() {
        super(100, 100, 50, 50, "entity/player.png"); // Default texture, updated path
        this.movement = new Movement(position.x, position.y);
        this.jumpCount = 0; // Initialize jump counter

        // Load all frames for animation
        playerFrames = new Texture[]{
            new Texture(Gdx.files.internal("entity/player.png")),
            new Texture(Gdx.files.internal("entity/player2.png")),
            new Texture(Gdx.files.internal("entity/player3.png")),
            new Texture(Gdx.files.internal("entity/player4.png")),
            new Texture(Gdx.files.internal("entity/player5.png")),
            new Texture(Gdx.files.internal("entity/player6.png"))
        };

        animationTimer = 0;
        currentFrame = 0;
    }

    @Override
    public void update(float deltaTime) {
        movement.applyGravity(deltaTime);
        this.position.set(movement.getPosition());

        // Reset jump count when player lands
        if (position.y <= 100) { 
            jumpCount = 0;
        }

        // Update animation timer
        animationTimer += deltaTime;
        if (animationTimer >= FRAME_DURATION) {
            animationTimer = 0;
            currentFrame = (currentFrame + 1) % playerFrames.length; // Loop frames
        }
    }

    public boolean canJump() {
        return jumpCount < MAX_JUMPS; // Allow jumping only if under the limit
    }

    public void performJump() {
        if (canJump()) {
            movement.startJump();
            jumpCount++; // Increment jump count on jump
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(playerFrames[currentFrame], position.x, position.y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        float hitboxWidth = width * 0.7f;  // Reduce hitbox width (70% of the sprite)
        float hitboxHeight = height * 0.7f; // Reduce hitbox height (70% of the sprite)
        float offsetX = (width - hitboxWidth) / 2;  // Center hitbox horizontally
        float offsetY = (height - hitboxHeight) / 2; // Center hitbox vertically

        return new Rectangle(position.x + offsetX, position.y + offsetY, hitboxWidth, hitboxHeight);
    }

    public Movement getMovement() {
        return movement;
    }

    @Override
    public void dispose() {
        for (Texture frame : playerFrames) {
            frame.dispose();
        }
    }
}
