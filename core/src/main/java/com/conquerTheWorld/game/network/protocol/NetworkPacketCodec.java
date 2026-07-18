package com.conquerTheWorld.game.network.protocol;

import com.conquerTheWorld.game.network.config.NetworkConfig;
import com.conquerTheWorld.game.objects.characters.CharacterType;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class NetworkPacketCodec {
    public static final int MAGIC = 0x43545747; // CTWG
    public static final int VERSION = 4;

    public static final int CONNECT = 1;
    public static final int WELCOME = 2;
    public static final int INPUT = 3;
    public static final int SNAPSHOT = 4;
    public static final int DISCONNECT = 5;

    private static final int HEADER_SIZE = 6;
    private static final int PLAYER_SNAPSHOT_SIZE = 26;
    private static final int ATTACK_SNAPSHOT_SIZE = 21;

    private NetworkPacketCodec() {
    }

    public static int packetType(byte[] data, int offset, int length) throws IOException {
        DataInputStream input = input(data, offset, length);
        return input.readUnsignedByte();
    }

    public static byte[] connect(long clientNonce) throws IOException {
        return connect(clientNonce, CharacterType.KNIGHT, SwordType.QUICK);
    }

    public static byte[] connect(
        long clientNonce,
        CharacterType characterType,
        SwordType swordType
    ) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(HEADER_SIZE + 10);
        DataOutputStream output = output(bytes, CONNECT);
        output.writeLong(clientNonce);
        output.writeByte(characterType.getNetworkId());
        output.writeByte(swordType.getNetworkId());
        return finish(bytes, output);
    }

    public static ConnectPacket readConnect(byte[] data, int offset, int length) throws IOException {
        DataInputStream input = typedInput(data, offset, length, CONNECT);
        return new ConnectPacket(
            input.readLong(),
            CharacterType.fromNetworkId(input.readUnsignedByte()),
            SwordType.fromNetworkId(input.readUnsignedByte())
        );
    }

    public static byte[] welcome(int playerId, long sessionToken, int serverTick) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(HEADER_SIZE + 16);
        DataOutputStream output = output(bytes, WELCOME);
        output.writeInt(playerId);
        output.writeLong(sessionToken);
        output.writeInt(serverTick);
        return finish(bytes, output);
    }

    public static WelcomePacket readWelcome(byte[] data, int offset, int length) throws IOException {
        DataInputStream input = typedInput(data, offset, length, WELCOME);
        return new WelcomePacket(input.readInt(), input.readLong(), input.readInt());
    }

    public static byte[] input(int playerId, long sessionToken, int sequence, float moveX, float moveY)
        throws IOException {
        return input(playerId, sessionToken, sequence, moveX, moveY, true, false);
    }

    public static byte[] input(
        int playerId,
        long sessionToken,
        int sequence,
        float moveX,
        float moveY,
        boolean facingRight,
        boolean attackDown
    ) throws IOException {
        return input(
            playerId,
            sessionToken,
            sequence,
            moveX,
            moveY,
            facingRight ? 0f : 180f,
            attackDown
        );
    }

    public static byte[] input(
        int playerId,
        long sessionToken,
        int sequence,
        float moveX,
        float moveY,
        float aimAngleDegrees,
        boolean attackDown
    ) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(HEADER_SIZE + 29);
        DataOutputStream output = output(bytes, INPUT);
        output.writeInt(playerId);
        output.writeLong(sessionToken);
        output.writeInt(sequence);
        output.writeFloat(moveX);
        output.writeFloat(moveY);
        output.writeFloat(aimAngleDegrees);
        output.writeBoolean(attackDown);
        return finish(bytes, output);
    }

    public static InputPacket readInput(byte[] data, int offset, int length) throws IOException {
        DataInputStream input = typedInput(data, offset, length, INPUT);
        return new InputPacket(
            input.readInt(),
            input.readLong(),
            input.readInt(),
            input.readFloat(),
            input.readFloat(),
            input.readFloat(),
            input.readBoolean()
        );
    }

    public static byte[] snapshot(int serverTick, List<PlayerSnapshot> players) throws IOException {
        return snapshot(serverTick, players, new ArrayList<AttackSnapshot>());
    }

    public static byte[] snapshot(
        int serverTick,
        List<PlayerSnapshot> players,
        List<AttackSnapshot> attacks
    ) throws IOException {
        int expectedSize = HEADER_SIZE + 8
            + players.size() * PLAYER_SNAPSHOT_SIZE
            + attacks.size() * ATTACK_SNAPSHOT_SIZE;
        if (expectedSize > NetworkConfig.MAX_PACKET_SIZE) {
            throw new IOException(
                "World snapshot exceeds one UDP packet: " + expectedSize + " bytes"
            );
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream(
            expectedSize
        );
        DataOutputStream output = output(bytes, SNAPSHOT);
        output.writeInt(serverTick);
        output.writeShort(players.size());

        for (PlayerSnapshot player : players) {
            output.writeInt(player.getPlayerId());
            output.writeFloat(player.getX());
            output.writeFloat(player.getY());
            output.writeFloat(player.getMoveX());
            output.writeFloat(player.getMoveY());
            output.writeFloat(player.getAimAngleDegrees());
            output.writeByte(player.getCharacterType().getNetworkId());
            output.writeByte(player.getSwordType().getNetworkId());
        }

        output.writeShort(attacks.size());
        for (AttackSnapshot attack : attacks) {
            output.writeInt(attack.getAttackId());
            output.writeInt(attack.getOwnerPlayerId());
            output.writeFloat(attack.getX());
            output.writeFloat(attack.getY());
            output.writeFloat(attack.getAimAngleDegrees());
            output.writeByte(attack.getSwordType().getNetworkId());
        }

        return finish(bytes, output);
    }

    public static WorldSnapshot readSnapshot(byte[] data, int offset, int length) throws IOException {
        DataInputStream input = typedInput(data, offset, length, SNAPSHOT);
        int serverTick = input.readInt();
        int playerCount = input.readUnsignedShort();
        int maximumPlayersInPacket = input.available() / PLAYER_SNAPSHOT_SIZE;
        if (playerCount > maximumPlayersInPacket) {
            throw new EOFException("Snapshot player count is larger than the packet");
        }

        List<PlayerSnapshot> players = new ArrayList<>(playerCount);
        for (int i = 0; i < playerCount; i++) {
            players.add(new PlayerSnapshot(
                input.readInt(),
                input.readFloat(),
                input.readFloat(),
                input.readFloat(),
                input.readFloat(),
                input.readFloat(),
                CharacterType.fromNetworkId(input.readUnsignedByte()),
                SwordType.fromNetworkId(input.readUnsignedByte())
            ));
        }

        int attackCount = input.readUnsignedShort();
        int maximumAttacksInPacket = input.available() / ATTACK_SNAPSHOT_SIZE;
        if (attackCount > maximumAttacksInPacket) {
            throw new EOFException("Snapshot attack count is larger than the packet");
        }
        List<AttackSnapshot> attacks = new ArrayList<>(attackCount);
        for (int i = 0; i < attackCount; i++) {
            attacks.add(new AttackSnapshot(
                input.readInt(),
                input.readInt(),
                input.readFloat(),
                input.readFloat(),
                input.readFloat(),
                SwordType.fromNetworkId(input.readUnsignedByte())
            ));
        }
        return new WorldSnapshot(serverTick, players, attacks);
    }

    public static byte[] disconnect(int playerId, long sessionToken) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(HEADER_SIZE + 12);
        DataOutputStream output = output(bytes, DISCONNECT);
        output.writeInt(playerId);
        output.writeLong(sessionToken);
        return finish(bytes, output);
    }

    public static DisconnectPacket readDisconnect(byte[] data, int offset, int length) throws IOException {
        DataInputStream input = typedInput(data, offset, length, DISCONNECT);
        return new DisconnectPacket(input.readInt(), input.readLong());
    }

    private static DataOutputStream output(ByteArrayOutputStream bytes, int type) throws IOException {
        DataOutputStream output = new DataOutputStream(bytes);
        output.writeInt(MAGIC);
        output.writeByte(VERSION);
        output.writeByte(type);
        return output;
    }

    private static byte[] finish(ByteArrayOutputStream bytes, DataOutputStream output)
        throws IOException {
        output.flush();
        byte[] packet = bytes.toByteArray();
        if (packet.length > NetworkConfig.MAX_PACKET_SIZE) {
            throw new IOException("UDP packet exceeds " + NetworkConfig.MAX_PACKET_SIZE + " bytes");
        }
        return packet;
    }

    private static DataInputStream typedInput(byte[] data, int offset, int length, int expectedType)
        throws IOException {
        DataInputStream input = input(data, offset, length);
        int type = input.readUnsignedByte();
        if (type != expectedType) {
            throw new IOException("Unexpected packet type " + type + ", expected " + expectedType);
        }
        return input;
    }

    private static DataInputStream input(byte[] data, int offset, int length) throws IOException {
        if (length < HEADER_SIZE) {
            throw new EOFException("Packet is shorter than the protocol header");
        }
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(data, offset, length));
        if (input.readInt() != MAGIC) {
            throw new IOException("Packet has the wrong protocol magic");
        }
        int version = input.readUnsignedByte();
        if (version != VERSION) {
            throw new IOException("Unsupported protocol version: " + version);
        }
        return input;
    }

    public static final class ConnectPacket {
        public final long clientNonce;
        public final CharacterType characterType;
        public final SwordType swordType;

        ConnectPacket(
            long clientNonce,
            CharacterType characterType,
            SwordType swordType
        ) {
            this.clientNonce = clientNonce;
            this.characterType = characterType;
            this.swordType = swordType;
        }
    }

    public static final class WelcomePacket {
        public final int playerId;
        public final long sessionToken;
        public final int serverTick;

        WelcomePacket(int playerId, long sessionToken, int serverTick) {
            this.playerId = playerId;
            this.sessionToken = sessionToken;
            this.serverTick = serverTick;
        }
    }

    public static final class InputPacket {
        public final int playerId;
        public final long sessionToken;
        public final int sequence;
        public final float moveX;
        public final float moveY;
        public final float aimAngleDegrees;
        public final boolean attackDown;

        InputPacket(
            int playerId,
            long sessionToken,
            int sequence,
            float moveX,
            float moveY,
            float aimAngleDegrees,
            boolean attackDown
        ) {
            this.playerId = playerId;
            this.sessionToken = sessionToken;
            this.sequence = sequence;
            this.moveX = moveX;
            this.moveY = moveY;
            this.aimAngleDegrees = aimAngleDegrees;
            this.attackDown = attackDown;
        }
    }

    public static final class DisconnectPacket {
        public final int playerId;
        public final long sessionToken;

        DisconnectPacket(int playerId, long sessionToken) {
            this.playerId = playerId;
            this.sessionToken = sessionToken;
        }
    }
}
