package com.conquerTheWorld.game.objects.weapons.swords;

public final class SwordAttackFactory {
    private SwordAttackFactory() {
    }

    public static SwordAttack create(
        SwordType type,
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        boolean facingRight
    ) {
        return create(
            type,
            attackId,
            ownerPlayerId,
            x,
            y,
            facingRight ? 0f : 180f
        );
    }

    public static SwordAttack create(
        SwordType type,
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees
    ) {
        if (type == SwordType.QUICK) {
            return new QuickSwordAttack(attackId, ownerPlayerId, x, y, aimAngleDegrees);
        }
        if (type == SwordType.HEAVY) {
            return new HeavySwordAttack(attackId, ownerPlayerId, x, y, aimAngleDegrees);
        }
        throw new IllegalArgumentException("Unsupported sword type: " + type);
    }
}
