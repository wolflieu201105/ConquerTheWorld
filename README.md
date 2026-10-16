# Conquer the World: Scrapborn

An in-development libGDX action roguelite about rebuilding robotic bodies from Cores, Parts, weapons,
and recovered company technology. The first public target is a free, solo, offline demo. Multiplayer
code currently in the repository is an experimental foundation for a later cooperative release.

## Current prototype

- Responsive title and offline-play menus
- Local arena movement, aiming, camera follow, and collision
- One temporary deterministic enemy that pursues and performs timed melee attacks
- Authored player health and weapon damage with enemy defeat and an offline health HUD
- Three selectable predefined fighting rooms with authored geometry and spawn points
- JSON-defined characters, skins, swords, attacks, and fighting rooms
- `Esc` pause menu with Resume, Restart Room, and Title Screen actions
- Focused JUnit tests for health, combat damage, room validation, and pause ownership
- Debug hitbox rendering
- Experimental server-authoritative LAN host/join and discovery

The authoritative scope and implementation backlog are in
[`GAME_RELEASE_ROADMAP.md`](GAME_RELEASE_ROADMAP.md). Narrative canon is in
[`STORY_BIBLE.md`](STORY_BIBLE.md). Code and asset placement rules are in
[`docs/architecture/PROJECT_STRUCTURE.md`](docs/architecture/PROJECT_STRUCTURE.md).

## Modules

- `core`: shared application, content, gameplay, presentation, and networking code
- `lwjgl3`: desktop launcher

Android and iOS modules are planned but do not exist yet.

## Run and verify

On Windows:

```powershell
.\gradlew.bat lwjgl3:run
.\gradlew.bat core:compileJava lwjgl3:compileJava test
.\gradlew.bat core:networkSmokeTest
```

On macOS or Linux:

```bash
./gradlew lwjgl3:run
./gradlew core:compileJava lwjgl3:compileJava test
./gradlew core:networkSmokeTest
```

The desktop runnable JAR is produced by `lwjgl3:jar` under `lwjgl3/build/libs`.
