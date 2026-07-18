# Project structure

```text
game/
  Main.java                         client rendering, camera, UI, debug toggle
  Constants.java                    global rendering/map constants
  assets/TileSet.java               tile texture slicing
  data/                              validated JSON definition classes/registry
  network/
    config/                          modes, ports, loadout, debug defaults
    connection/                      UDP client/server and server main
    discovery/                       LAN room broadcast/response
    protocol/                        packet codec and snapshot records
    testing/                         two-client integration smoke test
  objects/
    core/                            Entity, Prop, Hitbox, collision, rendering contract
    characters/                      Player, Knight, Rogue, type, factory
    weapons/                         shared weapon sprite renderer
      swords/                        sword types, attacks, subclasses, factory
  world/                             GameWorld, WorldLayout, CollisionMath
assets/
  data/                              characters, skins, weapons, attacks JSON
```

Package dependencies point inward: rendering/world code may use object and network records;
connection code uses protocol/config/data; protocol records use stable character/weapon IDs. LAN
discovery does not depend on gameplay classes. A future gun implementation belongs under
`objects/weapons/guns` rather than being mixed into the sword package.
