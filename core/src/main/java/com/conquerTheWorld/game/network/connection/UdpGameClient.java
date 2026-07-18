package com.conquerTheWorld.game.network.connection;

import com.conquerTheWorld.game.network.config.NetworkConfig;
import com.conquerTheWorld.game.network.protocol.NetworkPacketCodec;
import com.conquerTheWorld.game.network.protocol.WorldSnapshot;
import com.conquerTheWorld.game.objects.characters.CharacterType;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public final class UdpGameClient implements Closeable {
    private final DatagramSocket socket;
    private final InetSocketAddress serverAddress;
    private final CharacterType characterType;
    private final SwordType swordType;
    private final long clientNonce = ThreadLocalRandom.current().nextLong();
    private final AtomicReference<WorldSnapshot> latestSnapshot = new AtomicReference<>(
        new WorldSnapshot(-1, Collections.emptyList())
    );

    private volatile boolean running;
    private volatile boolean connected;
    private volatile int playerId = -1;
    private volatile long sessionToken;
    private volatile long lastServerPacketNanos;

    private Thread receiverThread;
    private int inputSequence;
    private long lastConnectNanos;
    private long lastInputNanos;

    public UdpGameClient(
        String serverHost,
        int serverPort,
        String clientBindHost,
        int clientPort
    ) throws IOException {
        this(
            serverHost,
            serverPort,
            clientBindHost,
            clientPort,
            CharacterType.KNIGHT,
            SwordType.QUICK
        );
    }

    public UdpGameClient(
        String serverHost,
        int serverPort,
        String clientBindHost,
        int clientPort,
        CharacterType characterType,
        SwordType swordType
    ) throws IOException {
        serverAddress = new InetSocketAddress(InetAddress.getByName(serverHost), serverPort);
        this.characterType = characterType;
        this.swordType = swordType;
        socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(InetAddress.getByName(clientBindHost), clientPort));
        socket.connect(serverAddress);
    }

    public synchronized void start() throws IOException {
        if (running) {
            return;
        }
        running = true;
        receiverThread = new Thread(this::receiveLoop, "game-client-receiver");
        receiverThread.setDaemon(true);
        receiverThread.start();
        sendConnect();
    }

    public void updateInput(float moveX, float moveY) {
        updateInput(moveX, moveY, true, false);
    }

    public void updateInput(
        float moveX,
        float moveY,
        boolean facingRight,
        boolean attackDown
    ) {
        updateInput(moveX, moveY, facingRight ? 0f : 180f, attackDown);
    }

    public void updateInput(
        float moveX,
        float moveY,
        float aimAngleDegrees,
        boolean attackDown
    ) {
        if (!running) {
            return;
        }

        long now = System.nanoTime();
        if (connected && now - lastServerPacketNanos > timeoutNanos()) {
            markDisconnected();
        }

        if (!connected) {
            if (now - lastConnectNanos >= NetworkConfig.RECONNECT_INTERVAL_MILLIS * 1_000_000L) {
                try {
                    sendConnect();
                } catch (IOException ignored) {
                    // The next render update will try again.
                }
            }
            return;
        }

        long inputInterval = 1_000_000_000L / NetworkConfig.CLIENT_INPUT_RATE;
        if (now - lastInputNanos < inputInterval) {
            return;
        }
        lastInputNanos = now;

        try {
            send(NetworkPacketCodec.input(
                playerId,
                sessionToken,
                inputSequence++,
                moveX,
                moveY,
                aimAngleDegrees,
                attackDown
            ));
        } catch (IOException ignored) {
            // UDP has no delivery guarantee. A later input packet supersedes this one.
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public int getPlayerId() {
        return playerId;
    }

    public WorldSnapshot getLatestSnapshot() {
        return latestSnapshot.get();
    }

    public InetSocketAddress getLocalAddress() {
        return new InetSocketAddress(socket.getLocalAddress(), socket.getLocalPort());
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public SwordType getSwordType() {
        return swordType;
    }

    private void receiveLoop() {
        byte[] buffer = new byte[NetworkConfig.MAX_PACKET_SIZE];
        while (running) {
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(datagram);
                handlePacket(datagram);
            } catch (SocketException exception) {
                if (running) {
                    markDisconnected();
                }
                return;
            } catch (IOException exception) {
                // Ignore a malformed packet. The connected socket already filters by server address.
            } catch (RuntimeException exception) {
                System.err.println("Client rejected a packet: " + exception.getMessage());
            }
        }
    }

    private void handlePacket(DatagramPacket datagram) throws IOException {
        int type = NetworkPacketCodec.packetType(
            datagram.getData(),
            datagram.getOffset(),
            datagram.getLength()
        );
        lastServerPacketNanos = System.nanoTime();

        if (type == NetworkPacketCodec.WELCOME) {
            NetworkPacketCodec.WelcomePacket packet = NetworkPacketCodec.readWelcome(
                datagram.getData(),
                datagram.getOffset(),
                datagram.getLength()
            );
            boolean newSession = !connected
                || playerId != packet.playerId
                || sessionToken != packet.sessionToken;
            playerId = packet.playerId;
            sessionToken = packet.sessionToken;
            connected = true;
            if (newSession) {
                System.out.println("Connected as player " + playerId + " to " + serverAddress);
            }
        } else if (type == NetworkPacketCodec.SNAPSHOT && connected) {
            latestSnapshot.set(NetworkPacketCodec.readSnapshot(
                datagram.getData(),
                datagram.getOffset(),
                datagram.getLength()
            ));
        }
    }

    private void sendConnect() throws IOException {
        lastConnectNanos = System.nanoTime();
        send(NetworkPacketCodec.connect(clientNonce, characterType, swordType));
    }

    private void send(byte[] bytes) throws IOException {
        socket.send(new DatagramPacket(bytes, bytes.length));
    }

    private long timeoutNanos() {
        return NetworkConfig.CONNECTION_TIMEOUT_MILLIS * 1_000_000L;
    }

    private void markDisconnected() {
        if (!connected) {
            return;
        }
        connected = false;
        playerId = -1;
        sessionToken = 0L;
        WorldSnapshot old = latestSnapshot.get();
        latestSnapshot.set(new WorldSnapshot(old.getServerTick() + 1, Collections.emptyList()));
        System.out.println("Lost connection to " + serverAddress + "; reconnecting...");
    }

    @Override
    public synchronized void close() {
        if (!running) {
            return;
        }
        if (connected) {
            try {
                send(NetworkPacketCodec.disconnect(playerId, sessionToken));
            } catch (IOException ignored) {
                // The server will remove this client on timeout if this packet is lost.
            }
        }
        running = false;
        connected = false;
        socket.close();
    }
}
