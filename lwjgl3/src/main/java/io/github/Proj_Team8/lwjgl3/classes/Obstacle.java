package io.github.Proj_Team8.lwjgl3.classes;

public class Obstacle extends Entity implements Trap {
	private static float baseSpeed = 300f; // Base speed for all obstacles

    public Obstacle(float x, float y, float width, float height) {
        super(x, y, width, height, "entity/trap.png");
    }

    @Override
    public void update(float deltaTime) {
        position.x -= baseSpeed * deltaTime;
    }

    public boolean isOutOfScreen() {
        return position.x + width < 0;
    }
}
