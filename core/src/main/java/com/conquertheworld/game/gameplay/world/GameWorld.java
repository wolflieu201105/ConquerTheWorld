package com.conquertheworld.game.gameplay.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.content.asset.TileSet;
import com.conquertheworld.game.content.definition.FightingRoomDefinition;
import com.conquertheworld.game.content.definition.GameDataRegistry;
import com.conquertheworld.game.networking.protocol.AttackSnapshot;
import com.conquertheworld.game.networking.config.NetworkConfig;
import com.conquertheworld.game.networking.protocol.PlayerSnapshot;
import com.conquertheworld.game.networking.connection.UdpGameClient;
import com.conquertheworld.game.networking.protocol.WorldSnapshot;
import com.conquertheworld.game.gameplay.combat.CombatSystem;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttack;
import com.conquertheworld.game.gameplay.character.CharacterType;
import com.conquertheworld.game.gameplay.entity.Entity;
import com.conquertheworld.game.gameplay.character.Player;
import com.conquertheworld.game.gameplay.character.PlayerFactory;
import com.conquertheworld.game.gameplay.enemy.BasicEnemy;
import com.conquertheworld.game.gameplay.entity.Prop;
import com.conquertheworld.game.gameplay.entity.RenderableObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameWorld {
    private TileSet stoneTiles;

    private TextureRegion[] stones;
    private TextureRegion[] stoneWalls;

    private final List<Entity> entities = new ArrayList<>();
    private final List<RenderableObject> renderables = new ArrayList<>();

    private final UdpGameClient networkClient;
    private final FightingRoomRuntime fightingRoom;
    private final CombatSystem combatSystem = new CombatSystem();
    private final Map<Integer, Player> networkPlayers = new HashMap<>();

    private Player player;
    private int lastAppliedServerTick = -1;
    private float localAimWorldX;
    private float localAimWorldY;
    private boolean hasLocalAimTarget;

    public GameWorld() {
        this((UdpGameClient) null, defaultFightingRoom());
    }

    public GameWorld(FightingRoomDefinition fightingRoom) {
        this(null, fightingRoom);
    }

    public GameWorld(UdpGameClient networkClient) {
        this(networkClient, defaultFightingRoom());
    }

    private GameWorld(
        UdpGameClient networkClient,
        FightingRoomDefinition fightingRoom
    ) {
        this.networkClient = networkClient;
        if (fightingRoom == null) {
            throw new IllegalArgumentException("fightingRoom cannot be null");
        }
        this.fightingRoom = new FightingRoomRuntime(fightingRoom);
        stoneTiles = new TileSet(GameConstants.STONE_TILESET_PATH);

        stones = new TextureRegion[9];
        stoneWalls = new TextureRegion[2];

        loadStoneTiles();
        loadStoneWalls();
        buildWorldFromMap();

        if (networkClient == null) {
            FightingRoomDefinition.SpawnPoint playerSpawn = fightingRoom.getPlayerSpawn();
            player = PlayerFactory.create(
                NetworkConfig.getCharacterType(),
                playerSpawn.x(),
                playerSpawn.y()
            );
            player.setEquippedSwordType(NetworkConfig.getSwordType());
            entities.add(player);

            for (FightingRoomDefinition.SpawnPoint spawn : fightingRoom.getEnemySpawns()) {
                BasicEnemy enemy = new BasicEnemy(spawn.x(), spawn.y());
                this.fightingRoom.addEnemy(enemy);
                entities.add(enemy);
            }
        }
        rebuildRenderables();
    }

    private static FightingRoomDefinition defaultFightingRoom() {
        return GameDataRegistry.get().getFightingRoom(WorldLayout.DEFAULT_ROOM_ID);
    }

    private void loadStoneTiles() {
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                stones[col * 3 + row] = stoneTiles.getTile(
                    GameConstants.SOURCE_TILE_SIZE * col,
                    GameConstants.SOURCE_TILE_SIZE * (row + 1),
                    GameConstants.SOURCE_TILE_SIZE,
                    GameConstants.SOURCE_TILE_SIZE
                );
            }
        }
    }

    private void loadStoneWalls() {
        stoneWalls[0] = stoneTiles.getTile(48, 16, 16, 24);
        stoneWalls[1] = stoneTiles.getTile(48, 40, 16, 24);
    }

    private void buildWorldFromMap() {
        entities.clear();

        FightingRoomDefinition definition = fightingRoom.getDefinition();
        for (int row = 0; row < definition.getRows(); row++) {
            for (int col = 0; col < definition.getColumns(); col++) {
                float x = col * GameConstants.FLOOR_DRAW_SIZE;
                float y = (definition.getRows() - 1 - row)
                    * GameConstants.FLOOR_DRAW_SIZE;

                if (definition.isWall(row, col)) {
                    addWall(x, y, row, col);
                } else {
                    addFloor(x, y, row, col);
                }
            }
        }

        rebuildRenderables();
    }

    private void addFloor(float gameX, float gameY, int row, int column) {
        int variant = fightingRoom.getDefinition()
            .tileVariant(row, column, stones.length, 0x464C4F4F);
        TextureRegion region = stones[variant];

        Prop floor = new Prop(
            gameX,
            gameY,
            GameConstants.DEFAULT_GAME_Z,
            0,
            0,
            region.getRegionWidth() * GameConstants.TILE_MULTIPLIER,
            region.getRegionHeight() * GameConstants.TILE_MULTIPLIER,
            GameConstants.DEFAULT_COLLISION_WIDTH,
            GameConstants.DEFAULT_COLLISION_DEPTH,
            GameConstants.DEFAULT_COLLISION_HEIGHT,
            GameConstants.GROUND_Z_HEIGHT,
            region,
            false
        );

        fightingRoom.addProp(floor);
    }

    private void addWall(float gameX, float gameY, int row, int column) {
        int variant = fightingRoom.getDefinition()
            .tileVariant(row, column, stoneWalls.length, 0x57414C4C);
        TextureRegion region = stoneWalls[variant];

        Prop wall = new Prop(
            gameX,
            gameY,
            GameConstants.DEFAULT_GAME_Z,
            0,
            0,
            region.getRegionWidth() * GameConstants.TILE_MULTIPLIER,
            region.getRegionHeight() * GameConstants.TILE_MULTIPLIER,
            GameConstants.WALL_COLLISION_WIDTH,
            GameConstants.WALL_COLLISION_DEPTH,
            GameConstants.WALL_COLLISION_HEIGHT,
            GameConstants.WALL_Z_HEIGHT,
            region,
            true
        );

        fightingRoom.addProp(wall);
    }

    private void rebuildRenderables() {
        renderables.clear();

        renderables.addAll(fightingRoom.getProps());
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
            if (entity instanceof Player currentPlayer && !currentPlayer.isAlive()) {
                currentPlayer.setMovementVector(0f, 0f);
                continue;
            }
            if (entity instanceof BasicEnemy enemy) {
                if (enemy.isAlive() && player != null && player.isAlive()) {
                    enemy.updateBehavior(
                        player.getGameX() + player.getDrawWidth() * 0.5f,
                        player.getGameY() + player.getDrawHeight() * 0.5f,
                        delta
                    );
                } else {
                    enemy.stop(delta);
                }
            } else {
                entity.update(delta);
            }
            moveEntity(entity, delta);
        }

        if (player != null
            && player.isAlive()
            && player.consumeAttackPressed()
            && player.hasEquippedWeapon()) {
            entities.add(combatSystem.startPlayerAttack(player));
        }
        for (BasicEnemy enemy : fightingRoom.getEnemies()) {
            if (enemy.consumeAttackRequested()) {
                entities.add(combatSystem.startEnemyAttack(enemy));
            }
        }
        updateOfflineCombat(delta);

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

        combatSystem.updateNetwork(delta);

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
            SwordAttack attack = combatSystem.getAttack(state.getAttackId());
            if (attack == null || attack.getSwordType() != state.getSwordType()) {
                if (attack != null) {
                    entities.remove(attack);
                    combatSystem.removeAttack(state.getAttackId());
                }
                attack = combatSystem.addAttack(
                    state.getAttackId(),
                    state.getOwnerPlayerId(),
                    state.getX(),
                    state.getY(),
                    state.getAimAngleDegrees(),
                    state.getSwordType()
                );
                entities.add(attack);
            }
        }

        entities.removeAll(combatSystem.removeAttacksExcept(presentAttackIds));
    }

    private void updateOfflineCombat(float delta) {
        entities.removeAll(combatSystem.updateOffline(
            delta,
            player,
            fightingRoom.getEnemies()
        ));
        for (BasicEnemy enemy : fightingRoom.removeDefeatedEnemies()) {
            entities.remove(enemy);
            enemy.dispose();
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

        Prop wall = fightingRoom.findCollidingWall(entity);
        if (wall != null && entity.wallCollide(wall)) {
            entity.setGameX(previousX);
            entity.setGameY(previousY);
        }
    }

    public List<Prop> getProps() {
        return fightingRoom.getProps();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<BasicEnemy> getEnemies() {
        return fightingRoom.getEnemies();
    }

    public List<RenderableObject> getRenderables() {
        return renderables;
    }

    public Player getPlayer() {
        return player;
    }

    public FightingRoomDefinition getFightingRoom() {
        return fightingRoom.getDefinition();
    }

    public float getWorldWidth() {
        return fightingRoom.getWorldWidth();
    }

    public float getWorldHeight() {
        return fightingRoom.getWorldHeight();
    }

    public boolean isFightingRoomCleared() {
        return networkClient == null && fightingRoom.isCleared();
    }

    public Map<Integer, Player> getNetworkPlayers() {
        return Collections.unmodifiableMap(networkPlayers);
    }

    public Map<Integer, SwordAttack> getAttacks() {
        return combatSystem.getAttacks();
    }

    public SwordAttack getActiveAttackForPlayer(int playerId) {
        return combatSystem.getActiveAttackForPlayer(playerId);
    }

    public SwordAttack getActiveAttackForPlayer(Player targetPlayer) {
        if (targetPlayer == null) {
            return null;
        }
        if (networkClient == null) {
            return targetPlayer == player ? getActiveAttackForPlayer(0) : null;
        }
        for (Map.Entry<Integer, Player> entry : networkPlayers.entrySet()) {
            if (entry.getValue() == targetPlayer) {
                return getActiveAttackForPlayer(entry.getKey());
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
            } else if (entity instanceof BasicEnemy) {
                ((BasicEnemy) entity).dispose();
            }
        }
    }
}
