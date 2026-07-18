package com.conquerTheWorld.game.network.protocol;

import com.conquerTheWorld.game.objects.characters.CharacterType;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

public final class PlayerSnapshot {
    private final int playerId;
    private final float x;
    private final float y;
    private final float moveX;
    private final float moveY;
    private final float aimAngleDegrees;
    private final CharacterType characterType;
    private final SwordType swordType;

    public PlayerSnapshot(int playerId, float x, float y, float moveX, float moveY) {
        this(
            playerId, x, y, moveX, moveY,
            moveX < 0f ? 180f : 0f,
            CharacterType.KNIGHT,
            SwordType.QUICK
        );
    }

    public PlayerSnapshot(
        int playerId,
        float x,
        float y,
        float moveX,
        float moveY,
        boolean facingRight
    ) {
        this(
            playerId, x, y, moveX, moveY,
            facingRight ? 0f : 180f,
            CharacterType.KNIGHT,
            SwordType.QUICK
        );
    }

    public PlayerSnapshot(
        int playerId,
        float x,
        float y,
        float moveX,
        float moveY,
        boolean facingRight,
        CharacterType characterType
    ) {
        this(
            playerId, x, y, moveX, moveY,
            facingRight ? 0f : 180f,
            characterType,
            SwordType.QUICK
        );
    }

    public PlayerSnapshot(
        int playerId,
        float x,
        float y,
        float moveX,
        float moveY,
        float aimAngleDegrees,
        CharacterType characterType,
        SwordType swordType
    ) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.moveX = moveX;
        this.moveY = moveY;
        this.aimAngleDegrees = normalizeDegrees(aimAngleDegrees);
        this.characterType = characterType;
        this.swordType = swordType;
    }

    public int getPlayerId() {
        return playerId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getMoveX() {
        return moveX;
    }

    public float getMoveY() {
        return moveY;
    }

    public boolean isFacingRight() {
        return aimAngleDegrees < 90f || aimAngleDegrees > 270f;
    }

    public float getAimAngleDegrees() {
        return aimAngleDegrees;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public SwordType getSwordType() {
        return swordType;
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }
}
