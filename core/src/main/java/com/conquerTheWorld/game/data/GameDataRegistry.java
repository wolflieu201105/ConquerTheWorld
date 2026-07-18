package com.conquerTheWorld.game.data;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.conquerTheWorld.game.objects.core.Hitbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/** Loads and validates the four gameplay data sheets once for clients and headless servers. */
public final class GameDataRegistry {
    private static volatile GameDataRegistry instance;

    private final Map<String, CharacterDefinition> characters;
    private final Map<String, SkinDefinition> skins;
    private final Map<String, WeaponDefinition> weapons;
    private final Map<String, AttackDefinition> attacks;

    private GameDataRegistry() {
        characters = Collections.unmodifiableMap(loadCharacters());
        skins = Collections.unmodifiableMap(loadSkins());
        attacks = Collections.unmodifiableMap(loadAttacks());
        weapons = Collections.unmodifiableMap(loadWeapons());
        validateReferences();
    }

    public static GameDataRegistry get() {
        GameDataRegistry result = instance;
        if (result == null) {
            synchronized (GameDataRegistry.class) {
                result = instance;
                if (result == null) {
                    result = new GameDataRegistry();
                    instance = result;
                }
            }
        }
        return result;
    }

    public CharacterDefinition getCharacter(String id) {
        return required(characters, id, "character");
    }

    public SkinDefinition getSkin(String id) {
        return required(skins, id, "skin");
    }

    public WeaponDefinition getWeapon(String id) {
        return required(weapons, id, "weapon");
    }

    public AttackDefinition getAttack(String id) {
        return required(attacks, id, "attack");
    }

    public Map<String, CharacterDefinition> getCharacters() { return characters; }
    public Map<String, SkinDefinition> getSkins() { return skins; }
    public Map<String, WeaponDefinition> getWeapons() { return weapons; }
    public Map<String, AttackDefinition> getAttacks() { return attacks; }

    private Map<String, CharacterDefinition> loadCharacters() {
        Map<String, CharacterDefinition> result = new LinkedHashMap<>();
        for (JsonValue value = array("characters.json", "characters").child;
             value != null;
             value = value.next) {
            String id = id(value.getString("id"));
            putUnique(result, id, new CharacterDefinition(
                id,
                positiveNetworkId(value, "networkId"),
                value.getString("displayName"),
                positive(value, "speed"),
                positive(value, "bodyHeight"),
                hitboxes(value.get("hitboxes")),
                id(value.getString("defaultSkinId")),
                strings(value.get("allowedSkinIds"))
            ), "character");
        }
        validateNetworkIds(result, "character");
        return result;
    }

    private Map<String, SkinDefinition> loadSkins() {
        Map<String, SkinDefinition> result = new LinkedHashMap<>();
        for (JsonValue value = array("skins.json", "skins").child;
             value != null;
             value = value.next) {
            String id = id(value.getString("id"));
            JsonValue tint = requiredArray(value, "tint", 4);
            putUnique(result, id, new SkinDefinition(
                id,
                id(value.getString("characterId")),
                value.getString("texture"),
                positiveInt(value, "frameWidth"),
                positiveInt(value, "frameHeight"),
                tint.getFloat(0),
                tint.getFloat(1),
                tint.getFloat(2),
                tint.getFloat(3)
            ), "skin");
        }
        return result;
    }

    private Map<String, WeaponDefinition> loadWeapons() {
        Map<String, WeaponDefinition> result = new LinkedHashMap<>();
        for (JsonValue value = array("weapons.json", "weapons").child;
             value != null;
             value = value.next) {
            String id = id(value.getString("id"));
            putUnique(result, id, new WeaponDefinition(
                id,
                positiveNetworkId(value, "networkId"),
                value.getString("displayName"),
                id(value.getString("category")),
                value.getString("texture"),
                positive(value, "drawWidth"),
                positive(value, "drawHeight"),
                value.getFloat("gripX"),
                value.getFloat("gripY"),
                id(value.getString("attackId"))
            ), "weapon");
        }
        validateNetworkIds(result, "weapon");
        return result;
    }

    private Map<String, AttackDefinition> loadAttacks() {
        Map<String, AttackDefinition> result = new LinkedHashMap<>();
        for (JsonValue value = array("attacks.json", "attacks").child;
             value != null;
             value = value.next) {
            String id = id(value.getString("id"));
            putUnique(result, id, new AttackDefinition(
                id,
                positive(value, "duration"),
                positive(value, "cooldown"),
                positive(value, "collisionHeight"),
                value.getFloat("swingStartDegrees"),
                value.getFloat("swingEndDegrees"),
                hitboxes(value.get("hitboxes"))
            ), "attack");
        }
        return result;
    }

    private void validateReferences() {
        for (CharacterDefinition character : characters.values()) {
            if (!character.getAllowedSkinIds().contains(character.getDefaultSkinId())) {
                throw new IllegalStateException(
                    character.getId() + " default skin is not in allowedSkinIds"
                );
            }
            for (String skinId : character.getAllowedSkinIds()) {
                SkinDefinition skin = getSkin(skinId);
                if (!character.getId().equals(skin.getCharacterId())) {
                    throw new IllegalStateException(
                        skinId + " belongs to " + skin.getCharacterId()
                            + ", not " + character.getId()
                    );
                }
            }
        }
        for (WeaponDefinition weapon : weapons.values()) {
            getAttack(weapon.getAttackId());
        }
    }

