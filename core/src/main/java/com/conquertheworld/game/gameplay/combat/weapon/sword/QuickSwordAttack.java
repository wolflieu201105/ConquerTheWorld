package com.conquertheworld.game.gameplay.combat.weapon.sword;

public final class QuickSwordAttack extends SwordAttack {
    public QuickSwordAttack(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees
    ) {
        super(attackId, ownerPlayerId, x, y, aimAngleDegrees, SwordType.QUICK);
    }
}
