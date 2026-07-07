package com.conquerTheWorld.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface RenderableObject {
    void update(float delta);
    void render(SpriteBatch batch);

    float getX();
    float getY();
    float getZ();

    float getWidth();
    float getHeight();

    boolean isSolid();
}