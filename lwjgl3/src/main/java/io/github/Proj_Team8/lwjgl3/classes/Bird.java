package io.github.Proj_Team8.lwjgl3.classes;

public class Bird extends Entity implements Trap {
    private static float baseSpeed = 400f; // Base speed of all birds

    public Bird(float x, float y) {
        super(x, y, 50, 30, "entity/bird2.png");
    }

    @Override
    public void update(float deltaTime) {
        position.x -= baseSpeed * deltaTime; // Move left
    }

    public boolean isOutOfScreen() {
        return position.x + width < 0;
    }
    
    public static void increaseSpeed() {
    	baseSpeed *= 1.1f;
    }
}
