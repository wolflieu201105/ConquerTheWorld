package com.conquerTheWorld.game.network.connection;

import com.conquerTheWorld.game.network.config.NetworkConfig;
import com.conquerTheWorld.game.network.discovery.LanDiscoveryResponder;

public final class GameServerMain {
    private GameServerMain() {
    }

    public static void main(String[] args) throws Exception {
        String bindHost = NetworkConfig.getServerBindHost();
        int port = NetworkConfig.getServerPort();
        int discoveryPort = NetworkConfig.getDiscoveryPort();
        String serverName = NetworkConfig.getServerName();
        boolean advertise = true;

        for (int i = 0; i < args.length; i++) {
            if ("--bind".equals(args[i]) && i + 1 < args.length) {
                bindHost = args[++i];
            } else if ("--port".equals(args[i]) && i + 1 < args.length) {
                port = Integer.parseInt(args[++i]);
            } else if ("--discovery-port".equals(args[i]) && i + 1 < args.length) {
                discoveryPort = Integer.parseInt(args[++i]);
            } else if ("--name".equals(args[i]) && i + 1 < args.length) {
                serverName = args[++i];
            } else if ("--no-discovery".equals(args[i])) {
                advertise = false;
            } else if ("--help".equals(args[i])) {
                System.out.println(
                    "Usage: GameServerMain [--bind IP] [--port PORT] "
                        + "[--discovery-port PORT] [--name NAME] [--no-discovery]"
                );
                return;
            } else {
                throw new IllegalArgumentException("Unknown or incomplete argument: " + args[i]);
            }
        }

        final UdpGameServer server = new UdpGameServer(bindHost, port);
        final LanDiscoveryResponder discovery = advertise
            ? new LanDiscoveryResponder(
                "0.0.0.0",
                discoveryPort,
                server.getLocalAddress().getPort(),
                serverName,
                server::getConnectedPlayerCount
            )
            : null;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (discovery != null) {
                discovery.close();
            }
            server.close();
        }, "game-server-shutdown"));
        server.start();
        if (discovery != null) {
            discovery.start();
            System.out.println(
                "Advertising '" + serverName + "' on UDP discovery port " + discoveryPort
            );
        }
        System.out.println("Authoritative UDP server listening on " + server.getLocalAddress());
        server.awaitTermination();
    }
}
