package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.math.Vector2;

public class Movement {
    private Vector2 position; // Stores the position (x, y) of the entity
    private float velocityY; // Vertical velocity for jumping and falling
    private final float GRAVITY = -800f; // Gravity applied to pull the entity down
    private final float JUMP_FORCE = 300f; // Force applied when the entity jumps
    private final float GROUND_Y = 100f; // Ground level (player cannot go below this)

    // Constructor: Initializes the movement with a starting position.
    public Movement(float x, float y) {
        position = new Vector2(x, y);
        velocityY = 0;
    }

    // Applies gravity to the entity, making it fall if in the air.
    public void applyGravity(float deltaTime) {
        velocityY += GRAVITY * deltaTime; // Apply gravity to velocity
        position.y += velocityY * deltaTime; // Move entity downward

        // Prevent entity from falling below the ground
        if (position.y <= GROUND_Y) {
            position.y = GROUND_Y;
            velocityY = 0; // Reset velocity when on the ground
        }
    }

    
    // Returns the current position of the entity.
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    // Returns the current vertical velocity of the entity.
    public float getVelocityY() {
        return velocityY;
    }

    // Sets the vertical velocity of the entity.
    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
    
    public void startJump() {
        velocityY = JUMP_FORCE; // Apply jump force to move entity upwards
    }
}
