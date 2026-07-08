package com.conquerTheWorld.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface RenderableObject {
    void update(float delta);
    void render(SpriteBatch batch);

    float getGameX();
    float getGameY();
    float getGameZ();

    float getRenderX();
    float getRenderY();

    float getDrawWidth();
    float getDrawHeight();

    float getCollisionWidth();
    float getCollisionDepth();
    float getCollisionHeight();

    float getZHeight();

    boolean isSolid();

    default float getTopZ() {
        return getGameZ() + getZHeight();
    }
}