package com.conquerTheWorld.game.objects.core;

public final class HitboxCollision {
    private HitboxCollision() {
    }

    public static boolean overlaps(Entity entity, Prop prop) {
        for (Hitbox hitbox : entity.getHitboxes()) {
            Hitbox propHitbox = new Hitbox(
                0f,
                0f,
                prop.getCollisionWidth(),
                prop.getCollisionDepth()
            );
            if (overlaps(
                entity.getGameX(), entity.getGameY(), entity.getGameZ(),
                entity.getCollisionHeight(), hitbox,
                prop.getGameX(), prop.getGameY(), prop.getGameZ(),
                prop.getCollisionHeight(), propHitbox
            )) {
                return true;
            }
        }
        return false;
    }

    public static boolean overlaps(Entity first, Entity second) {
        for (Hitbox firstHitbox : first.getHitboxes()) {
            for (Hitbox secondHitbox : second.getHitboxes()) {
                if (overlaps(
                    first.getGameX(), first.getGameY(), first.getGameZ(),
                    first.getCollisionHeight(), firstHitbox,
                    second.getGameX(), second.getGameY(), second.getGameZ(),
                    second.getCollisionHeight(), secondHitbox
                )) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean overlaps(
        float firstEntityX,
        float firstEntityY,
        float firstZ,
        float firstHeight,
        Hitbox first,
        float secondEntityX,
        float secondEntityY,
        float secondZ,
        float secondHeight,
        Hitbox second
    ) {
        if (!overlapsZ(firstZ, firstHeight, secondZ, secondHeight)) {
            return false;
        }

        // Separating Axis Theorem for two oriented rectangles. For unrotated body/wall
        // rectangles this produces the same answer as the previous AABB test.
        float[][] firstCorners = worldCorners(firstEntityX, firstEntityY, first);
        float[][] secondCorners = worldCorners(secondEntityX, secondEntityY, second);
        return overlapsOnAxes(firstCorners, secondCorners)
            && overlapsOnAxes(secondCorners, firstCorners);
    }

    private static boolean overlapsZ(
        float firstZ,
        float firstHeight,
        float secondZ,
        float secondHeight
    ) {
        return firstHeight > 0f && secondHeight > 0f
            && firstZ < secondZ + secondHeight
            && firstZ + firstHeight > secondZ;
    }

    private static float[][] worldCorners(float entityX, float entityY, Hitbox hitbox) {
        double radians = Math.toRadians(hitbox.getRotationDegrees());
        float cosine = (float) Math.cos(radians);
        float sine = (float) Math.sin(radians);
        float startX = entityX + hitbox.getXOffset();
        float startY = entityY + hitbox.getYOffset();
        float widthX = cosine * hitbox.getWidth();
        float widthY = sine * hitbox.getWidth();
        float heightX = -sine * hitbox.getHeight();
        float heightY = cosine * hitbox.getHeight();
        return new float[][] {
            {startX, startY},
            {startX + widthX, startY + widthY},
            {startX + widthX + heightX, startY + widthY + heightY},
            {startX + heightX, startY + heightY}
        };
    }

    private static boolean overlapsOnAxes(float[][] axesFrom, float[][] other) {
        for (int edge = 0; edge < 2; edge++) {
            float edgeX = axesFrom[edge + 1][0] - axesFrom[edge][0];
            float edgeY = axesFrom[edge + 1][1] - axesFrom[edge][1];
            float axisX = -edgeY;
            float axisY = edgeX;

            float firstMinimum = minimumProjection(axesFrom, axisX, axisY);
            float firstMaximum = maximumProjection(axesFrom, axisX, axisY);
            float secondMinimum = minimumProjection(other, axisX, axisY);
            float secondMaximum = maximumProjection(other, axisX, axisY);
            if (firstMaximum <= secondMinimum || secondMaximum <= firstMinimum) {
                return false;
            }
        }
        return true;
    }

    private static float minimumProjection(float[][] corners, float axisX, float axisY) {
        float result = Float.POSITIVE_INFINITY;
        for (float[] corner : corners) {
            result = Math.min(result, corner[0] * axisX + corner[1] * axisY);
        }
        return result;
    }

    private static float maximumProjection(float[][] corners, float axisX, float axisY) {
        float result = Float.NEGATIVE_INFINITY;
        for (float[] corner : corners) {
            result = Math.max(result, corner[0] * axisX + corner[1] * axisY);
        }
        return result;
    }
}
