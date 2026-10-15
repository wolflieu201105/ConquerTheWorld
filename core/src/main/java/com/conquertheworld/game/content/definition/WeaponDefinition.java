package com.conquertheworld.game.content.definition;

public final class WeaponDefinition {
    private final String id;
    private final int networkId;
    private final String displayName;
    private final String category;
    private final String texturePath;
    private final float drawWidth;
    private final float drawHeight;
    private final float gripX;
    private final float gripY;
    private final float rotationStepDegrees;
    private final String attackId;

    WeaponDefinition(
        String id,
        int networkId,
        String displayName,
        String category,
        String texturePath,
        float drawWidth,
        float drawHeight,
        float gripX,
        float gripY,
        float rotationStepDegrees,
        String attackId
    ) {
        this.id = id;
        this.networkId = networkId;
        this.displayName = displayName;
        this.category = category;
        this.texturePath = texturePath;
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
        this.gripX = gripX;
        this.gripY = gripY;
        this.rotationStepDegrees = rotationStepDegrees;
        this.attackId = attackId;
    }

    public String getId() { return id; }
    public int getNetworkId() { return networkId; }
    public String getDisplayName() { return displayName; }
    public String getCategory() { return category; }
    public String getTexturePath() { return texturePath; }
    public float getDrawWidth() { return drawWidth; }
    public float getDrawHeight() { return drawHeight; }
    public float getGripX() { return gripX; }
    public float getGripY() { return gripY; }
    public float getRotationStepDegrees() { return rotationStepDegrees; }
    public String getAttackId() { return attackId; }
}
