package com.conquerTheWorld.game.network.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public final class LanServerDiscovery {
    private LanServerDiscovery() {
    }

    public static List<DiscoveredServer> discover(int discoveryPort, int timeoutMillis)
        throws IOException {
        if (discoveryPort < 1 || discoveryPort > 65_535) {
            throw new IllegalArgumentException("Discovery port must be between 1 and 65535");
        }
        if (timeoutMillis < 1) {
            throw new IllegalArgumentException("Discovery timeout must be positive");
        }

        long nonce = ThreadLocalRandom.current().nextLong();
        byte[] requestBytes = LanDiscoveryProtocol.discover(nonce);
        long sentAtNanos = System.nanoTime();
        long deadlineNanos = sentAtNanos + timeoutMillis * 1_000_000L;
        Map<String, DiscoveredServer> discovered = new LinkedHashMap<>();

        try (DatagramSocket socket = new DatagramSocket(null)) {
            socket.setBroadcast(true);
            socket.bind(new InetSocketAddress(0));

            IOException lastSendFailure = null;
            int successfulSends = 0;
            for (InetAddress address : broadcastAddresses()) {
                try {
                    socket.send(new DatagramPacket(
                        requestBytes,
                        requestBytes.length,
                        address,
                        discoveryPort
                    ));
                    successfulSends++;
                } catch (IOException exception) {
                    lastSendFailure = exception;
                }
            }
            if (successfulSends == 0 && lastSendFailure != null) {
                throw lastSendFailure;
            }

            byte[] responseBuffer = new byte[LanDiscoveryProtocol.MAX_PACKET_SIZE];
            while (System.nanoTime() < deadlineNanos) {
                long remainingNanos = deadlineNanos - System.nanoTime();
                int remainingMillis = (int) Math.max(
                    1L,
                    Math.min(Integer.MAX_VALUE, (remainingNanos + 999_999L) / 1_000_000L)
                );
                socket.setSoTimeout(remainingMillis);

                DatagramPacket response = new DatagramPacket(
                    responseBuffer,
                    responseBuffer.length
                );
                try {
                    socket.receive(response);
                    LanDiscoveryProtocol.Announcement announcement =
                        LanDiscoveryProtocol.readAnnouncement(
                            response.getData(),
                            response.getOffset(),
                            response.getLength()
                        );
                    if (announcement.nonce != nonce) {
                        continue;
                    }

                    long roundTripMillis = Math.max(
                        0L,
                        (System.nanoTime() - sentAtNanos) / 1_000_000L
                    );
                    DiscoveredServer server = new DiscoveredServer(
                        announcement.serverName,
                        response.getAddress(),
                        announcement.gamePort,
                        announcement.playerCount,
                        roundTripMillis
                    );
                    String key = response.getAddress().getHostAddress()
                        + ":" + announcement.gamePort;
                    discovered.put(key, server);
                } catch (SocketTimeoutException exception) {
                    break;
                } catch (IOException exception) {
                    // Ignore unrelated or malformed responses until the timeout expires.
                }
            }
        }

        List<DiscoveredServer> result = new ArrayList<>(discovered.values());
        result.sort(
            Comparator.comparingLong(DiscoveredServer::getRoundTripMillis)
                .thenComparing(DiscoveredServer::getName)
        );
        return Collections.unmodifiableList(result);
    }

    private static Set<InetAddress> broadcastAddresses() throws IOException {
        Set<InetAddress> addresses = new LinkedHashSet<>();
        addresses.add(InetAddress.getByName("255.255.255.255"));
        addresses.add(InetAddress.getLoopbackAddress());

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        if (interfaces == null) {
            return addresses;
        }

        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            try {
                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }
            } catch (IOException exception) {
                continue;
            }

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast != null) {
                    addresses.add(broadcast);
                }
            }
        }
        return addresses;
    }
}
