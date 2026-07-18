package com.conquerTheWorld.game.world;

public final class CollisionMath {
    private CollisionMath() {
    }

    public static boolean overlaps3D(
        float firstX,
        float firstY,
        float firstZ,
        float firstWidth,
        float firstDepth,
        float firstHeight,
        float secondX,
        float secondY,
        float secondZ,
        float secondWidth,
        float secondDepth,
        float secondHeight
    ) {
        if (firstWidth <= 0f || firstDepth <= 0f || firstHeight <= 0f
            || secondWidth <= 0f || secondDepth <= 0f || secondHeight <= 0f) {
            return false;
        }

        return firstX < secondX + secondWidth
            && firstX + firstWidth > secondX
            && firstY < secondY + secondDepth
            && firstY + firstDepth > secondY
            && firstZ < secondZ + secondHeight
            && firstZ + firstHeight > secondZ;
    }
}
