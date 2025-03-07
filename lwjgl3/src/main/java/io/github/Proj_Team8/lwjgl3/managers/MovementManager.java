package io.github.Proj_Team8.lwjgl3.managers;

import io.github.Proj_Team8.lwjgl3.classes.Movement;
import io.github.Proj_Team8.lwjgl3.classes.Player;

public class MovementManager {
    private Movement movement;

    public MovementManager(Player player) {
        this.movement = player.getMovement();
    }

    public void update(float deltaTime) {
        movement.applyGravity(deltaTime);
    }

    public void startJump() {
        movement.startJump();
    }
}
