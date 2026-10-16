package com.conquertheworld.game.gameplay.combat.weapon.sword;

import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.content.definition.AttackDefinition;
import com.conquertheworld.game.content.definition.GameDataRegistry;
import com.conquertheworld.game.content.definition.WeaponDefinition;
import com.conquertheworld.game.gameplay.entity.Hitbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Stable sword identities; weapon visuals and attack numbers are loaded from JSON. */
public enum SwordType {
    QUICK("quick_sword"),
    HEAVY("heavy_sword");

    private final String id;

    SwordType(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public int getNetworkId() { return weapon().getNetworkId(); }
    public String getDisplayName() { return weapon().getDisplayName(); }
    public float getDamage() { return attack().getDamage(); }
    public float getDuration() { return attack().getDuration(); }
    public float getCooldown() { return attack().getCooldown(); }
    public float getCollisionHeight() { return attack().getCollisionHeight(); }
    public String getTexturePath() { return weapon().getTexturePath(); }
    public float getDrawWidth() { return weapon().getDrawWidth(); }
    public float getDrawHeight() { return weapon().getDrawHeight(); }
    public float getGripX() { return weapon().getGripX(); }
    public float getGripY() { return weapon().getGripY(); }
    public float getRotationStepDegrees() { return weapon().getRotationStepDegrees(); }

    public float snapAngle(float angleDegrees) {
        float step = getRotationStepDegrees();
        float normalized = normalizeDegrees(angleDegrees);
        return normalizeDegrees(Math.round(normalized / step) * step);
    }

    public float getSwingAngle(float progress) {
        float clamped = Math.max(0f, Math.min(1f, progress));
        return attack().getSwingStartDegrees()
            + (attack().getSwingEndDegrees() - attack().getSwingStartDegrees()) * clamped;
    }

    public List<Hitbox> createHitboxes(boolean facingRight) {
        return createHitboxes(facingRight ? 0f : 180f);
    }

    public List<Hitbox> createHitboxes(float angleDegrees) {
        float snappedAngle = snapAngle(angleDegrees);
        double radians = Math.toRadians(snappedAngle);
        float cosine = (float) Math.cos(radians);
        float sine = (float) Math.sin(radians);
        float pivot = GameConstants.PLAYER_DRAW_SIZE * 0.5f;
        List<Hitbox> base = attack().getHitboxes();
        List<Hitbox> rotated = new ArrayList<>(base.size());
        for (Hitbox hitbox : base) {
            float localX = hitbox.getXOffset() - pivot;
            float localY = hitbox.getYOffset() - pivot;
            rotated.add(new Hitbox(
                pivot + cosine * localX - sine * localY,
                pivot + sine * localX + cosine * localY,
                hitbox.getWidth(),
                hitbox.getHeight(),
                snappedAngle
            ));
        }
        return Collections.unmodifiableList(rotated);
    }

    private WeaponDefinition weapon() {
        return GameDataRegistry.get().getWeapon(id);
    }

    private AttackDefinition attack() {
        return GameDataRegistry.get().getAttack(weapon().getAttackId());
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }

    public static SwordType fromNetworkId(int networkId) {
        for (SwordType type : values()) {
            if (type.getNetworkId() == networkId) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown sword network ID: " + networkId);
    }

    public static SwordType fromConfig(String value) {
        for (SwordType type : values()) {
            if (type.name().equalsIgnoreCase(value)
                || type.id.equalsIgnoreCase(value)
                || type.getDisplayName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
            "Unknown sword '" + value + "'. Expected quick, quick_sword, heavy, or heavy_sword."
        );
    }
}
