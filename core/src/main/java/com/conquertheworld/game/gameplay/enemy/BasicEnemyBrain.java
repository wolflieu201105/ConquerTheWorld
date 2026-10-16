package com.conquertheworld.game.gameplay.enemy;

/** Deterministic pursuit and melee timing for the first enemy prototype. */
public final class BasicEnemyBrain {
    public static final float ATTACK_RANGE = 44f;
    public static final float ATTACK_INTERVAL_SECONDS = 1f;

    private float moveX;
    private float moveY;
    private float aimAngleDegrees;
    private float attackCooldownSeconds;
    private boolean attackRequested;

    public void update(
        float enemyCenterX,
        float enemyCenterY,
        float targetCenterX,
        float targetCenterY,
        float delta
    ) {
        attackCooldownSeconds = Math.max(0f, attackCooldownSeconds - Math.max(0f, delta));

        float differenceX = targetCenterX - enemyCenterX;
        float differenceY = targetCenterY - enemyCenterY;
        float distanceSquared = differenceX * differenceX + differenceY * differenceY;
        if (distanceSquared > 0.0001f) {
            aimAngleDegrees = normalizeDegrees(
                (float) Math.toDegrees(Math.atan2(differenceY, differenceX))
            );
        }

        if (distanceSquared > ATTACK_RANGE * ATTACK_RANGE) {
            float inverseDistance = 1f / (float) Math.sqrt(distanceSquared);
            moveX = differenceX * inverseDistance;
            moveY = differenceY * inverseDistance;
            return;
        }

        moveX = 0f;
        moveY = 0f;
        if (attackCooldownSeconds <= 0f) {
            attackRequested = true;
            attackCooldownSeconds = ATTACK_INTERVAL_SECONDS;
        }
    }

    public boolean consumeAttackRequested() {
        boolean result = attackRequested;
        attackRequested = false;
        return result;
    }

    public float getMoveX() {
        return moveX;
    }

    public float getMoveY() {
        return moveY;
    }

    public float getAimAngleDegrees() {
        return aimAngleDegrees;
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }
}
