package com.conquertheworld.game.application.session;

import com.badlogic.gdx.Gdx;
import com.conquertheworld.game.content.definition.FightingRoomDefinition;
import com.conquertheworld.game.gameplay.world.GameWorld;
import com.conquertheworld.game.networking.config.NetworkConfig;
import com.conquertheworld.game.networking.connection.UdpGameClient;
import com.conquertheworld.game.networking.connection.UdpGameServer;
import com.conquertheworld.game.networking.discovery.DiscoveredServer;
import com.conquertheworld.game.networking.discovery.LanDiscoveryResponder;
import com.conquertheworld.game.networking.discovery.LanServerDiscovery;

import java.io.IOException;
import java.util.List;

/** Owns the world and optional networking resources for one gameplay session. */
public final class GameplaySession implements AutoCloseable {
    private final boolean multiplayer;
    private final boolean host;

    private GameWorld world;
    private UdpGameClient networkClient;
    private UdpGameServer embeddedServer;
    private LanDiscoveryResponder embeddedDiscovery;
    private boolean closed;

    public GameplaySession(
        boolean multiplayer,
        FightingRoomDefinition fightingRoom
    ) throws IOException {
        this.multiplayer = multiplayer;
        host = multiplayer && NetworkConfig.isHostMode();

        try {
            if (multiplayer) {
                startMultiplayer();
            } else {
                world = fightingRoom == null
                    ? new GameWorld()
                    : new GameWorld(fightingRoom);
            }
        } catch (IOException | RuntimeException exception) {
            close();
            throw exception;
        }
    }

    private void startMultiplayer() throws IOException {
        String serverHost;
        int serverPort;

        if (host) {
            startEmbeddedServer();
            String bindHost = NetworkConfig.getServerBindHost();
            serverHost = "0.0.0.0".equals(bindHost) ? "127.0.0.1" : bindHost;
            serverPort = embeddedServer.getLocalAddress().getPort();
        } else {
            serverHost = NetworkConfig.getServerHost();
            serverPort = NetworkConfig.getServerPort();
            if (NetworkConfig.shouldDiscoverServer()) {
                DiscoveredServer selected = discoverServer();
                serverHost = selected.getAddress().getHostAddress();
                serverPort = selected.getGamePort();
                Gdx.app.log("Network", "Discovered " + selected);
            }
        }

        networkClient = new UdpGameClient(
            serverHost,
            serverPort,
            NetworkConfig.getClientBindHost(),
            NetworkConfig.getClientPort(),
            NetworkConfig.getCharacterType(),
            NetworkConfig.getSwordType()
        );
        networkClient.start();
        world = new GameWorld(networkClient);

        Gdx.app.log(
            "Network",
            NetworkConfig.getMode().toUpperCase() + " client "
                + networkClient.getLocalAddress() + " -> " + networkClient.getServerAddress()
        );
    }

    private void startEmbeddedServer() throws IOException {
        UdpGameServer server = new UdpGameServer(
            NetworkConfig.getServerBindHost(),
            NetworkConfig.getServerPort()
        );
        LanDiscoveryResponder discovery;
        try {
            discovery = new LanDiscoveryResponder(
                "0.0.0.0",
                NetworkConfig.getDiscoveryPort(),
                server.getLocalAddress().getPort(),
                NetworkConfig.getServerName(),
                server::getConnectedPlayerCount
            );
        } catch (IOException exception) {
            server.close();
            throw exception;
        }

        embeddedServer = server;
        embeddedDiscovery = discovery;
        embeddedServer.start();
        embeddedDiscovery.start();
        Gdx.app.log(
            "Network",
            "Hosting '" + NetworkConfig.getServerName() + "' on "
                + embeddedServer.getLocalAddress()
        );
    }

    private DiscoveredServer discoverServer() throws IOException {
        List<DiscoveredServer> servers = LanServerDiscovery.discover(
            NetworkConfig.getDiscoveryPort(),
            NetworkConfig.getDiscoveryTimeoutMillis()
        );
        if (servers.isEmpty()) {
            throw new IOException(
                "No LAN host found. Start one game with game.mode=host before joining."
            );
        }
        return servers.get(0);
    }

    public GameWorld getWorld() {
        return world;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public boolean isHost() {
        return host;
    }

    public boolean isConnected() {
        return networkClient != null && networkClient.isConnected();
    }

    public int getLocalPlayerId() {
        return networkClient == null ? 0 : networkClient.getPlayerId();
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;

        if (networkClient != null) {
            networkClient.close();
            networkClient = null;
        }
        if (embeddedDiscovery != null) {
            embeddedDiscovery.close();
            embeddedDiscovery = null;
        }
        if (embeddedServer != null) {
            embeddedServer.close();
            embeddedServer = null;
        }
        if (world != null) {
            world.dispose();
            world = null;
        }
    }
}