    private JsonValue array(String fileName, String property) {
        JsonValue value = parse(fileName).get(property);
        if (value == null || !value.isArray()) {
            throw new IllegalStateException(fileName + " must contain array '" + property + "'");
        }
        return value;
    }

    private JsonValue parse(String fileName) {
        return new JsonReader().parse(readDataFile(fileName));
    }

    private String readDataFile(String fileName) {
        List<Path> candidates = new ArrayList<>();
        String override = System.getProperty("game.data.dir");
        if (override == null || override.trim().isEmpty()) {
            override = System.getenv("GAME_DATA_DIR");
        }
        if (override != null && !override.trim().isEmpty()) {
            candidates.add(Paths.get(override.trim(), fileName));
        }
        candidates.add(Paths.get("assets", "data", fileName));
        candidates.add(Paths.get("data", fileName));

        for (Path candidate : candidates) {
            if (Files.isRegularFile(candidate)) {
                try {
                    return new String(Files.readAllBytes(candidate), StandardCharsets.UTF_8);
                } catch (IOException exception) {
                    throw new IllegalStateException("Could not read " + candidate, exception);
                }
            }
        }

        InputStream stream = GameDataRegistry.class.getClassLoader()
            .getResourceAsStream("data/" + fileName);
        if (stream != null) {
            try {
                return readStream(stream);
            } catch (IOException exception) {
                throw new IllegalStateException("Could not read classpath data/" + fileName, exception);
            }
        }
        throw new IllegalStateException(
            "Missing " + fileName + ". Set game.data.dir/GAME_DATA_DIR or place it in assets/data."
        );
    }

    private String readStream(InputStream stream) throws IOException {
        try (InputStream input = stream; ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = input.read(buffer)) >= 0) {
                output.write(buffer, 0, read);
            }
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    private List<Hitbox> hitboxes(JsonValue values) {
        if (values == null || !values.isArray() || values.size == 0) {
            throw new IllegalStateException("A hitboxes array must contain at least one rectangle");
        }
        List<Hitbox> result = new ArrayList<>();
        for (JsonValue value = values.child; value != null; value = value.next) {
            result.add(new Hitbox(
                value.getFloat("x"),
                value.getFloat("y"),
                positive(value, "width"),
                positive(value, "height")
            ));
        }
        return result;
    }

    private List<String> strings(JsonValue values) {
        if (values == null || !values.isArray() || values.size == 0) {
            throw new IllegalStateException("ID arrays cannot be empty");
        }
        List<String> result = new ArrayList<>();
        for (JsonValue value = values.child; value != null; value = value.next) {
            result.add(id(value.asString()));
        }
        return result;
    }

    private JsonValue requiredArray(JsonValue parent, String name, int size) {
        JsonValue value = parent.get(name);
        if (value == null || !value.isArray() || value.size != size) {
            throw new IllegalStateException(name + " must contain exactly " + size + " values");
        }
        return value;
    }

    private float positive(JsonValue value, String name) {
        float result = value.getFloat(name);
        if (!(result > 0f) || Float.isInfinite(result) || Float.isNaN(result)) {
            throw new IllegalStateException(name + " must be a positive finite number");
        }
        return result;
    }

    private int positiveInt(JsonValue value, String name) {
        int result = value.getInt(name);
        if (result <= 0) {
            throw new IllegalStateException(name + " must be positive");
        }
        return result;
    }

    private int positiveNetworkId(JsonValue value, String name) {
        int result = positiveInt(value, name);
        if (result > 255) {
            throw new IllegalStateException(name + " must fit in one unsigned protocol byte");
        }
        return result;
    }

    private String id(String value) {
        String result = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
        if (!result.matches("[a-z0-9_]+")) {
            throw new IllegalStateException("Invalid stable ID: '" + value + "'");
        }
        return result;
    }

    private <T> T required(Map<String, T> map, String id, String kind) {
        T result = map.get(this.id(id));
        if (result == null) {
            throw new IllegalArgumentException("Unknown " + kind + " ID: " + id);
        }
        return result;
    }

    private <T> void putUnique(Map<String, T> map, String id, T value, String kind) {
        if (map.put(id, value) != null) {
            throw new IllegalStateException("Duplicate " + kind + " ID: " + id);
        }
    }

    private void validateNetworkIds(Map<String, ?> values, String kind) {
        Set<Integer> ids = new HashSet<>();
        for (Object value : values.values()) {
            int networkId;
            if (value instanceof CharacterDefinition) {
                networkId = ((CharacterDefinition) value).getNetworkId();
            } else {
                networkId = ((WeaponDefinition) value).getNetworkId();
            }
            if (!ids.add(networkId)) {
                throw new IllegalStateException("Duplicate " + kind + " networkId: " + networkId);
            }
        }
    }
}
