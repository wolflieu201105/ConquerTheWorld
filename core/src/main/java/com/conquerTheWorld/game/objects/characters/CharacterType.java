package com.conquerTheWorld.game.objects.characters;

import com.conquerTheWorld.game.data.CharacterDefinition;
import com.conquerTheWorld.game.data.GameDataRegistry;
import com.conquerTheWorld.game.data.SkinDefinition;
import com.conquerTheWorld.game.objects.core.Hitbox;

import java.util.List;

/** Stable code-level character identities whose tunable values come from characters.json. */
public enum CharacterType {
    KNIGHT("knight"),
    ROGUE("rogue");

    private final String id;

    CharacterType(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public int getNetworkId() { return definition().getNetworkId(); }
    public String getDisplayName() { return definition().getDisplayName(); }
    public float getSpeed() { return definition().getSpeed(); }
    public float getBodyHeight() { return definition().getBodyHeight(); }
    public List<Hitbox> getBodyHitboxes() { return definition().getBodyHitboxes(); }
    public String getDefaultSkinId() { return definition().getDefaultSkinId(); }

    public SkinDefinition getDefaultSkin() {
        return GameDataRegistry.get().getSkin(getDefaultSkinId());
    }

    private CharacterDefinition definition() {
        return GameDataRegistry.get().getCharacter(id);
    }

    public static CharacterType fromNetworkId(int networkId) {
        for (CharacterType type : values()) {
            if (type.getNetworkId() == networkId) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown character network ID: " + networkId);
    }

    public static CharacterType fromConfig(String value) {
        for (CharacterType type : values()) {
            if (type.name().equalsIgnoreCase(value)
                || type.id.equalsIgnoreCase(value)
                || type.getDisplayName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
            "Unknown character '" + value + "'. Expected knight or rogue."
        );
    }
}
