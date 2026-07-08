package com.conquerTheWorld.game.worlds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.conquerTheWorld.game.Constants;
import com.conquerTheWorld.game.assets.TileSet;
import com.conquerTheWorld.game.objects.Entity;
import com.conquerTheWorld.game.objects.Player;
import com.conquerTheWorld.game.objects.Prop;
import com.conquerTheWorld.game.objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World1 {
    private TileSet stoneTiles;

    private TextureRegion[] stones;
    private TextureRegion[] stoneWalls;

    private final List<Prop> props = new ArrayList<>();
    private final List<Entity> entities = new ArrayList<>();
    private final List<RenderableObject> renderables = new ArrayList<>();

    private final Random random = new Random();

    private Player player;

    // -1 = random floor
    // -2 = random wall
    private int[][] map = {
        {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -1, -1, -1, -1, -1, -1, -1, -1, -2},
        {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2}
    };

    public World1() {
        stoneTiles = new TileSet(Constants.STONE_TILESET_PATH);

        stones = new TextureRegion[9];
        stoneWalls = new TextureRegion[2];

        loadStoneTiles();
        loadStoneWalls();
        buildWorldFromMap();

        player = new Player(64, 64);
        entities.add(player);
        rebuildRenderables();
    }

    private void loadStoneTiles() {
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                stones[col * 3 + row] = stoneTiles.getTile(
                    Constants.SOURCE_TILE_SIZE * col,
                    Constants.SOURCE_TILE_SIZE * (row + 1),
                    Constants.SOURCE_TILE_SIZE,
                    Constants.SOURCE_TILE_SIZE
                );
            }
        }
    }

    private void loadStoneWalls() {
        stoneWalls[0] = stoneTiles.getTile(48, 16, 16, 24);
        stoneWalls[1] = stoneTiles.getTile(48, 40, 16, 24);
    }

    private void buildWorldFromMap() {
        props.clear();
        entities.clear();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int id = map[row][col];

                float x = col * Constants.FLOOR_DRAW_SIZE;
                float y = (map.length - 1 - row) * Constants.FLOOR_DRAW_SIZE;

                if (id == -1) {
                    addRandomFloor(x, y);
                } else if (id == -2) {
                    addRandomWall(x, y);
                }
            }
        }

        rebuildRenderables();
    }

    private void addRandomFloor(float gameX, float gameY) {
        TextureRegion region = stones[random.nextInt(stones.length)];

        Prop floor = new Prop(
            gameX,
            gameY,
            Constants.DEFAULT_GAME_Z,
            0,
            0,
            region.getRegionWidth() * Constants.TILE_MULTIPLIER,
            region.getRegionHeight() * Constants.TILE_MULTIPLIER,
            Constants.DEFAULT_COLLISION_WIDTH,
            Constants.DEFAULT_COLLISION_DEPTH,
            Constants.DEFAULT_COLLISION_HEIGHT,
            Constants.GROUND_Z_HEIGHT,
            region,
            false
        );

        props.add(floor);
    }

    private void addRandomWall(float gameX, float gameY) {
        TextureRegion region = stoneWalls[random.nextInt(stoneWalls.length)];

        Prop wall = new Prop(
            gameX,
            gameY,
            Constants.DEFAULT_GAME_Z,
            0,
            0,
            region.getRegionWidth() * Constants.TILE_MULTIPLIER,
            region.getRegionHeight() * Constants.TILE_MULTIPLIER,
            Constants.WALL_COLLISION_WIDTH,
            Constants.WALL_COLLISION_DEPTH,
            Constants.WALL_COLLISION_HEIGHT,
            Constants.WALL_Z_HEIGHT,
            region,
            true
        );

        props.add(wall);
    }

    private void rebuildRenderables() {
        renderables.clear();

        renderables.addAll(props);
        renderables.addAll(entities);

        renderables.sort((a, b) -> {
            if (a.getGameZ() >= b.getTopZ()) {
                return 1;
            }

            if (b.getGameZ() >= a.getTopZ()) {
                return -1;
            }

            return Float.compare(b.getGameY(), a.getGameY());
        });
    }

    public void update(float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
            moveEntity(entity, delta);
        }

        rebuildRenderables();
    }

    private void moveEntity(Entity entity, float delta) {
        entity.setGameX(entity.getGameX() + entity.getMoveX(delta));
        entity.setGameY(entity.getGameY() + entity.getMoveY(delta));
    }

    public List<Prop> getProps() {
        return props;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<RenderableObject> getRenderables() {
        return renderables;
    }

    public Player getPlayer() {
        return player;
    }

    public void dispose() {
        stoneTiles.dispose();
        player.dispose();
    }
}