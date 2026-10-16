package com.conquertheworld.game.gameplay.combat;

import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttack;
import com.conquertheworld.game.gameplay.entity.Entity;
import com.conquertheworld.game.gameplay.entity.HitboxCollision;

/** Applies one authored attack hit to a living, overlapping damageable entity. */
public final class CombatDamageResolver {
    public boolean resolve(SwordAttack attack, Entity target) {
        if (attack == null) {
            throw new IllegalArgumentException("attack cannot be null");
        }
        if (!(target instanceof Damageable damageable)
            || !damageable.isAlive()
            || !HitboxCollision.overlaps(attack, target)
            || !attack.registerHit(target)) {
            return false;
        }

        damageable.takeDamage(attack.getDamage());
        return true;
    }
}
