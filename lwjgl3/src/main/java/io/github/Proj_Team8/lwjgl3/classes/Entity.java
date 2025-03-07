package io.github.Proj_Team8.lwjgl3.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    protected float width, height;
    protected Texture texture;

    public Entity(float x, float y, float width, float height, String texturePath) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.texture = new Texture(texturePath);
    }

    public abstract void update(float deltaTime);

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
