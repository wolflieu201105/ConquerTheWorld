package com.conquertheworld.game.gameplay.combat;

/** Bounded health value shared by every damageable gameplay entity. */
public final class Health {
    private final float maximum;
    private float current;

    public Health(float maximum) {
        if (!Float.isFinite(maximum) || maximum <= 0f) {
            throw new IllegalArgumentException("Maximum health must be finite and positive");
        }
        this.maximum = maximum;
        current = maximum;
    }

    /** Applies non-negative damage and returns the amount actually removed. */
    public float damage(float amount) {
        validateAmount(amount, "Damage");
        float previous = current;
        current = Math.max(0f, current - amount);
        return previous - current;
    }

    /** Applies non-negative healing and returns the amount actually restored. */
    public float heal(float amount) {
        validateAmount(amount, "Healing");
        float previous = current;
        current = Math.min(maximum, current + amount);
        return current - previous;
    }

    public float getCurrent() {
        return current;
    }

    public float getMaximum() {
        return maximum;
    }

    public boolean isDepleted() {
        return current <= 0f;
    }

    private static void validateAmount(float amount, String name) {
        if (!Float.isFinite(amount) || amount < 0f) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }
}
