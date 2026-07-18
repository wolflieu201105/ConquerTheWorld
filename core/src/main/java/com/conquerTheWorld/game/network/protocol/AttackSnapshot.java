package com.conquerTheWorld.game.network.protocol;

import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

public final class AttackSnapshot {
    private final int attackId;
    private final int ownerPlayerId;
    private final float x;
    private final float y;
    private final float aimAngleDegrees;
    private final SwordType swordType;

    public AttackSnapshot(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        boolean facingRight
    ) {
        this(
            attackId,
            ownerPlayerId,
            x,
            y,
            facingRight ? 0f : 180f,
            SwordType.QUICK
        );
    }

    public AttackSnapshot(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        boolean facingRight,
        SwordType swordType
    ) {
        this(
            attackId,
            ownerPlayerId,
            x,
            y,
            facingRight ? 0f : 180f,
            swordType
        );
    }

    public AttackSnapshot(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees,
        SwordType swordType
    ) {
        this.attackId = attackId;
        this.ownerPlayerId = ownerPlayerId;
        this.x = x;
        this.y = y;
        this.aimAngleDegrees = normalizeDegrees(aimAngleDegrees);
        this.swordType = swordType;
    }

    public int getAttackId() {
        return attackId;
    }

    public int getOwnerPlayerId() {
        return ownerPlayerId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isFacingRight() {
        return aimAngleDegrees < 90f || aimAngleDegrees > 270f;
    }

    public float getAimAngleDegrees() {
        return aimAngleDegrees;
    }

    public SwordType getSwordType() {
        return swordType;
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }
}
