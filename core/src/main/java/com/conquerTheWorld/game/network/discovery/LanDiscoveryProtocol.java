package com.conquerTheWorld.game.network.discovery;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

final class LanDiscoveryProtocol {
    static final int MAX_PACKET_SIZE = 512;

    private static final int MAGIC = 0x43545744; // CTWD
    private static final int VERSION = 1;
    private static final int DISCOVER = 1;
    private static final int ANNOUNCE = 2;
    private static final int HEADER_SIZE = 6;

    private LanDiscoveryProtocol() {
    }

    static byte[] discover(long nonce) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(HEADER_SIZE + 8);
        DataOutputStream output = output(bytes, DISCOVER);
        output.writeLong(nonce);
        return finish(bytes, output);
    }

    static long readDiscover(byte[] data, int offset, int length) throws IOException {
        return typedInput(data, offset, length, DISCOVER).readLong();
    }

    static byte[] announce(
        long nonce,
        int gamePort,
        int playerCount,
        String serverName
    ) throws IOException {
        if (gamePort < 1 || gamePort > 65_535) {
            throw new IOException("Invalid advertised game port: " + gamePort);
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream(128);
        DataOutputStream output = output(bytes, ANNOUNCE);
        output.writeLong(nonce);
        output.writeShort(gamePort);
        output.writeShort(Math.min(65_535, Math.max(0, playerCount)));
        output.writeUTF(shortName(serverName));
        return finish(bytes, output);
    }

    static Announcement readAnnouncement(byte[] data, int offset, int length) throws IOException {
        DataInputStream input = typedInput(data, offset, length, ANNOUNCE);
        long nonce = input.readLong();
        int gamePort = input.readUnsignedShort();
        int playerCount = input.readUnsignedShort();
        String serverName = input.readUTF();
        if (gamePort == 0) {
            throw new IOException("Discovery response advertised port 0");
        }
        return new Announcement(nonce, gamePort, playerCount, serverName);
    }

    private static String shortName(String value) {
        String result = value == null ? "LAN Game" : value.trim();
        if (result.isEmpty()) {
            result = "LAN Game";
        }
        return result.length() <= 64 ? result : result.substring(0, 64);
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
        if (packet.length > MAX_PACKET_SIZE) {
            throw new IOException("LAN discovery packet is too large");
        }
        return packet;
    }

    private static DataInputStream typedInput(byte[] data, int offset, int length, int expectedType)
        throws IOException {
        if (length < HEADER_SIZE) {
            throw new EOFException("Discovery packet is shorter than its header");
        }
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(data, offset, length));
        if (input.readInt() != MAGIC) {
            throw new IOException("Not a Conquer the World discovery packet");
        }
        int version = input.readUnsignedByte();
        if (version != VERSION) {
            throw new IOException("Unsupported discovery version: " + version);
        }
        int type = input.readUnsignedByte();
        if (type != expectedType) {
            throw new IOException("Unexpected discovery packet type: " + type);
        }
        return input;
    }

    static final class Announcement {
        final long nonce;
        final int gamePort;
        final int playerCount;
        final String serverName;

        Announcement(long nonce, int gamePort, int playerCount, String serverName) {
            this.nonce = nonce;
            this.gamePort = gamePort;
            this.playerCount = playerCount;
            this.serverName = serverName;
        }
    }
}
