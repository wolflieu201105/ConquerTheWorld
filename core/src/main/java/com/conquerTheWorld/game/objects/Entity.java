package com.conquerTheWorld.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Entity implements RenderableObject {
    protected float gameX;
    protected float gameY;
    protected float gameZ;

    protected float renderOffsetX;
    protected float renderOffsetY;

    protected float drawWidth;
    protected float drawHeight;

    protected float collisionWidth;
    protected float collisionDepth;
    protected float collisionHeight;

    protected float zHeight;

    protected float velocity;

    protected Vector2 movementVector;

    protected TextureRegion texture;
    protected boolean solid;

    public Entity(
        float gameX,
        float gameY,
        float gameZ,
        float renderOffsetX,
        float renderOffsetY,
        float drawWidth,
        float drawHeight,
        float collisionWidth,
        float collisionDepth,
        float collisionHeight,
        float zHeight,
        float velocity,
        TextureRegion texture,
        boolean solid
    ) {
        this.gameX = gameX;
        this.gameY = gameY;
        this.gameZ = gameZ;
        this.renderOffsetX = renderOffsetX;
        this.renderOffsetY = renderOffsetY;
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
        this.collisionWidth = collisionWidth;
        this.collisionDepth = collisionDepth;
        this.collisionHeight = collisionHeight;
        this.zHeight = zHeight;
        this.velocity = velocity;
        this.texture = texture;
        this.solid = solid;
        this.movementVector = new Vector2();
    }

    @Override
    public void update(float delta) {
        // Entity decides intent. World applies movement/collision.
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, getRenderX(), getRenderY(), drawWidth, drawHeight);
    }

    public float getMoveX(float delta) {
        return movementVector.x * velocity * delta;
    }

    public float getMoveY(float delta) {
        return movementVector.y * velocity * delta;
    }

    public void setMovementVector(float x, float y) {
        movementVector.set(x, y);

        if (movementVector.len2() > 1f) {
            movementVector.nor();
        }
    }

    public Vector2 getMovementVector() {
        return movementVector;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public void setGameX(float gameX) {
        this.gameX = gameX;
    }

    public void setGameY(float gameY) {
        this.gameY = gameY;
    }

    public void setGameZ(float gameZ) {
        this.gameZ = gameZ;
    }

    @Override
    public float getGameX() {
        return gameX;
    }

    @Override
    public float getGameY() {
        return gameY;
    }

    @Override
    public float getGameZ() {
        return gameZ;
    }

    @Override
    public float getRenderX() {
        return gameX + renderOffsetX;
    }

    @Override
    public float getRenderY() {
        return gameY + renderOffsetY;
    }

    @Override
    public float getDrawWidth() {
        return drawWidth;
    }

    @Override
    public float getDrawHeight() {
        return drawHeight;
    }

    @Override
    public float getCollisionWidth() {
        return collisionWidth;
    }

    @Override
    public float getCollisionDepth() {
        return collisionDepth;
    }

    @Override
    public float getCollisionHeight() {
        return collisionHeight;
    }

    @Override
    public float getZHeight() {
        return zHeight;
    }

    @Override
    public boolean isSolid() {
        return solid;
    }
}