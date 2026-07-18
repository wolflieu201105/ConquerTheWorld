package com.conquerTheWorld.game.data;

public final class SkinDefinition {
    private final String id;
    private final String characterId;
    private final String texturePath;
    private final int frameWidth;
    private final int frameHeight;
    private final float tintR;
    private final float tintG;
    private final float tintB;
    private final float tintA;

    SkinDefinition(
        String id,
        String characterId,
        String texturePath,
        int frameWidth,
        int frameHeight,
        float tintR,
        float tintG,
        float tintB,
        float tintA
    ) {
        this.id = id;
        this.characterId = characterId;
        this.texturePath = texturePath;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.tintR = tintR;
        this.tintG = tintG;
        this.tintB = tintB;
        this.tintA = tintA;
    }

    public String getId() { return id; }
    public String getCharacterId() { return characterId; }
    public String getTexturePath() { return texturePath; }
    public int getFrameWidth() { return frameWidth; }
    public int getFrameHeight() { return frameHeight; }
    public float getTintR() { return tintR; }
    public float getTintG() { return tintG; }
    public float getTintB() { return tintB; }
    public float getTintA() { return tintA; }
}
