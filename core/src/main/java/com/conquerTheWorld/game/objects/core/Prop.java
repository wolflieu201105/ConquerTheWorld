package com.conquerTheWorld.game.objects.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Prop implements RenderableObject {
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

    protected TextureRegion texture;
    protected boolean solid;

    public Prop(
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
        this.texture = texture;
        this.solid = solid;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, getRenderX(), getRenderY(), drawWidth, drawHeight);
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