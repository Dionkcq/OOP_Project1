package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.math.Rectangle;

public class CollisionHandler {
    public static boolean checkCollision(Rectangle a, Rectangle b) {
        return a.overlaps(b);
    }
}
