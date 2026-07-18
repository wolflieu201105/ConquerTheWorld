package com.conquerTheWorld.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.conquerTheWorld.game.Constants;
import com.conquerTheWorld.game.assets.TileSet;
import com.conquerTheWorld.game.network.protocol.AttackSnapshot;
import com.conquerTheWorld.game.network.config.NetworkConfig;
import com.conquerTheWorld.game.network.protocol.PlayerSnapshot;
import com.conquerTheWorld.game.network.connection.UdpGameClient;
import com.conquerTheWorld.game.network.protocol.WorldSnapshot;
import com.conquerTheWorld.game.objects.weapons.swords.SwordAttack;
import com.conquerTheWorld.game.objects.weapons.swords.SwordAttackFactory;
import com.conquerTheWorld.game.objects.characters.CharacterType;
import com.conquerTheWorld.game.objects.core.Entity;
import com.conquerTheWorld.game.objects.core.HitboxCollision;
import com.conquerTheWorld.game.objects.characters.Player;
import com.conquerTheWorld.game.objects.characters.PlayerFactory;
import com.conquerTheWorld.game.objects.core.Prop;
import com.conquerTheWorld.game.objects.core.RenderableObject;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameWorld {
    private TileSet stoneTiles;

    private TextureRegion[] stones;
    private TextureRegion[] stoneWalls;

    private final List<Prop> props = new ArrayList<>();
    private final List<Entity> entities = new ArrayList<>();
    private final List<RenderableObject> renderables = new ArrayList<>();

    private final Random random = new Random();
    private final UdpGameClient networkClient;
    private final Map<Integer, Player> networkPlayers = new HashMap<>();
    private final Map<Integer, SwordAttack> attacks = new HashMap<>();

    private Player player;
    private int lastAppliedServerTick = -1;
    private int nextLocalAttackId = -1;
    private float localAimWorldX;
    private float localAimWorldY;
    private boolean hasLocalAimTarget;

    private final int[][] map = WorldLayout.copyMap();

    public GameWorld() {
        this(null);
    }

    public GameWorld(UdpGameClient networkClient) {
        this.networkClient = networkClient;
        stoneTiles = new TileSet(Constants.STONE_TILESET_PATH);

        stones = new TextureRegion[9];
        stoneWalls = new TextureRegion[2];

        loadStoneTiles();
        loadStoneWalls();
        buildWorldFromMap();

        if (networkClient == null) {
            player = PlayerFactory.create(NetworkConfig.getCharacterType(), 64f, 64f);
            player.setEquippedSwordType(NetworkConfig.getSwordType());
            entities.add(player);
        }
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

                if (id == WorldLayout.RANDOM_FLOOR) {
                    addRandomFloor(x, y);
                } else if (id == WorldLayout.RANDOM_WALL) {
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
        if (networkClient != null) {
            updateNetworked(delta);
            return;
        }

        applyLocalAim();
        for (Entity entity : entities) {
            if (entity instanceof SwordAttack) {
                continue;
            }
            entity.update(delta);
            moveEntity(entity, delta);
        }

        if (player != null && player.consumeAttackPressed()) {
            addAttack(
                nextLocalAttackId--,
                0,
                player.getGameX(),
                player.getGameY(),
                player.getAimAngleDegrees(),
                NetworkConfig.getSwordType()
            );
        }
        updateLocalAttacks(delta);

        rebuildRenderables();
    }

    private void updateNetworked(float delta) {
        applyLatestSnapshot();

        int localPlayerId = networkClient.getPlayerId();
        if (localPlayerId >= 0 && !networkPlayers.containsKey(localPlayerId)) {
            Player localNetworkPlayer = addNetworkPlayer(
                localPlayerId,
                64f,
                64f,
                networkClient.getCharacterType()
            );
            localNetworkPlayer.setEquippedSwordType(networkClient.getSwordType());
        }
        player = networkPlayers.get(localPlayerId);
        applyLocalAim();

        for (Map.Entry<Integer, Player> entry : networkPlayers.entrySet()) {
            Player networkPlayer = entry.getValue();
            if (entry.getKey() == localPlayerId) {
                networkPlayer.update(delta);
            } else {
                networkPlayer.updateRemote(delta);
            }
            // Extrapolate between server snapshots. Each new snapshot corrects drift.
            moveEntity(networkPlayer, delta);
        }

        if (player == null) {
            networkClient.updateInput(0f, 0f);
        } else {
            networkClient.updateInput(
                player.getMovementVector().x,
                player.getMovementVector().y,
                player.getAimAngleDegrees(),
                player.isAttackDown()
            );
        }

        updateNetworkAttacks(delta);

        rebuildRenderables();
    }

    private void applyLatestSnapshot() {
        WorldSnapshot snapshot = networkClient.getLatestSnapshot();
        if (snapshot.getServerTick() == lastAppliedServerTick) {
            return;
        }
        lastAppliedServerTick = snapshot.getServerTick();

        Set<Integer> presentPlayerIds = new HashSet<>();
        for (PlayerSnapshot state : snapshot.getPlayers()) {
            presentPlayerIds.add(state.getPlayerId());
            Player networkPlayer = networkPlayers.get(state.getPlayerId());
            if (networkPlayer == null
                || networkPlayer.getCharacterType() != state.getCharacterType()) {
                if (networkPlayer != null) {
                    entities.remove(networkPlayer);
                    networkPlayer.dispose();
                    networkPlayers.remove(state.getPlayerId());
                }
                networkPlayer = addNetworkPlayer(
                    state.getPlayerId(),
                    state.getX(),
                    state.getY(),
                    state.getCharacterType()
                );
            }
            networkPlayer.setGameX(state.getX());
            networkPlayer.setGameY(state.getY());
            networkPlayer.applyNetworkMovement(
                state.getMoveX(),
                state.getMoveY(),
                state.getAimAngleDegrees()
            );
            networkPlayer.setEquippedSwordType(state.getSwordType());
        }

        Iterator<Map.Entry<Integer, Player>> iterator = networkPlayers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Player> entry = iterator.next();
            if (!presentPlayerIds.contains(entry.getKey())) {
                entities.remove(entry.getValue());
                entry.getValue().dispose();
                iterator.remove();
            }
        }

        applyAttackSnapshot(snapshot);
    }

    private void applyAttackSnapshot(WorldSnapshot snapshot) {
        Set<Integer> presentAttackIds = new HashSet<>();
        for (AttackSnapshot state : snapshot.getAttacks()) {
            presentAttackIds.add(state.getAttackId());
            SwordAttack attack = attacks.get(state.getAttackId());
            if (attack == null || attack.getSwordType() != state.getSwordType()) {
                if (attack != null) {
                    entities.remove(attack);
                    attacks.remove(state.getAttackId());
                }
                attack = addAttack(
                    state.getAttackId(),
                    state.getOwnerPlayerId(),
                    state.getX(),
                    state.getY(),
                    state.getAimAngleDegrees(),
                    state.getSwordType()
                );
            }
            attack.setGameX(state.getX());
            attack.setGameY(state.getY());
        }

        Iterator<Map.Entry<Integer, SwordAttack>> iterator = attacks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, SwordAttack> entry = iterator.next();
            if (!presentAttackIds.contains(entry.getKey())) {
                entities.remove(entry.getValue());
                iterator.remove();
            }
        }
    }

    private SwordAttack addAttack(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees,
        SwordType swordType
    ) {
        SwordAttack attack = SwordAttackFactory.create(
            swordType,
            attackId,
            ownerPlayerId,
            x,
            y,
            aimAngleDegrees
        );
        attacks.put(attackId, attack);
        entities.add(attack);
        return attack;
    }

    private void updateLocalAttacks(float delta) {
        Iterator<Map.Entry<Integer, SwordAttack>> iterator = attacks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, SwordAttack> entry = iterator.next();
            SwordAttack attack = entry.getValue();
            if (player != null && attack.getOwnerPlayerId() == 0) {
                attack.setGameX(player.getGameX());
                attack.setGameY(player.getGameY());
            }
            attack.update(delta);
            if (attack.isExpired()) {
                entities.remove(attack);
                iterator.remove();
            }
        }
    }

    private void updateNetworkAttacks(float delta) {
        for (SwordAttack attack : attacks.values()) {
            Player owner = networkPlayers.get(attack.getOwnerPlayerId());
            if (owner != null) {
                attack.setGameX(owner.getGameX());
                attack.setGameY(owner.getGameY());
            }
            attack.update(delta);
        }
    }

    private void applyLocalAim() {
        if (player != null && hasLocalAimTarget) {
            player.aimAt(localAimWorldX, localAimWorldY);
        }
    }

    public void setLocalAimTarget(float worldX, float worldY) {
        localAimWorldX = worldX;
        localAimWorldY = worldY;
        hasLocalAimTarget = true;
    }

    private Player addNetworkPlayer(
        int playerId,
        float x,
        float y,
        CharacterType characterType
    ) {
        Player networkPlayer = PlayerFactory.create(characterType, x, y);
        networkPlayers.put(playerId, networkPlayer);
        entities.add(networkPlayer);
        return networkPlayer;
    }

    private void moveEntity(Entity entity, float delta) {
        tryMoveEntity(entity, entity.getMoveX(delta), 0f);
        tryMoveEntity(entity, 0f, entity.getMoveY(delta));
    }

    private void tryMoveEntity(Entity entity, float moveX, float moveY) {
        if (moveX == 0f && moveY == 0f) {
            return;
        }

        float previousX = entity.getGameX();
        float previousY = entity.getGameY();
        entity.setGameX(previousX + moveX);
        entity.setGameY(previousY + moveY);

        Prop wall = findCollidingWall(entity);
        if (wall != null && entity.wallCollide(wall)) {
            entity.setGameX(previousX);
            entity.setGameY(previousY);
        }
    }

    private Prop findCollidingWall(Entity entity) {
        if (!entity.isSolid()) {
            return null;
        }

        for (Prop prop : props) {
            if (prop.isSolid() && HitboxCollision.overlaps(entity, prop)) {
                return prop;
            }
        }
        return null;
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

    public Map<Integer, Player> getNetworkPlayers() {
        return Collections.unmodifiableMap(networkPlayers);
    }

    public Map<Integer, SwordAttack> getAttacks() {
        return Collections.unmodifiableMap(attacks);
    }

    public SwordAttack getActiveAttackForPlayer(int playerId) {
        for (SwordAttack attack : attacks.values()) {
            if (attack.getOwnerPlayerId() == playerId) {
                return attack;
            }
        }
        return null;
    }

    public int getLocalPlayerId() {
        return networkClient == null ? 0 : networkClient.getPlayerId();
    }

    public void dispose() {
        stoneTiles.dispose();
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                ((Player) entity).dispose();
            }
        }
    }
}
