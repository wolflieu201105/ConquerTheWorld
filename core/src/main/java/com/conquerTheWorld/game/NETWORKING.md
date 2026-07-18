# LAN multiplayer setup

This version uses an authoritative UDP server:

- clients send normalized movement input, never trusted positions;
- the server simulates players at 60 ticks/second;
- the server sends snapshots at 30 snapshots/second;
- clients predict movement between snapshots and correct from the next snapshot;
- idle clients still send input packets, which also act as a heartbeat;
- a session token prevents another local UDP sender from controlling a player.

LAN servers also answer discovery broadcasts. A client can therefore find a running room
without the player typing the host computer's IP address.

The normal workflow no longer requires a separate headless-server window:

- `game.mode=host` starts a server inside the first game and connects the host player to it;
- `game.mode=join` discovers that server and connects the second game instance;
- `game.mode=local` keeps the original offline behavior.

Every snapshot contains every connected player's ID, character type, position, and movement
vector, continuous aim angle, and equipped sword. `GameWorld` creates one renderable `Player` per
ID, updates remote positions, and removes
players that leave. The game draws labels such as `YOU (P1) - Knight` and `P2 - Rogue` above
the synchronized characters.

## Screen-space labels and full-map camera

The world camera now uses a 320x256 logical view, exactly matching the current bordered map, so
the complete wall border stays visible without exposing world space beyond it. `resize()` uses a
whole-number scale and centers that viewport to preserve pixel-art sampling. `updateCameraFollow()` still clamps to
`WorldLayout.getWorldWidth()` and `getWorldHeight()`; when a later map is larger than the view,
the camera follows the local player without crossing the map boundary.

Player names are no longer drawn through the low-resolution world camera. `Main` projects each
player's world position to the window, switches to a native-resolution UI camera, and draws the
label with a black four-direction outline and a white center. The font is therefore independent
of tile zoom and scales with window height.

Press `F3` at any time to toggle the play-test overlay. It shows player body rectangles, rotated
sword rectangles, and solid wall footprints. The startup default can still be set with
`-Dgame.debug.hitboxes=true` or `GAME_DEBUG_HITBOXES=true`.

## Package layout

The source packages now match responsibility:

- `network/connection`: host/client sockets and the standalone server entry point;
- `network/protocol`: packet codec and immutable snapshot records;
- `network/discovery`: LAN room discovery;
- `network/config`: command-line, system-property, and environment configuration;
- `objects/core`: base entity, prop, renderable, hitbox, and collision classes;
- `objects/characters`: player parent, character subclasses, type, and factory;
- `objects/weapons`: shared weapon rendering;
- `objects/weapons/swords`: sword type, attack parent/subclasses, and factory;
- `world`: `GameWorld`, shared layout, and collision math;
- `data`: JSON definitions, validation, and the gameplay registry.

## JSON gameplay sheets

Four files under `assets/data` are loaded and validated by `GameDataRegistry`:

- `characters.json`: stable/network IDs, names, speed, body height, skins, and body rectangles;
- `skins.json`: stable skin IDs, compatible character, texture/frame layout, and tint;
- `weapons.json`: stable/network IDs, category, sprite, draw size, grip, and attack reference;
- `attacks.json`: timing, cooldown, collision height, swing range, and hitbox rectangles.

The client and headless server read the same numbers. Set `game.data.dir` or `GAME_DATA_DIR` only
when the JSON is stored somewhere other than the normal `assets/data` directory. IDs must remain
stable after release; do not renumber existing network IDs.

## Mouse aiming, camera follow, and rotating pixel-art weapons

libGDX can rotate a pixel-art texture by any angle. The art remains a low-resolution sprite;
the difference is that `SpriteBatch.draw(...)` rotates the complete sprite around a chosen grip
point instead of requiring a separate animation frame for every direction.

The local aiming path is:

1. `Main.updateCameraFollow()` centers/clamps the 320x256 view, showing the complete bordered
   arena. If a later map is larger, the same method follows the player inside its limits.
2. `camera.unproject(...)` converts the mouse from window coordinates into world coordinates,
   including the integer-scaled viewport and letterbox offset.
3. `GameWorld.setLocalAimTarget(...)` passes that target to the local `Player`.
4. `Player.aimAt(...)` uses `atan2(mouseY - playerY, mouseX - playerX)` to store a 0-360 degree
   aim angle. The character sheet still uses left/right rows, chosen from the angle's X direction.
5. `WeaponRenderer` draws the equipped sword around its grip pixel at that angle. During an
   attack it uses `SwordAttack.getRenderAngleDegrees()` to animate through that sword type's
   swing arc.

The mouse controls are:

- move with `W`, `A`, `S`, and `D`;
- aim by moving the mouse;
- swing with the left mouse button; `Space` remains as a compatibility attack key.

Weapon assets are optional while prototyping. `WeaponRenderer` first looks for:

```text
assets/weapons/quick_sword.png
assets/weapons/heavy_sword.png
```

