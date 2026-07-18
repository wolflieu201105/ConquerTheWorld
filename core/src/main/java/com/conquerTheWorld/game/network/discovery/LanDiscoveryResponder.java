package com.conquerTheWorld.game.network.discovery;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.function.IntSupplier;

public final class LanDiscoveryResponder implements Closeable {
    private final DatagramSocket socket;
    private final int gamePort;
    private final String serverName;
    private final IntSupplier playerCount;

    private volatile boolean running;
    private Thread receiverThread;

    public LanDiscoveryResponder(
        String bindHost,
        int discoveryPort,
        int gamePort,
        String serverName,
        IntSupplier playerCount
    ) throws IOException {
        this.gamePort = gamePort;
        this.serverName = serverName;
        this.playerCount = playerCount;

        socket = new DatagramSocket(null);
        socket.setReuseAddress(true);
        socket.setBroadcast(true);
        socket.bind(new InetSocketAddress(InetAddress.getByName(bindHost), discoveryPort));
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        receiverThread = new Thread(this::receiveLoop, "lan-discovery-responder");
        receiverThread.start();
    }

    public int getLocalPort() {
        return socket.getLocalPort();
    }

    private void receiveLoop() {
        byte[] buffer = new byte[LanDiscoveryProtocol.MAX_PACKET_SIZE];
        while (running) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(request);
                long nonce = LanDiscoveryProtocol.readDiscover(
                    request.getData(),
                    request.getOffset(),
                    request.getLength()
                );
                byte[] response = LanDiscoveryProtocol.announce(
                    nonce,
                    gamePort,
                    playerCount.getAsInt(),
                    serverName
                );
                socket.send(new DatagramPacket(
                    response,
                    response.length,
                    request.getAddress(),
                    request.getPort()
                ));
            } catch (SocketException exception) {
                if (running) {
                    System.err.println("LAN discovery socket failed: " + exception.getMessage());
                }
                return;
            } catch (IOException exception) {
                // Other applications may broadcast on the same LAN. Ignore unrelated packets.
            } catch (RuntimeException exception) {
                System.err.println("LAN discovery request failed: " + exception.getMessage());
            }
        }
    }

    @Override
    public synchronized void close() {
        running = false;
        socket.close();
    }
}
