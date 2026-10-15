# Project structure

This repository is organized by responsibility. Package names are lowercase and production source,
tests, assets, documentation, and platform launchers are kept separate.

```text
ConquerTheWorld/
  assets/
    data/                                  authored JSON gameplay definitions
    textures/
      characters/                         character sprite sheets
      world/                              tiles and world textures
  core/
    src/main/java/com/conquertheworld/game/
      application/                        game startup and high-level flow
      config/                             shared constants and configuration
      content/
        asset/                            asset loading helpers
        definition/                       JSON records, validation, and registries
      gameplay/
        character/                        playable character models and factories
        combat/weapon/                    weapon rules and weapon families
        entity/                           entities, props, hitboxes, and collision contracts
        world/                            world state, layout, and world collision
      networking/                         post-demo networking prototype
        config/                           network modes and runtime options
        connection/                       clients, servers, and dedicated entry point
        discovery/                        LAN discovery
        protocol/                         packets and snapshots
      presentation/
        menu/                             title and offline-play UI
        rendering/                        visual-only gameplay renderers
    src/smokeTest/java/com/conquertheworld/game/
      networking/testing/                 network integration smoke test
  lwjgl3/
    src/main/java/com/conquertheworld/launcher/lwjgl3/
                                            desktop launcher and startup helper
  docs/
    architecture/                         codebase organization and design rules
    networking/                           networking prototype documentation
    reference/                            older/reference design documents
  GAME_RELEASE_ROADMAP.md                 scope, decisions, milestones, and release gates
  STORY_BIBLE.md                          narrative canon and open story questions
```

## Dependency direction

- `application` coordinates screens, gameplay, presentation, and optional networking.
- `presentation` may read gameplay state, but gameplay rules must not depend on menu or renderer
  classes.
- `gameplay` may use stable definitions from `content`, but it must not import desktop launcher code.
- `networking` exchanges IDs and snapshots. Platform APIs belong in launcher modules, not `core`.
- `lwjgl3` depends on `core`; `core` never depends on `lwjgl3`.
- Automated tests mirror the package they cover under `src/test/java`. Standalone diagnostic programs
  use a named source set such as `src/smokeTest/java`; neither belongs in production source.

Some early entity classes still expose libGDX rendering contracts. Separate those contracts further
when the renderer is replaced; do not add new rendering behavior to gameplay models meanwhile.

## Where future systems belong

- Core types, Parts, resonance, stats, inventory, manufacturing, stages, rooms, enemies, objectives,
  loot, and extraction each receive a focused package under `gameplay` when implemented.
- Serializable authored records and registries belong under `content/definition`; runtime state does
  not.
- New weapon families belong beside `gameplay/combat/weapon/sword`, such as `gun` or `shield`.
- Reusable visual widgets belong under `presentation/ui`; complete menu or gameplay screens belong
  under a matching `presentation` package.
- Android and iOS launchers should be separate Gradle modules. Shared rules remain in `core`.
- Never organize source by vague buckets such as `misc`, `helpers`, or a generic `objects` package.
  Name the responsibility the code owns.

## Asset placement

- JSON definitions: `assets/data/<system>.json`
- Character sprites: `assets/textures/characters/<character-or-skin>.png`
- World art: `assets/textures/world/<tileset-or-prop>.png`
- Future UI, effects, weapons, audio, fonts, and localization should use their own top-level asset
  categories rather than being mixed with character or world textures.

Asset references are relative to `assets`. Moving an asset requires updating every corresponding JSON
or Java reference in the same change.
