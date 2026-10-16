package com.conquertheworld.game.gameplay.combat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HealthTest {
    @Test
    void damageAndHealingStayWithinBounds() {
        Health health = new Health(100f);

        assertEquals(30f, health.damage(30f));
        assertEquals(70f, health.getCurrent());
        assertFalse(health.isDepleted());

        assertEquals(70f, health.damage(500f));
        assertEquals(0f, health.getCurrent());
        assertTrue(health.isDepleted());
        assertEquals(0f, health.damage(10f));

        assertEquals(100f, health.heal(500f));
        assertEquals(100f, health.getCurrent());
        assertFalse(health.isDepleted());
    }

    @Test
    void rejectsInvalidMaximumAndAmounts() {
        assertThrows(IllegalArgumentException.class, () -> new Health(0f));
        assertThrows(IllegalArgumentException.class, () -> new Health(Float.NaN));

        Health health = new Health(10f);
        assertThrows(IllegalArgumentException.class, () -> health.damage(-1f));
        assertThrows(IllegalArgumentException.class, () -> health.heal(Float.POSITIVE_INFINITY));
    }
}
