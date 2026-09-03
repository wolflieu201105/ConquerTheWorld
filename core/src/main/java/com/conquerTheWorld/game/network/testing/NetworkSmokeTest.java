package com.conquerTheWorld.game.network.testing;

import com.conquerTheWorld.game.Constants;
import com.conquerTheWorld.game.data.GameDataRegistry;
import com.conquerTheWorld.game.network.config.NetworkConfig;
import com.conquerTheWorld.game.network.connection.UdpGameClient;
import com.conquerTheWorld.game.network.connection.UdpGameServer;
import com.conquerTheWorld.game.network.discovery.DiscoveredServer;
import com.conquerTheWorld.game.network.discovery.LanDiscoveryResponder;
import com.conquerTheWorld.game.network.discovery.LanServerDiscovery;
import com.conquerTheWorld.game.network.protocol.AttackSnapshot;
import com.conquerTheWorld.game.network.protocol.PlayerSnapshot;
import com.conquerTheWorld.game.network.protocol.WorldSnapshot;
import com.conquerTheWorld.game.objects.characters.CharacterType;
import com.conquerTheWorld.game.objects.core.Entity;
import com.conquerTheWorld.game.objects.core.Hitbox;
import com.conquerTheWorld.game.objects.core.HitboxCollision;
import com.conquerTheWorld.game.objects.weapons.WeaponHand;
import com.conquerTheWorld.game.objects.weapons.swords.SwordAttack;
import com.conquerTheWorld.game.objects.weapons.swords.SwordAttackFactory;
import com.conquerTheWorld.game.objects.weapons.swords.SwordType;
import com.conquerTheWorld.game.world.WorldLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class NetworkSmokeTest {
    private NetworkSmokeTest() {
    }

    public static void main(String[] args) throws Exception {
        assertDataSheetsAndWorld();
        assertHostAndJoinModes();
        assertCollisionVolumes();
        assertRotatedSwordHitboxes();
        assertStationarySwordAttack();

        UdpGameServer server = new UdpGameServer("127.0.0.1", 0);
        LanDiscoveryResponder discovery = null;
        UdpGameClient first = null;
        UdpGameClient second = null;

        try {
            server.start();
            int port = server.getLocalAddress().getPort();
            discovery = new LanDiscoveryResponder(
                "0.0.0.0",
                0,
                port,
                "Smoke Test LAN Game",
                server::getConnectedPlayerCount
            );
            discovery.start();

            List<DiscoveredServer> initialDiscovery = LanServerDiscovery.discover(
                discovery.getLocalPort(),
                400
            );
            assertDiscovered(initialDiscovery, port, 0);

            first = new UdpGameClient(
                "127.0.0.1",
                port,
                "127.0.0.1",
                0,
                CharacterType.KNIGHT,
                SwordType.QUICK
            );
            second = new UdpGameClient(
                "127.0.0.1",
                port,
                "127.0.0.1",
                0,
                CharacterType.ROGUE,
                SwordType.HEAVY
            );
            first.start();
            second.start();

            waitForConnections(first, second, 3_000L);
            if (first.getPlayerId() == second.getPlayerId()) {
                throw new AssertionError("Clients received the same player ID");
            }
            if (first.getLocalAddress().getPort() == second.getLocalAddress().getPort()) {
                throw new AssertionError("Clients received the same ephemeral UDP port");
            }

            waitForPlayerCount(first, 2, 3_000L);
            waitForPlayerCount(second, 2, 3_000L);
            if (find(first.getLatestSnapshot(), first.getPlayerId()).getCharacterType()
                != CharacterType.KNIGHT) {
                throw new AssertionError("First client was not synchronized as a Knight");
            }
            if (find(first.getLatestSnapshot(), second.getPlayerId()).getCharacterType()
                != CharacterType.ROGUE) {
                throw new AssertionError("Second client was not synchronized as a Rogue");
            }
            if (find(first.getLatestSnapshot(), first.getPlayerId()).getSwordType()
                != SwordType.QUICK
                || find(first.getLatestSnapshot(), second.getPlayerId()).getSwordType()
                != SwordType.HEAVY) {
                throw new AssertionError("Equipped swords were not synchronized while idle");
            }
            waitForAim(first, second, 37f, 215f, 1_000L);
            List<DiscoveredServer> occupiedDiscovery = LanServerDiscovery.discover(
                discovery.getLocalPort(),
                400
            );
            assertDiscovered(occupiedDiscovery, port, 2);

            AttackSnapshot synchronizedAttack = waitForSynchronizedAttack(
                first,
                second,
                SwordType.QUICK,
                1_000L
            );
            if (synchronizedAttack.getOwnerPlayerId() != first.getPlayerId()) {
                throw new AssertionError("The server assigned the attack to the wrong player");
            }
            if (angleDifference(
                synchronizedAttack.getAimAngleDegrees(),
                SwordType.QUICK.snapAngle(37f)
            ) > 0.001f) {
                throw new AssertionError("The quick attack did not preserve snapped mouse aim");
            }
            moveFor(first, second, 0f, 0f, 0f, 0f, 100L);
            waitForAttacksToExpire(first, second, 1_000L);

            AttackSnapshot heavyAttack = waitForSynchronizedAttack(
                second,
                first,
                SwordType.HEAVY,
                1_000L
            );
            if (heavyAttack.getOwnerPlayerId() != second.getPlayerId()) {
                throw new AssertionError("Heavy attack was assigned to the wrong player");
            }
            assertAttackStaysAtSpawnWhileOwnerMoves(second, first, heavyAttack, 1_000L);
            moveFor(first, second, 0f, 0f, 0f, 0f, 100L);
            waitForAttacksToExpire(first, second, 1_000L);

            PlayerSnapshot firstBefore = find(first.getLatestSnapshot(), first.getPlayerId());
            PlayerSnapshot secondBefore = find(first.getLatestSnapshot(), second.getPlayerId());

            long movementDeadline = System.nanoTime() + 700_000_000L;
            while (System.nanoTime() < movementDeadline) {
                first.updateInput(1f, 0f);
                second.updateInput(0f, 1f);
                Thread.sleep(5L);
            }
            long stopDeadline = System.nanoTime() + 100_000_000L;
            while (System.nanoTime() < stopDeadline) {
                first.updateInput(0f, 0f);
                second.updateInput(0f, 0f);
                Thread.sleep(5L);
            }
            waitForPlayerCount(first, 2, 1_000L);
            waitForPlayerCount(second, 2, 1_000L);

            PlayerSnapshot firstAfter = find(first.getLatestSnapshot(), first.getPlayerId());
            PlayerSnapshot secondAfter = find(first.getLatestSnapshot(), second.getPlayerId());
            if (firstAfter.getX() <= firstBefore.getX() + 20f) {
                throw new AssertionError("The server did not move the first player right");
            }
            if (secondAfter.getY() <= secondBefore.getY() + 20f) {
                throw new AssertionError("The server did not move the second player up");
            }
            float knightDistance = firstAfter.getX() - firstBefore.getX();
            float rogueDistance = secondAfter.getY() - secondBefore.getY();
            if (rogueDistance <= knightDistance + 10f) {
                throw new AssertionError("Rogue did not move faster than Knight");
            }
            find(second.getLatestSnapshot(), first.getPlayerId());
            find(second.getLatestSnapshot(), second.getPlayerId());

            moveFor(first, second, -1f, 0f, 0f, 1f, 2_000L);
            moveFor(first, second, 0f, 0f, 0f, 0f, 100L);
            PlayerSnapshot firstAtWall = find(
                first.getLatestSnapshot(),
                first.getPlayerId()
            );
            PlayerSnapshot secondAtWall = find(
                first.getLatestSnapshot(),
                second.getPlayerId()
            );
            float leftPlayerX = Constants.FLOOR_DRAW_SIZE
                - minimumXOffset(CharacterType.KNIGHT.getBodyHitboxes());
            if (firstAtWall.getX() < leftPlayerX - 0.001f) {
                throw new AssertionError("The server allowed a player through the left wall");
            }
            float topPlayerY = WorldLayout.getWorldHeight() - Constants.FLOOR_DRAW_SIZE
                - maximumYExtent(CharacterType.ROGUE.getBodyHitboxes());
            if (secondAtWall.getY() > topPlayerY + 0.001f) {
                throw new AssertionError("The server allowed a player through the top wall");
            }

            System.out.println(
                "PASS: two UDP clients connected on ports "
                    + first.getLocalAddress().getPort() + " and "
                    + second.getLocalAddress().getPort()
                    + ", LAN discovery found the room, and both clients received "
                    + "two character classes, two synchronized sword classes, authoritative "
                    + "movement, 15-degree aim steps, rotated multi-rectangle hitboxes, "
                    + "stationary attacks, mirrored weapon-hand layers, wall collision, "
                    + "a deterministic larger arena, shared JSON registries, a following "
                    + "camera, and snapshots"
            );
        } finally {
            if (first != null) {
                first.close();
            }
            if (second != null) {
                second.close();
            }
            if (discovery != null) {
                discovery.close();
            }
            server.close();
        }
    }

    private static void assertDataSheetsAndWorld() {
        GameDataRegistry data = GameDataRegistry.get();
        if (data.getCharacters().size() != 2
            || data.getSkins().size() != 4
            || data.getWeapons().size() != 2
            || data.getAttacks().size() != 2) {
            throw new AssertionError("The four JSON registries did not load every definition");
        }
        if (!"knight_default".equals(CharacterType.KNIGHT.getDefaultSkinId())
            || CharacterType.ROGUE.getSpeed() <= CharacterType.KNIGHT.getSpeed()) {
            throw new AssertionError("Character JSON values were not applied");
        }
        if (CharacterType.KNIGHT.getDefaultWeaponHand() != WeaponHand.RIGHT
            || CharacterType.ROGUE.getDefaultWeaponHand() != WeaponHand.LEFT) {
            throw new AssertionError("Character weapon-hand values were not applied");
        }
        if (!WeaponHand.LEFT.rendersBeforePlayer(true)
            || WeaponHand.LEFT.rendersBeforePlayer(false)
            || WeaponHand.RIGHT.rendersBeforePlayer(true)
            || !WeaponHand.RIGHT.rendersBeforePlayer(false)) {
            throw new AssertionError("Weapon-hand layers did not mirror with facing direction");
        }
        if (WorldLayout.getWorldWidth() <= Constants.CAMERA_VIEW_WIDTH
            || WorldLayout.getWorldHeight() <= Constants.CAMERA_VIEW_HEIGHT) {
            throw new AssertionError("The enlarged map does not extend beyond the camera view");
        }
        if (WorldLayout.getInteriorWallCount() < 20) {
            throw new AssertionError("The enlarged map did not generate enough interior walls");
        }
        if (!Arrays.deepEquals(WorldLayout.copyMap(), WorldLayout.copyMap())) {
            throw new AssertionError("The seeded map was not deterministic");
        }
        int firstVariant = WorldLayout.tileVariant(3, 8, 9, 0x464C4F4F);
        int repeatedVariant = WorldLayout.tileVariant(3, 8, 9, 0x464C4F4F);
        if (firstVariant != repeatedVariant || firstVariant < 0 || firstVariant >= 9) {
            throw new AssertionError("Tile texture variants were not deterministic");
        }
    }

    private static void assertCollisionVolumes() {
        List<Hitbox> playerHitboxes = CharacterType.KNIGHT.getBodyHitboxes();

        if (WorldLayout.collidesWithWall(
            64f,
            64f,
            0f,
            playerHitboxes,
            CharacterType.KNIGHT.getBodyHeight()
        )) {
            throw new AssertionError("The normal player spawn overlaps a wall");
        }

        if (!WorldLayout.collidesWithWall(
            25f,
            64f,
            0f,
            playerHitboxes,
            CharacterType.KNIGHT.getBodyHeight()
        )) {
            throw new AssertionError("The collision map did not detect the left wall");
        }

        if (WorldLayout.collidesWithWall(
            25f,
            64f,
            Constants.WALL_COLLISION_HEIGHT,
            playerHitboxes,
            CharacterType.KNIGHT.getBodyHeight()
        )) {
            throw new AssertionError("Separated vertical collision volumes overlapped");
        }

        List<Hitbox> curvedHitboxes = Arrays.asList(
            new Hitbox(0f, 0f, 4f, 12f),
            new Hitbox(4f, 8f, 8f, 4f)
        );
        Entity curvedEntity = new Entity(
            32f, 64f, 0f,
            0f, 0f,
            16f, 16f,
            curvedHitboxes,
            8f, 8f, 0f,
            null,
            true
        );
        Entity target = new Entity(
            40f, 72f, 0f,
            0f, 0f,
            4f, 4f,
            Collections.singletonList(new Hitbox(0f, 0f, 4f, 4f)),
            8f, 8f, 0f,
            null,
            true
        );
        if (!HitboxCollision.overlaps(curvedEntity, target)) {
            throw new AssertionError("A secondary rectangle in a hitbox list was ignored");
        }
        target.setGameX(50f);
        if (HitboxCollision.overlaps(curvedEntity, target)) {
            throw new AssertionError("Separated entity hitbox lists overlapped");
        }

        Entity rotatedEntity = new Entity(
            0f, 0f, 0f,
            0f, 0f,
            16f, 16f,
            Collections.singletonList(new Hitbox(0f, 0f, 12f, 3f, 45f)),
            8f, 8f, 0f,
            null,
            true
        );
        target.setGameX(5f);
        target.setGameY(5f);
        if (!HitboxCollision.overlaps(rotatedEntity, target)) {
            throw new AssertionError("Rotated hitbox did not overlap an intersecting target");
        }
        target.setGameX(16f);
        if (HitboxCollision.overlaps(rotatedEntity, target)) {
            throw new AssertionError("Rotated hitbox overlapped a separated target");
        }
    }

    private static void assertRotatedSwordHitboxes() {
        for (SwordType swordType : SwordType.values()) {
            if (Math.abs(swordType.getRotationStepDegrees() - 15f) > 0.001f
                || angleDifference(swordType.snapAngle(7f), 0f) > 0.001f
                || angleDifference(swordType.snapAngle(8f), 15f) > 0.001f
                || angleDifference(swordType.snapAngle(22f), 15f) > 0.001f
                || angleDifference(swordType.snapAngle(23f), 30f) > 0.001f
                || angleDifference(swordType.snapAngle(44f), 45f) > 0.001f) {
                throw new AssertionError("Sword angles did not snap to 15-degree steps");
            }
            List<Hitbox> right = swordType.createHitboxes(true);
            List<Hitbox> left = swordType.createHitboxes(false);
            if (right.size() != left.size() || right.size() < 2) {
                throw new AssertionError("Sword arc is not a multi-rectangle rotated shape");
            }
            for (int i = 0; i < right.size(); i++) {
                Hitbox rightHitbox = right.get(i);
                Hitbox leftHitbox = left.get(i);
                float expectedLeftX = Constants.PLAYER_DRAW_SIZE - rightHitbox.getXOffset();
                float expectedLeftY = Constants.PLAYER_DRAW_SIZE - rightHitbox.getYOffset();
                if (Math.abs(expectedLeftX - leftHitbox.getXOffset()) > 0.001f
                    || Math.abs(expectedLeftY - leftHitbox.getYOffset()) > 0.001f
                    || angleDifference(leftHitbox.getRotationDegrees(), 180f) > 0.001f) {
                    throw new AssertionError("Left sword arc was not rotated by 180 degrees");
                }
            }
            List<Hitbox> upward = swordType.createHitboxes(90f);
            if (angleDifference(upward.get(0).getRotationDegrees(), 90f) > 0.001f) {
                throw new AssertionError("Arbitrary-angle sword hitboxes were not preserved");
            }
            List<Hitbox> stepped = swordType.createHitboxes(37f);
            if (angleDifference(stepped.get(0).getRotationDegrees(), 30f) > 0.001f) {
                throw new AssertionError("Sword hitboxes did not use the snapped display angle");
            }
        }
        if (SwordType.QUICK.createHitboxes(true).size()
            == SwordType.HEAVY.createHitboxes(true).size()) {
            throw new AssertionError("Quick and Heavy swords use the same hitbox shape");
        }
    }

    private static void assertStationarySwordAttack() {
        SwordAttack attack = SwordAttackFactory.create(
            SwordType.QUICK,
            99,
            7,
            96f,
            128f,
            37f
        );
        List<Hitbox> initialHitboxes = attack.getHitboxes();
        float initialWeaponAngle = attack.getCarriedWeaponAngleDegrees();

        attack.update(SwordType.QUICK.getDuration() * 0.5f);
        if (Math.abs(attack.getGameX() - 96f) > 0.001f
            || Math.abs(attack.getGameY() - 128f) > 0.001f
            || attack.getHitboxes() != initialHitboxes) {
            throw new AssertionError("Sword attack position or hitboxes moved during its lifetime");
        }
        if (angleDifference(
            attack.getCarriedWeaponAngleDegrees(),
            initialWeaponAngle
        ) < 0.001f) {
            throw new AssertionError("The carried weapon did not animate independently");
        }

        try {
            attack.setGameX(100f);
            throw new AssertionError("A sword attack allowed its spawn position to change");
        } catch (UnsupportedOperationException expected) {
            // The position is intentionally immutable after construction.
        }
    }

    private static void assertAttackStaysAtSpawnWhileOwnerMoves(
        UdpGameClient attacker,
        UdpGameClient observer,
        AttackSnapshot initialAttack,
        long timeoutMillis
    ) throws Exception {
        PlayerSnapshot initialOwner = find(
            attacker.getLatestSnapshot(),
            initialAttack.getOwnerPlayerId()
        );
        long deadline = System.nanoTime() + timeoutMillis * 1_000_000L;
        while (System.nanoTime() < deadline) {
            attacker.updateInput(1f, 0f, initialAttack.getAimAngleDegrees(), true);
            observer.updateInput(0f, 0f, 0f, false);

            WorldSnapshot snapshot = attacker.getLatestSnapshot();
            AttackSnapshot currentAttack = findAttack(
                snapshot,
                initialAttack.getAttackId()
            );
            PlayerSnapshot currentOwner = find(snapshot, initialAttack.getOwnerPlayerId());
            if (currentOwner.getX() > initialOwner.getX() + 2f) {
                if (currentAttack == null) {
                    throw new AssertionError("Sword attack expired before stationary movement test");
                }
                if (Math.abs(currentAttack.getX() - initialAttack.getX()) > 0.001f
                    || Math.abs(currentAttack.getY() - initialAttack.getY()) > 0.001f) {
                    throw new AssertionError("Server moved a sword attack with its owner");
                }
                return;
            }
            Thread.sleep(5L);
        }
        throw new AssertionError("Attack owner did not move during stationary attack test");
    }

    private static AttackSnapshot waitForSynchronizedAttack(
        UdpGameClient attacker,
        UdpGameClient observer,
        SwordType expectedSwordType,
        long timeoutMillis
    ) throws Exception {
        long deadline = System.nanoTime() + timeoutMillis * 1_000_000L;
        while (System.nanoTime() < deadline) {
            attacker.updateInput(0f, 0f, 37f, true);
            observer.updateInput(0f, 0f, 215f, false);
            List<AttackSnapshot> firstAttacks = attacker.getLatestSnapshot().getAttacks();
            List<AttackSnapshot> secondAttacks = observer.getLatestSnapshot().getAttacks();
            if (!firstAttacks.isEmpty() && !secondAttacks.isEmpty()) {
                AttackSnapshot firstAttack = firstAttacks.get(0);
                AttackSnapshot secondAttack = secondAttacks.get(0);
                if (firstAttack.getAttackId() != secondAttack.getAttackId()) {
                    throw new AssertionError("Clients received different attack IDs");
                }
                if (firstAttack.getSwordType() != expectedSwordType
                    || secondAttack.getSwordType() != expectedSwordType) {
                    throw new AssertionError("Clients received the wrong sword subclass");
                }
                return firstAttack;
            }
            Thread.sleep(5L);
        }
        throw new AssertionError("Both clients did not receive the sword attack");
    }

    private static void waitForAim(
        UdpGameClient first,
        UdpGameClient second,
        float firstAngle,
        float secondAngle,
        long timeoutMillis
    ) throws Exception {
        long deadline = System.nanoTime() + timeoutMillis * 1_000_000L;
        float expectedFirstAngle = first.getSwordType().snapAngle(firstAngle);
        float expectedSecondAngle = second.getSwordType().snapAngle(secondAngle);
        while (System.nanoTime() < deadline) {
            first.updateInput(0f, 0f, firstAngle, false);
            second.updateInput(0f, 0f, secondAngle, false);
            WorldSnapshot snapshot = first.getLatestSnapshot();
            if (snapshot.getPlayers().size() >= 2
                && angleDifference(
                    find(snapshot, first.getPlayerId()).getAimAngleDegrees(),
                    expectedFirstAngle
                ) < 0.001f
                && angleDifference(
                    find(snapshot, second.getPlayerId()).getAimAngleDegrees(),
                    expectedSecondAngle
                ) < 0.001f) {
                return;
            }
            Thread.sleep(5L);
        }
        throw new AssertionError("Stepped player aim did not synchronize");
    }

    private static float angleDifference(float first, float second) {
        float difference = Math.abs(first - second) % 360f;
        return Math.min(difference, 360f - difference);
    }

    private static float minimumXOffset(List<Hitbox> hitboxes) {
        float result = Float.POSITIVE_INFINITY;
        for (Hitbox hitbox : hitboxes) {
            result = Math.min(result, hitbox.getXOffset());
        }
        return result;
    }

    private static float maximumYExtent(List<Hitbox> hitboxes) {
        float result = Float.NEGATIVE_INFINITY;
        for (Hitbox hitbox : hitboxes) {
            result = Math.max(result, hitbox.getYOffset() + hitbox.getHeight());
        }
        return result;
    }

    private static void waitForAttacksToExpire(
        UdpGameClient first,
        UdpGameClient second,
        long timeoutMillis
    ) throws Exception {
        long deadline = System.nanoTime() + timeoutMillis * 1_000_000L;
        while (System.nanoTime() < deadline) {
            first.updateInput(0f, 0f, true, false);
            second.updateInput(0f, 0f, true, false);
            if (first.getLatestSnapshot().getAttacks().isEmpty()
                && second.getLatestSnapshot().getAttacks().isEmpty()) {
                return;
            }
            Thread.sleep(5L);
        }
        throw new AssertionError("Sword attack did not expire on both clients");
    }

    private static void moveFor(
        UdpGameClient first,
        UdpGameClient second,
        float firstX,
        float firstY,
        float secondX,
        float secondY,
        long durationMillis
    ) throws InterruptedException {
        long deadline = System.nanoTime() + durationMillis * 1_000_000L;
        while (System.nanoTime() < deadline) {
            first.updateInput(firstX, firstY);
            second.updateInput(secondX, secondY);
            Thread.sleep(5L);
        }
    }

    private static void assertHostAndJoinModes() {
        String previousMode = System.getProperty("game.mode");
        try {
            System.setProperty("game.mode", "host");
            if (!NetworkConfig.isHostMode() || !NetworkConfig.isClientMode()) {
                throw new AssertionError("Host mode was not recognized as a networked client");
            }

            System.setProperty("game.mode", "join");
            if (!NetworkConfig.isJoinMode()
                || !NetworkConfig.isClientMode()
                || !NetworkConfig.shouldDiscoverServer()) {
                throw new AssertionError("Join mode did not enable LAN discovery");
            }
        } finally {
            if (previousMode == null) {
                System.clearProperty("game.mode");
            } else {
                System.setProperty("game.mode", previousMode);
            }
        }
    }

    private static void assertDiscovered(
        List<DiscoveredServer> servers,
        int expectedGamePort,
        int expectedPlayers
    ) {
        for (DiscoveredServer discovered : servers) {
            if (discovered.getGamePort() == expectedGamePort) {
                if (!"Smoke Test LAN Game".equals(discovered.getName())) {
                    throw new AssertionError("Discovery returned the wrong room name");
                }
                if (discovered.getPlayerCount() != expectedPlayers) {
                    throw new AssertionError(
                        "Discovery returned " + discovered.getPlayerCount()
                            + " players instead of " + expectedPlayers
                    );
                }
                return;
            }
        }
        throw new AssertionError("LAN discovery did not find the test server");
    }

    private static void waitForConnections(
        UdpGameClient first,
        UdpGameClient second,
        long timeoutMillis
    ) throws Exception {
        long deadline = System.nanoTime() + timeoutMillis * 1_000_000L;
        while ((!first.isConnected() || !second.isConnected()) && System.nanoTime() < deadline) {
            first.updateInput(0f, 0f);
            second.updateInput(0f, 0f);
            Thread.sleep(5L);
        }
        if (!first.isConnected() || !second.isConnected()) {
            throw new AssertionError("Clients did not connect before the timeout");
        }
    }

    private static void waitForPlayerCount(
        UdpGameClient client,
        int expectedCount,
        long timeoutMillis
    ) throws Exception {
        long deadline = System.nanoTime() + timeoutMillis * 1_000_000L;
        while (client.getLatestSnapshot().getPlayers().size() < expectedCount
            && System.nanoTime() < deadline) {
            client.updateInput(0f, 0f);
            Thread.sleep(5L);
        }
        if (client.getLatestSnapshot().getPlayers().size() < expectedCount) {
            throw new AssertionError("Client did not receive all players before the timeout");
        }
    }

    private static PlayerSnapshot find(WorldSnapshot snapshot, int playerId) {
        for (PlayerSnapshot player : snapshot.getPlayers()) {
            if (player.getPlayerId() == playerId) {
                return player;
            }
        }
        throw new AssertionError("Snapshot does not contain player " + playerId);
    }

    private static AttackSnapshot findAttack(WorldSnapshot snapshot, int attackId) {
        for (AttackSnapshot attack : snapshot.getAttacks()) {
            if (attack.getAttackId() == attackId) {
                return attack;
            }
        }
        return null;
    }
}
