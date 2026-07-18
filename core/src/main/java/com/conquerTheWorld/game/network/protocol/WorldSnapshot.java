package com.conquerTheWorld.game.network.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WorldSnapshot {
    private final int serverTick;
    private final List<PlayerSnapshot> players;
    private final List<AttackSnapshot> attacks;

    public WorldSnapshot(int serverTick, List<PlayerSnapshot> players) {
        this(serverTick, players, Collections.emptyList());
    }

    public WorldSnapshot(
        int serverTick,
        List<PlayerSnapshot> players,
        List<AttackSnapshot> attacks
    ) {
        this.serverTick = serverTick;
        this.players = Collections.unmodifiableList(new ArrayList<>(players));
        this.attacks = Collections.unmodifiableList(new ArrayList<>(attacks));
    }

    public int getServerTick() {
        return serverTick;
    }

    public List<PlayerSnapshot> getPlayers() {
        return players;
    }

    public List<AttackSnapshot> getAttacks() {
        return attacks;
    }
}