If a file is missing, it creates a small nearest-filtered pixel sword at runtime, so the game is
still runnable. Replacement artwork should point to the right, use a transparent background, and
place the handle near the left edge. The render size and grip pivot are configured per entry in
`SwordType` (`drawWidth`, `drawHeight`, `gripX`, and `gripY`). Adjust those four values when the
new sprite's proportions or handle location differ.

The collision rectangles are a line-based debug overlay so they do not cover the weapon art.
Toggle them with `F3`.

In multiplayer, the client sends the angle as input rather than sending a trusted position. The
server validates and stores the angle, creates attacks from that authoritative direction, and
includes both player and attack angles in snapshots. Consequently, the same rotating weapon is
visible on the host and every joining client. Protocol version 4 is required; version 3 clients
cannot join a version 4 server.

## Character and sword classes

`Player` is now an abstract parent class containing the behavior shared by all players: input,
animation, rendering, movement state, and wall collision. The two concrete character classes are:

| Character | Class | Speed | Collision height | Body hitboxes |
| --- | --- | ---: | ---: | ---: |
| Knight | `KnightPlayer` | 80 | 32 | 1 rectangle |
| Rogue | `RoguePlayer` | 110 | 26 | 2 rectangles |

`PlayerFactory.create(type, x, y)` constructs the correct subclass. `CharacterType` preserves the
small code-level identity while delegating IDs and stats to the matching entry in
`characters.json`. The default skin ID resolves through `skins.json`; the headless server reads
the same movement/collision data without loading textures.

`SwordAttack` is the abstract parent for short-lived sword swings. It contains the shared
owner, aim angle, lifetime, and multi-rectangle hitbox behavior. The concrete attacks are:

| Sword | Class | Duration | Cooldown | Hitboxes |
| --- | --- | ---: | ---: | ---: |
| Quick Sword | `QuickSwordAttack` | 0.22 seconds | 0.30 seconds | 3 rectangles |
| Heavy Sword | `HeavySwordAttack` | 0.36 seconds | 0.60 seconds | 4 rectangles |

`SwordAttackFactory.create(...)` constructs the selected attack subclass. `SwordType` preserves
the code-level identity and resolves weapon/attack values from `weapons.json` and `attacks.json`;
it rotates those rectangles to any aim angle. With the debug overlay enabled, Quick attacks render
cyan for you and orange for other players, while Heavy attacks render blue for you and red for
other players.

Choose a loadout with system properties:

```text
-Dgame.character=knight
-Dgame.sword=quick
```

or use `rogue` and `heavy`. The equivalent environment variables are `GAME_CHARACTER` and
`GAME_SWORD`. A client sends both choices in its connection packet. The authoritative server
uses the chosen stats, then includes the character and sword type in snapshots so every client
constructs the same subclasses.

To add another character with unique behavior later:

1. Add the stats and permanent network ID to `characters.json`.
2. Add one or more compatible entries to `skins.json`.
3. Add a small identity entry to `CharacterType`.
4. Create a new `Player` subclass and register it in `PlayerFactory`.

To add another sword attack, add `weapons.json` and `attacks.json` entries, then follow the same
pattern with `SwordType`, a `SwordAttack` subclass, and `SwordAttackFactory`. All clients and the
server must use the same JSON data and enum/network IDs.

## Wall collision

Every entity owns an immutable view of a `List<Hitbox>`. Each `Hitbox` is a rectangle relative
to the entity's `gameX` and `gameY`:

```java
new Hitbox(xOffset, yOffset, width, height)
```

For example, the Knight's 32×32 sprite uses a smaller body rectangle:

```java
new Hitbox(6f, 2f, 20f, 14f)
```

That rectangle is centered horizontally and covers the lower portion of the sprite instead of
making the entire image collide with a wall. Character hitboxes are defined in
`assets/data/characters.json`.

Every collidable entity therefore has a 3D volume composed of:

- one or more offset rectangles in the X/Y plane;
- collision height along Z, starting at `gameZ`.

Movement is attempted one axis at a time. If an entity's volume overlaps a solid wall prop,
the world calls:

```java
entity.wallCollide(wall)
```

Returning `true` cancels movement on that axis; returning `false` allows the entity through.
`Player` returns `true`, so players stop at walls while retaining movement along the other axis.
This creates natural wall sliding when diagonal input is held.

Multiple rectangles approximate each implemented sword arc. Left-click or press `Space` to
attack. The game
uses the factory to create a separate `SwordAttack`, leaving the player's body hitbox unchanged:

```java
SwordAttack swordAttack = SwordAttackFactory.create(
    SwordType.QUICK,
    attackId,
    ownerPlayerId,
    playerX,
    playerY,
    player.getAimAngleDegrees()
);

if (HitboxCollision.overlaps(swordAttack, enemy)) {
    // The server can resolve health/damage here.
}
```

The attack is non-solid and does not participate in player wall collision. The chosen
`SwordType` supplies its rectangles, duration, and cooldown.

Sword rectangles rotate around the center of the player and retain their individual rotation.
`HitboxCollision` uses an oriented-rectangle Separating Axis Theorem test, so the collision shape
follows the visible weapon instead of expanding into a loose axis-aligned box.

