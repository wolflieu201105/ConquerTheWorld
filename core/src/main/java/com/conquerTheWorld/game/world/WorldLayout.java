package com.conquerTheWorld.game.world;

import com.conquerTheWorld.game.Constants;
import com.conquerTheWorld.game.objects.core.Hitbox;

import java.util.Collections;
import java.util.List;

public final class WorldLayout {
    public static final int RANDOM_FLOOR = -1;
    public static final int RANDOM_WALL = -2;

    private static final int[][] MAP = {
        {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2}
    };

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
        return MAP[0].length * Constants.FLOOR_DRAW_SIZE;
    }

    public static float getWorldHeight() {
        return MAP.length * Constants.FLOOR_DRAW_SIZE;
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

                float wallX = column * Constants.FLOOR_DRAW_SIZE;
                float wallY = (MAP.length - 1 - row) * Constants.FLOOR_DRAW_SIZE;
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
                        Constants.DEFAULT_GAME_Z,
                        Constants.WALL_COLLISION_WIDTH,
                        Constants.WALL_COLLISION_DEPTH,
                        Constants.WALL_COLLISION_HEIGHT
                    )) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
