package com.conquerTheWorld.game.network.connection;

import com.conquerTheWorld.game.Constants;
import com.conquerTheWorld.game.network.config.NetworkConfig;
import com.conquerTheWorld.game.network.protocol.AttackSnapshot;
import com.conquerTheWorld.game.network.protocol.NetworkPacketCodec;
import com.conquerTheWorld.game.network.protocol.PlayerSnapshot;
import com.conquerTheWorld.game.objects.characters.CharacterType;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;
import com.conquerTheWorld.game.world.WorldLayout;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class UdpGameServer implements Closeable {
    private final DatagramSocket socket;
    private final Object stateLock = new Object();
    private final Map<SocketAddress, ClientSession> clients = new HashMap<>();
    private final List<ServerAttackState> attacks = new ArrayList<>();

    private volatile boolean running;
    private Thread receiverThread;
    private Thread tickThread;
    private int serverTick;
    private int nextPlayerId = 1;
    private int nextAttackId = 1;

    public UdpGameServer(String bindHost, int port) throws IOException {
        socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(InetAddress.getByName(bindHost), port));
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;

        receiverThread = new Thread(this::receiveLoop, "game-server-receiver");
        tickThread = new Thread(this::tickLoop, "game-server-tick");
        receiverThread.start();
        tickThread.start();
    }

    public void awaitTermination() throws InterruptedException {
        Thread thread = tickThread;
        if (thread != null) {
            thread.join();
        }
    }

    public InetSocketAddress getLocalAddress() {
        return new InetSocketAddress(socket.getLocalAddress(), socket.getLocalPort());
    }

    public boolean isRunning() {
        return running;
    }

    public int getConnectedPlayerCount() {
        synchronized (stateLock) {
            return clients.size();
        }
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
                    System.err.println("Server UDP socket failed: " + exception.getMessage());
                }
                return;
            } catch (IOException exception) {
                // Ignore malformed or unrelated UDP packets; the server stays alive.
            } catch (RuntimeException exception) {
                System.err.println("Server rejected a packet: " + exception.getMessage());
            }
        }
    }

    private void handlePacket(DatagramPacket datagram) throws IOException {
        int type = NetworkPacketCodec.packetType(
            datagram.getData(),
            datagram.getOffset(),
            datagram.getLength()
        );

        if (type == NetworkPacketCodec.CONNECT) {
            handleConnect(datagram);
        } else if (type == NetworkPacketCodec.INPUT) {
            handleInput(datagram);
        } else if (type == NetworkPacketCodec.DISCONNECT) {
            handleDisconnect(datagram);
        }
    }

    private void handleConnect(DatagramPacket datagram) throws IOException {
        NetworkPacketCodec.ConnectPacket packet = NetworkPacketCodec.readConnect(
            datagram.getData(),
            datagram.getOffset(),
            datagram.getLength()
        );

        SocketAddress address = datagram.getSocketAddress();
        ClientSession session;
        int currentServerTick;
        synchronized (stateLock) {
            session = clients.get(address);
            if (session == null || session.clientNonce != packet.clientNonce) {
                session = createSession(
                    address,
                    packet.clientNonce,
                    packet.characterType,
                    packet.swordType
                );
                clients.put(address, session);
                System.out.println("Player " + session.playerId + " connected from " + address);
            }
            session.lastHeardNanos = System.nanoTime();
            currentServerTick = serverTick;
        }

        send(
            NetworkPacketCodec.welcome(session.playerId, session.sessionToken, currentServerTick),
            address
        );
    }

    private ClientSession createSession(
        SocketAddress address,
        long clientNonce,
        CharacterType characterType,
        SwordType swordType
    ) {
        int playerId = nextPlayerId++;
        float spawnX = 64f + ((playerId - 1) % 5) * 12f;
        float spawnY = 64f + (((playerId - 1) / 5) % 5) * 12f;
        return new ClientSession(
            address,
            clientNonce,
            playerId,
            ThreadLocalRandom.current().nextLong(),
            spawnX,
            spawnY,
            characterType,
            swordType
        );
    }

    private void handleInput(DatagramPacket datagram) throws IOException {
        NetworkPacketCodec.InputPacket packet = NetworkPacketCodec.readInput(
            datagram.getData(),
            datagram.getOffset(),
            datagram.getLength()
        );

        synchronized (stateLock) {
            ClientSession session = clients.get(datagram.getSocketAddress());
            if (!matches(session, packet.playerId, packet.sessionToken)) {
                return;
            }

            session.lastHeardNanos = System.nanoTime();
            if (packet.sequence <= session.lastInputSequence) {
                return;
            }
            session.lastInputSequence = packet.sequence;

            if (!isFinite(packet.moveX) || !isFinite(packet.moveY)
                || !isFinite(packet.aimAngleDegrees)) {
                return;
            }
            float lengthSquared = packet.moveX * packet.moveX + packet.moveY * packet.moveY;
            if (lengthSquared > 1f) {
                float inverseLength = 1f / (float) Math.sqrt(lengthSquared);
                session.moveX = packet.moveX * inverseLength;
                session.moveY = packet.moveY * inverseLength;
            } else {
                session.moveX = packet.moveX;
                session.moveY = packet.moveY;
            }

            session.aimAngleDegrees = normalizeDegrees(packet.aimAngleDegrees);
            boolean startedAttack = packet.attackDown && !session.attackDown;
            session.attackDown = packet.attackDown;
            if (startedAttack && session.attackCooldownRemaining <= 0f) {
                attacks.add(new ServerAttackState(
                    nextAttackId++,
                    session.playerId,
                    session.x,
                    session.y,
                    session.aimAngleDegrees,
                    session.swordType
                ));
                session.attackCooldownRemaining = session.swordType.getCooldown();
            }
        }
    }

    private void handleDisconnect(DatagramPacket datagram) throws IOException {
        NetworkPacketCodec.DisconnectPacket packet = NetworkPacketCodec.readDisconnect(
            datagram.getData(),
            datagram.getOffset(),
            datagram.getLength()
        );

        synchronized (stateLock) {
            ClientSession session = clients.get(datagram.getSocketAddress());
            if (matches(session, packet.playerId, packet.sessionToken)) {
                clients.remove(datagram.getSocketAddress());
                System.out.println("Player " + session.playerId + " disconnected");
            }
        }
    }

    private boolean matches(ClientSession session, int playerId, long token) {
        return session != null && session.playerId == playerId && session.sessionToken == token;
    }

    private boolean isFinite(float value) {
        return !Float.isNaN(value) && !Float.isInfinite(value);
    }

    private void tickLoop() {
        final long tickNanos = 1_000_000_000L / NetworkConfig.SERVER_TICK_RATE;
        final int snapshotEveryTicks = Math.max(
            1,
            NetworkConfig.SERVER_TICK_RATE / NetworkConfig.SNAPSHOT_RATE
        );
        long nextTickNanos = System.nanoTime();

        while (running) {
            waitUntil(nextTickNanos);
            if (!running) {
                return;
            }

            List<ClientSession> snapshotRecipients;
            byte[] snapshotBytes = null;
            synchronized (stateLock) {
                simulate(1f / NetworkConfig.SERVER_TICK_RATE);
                removeTimedOutClients();
                serverTick++;

                snapshotRecipients = new ArrayList<>(clients.values());
                if (serverTick % snapshotEveryTicks == 0 && !snapshotRecipients.isEmpty()) {
                    try {
                        snapshotBytes = createSnapshot(snapshotRecipients);
                    } catch (IOException exception) {
                        System.err.println("Could not create snapshot: " + exception.getMessage());
                    }
                }
            }

            if (snapshotBytes != null) {
                for (ClientSession recipient : snapshotRecipients) {
                    try {
                        send(snapshotBytes, recipient.address);
                    } catch (IOException exception) {
                        if (running) {
                            System.err.println("Could not send snapshot to " + recipient.address);
                        }
                    }
                }
            }

            nextTickNanos += tickNanos;
            long now = System.nanoTime();
            if (now - nextTickNanos > tickNanos * 5) {
                nextTickNanos = now + tickNanos;
            }
        }
    }

    private void simulate(float delta) {
        for (ClientSession session : clients.values()) {
            float speed = session.characterType.getSpeed();
            tryMoveSession(session, session.moveX * speed * delta, 0f);
            tryMoveSession(session, 0f, session.moveY * speed * delta);
            session.attackCooldownRemaining = Math.max(
                0f,
                session.attackCooldownRemaining - delta
            );
        }

        Iterator<ServerAttackState> attackIterator = attacks.iterator();
        while (attackIterator.hasNext()) {
            ServerAttackState attack = attackIterator.next();
            ClientSession owner = findSessionByPlayerId(attack.ownerPlayerId);
            if (owner != null) {
                attack.x = owner.x;
                attack.y = owner.y;
            }
            attack.remainingSeconds -= delta;
            if (attack.remainingSeconds <= 0f) {
                attackIterator.remove();
            }
        }
    }

    private ClientSession findSessionByPlayerId(int playerId) {
        for (ClientSession session : clients.values()) {
            if (session.playerId == playerId) {
                return session;
            }
        }
        return null;
    }

    private void tryMoveSession(ClientSession session, float moveX, float moveY) {
        float nextX = session.x + moveX;
        float nextY = session.y + moveY;
        if (!WorldLayout.collidesWithWall(
            nextX,
            nextY,
            Constants.DEFAULT_GAME_Z,
            session.characterType.getBodyHitboxes(),
            session.characterType.getBodyHeight()
        )) {
            session.x = nextX;
            session.y = nextY;
        }
    }

    private void removeTimedOutClients() {
        long timeoutNanos = NetworkConfig.CONNECTION_TIMEOUT_MILLIS * 1_000_000L;
        long now = System.nanoTime();
        Iterator<ClientSession> iterator = clients.values().iterator();
        while (iterator.hasNext()) {
            ClientSession session = iterator.next();
            if (now - session.lastHeardNanos > timeoutNanos) {
                iterator.remove();
                System.out.println("Player " + session.playerId + " timed out");
            }
        }
    }

    private byte[] createSnapshot(List<ClientSession> sessions) throws IOException {
        sessions.sort(Comparator.comparingInt(session -> session.playerId));
        List<PlayerSnapshot> players = new ArrayList<>(sessions.size());
        for (ClientSession session : sessions) {
            players.add(new PlayerSnapshot(
                session.playerId,
                session.x,
                session.y,
                session.moveX,
                session.moveY,
                session.aimAngleDegrees,
                session.characterType,
                session.swordType
            ));
        }
        List<AttackSnapshot> attackSnapshots = new ArrayList<>(attacks.size());
        for (ServerAttackState attack : attacks) {
            attackSnapshots.add(new AttackSnapshot(
                attack.attackId,
                attack.ownerPlayerId,
                attack.x,
                attack.y,
                attack.aimAngleDegrees,
                attack.swordType
            ));
        }
        return NetworkPacketCodec.snapshot(serverTick, players, attackSnapshots);
    }

    private void waitUntil(long targetNanos) {
        while (running) {
            long remaining = targetNanos - System.nanoTime();
            if (remaining <= 0) {
                return;
            }
            try {
                long millis = remaining / 1_000_000L;
                int nanos = (int) (remaining % 1_000_000L);
                Thread.sleep(millis, nanos);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void send(byte[] bytes, SocketAddress address) throws IOException {
        socket.send(new DatagramPacket(bytes, bytes.length, address));
    }

    @Override
    public synchronized void close() {
        if (!running && socket.isClosed()) {
            return;
        }
        running = false;
        socket.close();
        if (tickThread != null) {
            tickThread.interrupt();
        }
    }

    private static final class ClientSession {
        final SocketAddress address;
        final long clientNonce;
        final int playerId;
        final long sessionToken;
        final CharacterType characterType;
        final SwordType swordType;
        float x;
        float y;
        float moveX;
        float moveY;
        float aimAngleDegrees;
        boolean attackDown;
        float attackCooldownRemaining;
        int lastInputSequence = -1;
        long lastHeardNanos = System.nanoTime();

        ClientSession(
            SocketAddress address,
            long clientNonce,
            int playerId,
            long sessionToken,
            float x,
            float y,
            CharacterType characterType,
            SwordType swordType
        ) {
            this.address = address;
            this.clientNonce = clientNonce;
            this.playerId = playerId;
            this.sessionToken = sessionToken;
            this.x = x;
            this.y = y;
            this.characterType = characterType;
            this.swordType = swordType;
        }
    }

    private static final class ServerAttackState {
        final int attackId;
        final int ownerPlayerId;
        float x;
        float y;
        final float aimAngleDegrees;
        final SwordType swordType;
        float remainingSeconds;

        ServerAttackState(
            int attackId,
            int ownerPlayerId,
            float x,
            float y,
            float aimAngleDegrees,
            SwordType swordType
        ) {
            this.attackId = attackId;
            this.ownerPlayerId = ownerPlayerId;
            this.x = x;
            this.y = y;
            this.aimAngleDegrees = normalizeDegrees(aimAngleDegrees);
            this.swordType = swordType;
            this.remainingSeconds = swordType.getDuration();
        }
    }

    private static float normalizeDegrees(float degrees) {
        float normalized = degrees % 360f;
        return normalized < 0f ? normalized + 360f : normalized;
    }
}
