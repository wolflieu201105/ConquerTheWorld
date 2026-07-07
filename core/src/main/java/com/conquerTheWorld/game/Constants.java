package com.conquerTheWorld.game;

public class Constants {
    private Constants() {
        // Prevent creating Constants objects.
    }

    // Window / world size
    public static final int WORLD_WIDTH = 400;
    public static final int WORLD_HEIGHT = 300;

    // Tiles
    public static final int SOURCE_TILE_SIZE = 16;
    public static final int TILE_MULTIPLIER = 2;
    public static final int FLOOR_DRAW_SIZE = SOURCE_TILE_SIZE * TILE_MULTIPLIER;

    // Z layers
    public static final float Z_FLOOR = 0f;
    public static final float Z_ENTITY = 500f;
    public static final float Z_WALL = 1000f;

    // Asset paths
    public static final String STONE_TILESET_PATH = "tiles/stone_tiles_01.png";

    // Clear screen color
    public static final float CLEAR_R = 0.15f;
    public static final float CLEAR_G = 0.15f;
    public static final float CLEAR_B = 0.20f;
    public static final float CLEAR_A = 1f;
    
    // Player
    public static final String DEFAULT_PLAYER_SKIN_PATH = "players/player_1.png";

    public static final int PLAYER_SOURCE_SIZE = 16;
    public static final int PLAYER_MULTIPLIER = 2;
    public static final int PLAYER_DRAW_SIZE = PLAYER_SOURCE_SIZE * PLAYER_MULTIPLIER;

    public static final float PLAYER_SPEED = 90f;

    // Animation
    public static final float PLAYER_ANIMATION_FRAME_TIME = 0.16f;
}