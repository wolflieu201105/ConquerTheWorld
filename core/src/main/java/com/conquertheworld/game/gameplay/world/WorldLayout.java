package com.conquertheworld.game.gameplay.world;

import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.content.definition.FightingRoomDefinition;
import com.conquertheworld.game.content.definition.GameDataRegistry;
import com.conquertheworld.game.gameplay.entity.Hitbox;

import java.util.Collections;
import java.util.List;

/** Compatibility access to the authored room used by the networking prototype. */
public final class WorldLayout {
    public static final int RANDOM_FLOOR = FightingRoomDefinition.FLOOR;
    public static final int RANDOM_WALL = FightingRoomDefinition.WALL;

    public static final String DEFAULT_ROOM_ID = "training_yard";

    private static final FightingRoomDefinition DEFAULT_ROOM = GameDataRegistry.get()
        .getFightingRoom(DEFAULT_ROOM_ID);

    private WorldLayout() {
    }

    public static int[][] copyMap() {
        return DEFAULT_ROOM.copyMap();
    }

    public static float getWorldWidth() {
        return DEFAULT_ROOM.getWorldWidth();
    }

    public static float getWorldHeight() {
        return DEFAULT_ROOM.getWorldHeight();
    }

    public static long getMapSeed() {
        return DEFAULT_ROOM.getVisualSeed();
    }

    public static int getInteriorWallCount() {
        return DEFAULT_ROOM.getInteriorWallCount();
    }

    /** Returns a stable visual variant for one map coordinate on every machine. */
    public static int tileVariant(int row, int column, int variantCount, int salt) {
        return DEFAULT_ROOM.tileVariant(row, column, variantCount, salt);
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
        for (int row = 0; row < DEFAULT_ROOM.getRows(); row++) {
            for (int column = 0; column < DEFAULT_ROOM.getColumns(); column++) {
                if (!DEFAULT_ROOM.isWall(row, column)) {
                    continue;
                }

                float wallX = column * GameConstants.FLOOR_DRAW_SIZE;
                float wallY = (DEFAULT_ROOM.getRows() - 1 - row)
                    * GameConstants.FLOOR_DRAW_SIZE;
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
