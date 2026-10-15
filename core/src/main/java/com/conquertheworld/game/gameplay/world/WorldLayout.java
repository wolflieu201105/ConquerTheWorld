package com.conquertheworld.game.gameplay.world;

import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.gameplay.entity.Hitbox;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class WorldLayout {
    public static final int RANDOM_FLOOR = -1;
    public static final int RANDOM_WALL = -2;

    private static final int MAP_COLUMNS = 24;
    private static final int MAP_ROWS = 18;
    private static final long MAP_SEED = 0x434F4E5155455354L;
    private static final float INTERIOR_WALL_CHANCE = 0.20f;
    private static final int[][] MAP = createMap();

    private WorldLayout() {
    }

    public static int[][] copyMap() {
        int[][] result = new int[MAP.length][];
        for (int row = 0; row < MAP.length; row++) {
            result[row] = MAP[row].clone();
        }
        return result;
    }

    public static float getWorldWidth() {
        return MAP[0].length * GameConstants.FLOOR_DRAW_SIZE;
    }

    public static float getWorldHeight() {
        return MAP.length * GameConstants.FLOOR_DRAW_SIZE;
    }

    public static long getMapSeed() {
        return MAP_SEED;
    }

    public static int getInteriorWallCount() {
        int count = 0;
        for (int row = 1; row < MAP.length - 1; row++) {
            for (int column = 1; column < MAP[row].length - 1; column++) {
                if (MAP[row][column] == RANDOM_WALL) {
                    count++;
                }
            }
        }
        return count;
    }

    /** Returns a stable visual variant for one map coordinate on every machine. */
    public static int tileVariant(int row, int column, int variantCount, int salt) {
        if (variantCount <= 0) {
            throw new IllegalArgumentException("variantCount must be positive");
        }

        long hash = MAP_SEED;
        hash ^= (long) row * 0x9E3779B97F4A7C15L;
        hash ^= (long) column * 0xC2B2AE3D27D4EB4FL;
        hash ^= (long) salt * 0x165667B19E3779F9L;
        hash ^= hash >>> 30;
        hash *= 0xBF58476D1CE4E5B9L;
        hash ^= hash >>> 27;
        hash *= 0x94D049BB133111EBL;
        hash ^= hash >>> 31;
        return Math.floorMod(hash, variantCount);
    }

    private static int[][] createMap() {
        int[][] result = new int[MAP_ROWS][MAP_COLUMNS];
        Random random = new Random(MAP_SEED);

        for (int row = 0; row < MAP_ROWS; row++) {
            for (int column = 0; column < MAP_COLUMNS; column++) {
                int worldTileY = MAP_ROWS - 1 - row;
                boolean border = row == 0 || row == MAP_ROWS - 1
                    || column == 0 || column == MAP_COLUMNS - 1;
                if (border) {
                    result[row][column] = RANDOM_WALL;
                    continue;
                }

                // Keep the multiplayer spawn and its immediate movement lanes open.
                boolean safeSpawnArea = column <= 7 && worldTileY <= 6;
                // Regular open lanes prevent the procedural walls from sealing the map.
                boolean corridor = column % 5 == 0 || worldTileY % 5 == 0;
                boolean interiorWall = !safeSpawnArea
                    && !corridor
                    && random.nextFloat() < INTERIOR_WALL_CHANCE;
                result[row][column] = interiorWall ? RANDOM_WALL : RANDOM_FLOOR;
            }
        }
        return result;
    }

    public static boolean collidesWithWall(
        float x,
        float y,
        float z,
        float width,
        float depth,
        float height
    ) {
        if (width <= 0f || depth <= 0f) {
            return false;
        }
        return collidesWithWall(
            x,
            y,
            z,
            Collections.singletonList(new Hitbox(0f, 0f, width, depth)),
            height
        );
    }

    public static boolean collidesWithWall(
        float x,
        float y,
        float z,
        List<Hitbox> hitboxes,
        float collisionHeight
    ) {
        for (int row = 0; row < MAP.length; row++) {
            for (int column = 0; column < MAP[row].length; column++) {
                if (MAP[row][column] != RANDOM_WALL) {
                    continue;
                }

                float wallX = column * GameConstants.FLOOR_DRAW_SIZE;
                float wallY = (MAP.length - 1 - row) * GameConstants.FLOOR_DRAW_SIZE;
                for (Hitbox hitbox : hitboxes) {
                    if (CollisionMath.overlaps3D(
                        x + hitbox.getXOffset(),
                        y + hitbox.getYOffset(),
                        z,
                        hitbox.getWidth(),
                        hitbox.getHeight(),
                        collisionHeight,
                        wallX,
                        wallY,
                        GameConstants.DEFAULT_GAME_Z,
                        GameConstants.WALL_COLLISION_WIDTH,
                        GameConstants.WALL_COLLISION_DEPTH,
                        GameConstants.WALL_COLLISION_HEIGHT
                    )) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
