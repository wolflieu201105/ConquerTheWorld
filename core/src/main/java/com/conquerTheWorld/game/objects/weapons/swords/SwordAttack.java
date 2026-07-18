package com.conquerTheWorld.game.objects.weapons.swords;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.conquerTheWorld.game.Constants;
import com.conquerTheWorld.game.objects.core.Entity;
import com.conquerTheWorld.game.objects.core.Hitbox;

import java.util.List;

public abstract class SwordAttack extends Entity {
    private final int attackId;
    private final int ownerPlayerId;
    private final float aimAngleDegrees;
    private final SwordType swordType;
    private float remainingSeconds;

    protected SwordAttack(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees,
        SwordType swordType
    ) {
        super(
            x,
            y,
            Constants.DEFAULT_GAME_Z,
            0f,
            0f,
            0f,
            0f,
            swordType.createHitboxes(aimAngleDegrees + swordType.getSwingAngle(0f)),
            swordType.getCollisionHeight(),
            0f,
            0f,
            null,
            false
        );
        this.attackId = attackId;
        this.ownerPlayerId = ownerPlayerId;
        this.aimAngleDegrees = normalizeDegrees(aimAngleDegrees);
        this.swordType = swordType;
        this.remainingSeconds = swordType.getDuration();
    }

    public static List<Hitbox> createSwordArc(boolean facingRight) {
        return SwordType.QUICK.createHitboxes(facingRight);
    }

    @Override
    public void update(float delta) {
        remainingSeconds -= delta;
        setHitboxes(swordType.createHitboxes(getRenderAngleDegrees()));
    }

    @Override
    public void render(SpriteBatch batch) {
        // WeaponRenderer draws the sword; Main optionally draws debug hitboxes.
    }

    public int getAttackId() {
        return attackId;
    }

    public int getOwnerPlayerId() {
        return ownerPlayerId;
    }

    public boolean isFacingRight() {
        return aimAngleDegrees < 90f || aimAngleDegrees > 270f;
    }

    public float getAimAngleDegrees() {
        return aimAngleDegrees;
    }

    public float getProgress() {
        return Math.max(0f, Math.min(1f, 1f - remainingSeconds / swordType.getDuration()));
    }

    public float getRenderAngleDegrees() {
        return normalizeDegrees(aimAngleDegrees + swordType.getSwingAngle(getProgress()));
    }

    public SwordType getSwordType() {
        return swordType;
    }

    public boolean isExpired() {
        return remainingSeconds <= 0f;
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }
}
