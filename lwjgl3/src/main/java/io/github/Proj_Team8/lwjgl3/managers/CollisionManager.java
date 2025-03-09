package io.github.Proj_Team8.lwjgl3.managers;

import io.github.Proj_Team8.lwjgl3.classes.Entity;
import io.github.Proj_Team8.lwjgl3.classes.CollisionHandler;
import io.github.Proj_Team8.lwjgl3.classes.Player;
import java.util.List;

public class CollisionManager {
//    public boolean checkCollision(Player player, List<Entity> entities) {
//        for (Entity entity : entities) {
//            if (entity != player && CollisionHandler.checkCollision(player.getBounds(), entity.getBounds())) {
//                return true;
//            }
//        }
//        return false;
//    }
	public Entity getCollidedEntity(Player player, List<Entity> entities) {
	    for (Entity entity : entities) {
	        if (entity != player && CollisionHandler.checkCollision(player.getBounds(), entity.getBounds())) {
	            return entity; 
	        }
	    }
	    return null; 
	}

}
