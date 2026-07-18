package com.conquerTheWorld.game;

public class Constants {
    private Constants() {
    }

    // World camera size
    // Exactly matches the current bordered map so no world outside the border is exposed.
    public static final float CAMERA_VIEW_WIDTH = 320f;
    public static final float CAMERA_VIEW_HEIGHT = 256f;

    // Tiles
    public static final int SOURCE_TILE_SIZE = 16;
    public static final int TILE_MULTIPLIER = 2;
    public static final int FLOOR_DRAW_SIZE = SOURCE_TILE_SIZE * TILE_MULTIPLIER;

    // Player
    public static final int PLAYER_DRAW_SIZE = 32;
    public static final float PLAYER_ANIMATION_FRAME_TIME = 0.16f;
    public static final float AIM_DEAD_ZONE_SQUARED = 0.0001f;

    // Z / elevation
    public static final float DEFAULT_GAME_Z = 0f;

    public static final float GROUND_Z_HEIGHT = 0f;
    public static final float WALL_Z_HEIGHT = 32f;

    // Collision footprint
    public static final float DEFAULT_COLLISION_HEIGHT = 0f;
    public static final float WALL_COLLISION_HEIGHT = 1000f;

    public static final float DEFAULT_COLLISION_WIDTH = 0f;
    public static final float DEFAULT_COLLISION_DEPTH = 0f;

    public static final float WALL_COLLISION_WIDTH = SOURCE_TILE_SIZE * TILE_MULTIPLIER;
    public static final float WALL_COLLISION_DEPTH = SOURCE_TILE_SIZE * TILE_MULTIPLIER;

    // Asset paths
    public static final String STONE_TILESET_PATH = "tiles/stone_tiles_01.png";

    // Clear screen color
    public static final float CLEAR_R = 0.15f;
    public static final float CLEAR_G = 0.15f;
    public static final float CLEAR_B = 0.20f;
    public static final float CLEAR_A = 1f;
}
