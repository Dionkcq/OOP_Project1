package io.github.Proj_Team8.lwjgl3.classes;

public class HealthyFood2 extends Entity{
	 private static float baseSpeed = 400f; // Base speed of all birds

	    public HealthyFood2(float x, float y) {
	        super(x, y, 50, 50, "entity/health_food2.png");
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
