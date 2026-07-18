package com.conquerTheWorld.game.objects.characters;

public final class PlayerFactory {
    private PlayerFactory() {
    }

    public static Player create(CharacterType type, float x, float y) {
        if (type == CharacterType.KNIGHT) {
            return new KnightPlayer(x, y);
        }
        if (type == CharacterType.ROGUE) {
            return new RoguePlayer(x, y);
        }
        throw new IllegalArgumentException("Unsupported character type: " + type);
    }
}
