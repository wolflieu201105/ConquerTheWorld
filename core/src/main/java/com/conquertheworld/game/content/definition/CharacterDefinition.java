package com.conquertheworld.game.content.definition;

import com.conquertheworld.game.gameplay.entity.Hitbox;
import com.conquertheworld.game.gameplay.combat.weapon.WeaponHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CharacterDefinition {
    private final String id;
    private final int networkId;
    private final String displayName;
    private final float speed;
    private final float bodyHeight;
    private final List<Hitbox> bodyHitboxes;
    private final WeaponHand defaultWeaponHand;
    private final String defaultSkinId;
    private final List<String> allowedSkinIds;

    CharacterDefinition(
        String id,
        int networkId,
        String displayName,
        float speed,
        float bodyHeight,
        List<Hitbox> bodyHitboxes,
        WeaponHand defaultWeaponHand,
        String defaultSkinId,
        List<String> allowedSkinIds
    ) {
        this.id = id;
        this.networkId = networkId;
        this.displayName = displayName;
        this.speed = speed;
        this.bodyHeight = bodyHeight;
        this.bodyHitboxes = Collections.unmodifiableList(new ArrayList<>(bodyHitboxes));
        this.defaultWeaponHand = defaultWeaponHand;
        this.defaultSkinId = defaultSkinId;
        this.allowedSkinIds = Collections.unmodifiableList(new ArrayList<>(allowedSkinIds));
    }

    public String getId() { return id; }
    public int getNetworkId() { return networkId; }
    public String getDisplayName() { return displayName; }
    public float getSpeed() { return speed; }
    public float getBodyHeight() { return bodyHeight; }
    public List<Hitbox> getBodyHitboxes() { return bodyHitboxes; }
    public WeaponHand getDefaultWeaponHand() { return defaultWeaponHand; }
    public String getDefaultSkinId() { return defaultSkinId; }
    public List<String> getAllowedSkinIds() { return allowedSkinIds; }
}
