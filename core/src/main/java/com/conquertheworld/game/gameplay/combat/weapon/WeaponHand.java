package com.conquertheworld.game.gameplay.combat.weapon;

/** The hand used to carry a weapon and its mirrored player-layer relationship. */
public enum WeaponHand {
    LEFT,
    RIGHT;

    /**
     * Returns true when the weapon belongs behind the player sprite for this direction.
     * The relationship mirrors when the player turns around.
     */
    public boolean rendersBeforePlayer(boolean facingRight) {
        return this == LEFT ? facingRight : !facingRight;
    }

    public static WeaponHand fromData(String value) {
        for (WeaponHand hand : values()) {
            if (hand.name().equalsIgnoreCase(value)) {
                return hand;
            }
        }
        throw new IllegalArgumentException(
            "Unknown weapon hand '" + value + "'. Expected left or right."
        );
    }
}
