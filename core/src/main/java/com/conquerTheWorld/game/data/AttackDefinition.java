package com.conquerTheWorld.game.data;

import com.conquerTheWorld.game.objects.core.Hitbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AttackDefinition {
    private final String id;
    private final float duration;
    private final float cooldown;
    private final float collisionHeight;
    private final float swingStartDegrees;
    private final float swingEndDegrees;
    private final List<Hitbox> hitboxes;

    AttackDefinition(
        String id,
        float duration,
        float cooldown,
        float collisionHeight,
        float swingStartDegrees,
        float swingEndDegrees,
        List<Hitbox> hitboxes
    ) {
        this.id = id;
        this.duration = duration;
        this.cooldown = cooldown;
        this.collisionHeight = collisionHeight;
        this.swingStartDegrees = swingStartDegrees;
        this.swingEndDegrees = swingEndDegrees;
        this.hitboxes = Collections.unmodifiableList(new ArrayList<>(hitboxes));
    }

    public String getId() { return id; }
    public float getDuration() { return duration; }
    public float getCooldown() { return cooldown; }
    public float getCollisionHeight() { return collisionHeight; }
    public float getSwingStartDegrees() { return swingStartDegrees; }
    public float getSwingEndDegrees() { return swingEndDegrees; }
    public List<Hitbox> getHitboxes() { return hitboxes; }
}
