package com.conquerTheWorld.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Entity implements RenderableObject {
    protected float x;
    protected float y;
    protected float z;

    protected float width;
    protected float height;

    protected float velocityX;
    protected float velocityY;

    protected TextureRegion texture;
    protected boolean solid;

    public Entity(
        float x,
        float y,
        float z,
        float width,
        float height,
        TextureRegion texture,
        boolean solid
    ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.solid = solid;
    }

    @Override
    public void update(float delta) {
        x += velocityX * delta;
        y += velocityY * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isSolid() {
        return solid;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}