package com.conquerTheWorld.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Prop implements RenderableObject {
    protected float x;
    protected float y;
    protected float z;

    protected float width;
    protected float height;

    protected TextureRegion texture;
    protected boolean solid;

    public Prop(
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
        // Props do not move.
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
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