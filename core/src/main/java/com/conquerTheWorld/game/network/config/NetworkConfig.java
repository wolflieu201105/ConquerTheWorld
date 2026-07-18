package com.conquerTheWorld.game.network.config;

import com.conquerTheWorld.game.objects.characters.CharacterType;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

public final class NetworkConfig {
    public static final String DEFAULT_SERVER_HOST = "127.0.0.1";
    public static final String DEFAULT_SERVER_BIND_HOST = "0.0.0.0";
    public static final int DEFAULT_SERVER_PORT = 54555;
    public static final int DEFAULT_DISCOVERY_PORT = 54554;
    public static final int DEFAULT_DISCOVERY_TIMEOUT_MILLIS = 1_200;
    public static final String DEFAULT_SERVER_NAME = "Conquer the World LAN";

    public static final String DEFAULT_CLIENT_BIND_HOST = "0.0.0.0";
    public static final int DEFAULT_CLIENT_PORT = 0;

    public static final int SERVER_TICK_RATE = 60;
    public static final int SNAPSHOT_RATE = 30;
    public static final int CLIENT_INPUT_RATE = 60;
    public static final int CONNECTION_TIMEOUT_MILLIS = 5_000;
    public static final int RECONNECT_INTERVAL_MILLIS = 500;
    public static final int MAX_PACKET_SIZE = 1_400;

    private NetworkConfig() {
    }

    public static String getMode() {
        return value("game.mode", "GAME_MODE", "local").toLowerCase();
    }

    public static boolean isClientMode() {
        String mode = getMode();
        return "client".equals(mode) || "host".equals(mode) || "join".equals(mode);
    }

    public static boolean isHostMode() {
        return "host".equals(getMode());
    }

    public static boolean isJoinMode() {
        return "join".equals(getMode());
    }

    public static String getServerHost() {
        return value("game.server.host", "GAME_SERVER_HOST", DEFAULT_SERVER_HOST);
    }

    public static String getServerBindHost() {
        return value("game.server.bind", "GAME_SERVER_BIND", DEFAULT_SERVER_BIND_HOST);
    }

    public static int getServerPort() {
        return intValue("game.server.port", "GAME_SERVER_PORT", DEFAULT_SERVER_PORT);
    }

    public static String getServerName() {
        return value("game.server.name", "GAME_SERVER_NAME", DEFAULT_SERVER_NAME);
    }

    public static int getDiscoveryPort() {
        return intValue("game.discovery.port", "GAME_DISCOVERY_PORT", DEFAULT_DISCOVERY_PORT);
    }

    public static int getDiscoveryTimeoutMillis() {
        return intValue(
            "game.discovery.timeout",
            "GAME_DISCOVERY_TIMEOUT",
            DEFAULT_DISCOVERY_TIMEOUT_MILLIS
        );
    }

    public static boolean shouldDiscoverServer() {
        return isJoinMode()
            || "discover".equalsIgnoreCase(getServerHost())
            || booleanValue("game.server.discover", "GAME_SERVER_DISCOVER", false);
    }

    public static String getClientBindHost() {
        return value("game.client.bind", "GAME_CLIENT_BIND", DEFAULT_CLIENT_BIND_HOST);
    }

    public static int getClientPort() {
        return intValue("game.client.port", "GAME_CLIENT_PORT", DEFAULT_CLIENT_PORT);
    }

    public static CharacterType getCharacterType() {
        return CharacterType.fromConfig(
            value("game.character", "GAME_CHARACTER", "knight")
        );
    }

    public static SwordType getSwordType() {
        return SwordType.fromConfig(value("game.sword", "GAME_SWORD", "quick"));
    }

    public static boolean shouldShowHitboxes() {
        return booleanValue("game.debug.hitboxes", "GAME_DEBUG_HITBOXES", false);
    }

    private static String value(String property, String environment, String fallback) {
        String result = System.getProperty(property);
        if (result == null || result.trim().isEmpty()) {
            result = System.getenv(environment);
        }
        return result == null || result.trim().isEmpty() ? fallback : result.trim();
    }

    private static int intValue(String property, String environment, int fallback) {
        String raw = value(property, environment, Integer.toString(fallback));
        try {
            int result = Integer.parseInt(raw);
            if (result < 0 || result > 65_535) {
                throw new IllegalArgumentException(property + " must be between 0 and 65535");
            }
            return result;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(property + " must be a number: " + raw, exception);
        }
    }

    private static boolean booleanValue(String property, String environment, boolean fallback) {
        String raw = value(property, environment, Boolean.toString(fallback));
        return "true".equalsIgnoreCase(raw)
            || "yes".equalsIgnoreCase(raw)
            || "1".equals(raw);
    }
}
