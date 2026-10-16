package com.conquertheworld.game.gameplay.combat.weapon.sword;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.gameplay.entity.Entity;
import com.conquertheworld.game.gameplay.entity.Hitbox;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

public abstract class SwordAttack extends Entity {
    private final int attackId;
    private final int ownerPlayerId;
    private final float aimAngleDegrees;
    private final SwordType swordType;
    private final Set<Entity> hitTargets = Collections.newSetFromMap(new IdentityHashMap<>());
    private float remainingSeconds;

    protected SwordAttack(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees,
        SwordType swordType
    ) {
        super(
            x,
            y,
            GameConstants.DEFAULT_GAME_Z,
            0f,
            0f,
            0f,
            0f,
            swordType.createHitboxes(aimAngleDegrees),
            swordType.getCollisionHeight(),
            0f,
            0f,
            null,
            false
        );
        this.attackId = attackId;
        this.ownerPlayerId = ownerPlayerId;
        this.aimAngleDegrees = swordType.snapAngle(aimAngleDegrees);
        this.swordType = swordType;
        this.remainingSeconds = swordType.getDuration();
    }

    public static List<Hitbox> createSwordArc(boolean facingRight) {
        return SwordType.QUICK.createHitboxes(facingRight);
    }

    @Override
    public void update(float delta) {
        remainingSeconds -= delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        // Add the stationary swing/effect sprite here later. WeaponRenderer draws only
        // the carried weapon at the owner's current position.
    }

    public int getAttackId() {
        return attackId;
    }

    public int getOwnerPlayerId() {
        return ownerPlayerId;
    }

    public boolean isFacingRight() {
        return aimAngleDegrees < 90f || aimAngleDegrees > 270f;
    }

    public float getAimAngleDegrees() {
        return aimAngleDegrees;
    }

    public float getProgress() {
        return Math.max(0f, Math.min(1f, 1f - remainingSeconds / swordType.getDuration()));
    }

    public float getCarriedWeaponAngleDegrees() {
        return swordType.snapAngle(
            aimAngleDegrees + swordType.getSwingAngle(getProgress())
        );
    }

    public SwordType getSwordType() {
        return swordType;
    }

    public float getDamage() {
        return swordType.getDamage();
    }

    /** Returns true only the first time this attack registers the target. */
    public boolean registerHit(Entity target) {
        if (target == null) {
            throw new IllegalArgumentException("Attack target cannot be null");
        }
        return hitTargets.add(target);
    }

    public boolean isExpired() {
        return remainingSeconds <= 0f;
    }

    @Override
    public final void setGameX(float gameX) {
        throw new UnsupportedOperationException("A sword attack stays at its spawn position");
    }

    @Override
    public final void setGameY(float gameY) {
        throw new UnsupportedOperationException("A sword attack stays at its spawn position");
    }

    @Override
    public final void setGameZ(float gameZ) {
        throw new UnsupportedOperationException("A sword attack stays at its spawn position");
    }
}
