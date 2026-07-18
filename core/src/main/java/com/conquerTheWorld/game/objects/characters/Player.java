package com.conquerTheWorld.game.objects.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.conquerTheWorld.game.Constants;
import com.conquerTheWorld.game.data.SkinDefinition;
import com.conquerTheWorld.game.objects.core.Entity;
import com.conquerTheWorld.game.objects.core.Prop;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

public abstract class Player extends Entity {
    private Texture skinTexture;
    private final CharacterType characterType;
    private final String skinId;
    private final Color tint;

    private TextureRegion rightIdle1;
    private TextureRegion rightMove1;
    private TextureRegion rightMove2;
    private TextureRegion rightIdle2;

    private TextureRegion leftIdle1;
    private TextureRegion leftMove1;
    private TextureRegion leftMove2;
    private TextureRegion leftIdle2;

    private boolean facingRight = true;
    private float aimAngleDegrees = 0f;
    private SwordType equippedSwordType = SwordType.QUICK;
    private boolean moving = false;
    private boolean attackDown = false;
    private boolean attackPressed = false;

    private float animationTimer = 0f;
    private int animationFrame = 0;

    protected Player(float x, float y, CharacterType characterType) {
        super(
            x,
            y,
            Constants.DEFAULT_GAME_Z,
            0,
            0,
            Constants.PLAYER_DRAW_SIZE,
            Constants.PLAYER_DRAW_SIZE,
            characterType.getBodyHitboxes(),
            characterType.getBodyHeight(),
            characterType.getBodyHeight(),
            characterType.getSpeed(),
            null,
            true
        );

        this.characterType = characterType;
        SkinDefinition skin = characterType.getDefaultSkin();
        skinId = skin.getId();
        tint = new Color(
            skin.getTintR(),
            skin.getTintG(),
            skin.getTintB(),
            skin.getTintA()
        );
        loadSkin(skin);
        texture = rightIdle1;
    }

    private void loadSkin(SkinDefinition skin) {
        skinTexture = new Texture(skin.getTexturePath());
        skinTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        int width = skin.getFrameWidth();
        int height = skin.getFrameHeight();

        rightIdle1 = new TextureRegion(skinTexture, 0 * width, 0, width, height);
        rightMove1 = new TextureRegion(skinTexture, 1 * width, 0, width, height);
        rightMove2 = new TextureRegion(skinTexture, 2 * width, 0, width, height);
        rightIdle2 = new TextureRegion(skinTexture, 3 * width, 0, width, height);

        leftIdle1 = new TextureRegion(skinTexture, 0 * width, height, width, height);
        leftMove1 = new TextureRegion(skinTexture, 1 * width, height, width, height);
        leftMove2 = new TextureRegion(skinTexture, 2 * width, height, width, height);
        leftIdle2 = new TextureRegion(skinTexture, 3 * width, height, width, height);
    }

    @Override
    public void update(float delta) {
        handleInput();
        updateAnimation(delta);
    }

    public void updateRemote(float delta) {
        updateMovementState();
        updateAnimation(delta);
    }

    private void handleInput() {
        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX -= 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveY += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveY -= 1;
        }

        setMovementVector(moveX, moveY);
        updateMovementState();

        boolean newAttackDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT)
            || Gdx.input.isKeyPressed(Input.Keys.SPACE);
        attackPressed = newAttackDown && !attackDown;
        attackDown = newAttackDown;
    }

    public void applyNetworkMovement(float moveX, float moveY) {
        setMovementVector(moveX, moveY);
        updateMovementState();
    }

    public void applyNetworkMovement(float moveX, float moveY, boolean facingRight) {
        applyNetworkMovement(moveX, moveY, facingRight ? 0f : 180f);
    }

    public void applyNetworkMovement(float moveX, float moveY, float aimAngleDegrees) {
        setMovementVector(moveX, moveY);
        moving = movementVector.len2() > 0.0001f;
        setAimAngleDegrees(aimAngleDegrees);
    }

    private void updateMovementState() {
        moving = movementVector.len2() > 0.0001f;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public float getAimAngleDegrees() {
        return aimAngleDegrees;
    }

    public void setAimAngleDegrees(float angleDegrees) {
        if (Float.isNaN(angleDegrees) || Float.isInfinite(angleDegrees)) {
            return;
        }
        aimAngleDegrees = normalizeDegrees(angleDegrees);
        facingRight = aimAngleDegrees < 90f || aimAngleDegrees > 270f;
    }

    public void aimAt(float worldX, float worldY) {
        float centerX = gameX + drawWidth * 0.5f;
        float centerY = gameY + drawHeight * 0.5f;
        float differenceX = worldX - centerX;
        float differenceY = worldY - centerY;
        if (differenceX * differenceX + differenceY * differenceY
            < Constants.AIM_DEAD_ZONE_SQUARED) {
            return;
        }
        setAimAngleDegrees((float) Math.toDegrees(Math.atan2(differenceY, differenceX)));
    }

    public SwordType getEquippedSwordType() {
        return equippedSwordType;
    }

    public void setEquippedSwordType(SwordType equippedSwordType) {
        if (equippedSwordType == null) {
            throw new IllegalArgumentException("Equipped sword type cannot be null");
        }
        this.equippedSwordType = equippedSwordType;
    }

    public boolean isAttackDown() {
        return attackDown;
    }

    public boolean consumeAttackPressed() {
        boolean result = attackPressed;
        attackPressed = false;
        return result;
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
        batch.setColor(tint);
        batch.draw(texture, getRenderX(), getRenderY(), drawWidth, drawHeight);
        batch.setColor(Color.WHITE);
    }

    @Override
    public boolean wallCollide(Prop wall) {
        // The world rolls back the attempted movement on this axis.
        return true;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public String getSkinId() {
        return skinId;
    }

    public void dispose() {
        skinTexture.dispose();
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }
}
