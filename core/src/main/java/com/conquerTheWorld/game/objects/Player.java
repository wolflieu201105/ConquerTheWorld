package com.conquerTheWorld.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.conquerTheWorld.game.Constants;

public class Player extends Entity {
    private Texture skinTexture;

    private TextureRegion rightIdle1;
    private TextureRegion rightMove1;
    private TextureRegion rightMove2;
    private TextureRegion rightIdle2;

    private TextureRegion leftIdle1;
    private TextureRegion leftMove1;
    private TextureRegion leftMove2;
    private TextureRegion leftIdle2;

    private boolean facingRight = true;
    private boolean moving = false;

    private float animationTimer = 0f;
    private int animationFrame = 0;

    public Player(float x, float y) {
        this(x, y, Constants.DEFAULT_PLAYER_SKIN_PATH);
    }

    public Player(float x, float y, String skinPath) {
        super(
            x,
            y,
            Constants.Z_ENTITY,
            Constants.PLAYER_DRAW_SIZE,
            Constants.PLAYER_DRAW_SIZE,
            null,
            true
        );

        loadSkin(skinPath);
        texture = rightIdle1;
    }

    private void loadSkin(String skinPath) {
        skinTexture = new Texture(skinPath);
        skinTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        int size = Constants.PLAYER_SOURCE_SIZE;

        rightIdle1 = new TextureRegion(skinTexture, 0 * size, 0 * size, size, size);
        rightMove1 = new TextureRegion(skinTexture, 1 * size, 0 * size, size, size);
        rightMove2 = new TextureRegion(skinTexture, 2 * size, 0 * size, size, size);
        rightIdle2 = new TextureRegion(skinTexture, 3 * size, 0 * size, size, size);

        leftIdle1 = new TextureRegion(skinTexture, 0 * size, 1 * size, size, size);
        leftMove1 = new TextureRegion(skinTexture, 1 * size, 1 * size, size, size);
        leftMove2 = new TextureRegion(skinTexture, 2 * size, 1 * size, size, size);
        leftIdle2 = new TextureRegion(skinTexture, 3 * size, 1 * size, size, size);
    }

    @Override
    public void update(float delta) {
        handleInput();
        super.update(delta);
        updateAnimation(delta);
    }

    private void handleInput() {
        velocityX = 0;
        velocityY = 0;
        moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX = -Constants.PLAYER_SPEED;
            facingRight = false;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX = Constants.PLAYER_SPEED;
            facingRight = true;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityY = Constants.PLAYER_SPEED;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityY = -Constants.PLAYER_SPEED;
            moving = true;
        }
    }

    private void updateAnimation(float delta) {
        animationTimer += delta;

        if (animationTimer >= Constants.PLAYER_ANIMATION_FRAME_TIME) {
            animationTimer = 0f;
            animationFrame++;
        }

        if (moving) {
            if (facingRight) {
                texture = animationFrame % 2 == 0 ? rightMove1 : rightMove2;
            } else {
                texture = animationFrame % 2 == 0 ? leftMove1 : leftMove2;
            }
        } else {
            if (facingRight) {
                texture = animationFrame % 2 == 0 ? rightIdle1 : rightIdle2;
            } else {
                texture = animationFrame % 2 == 0 ? leftIdle1 : leftIdle2;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void dispose() {
        skinTexture.dispose();
    }
}