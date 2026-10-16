package com.conquertheworld.game.gameplay.combat;

import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttack;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttackFactory;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordType;
import com.conquertheworld.game.gameplay.entity.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CombatDamageResolverTest {
    private final CombatDamageResolver resolver = new CombatDamageResolver();

    @Test
    void appliesAuthoredDamageOnlyOncePerAttackAndTarget() {
        SwordAttack attack = attackAtOrigin();
        TestDamageableEntity target = new TestDamageableEntity(22f, 6f, 100f);

        assertTrue(resolver.resolve(attack, target));
        assertEquals(80f, target.getCurrentHealth());
        assertFalse(resolver.resolve(attack, target));
        assertEquals(80f, target.getCurrentHealth());
    }

    @Test
    void clampsLethalDamageAndIgnoresSeparatedTargets() {
        TestDamageableEntity lethalTarget = new TestDamageableEntity(22f, 6f, 10f);
        assertTrue(resolver.resolve(attackAtOrigin(), lethalTarget));
        assertEquals(0f, lethalTarget.getCurrentHealth());
        assertFalse(lethalTarget.isAlive());

        TestDamageableEntity separated = new TestDamageableEntity(200f, 200f, 100f);
        assertFalse(resolver.resolve(attackAtOrigin(), separated));
        assertEquals(100f, separated.getCurrentHealth());
    }

    private static SwordAttack attackAtOrigin() {
        return SwordAttackFactory.create(SwordType.QUICK, 1, 0, 0f, 0f, 0f);
    }

    private static final class TestDamageableEntity extends Entity implements Damageable {
        private final Health health;

        private TestDamageableEntity(float x, float y, float maximumHealth) {
            super(
                x, y, 0f,
                0f, 0f,
                10f, 5f,
                10f, 5f, 32f,
                32f, 0f,
                null, true
            );
            health = new Health(maximumHealth);
        }

        @Override
        public Health getHealth() {
            return health;
        }
    }
}
