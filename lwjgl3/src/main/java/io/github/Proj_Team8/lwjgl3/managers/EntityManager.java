package io.github.Proj_Team8.lwjgl3.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Proj_Team8.lwjgl3.classes.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EntityManager {
    private List<Entity> entities;
    private Player player;
    private float spawnTimer; // Timer to track obstacle spawning
    private final float SPAWN_INTERVAL = 1f; // Time interval for spawning obstacles
    private Random random; // Random generator for spawning obstacles

  
     // Creates a player and adds it to the entity list.
    public EntityManager() {
        entities = new ArrayList<>();
        player = new Player();
        entities.add(player);
        spawnTimer = 0;
        random = new Random();
    }

     // Updates all entities in the game, including spawning obstacles.
     // Called every frame.
    public void update(float deltaTime) {
        spawnTimer += deltaTime;

        // Spawn a new obstacle if the timer exceeds the interval
        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnObstacle();  
            spawnHealthyFood(); 
            spawnTimer = 0;
        }

        // Iterate through entities and update them
        Iterator<Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            entity.update(deltaTime);

            // Remove obstacles that move off-screen
            if (entity instanceof Obstacle && ((Obstacle) entity).isOutOfScreen()) {
                iter.remove();
            }
        }
    }

   
    // Spawns an obstacle (either a ground trap or a flying bird).
    // Uses random logic to determine the type.
    private void spawnObstacle() {
        float spawnX = 800; // Start obstacles off-screen to the right

        if (random.nextBoolean()) { // 50% chance of spawning a bird or a trap
            // Spawn Trap
            float spawnY = 75; // Ground level
            float minTrapHeight = 50 * 1.2f;
            float maxTrapHeight = 50 * 2.0f;
            float height = random.nextFloat() * (maxTrapHeight - minTrapHeight) + minTrapHeight;
            float width = 30 + random.nextFloat() * 20;

            entities.add(new Obstacle(spawnX, spawnY, width, height));
        } else {
            // Spawn Bird
            float spawnY = random.nextFloat() * (100) + 150; // Random height in the sky
            entities.add(new Bird(spawnX, spawnY));
        }
    }
    
    private void spawnHealthyFood() {
        float spawnX = 800;  

        if (random.nextBoolean()) { 

<<<<<<< HEAD
            if (random.nextBoolean()) { 
                spawnY = 75; 
            } else {
                spawnY = random.nextFloat() * (25) + 250;
            }
=======
            float spawnY = 75;
            float width = 30 + random.nextFloat() * 20;
            float height = random.nextFloat() * (100 - 50) + 50;
>>>>>>> parent of 59665ea (update sound, duplicate bug)

            entities.add(new HealthyFood1(spawnX, spawnY, width, height));
        } else {
  
            float spawnY = random.nextFloat() * (250 - 150) + 150; 
            entities.add(new HealthyFood2(spawnX, spawnY));
        }
    }


<<<<<<< HEAD
=======


>>>>>>> parent of 59665ea (update sound, duplicate bug)
    public Player getPlayer() {
        return player;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void render(SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.render(batch);
        }
    }

    public void dispose() {
        for (Entity entity : entities) {
            entity.dispose();
        }
        entities.clear();
    }
    
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

}
