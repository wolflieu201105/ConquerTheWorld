package com.conquerTheWorld.game.network.discovery;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public final class DiscoveredServer {
    private final String name;
    private final InetAddress address;
    private final int gamePort;
    private final int playerCount;
    private final long roundTripMillis;

    public DiscoveredServer(
        String name,
        InetAddress address,
        int gamePort,
        int playerCount,
        long roundTripMillis
    ) {
        this.name = name;
        this.address = address;
        this.gamePort = gamePort;
        this.playerCount = playerCount;
        this.roundTripMillis = roundTripMillis;
    }

    public String getName() {
        return name;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getGamePort() {
        return gamePort;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public long getRoundTripMillis() {
        return roundTripMillis;
    }

    public InetSocketAddress getSocketAddress() {
        return new InetSocketAddress(address, gamePort);
    }

    @Override
    public String toString() {
        return name + " at " + address.getHostAddress() + ":" + gamePort
            + " (" + playerCount + " players, " + roundTripMillis + " ms discovery reply)";
    }
}