In multiplayer, clients send aim angle and current attack-button state. The server detects an
attack-button up-to-down transition, enforces the selected sword's cooldown, creates the attack
ID, and sends the type, direction, and attack in world snapshots. The current prototype
synchronizes and renders attacks but does not yet contain health or damage.

`WorldLayout` owns the map shared by the rendered world and the headless server. The server
performs the same collision test before changing authoritative player positions, so a client
cannot walk through a wall and later send that invalid position to other players.

UDP is used because game inputs and snapshots are time-sensitive. A newer input or snapshot
supersedes an older lost one. The numeric port does not affect speed; `54555` is simply an
unprivileged default game port. Each client defaults to local port `0`, which asks the operating
system for a free ephemeral port and lets several clients run on one computer.

Two UDP ports are used by default:

- `54554`: LAN discovery only;
- `54555`: connection, input, and world snapshots.

## How LAN joining works

1. The server binds game port `54555` and listens on all local interfaces.
2. The server listens for discovery questions on UDP port `54554`.
3. A joining client broadcasts a discovery question to each local IPv4 network.
4. The server replies directly with its room name, game port, and current player count.
5. The client chooses the first response and sends a `CONNECT` packet to that address.
6. The server assigns a player ID and private session token.
7. The client sends movement input; the server simulates positions and sends snapshots.

Discovery broadcasts stay inside the local broadcast network. They are not intended to cross
the public Internet or most routers.

## Same computer

Open two terminals or two IDE run configurations. Start the first game as the host:

```text
-Dgame.mode=host
-Dgame.server.name="Local Test"
-Dgame.character=knight
-Dgame.sword=quick
```

Start the second copy of the game as a joining player:

```text
-Dgame.mode=join
-Dgame.character=rogue
-Dgame.sword=heavy
```

Each game uses a different automatic client port, so both can run on one computer. If using an
IDE, enable its option to allow multiple simultaneous instances of the desktop launcher.

Environment variables are often easier when using Gradle. In PowerShell window 1:

```powershell
$env:GAME_MODE = "host"
$env:GAME_SERVER_NAME = "Local Test"
$env:GAME_CHARACTER = "knight"
$env:GAME_SWORD = "quick"
./gradlew lwjgl3:run
```

In PowerShell window 2:

```powershell
$env:GAME_MODE = "join"
$env:GAME_CHARACTER = "rogue"
$env:GAME_SWORD = "heavy"
./gradlew lwjgl3:run
```

Replace `lwjgl3:run` with the run task from the full game project. The uploaded source snapshot
does not include its Gradle launcher modules.

## Local network on different computers

Start the first game on the host computer with:

```text
-Dgame.mode=host
-Dgame.server.name="Huy's Room"
```

Allow inbound UDP ports `54554` and `54555` through the host computer's firewall. On another
computer connected to the same LAN, start the game with:

```text
-Dgame.mode=join
```

`0.0.0.0` means “let the operating system use the correct local interface.” A client only
needs a fixed bind IP or fixed port for unusual routing/firewall requirements.

The current game has no room-selection UI. If several LAN servers reply, the code selects the
first/lowest-latency response. `LanServerDiscovery.discover(...)` returns the complete list so
a menu can display every room later.

## Direct-IP fallback

If a network blocks broadcast discovery, find the host computer's LAN IPv4 address (for
example `192.168.1.50`) and connect directly:

```text
-Dgame.mode=client
-Dgame.server.host=192.168.1.50
-Dgame.server.port=54555
-Dgame.client.bind=0.0.0.0
-Dgame.client.port=0
```

Discovery port `54554` is not needed for direct-IP joining, but gameplay port `54555` must be
allowed through the host firewall.

## Offline mode

With no `game.mode` option, the original local-only game still runs. You can also set:

```text
-Dgame.mode=local
```

The equivalent environment variables include `GAME_MODE`, `GAME_SERVER_HOST`,
`GAME_SERVER_PORT`, `GAME_SERVER_BIND`, `GAME_SERVER_NAME`, `GAME_SERVER_DISCOVER`,
`GAME_DISCOVERY_PORT`, `GAME_DISCOVERY_TIMEOUT`, `GAME_CLIENT_BIND`, and `GAME_CLIENT_PORT`.
Character loadouts additionally use `GAME_CHARACTER` and `GAME_SWORD`.

The server accepts `--no-discovery` when only direct-IP joining should be available.

## Optional separate headless server

For a dedicated server instead of `game.mode=host`, run:

```text
com.conquerTheWorld.game.network.connection.GameServerMain --bind 0.0.0.0 --port 54555 --discovery-port 54554 --name "Dedicated Room"
```

Then start every game window with `-Dgame.mode=join`.

## Production notes

The protocol is intentionally small and suitable for this movement prototype. Before adding
combat or public Internet hosting, add input acknowledgement/reconciliation, snapshot
interpolation, reliable messages for inventory/chat, rate limiting, and real authentication.
