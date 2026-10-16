package com.conquertheworld.game.content.definition;

import com.conquertheworld.game.config.GameConstants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/** Immutable, validated geometry and spawn data for one authored fighting room. */
public final class FightingRoomDefinition {
    public static final int FLOOR = -1;
    public static final int WALL = -2;

    private static final char FLOOR_SYMBOL = '.';
    private static final char WALL_SYMBOL = '#';
    private static final char PLAYER_SYMBOL = 'P';
    private static final char ENEMY_SYMBOL = 'E';

    private final String id;
    private final String displayName;
    private final boolean[][] walls;
    private final SpawnPoint playerSpawn;
    private final List<SpawnPoint> enemySpawns;
    private final long visualSeed;

    FightingRoomDefinition(String id, String displayName, List<String> layout) {
        this.id = id;
        this.displayName = requireText(displayName, "displayName");
        if (layout == null || layout.size() < 3) {
            throw new IllegalStateException(id + " layout must have at least three rows");
        }

        int columns = layout.get(0).length();
        if (columns < 3) {
            throw new IllegalStateException(id + " layout must have at least three columns");
        }

        walls = new boolean[layout.size()][columns];
        SpawnPoint foundPlayerSpawn = null;
        List<SpawnPoint> foundEnemySpawns = new ArrayList<>();
        for (int row = 0; row < layout.size(); row++) {
            String line = layout.get(row);
            if (line.length() != columns) {
                throw new IllegalStateException(id + " layout rows must have equal width");
            }
            for (int column = 0; column < columns; column++) {
                char symbol = line.charAt(column);
                boolean border = row == 0 || row == layout.size() - 1
                    || column == 0 || column == columns - 1;
                if (border && symbol != WALL_SYMBOL) {
                    throw new IllegalStateException(id + " layout must have a solid wall boundary");
                }

                switch (symbol) {
                    case WALL_SYMBOL -> walls[row][column] = true;
                    case FLOOR_SYMBOL -> walls[row][column] = false;
                    case PLAYER_SYMBOL -> {
                        if (foundPlayerSpawn != null) {
                            throw new IllegalStateException(id + " must have exactly one player spawn");
                        }
                        foundPlayerSpawn = spawnPoint(row, column, layout.size());
                    }
                    case ENEMY_SYMBOL -> foundEnemySpawns.add(
                        spawnPoint(row, column, layout.size())
                    );
                    default -> throw new IllegalStateException(
                        id + " layout contains unsupported symbol '" + symbol + "'"
                    );
                }
            }
        }

        if (foundPlayerSpawn == null) {
            throw new IllegalStateException(id + " layout must have exactly one player spawn");
        }
        if (foundEnemySpawns.isEmpty()) {
            throw new IllegalStateException(id + " fighting room must have an enemy spawn");
        }

        playerSpawn = foundPlayerSpawn;
        enemySpawns = Collections.unmodifiableList(foundEnemySpawns);
        validateReachability();
        visualSeed = createVisualSeed(id, layout);
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getRows() {
        return walls.length;
    }

    public int getColumns() {
        return walls[0].length;
    }

    public float getWorldWidth() {
        return getColumns() * GameConstants.FLOOR_DRAW_SIZE;
    }

    public float getWorldHeight() {
        return getRows() * GameConstants.FLOOR_DRAW_SIZE;
    }

    public boolean isWall(int row, int column) {
        return walls[row][column];
    }

    public SpawnPoint getPlayerSpawn() {
        return playerSpawn;
    }

    public List<SpawnPoint> getEnemySpawns() {
        return enemySpawns;
    }

    public int getInteriorWallCount() {
        int count = 0;
        for (int row = 1; row < getRows() - 1; row++) {
            for (int column = 1; column < getColumns() - 1; column++) {
                if (walls[row][column]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int[][] copyMap() {
        int[][] result = new int[getRows()][getColumns()];
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                result[row][column] = walls[row][column] ? WALL : FLOOR;
            }
        }
        return result;
    }

    /** Returns a stable visual variant for one coordinate in this room. */
    public int tileVariant(int row, int column, int variantCount, int salt) {
        if (variantCount <= 0) {
            throw new IllegalArgumentException("variantCount must be positive");
        }

        long hash = visualSeed;
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

    public long getVisualSeed() {
        return visualSeed;
    }

    private void validateReachability() {
        int startColumn = Math.round(playerSpawn.x() / GameConstants.FLOOR_DRAW_SIZE);
        int worldRow = Math.round(playerSpawn.y() / GameConstants.FLOOR_DRAW_SIZE);
        int startRow = getRows() - 1 - worldRow;
        boolean[][] visited = new boolean[getRows()][getColumns()];
        Queue<int[]> remaining = new ArrayDeque<>();
        remaining.add(new int[] {startRow, startColumn});
        visited[startRow][startColumn] = true;

        int reachableFloors = 0;
        while (!remaining.isEmpty()) {
            int[] current = remaining.remove();
            reachableFloors++;
            visit(current[0] - 1, current[1], visited, remaining);
            visit(current[0] + 1, current[1], visited, remaining);
            visit(current[0], current[1] - 1, visited, remaining);
            visit(current[0], current[1] + 1, visited, remaining);
        }

        int totalFloors = 0;
        for (boolean[] row : walls) {
            for (boolean wall : row) {
                if (!wall) {
                    totalFloors++;
                }
            }
        }
        if (reachableFloors != totalFloors) {
            throw new IllegalStateException(id + " layout contains unreachable floor space");
        }
    }

    private void visit(int row, int column, boolean[][] visited, Queue<int[]> remaining) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()
            || walls[row][column] || visited[row][column]) {
            return;
        }
        visited[row][column] = true;
        remaining.add(new int[] {row, column});
    }

    private static SpawnPoint spawnPoint(int row, int column, int rowCount) {
        return new SpawnPoint(
            column * GameConstants.FLOOR_DRAW_SIZE,
            (rowCount - 1 - row) * GameConstants.FLOOR_DRAW_SIZE
        );
    }

    private static String requireText(String value, String name) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(name + " cannot be blank");
        }
        return value.trim();
    }

    private static long createVisualSeed(String id, List<String> layout) {
        long hash = 0xcbf29ce484222325L;
        String value = id + String.join("", layout);
        for (int index = 0; index < value.length(); index++) {
            hash ^= value.charAt(index);
            hash *= 0x100000001b3L;
        }
        return hash;
    }

    public record SpawnPoint(float x, float y) {
    }
}
