package com.conquertheworld.game.gameplay.combat;

/** Contract for an entity with bounded health that can receive combat damage. */
public interface Damageable {
    Health getHealth();

    default float takeDamage(float amount) {
        return getHealth().damage(amount);
    }

    default float getCurrentHealth() {
        return getHealth().getCurrent();
    }

    default float getMaximumHealth() {
        return getHealth().getMaximum();
    }

    default boolean isAlive() {
        return !getHealth().isDepleted();
    }
}
