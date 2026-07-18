package com.conquerTheWorld.game.objects.weapons.swords;

public final class HeavySwordAttack extends SwordAttack {
    public HeavySwordAttack(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees
    ) {
        super(attackId, ownerPlayerId, x, y, aimAngleDegrees, SwordType.HEAVY);
    }
}
