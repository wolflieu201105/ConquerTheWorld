package com.conquerTheWorld.game.objects.core;

public final class Hitbox {
    private final float xOffset;
    private final float yOffset;
    private final float width;
    private final float height;
    private final float rotationDegrees;

    public Hitbox(float xOffset, float yOffset, float width, float height) {
        this(xOffset, yOffset, width, height, 0f);
    }

    /**
     * Creates a rectangle relative to its entity. Rotation is counter-clockwise around the
     * rectangle's local lower-left corner. Sword hitboxes use this after their lower-left offset
     * has already been rotated around the player's hand/pivot.
     */
    public Hitbox(
        float xOffset,
        float yOffset,
        float width,
        float height,
        float rotationDegrees
    ) {
        if (!isFinite(xOffset) || !isFinite(yOffset)
            || !isFinite(width) || !isFinite(height) || !isFinite(rotationDegrees)) {
            throw new IllegalArgumentException("Hitbox values must be finite numbers");
        }
        if (width <= 0f || height <= 0f) {
            throw new IllegalArgumentException("Hitbox width and height must be positive");
        }
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.rotationDegrees = normalizeDegrees(rotationDegrees);
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public float getWidth() {
        return width;
    }

    /** The rectangle size along the world's Y axis. */
    public float getHeight() {
        return height;
    }

    public float getRotationDegrees() {
        return rotationDegrees;
    }

    public float getMinimumX() {
        return minimumCorner(true);
    }

    public float getMaximumX() {
        return maximumCorner(true);
    }

    public float getMinimumY() {
        return minimumCorner(false);
    }

    public float getMaximumY() {
        return maximumCorner(false);
    }

    private float minimumCorner(boolean xAxis) {
        float[] corners = corners();
        float result = Float.POSITIVE_INFINITY;
        for (int i = xAxis ? 0 : 1; i < corners.length; i += 2) {
            result = Math.min(result, corners[i]);
        }
        return result;
    }

    private float maximumCorner(boolean xAxis) {
        float[] corners = corners();
        float result = Float.NEGATIVE_INFINITY;
        for (int i = xAxis ? 0 : 1; i < corners.length; i += 2) {
            result = Math.max(result, corners[i]);
        }
        return result;
    }

    private float[] corners() {
        double radians = Math.toRadians(rotationDegrees);
        float cosine = (float) Math.cos(radians);
        float sine = (float) Math.sin(radians);
        return new float[] {
            xOffset, yOffset,
            xOffset + cosine * width, yOffset + sine * width,
            xOffset - sine * height, yOffset + cosine * height,
            xOffset + cosine * width - sine * height,
            yOffset + sine * width + cosine * height
        };
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }

    private static boolean isFinite(float value) {
        return !Float.isNaN(value) && !Float.isInfinite(value);
    }
}
