# Conquer the World: Scrapborn - Story Bible

Last reviewed: 2026-10-15
Status: Living story draft
Related project: *Conquer the World*

This file is the canonical home for the game's story, setting, characters, terminology, and open
narrative decisions. The original supplied narrative is preserved at the end of the file so future
edits do not erase the source material.

Implementation tasks, priorities, and release gates belong in `GAME_RELEASE_ROADMAP.md`. This file
records only the fiction, gameplay rules that affect the fiction, and unresolved creator choices.

## Canon rules

- **Established canon** comes directly from the supplied story or a decision explicitly approved
  by the creator.
- **Gameplay canon** is an approved mechanic that exists in the fiction, even if its fictional
  explanation still needs to be written.
- **Proposed** material is a suggestion only. It does not become canon until approved.
- **Open question** means the story currently has no definite answer.
- When changing established canon, record the change in the change log at the end of this file.

## Current title status

- The confirmed game title is ***Conquer the World: Scrapborn***.
- *Conquer the World* remains the repository/project name where a shorter technical name is needed.

## First-demo implementation scope

- The first public demo runs entirely local/offline on each supported platform. It has no LAN,
  Internet multiplayer, cross-device play, remote account, or backend dependency.
- Cross-platform multiplayer remains part of the later full-game direction, not the first demo.
- For the offline demo, the in-world consciousness cloud, persistent inventory/progression, and
  encounter log are represented by durable local save data rather than a real online service.
- The offline demo is solo-only with exactly one local player. Same-device cooperative play may be
  reconsidered later; the overall later multiplayer target remains 1–6 players.
- The game is PvE: the solo player and later cooperative party fight enemies and complete shared
  objectives. Competitive PvP is not part of the demo or foreseeable roadmap.

## One-sentence story premise

A discarded experimental consciousness rebuilds itself on a planet the Hub considers waste, then
leads its new community in reclaiming the technology needed to survive before Project Clean Slate
arrives to erase their inhabited world.

## Player fantasy

The player is a consciousness housed in a mechanical Core. They survive, adapt, and fight by
building a body from interchangeable Parts. Their strength does not come from finding one perfect
body; it comes from choosing the right imperfect body for the next stage and protecting a society
that the wider civilization refuses to recognize as alive.

## Narrative pillars

1. **Personhood is not granted by a registry.** The Scrap Planet's inhabitants are people even
   though the Hub's records list their world as waste.
2. **Repair is an act of resistance.** The company and Hub discard failed technology; the player
   preserves, repairs, and gives it new purpose.
3. **A body can change without erasing the self.** The Core carries identity while Parts change
   capability and outward form.
4. **Community turns survival into a future.** Reclaimed facilities matter because they provide
   power, shelter, tools, transport, and security to settlements.
5. **Every build is a meaningful tradeoff.** Resonating Parts strengthen the Core, while other
   Parts provide different abilities that must be given up to obtain that synergy.
6. **The threat is systemic as well as physical.** Project Clean Slate exists because a governing
   system can classify a populated world as an unregistered accumulation of obsolete material.

## Tone

### Established

- Post-industrial science fiction built from discarded machines, ships, factories, and bodies.
- Serious stakes balanced by repair, cooperation, ingenuity, and hope.
- Violence has material consequences: a destroyed body costs equipment and can leave a Core
  stranded even when consciousness survives.
- The emotional center is the Scrap Planet's community rather than conquest for personal power.

Open tone, choice, and ending decisions are tracked as PRES-Q02 and NAR-09 under **Open creator
decisions**.

## The world

### The Hub

The Hub controls registered worlds. It governs transport, enforces law, and maintains the security
systems that keep recognized planets running. Registered settlements have a place in its records;
the Scrap Planet does not.

The story has not yet established whether the Hub is deliberately cruel, bureaucratically
indifferent, deceived by the company, or internally divided. It has approved Project Clean Slate,
but its ultimate leadership and motives remain open.

### The Scrap Planet

The Scrap Planet formed from discarded technology far outside the Hub's registered network. Its
surface contains broken machines, failed prototypes, abandoned ships, and dismantled factories.
Visiting vessels recover valuable material or deposit more waste.

Living consciousnesses inhabit service machines and bodies assembled from forgotten Parts. Their
settlements form around generators, repair benches, and shelters made from shipping containers.
They survive by scavenging, repairing, sharing, and sometimes competing over increasingly scarce
working technology.

The Hub's cleanup records describe the planet's composition, salvage value, and removal equipment.
They contain no population count.

### Reclaimed facilities

Abandoned company facilities still contain power, workshops, storage, transport systems,
maintenance machines, security units, and central guardians. Reclaiming a facility makes it a base
and gives the Scrap Planet access to a new category of supplies or infrastructure.

At least one reclaimed facility contains a neural Backup Terminal capable of storing a
consciousness and restoring it into a compatible body.

### Project Clean Slate

Project Clean Slate is a Hub-approved cleanup operation targeting unregistered accumulations of
obsolete technology. The Scrap Planet is scheduled for removal because it is recorded as waste
rather than as an inhabited world.

The operation has a schedule, but the exact deadline, cleanup method, commanding organization, and
meaning of "removal" have not been established.

## Stages and rooms

Established gameplay canon:

- The overall game targets 1–6 player-controlled characters per room/stage, including the host;
  six is the hard multiplayer maximum. The offline demo has exactly one local player.
- A stage is an expedition through a location such as a company complex, ship, abandoned facility,
  or another connected site.
- Each stage is structured as a graph of rooms rather than one uninterrupted arena.
- The player enters a room without automatically seeing the entire remaining route.
- Clearing the current room reveals the other rooms directly connected to it, allowing the player
  or party to choose where to go next.
- Newly revealed connections appear only as unknown paths. The next room's type, encounter, reward,
  and lore are not shown before the party enters it.
- Players may backtrack through previously cleared rooms.
- Every battle room has an assembly room immediately before it. The battle door remains closed
  until every active player is physically together inside the assembly room, then opens for the
  party. Players are not force-teleported into a battle selected by somebody else.
- In the solo offline demo, defeating the sole player ends the stage attempt immediately. AI
  companions cannot revive the player or carry/use a recovery USB on the player's behalf.
- The downed timer, revive circle, spectator, and USB teammate-recovery rules below apply to the
  later multiplayer game rather than the solo demo.
- During combat, a downed player creates a revive circle. A living teammate can remain inside the
  circle for the required duration to revive that player with exactly 1 HP.
- If the reviving teammate leaves the circle before completion, revival progress resets to zero.
- If the entire party is downed and nobody can complete a revive, the current game/run ends because
  no survivor remains to reach a consciousness-download room. The party returns to its company/base
  preparation flow instead of automatically restarting the stage.
- If a player's connection is lost, their body remains at its last authoritative position while
  waiting for that player to reconnect. The host decides whether to keep that retained player or
  kick them from the party.
- A disconnected retained body counts as present if its position is already inside an assembly
  room, and enemies can still damage or down it.
- Taking damage does not interrupt a revive attempt. Revival progress resets only when the reviving
  teammate leaves the revive circle before completion.
- A reviving teammate may continue attacking while remaining inside the circle.
- Additional teammates do not accelerate revival; the progress rate is fixed whenever at least one
  eligible teammate remains in the circle.
- A downed player has a 15-second revival window. An active valid revive pauses that expiration
  timer, and leaving the circle resumes it from the remaining time while resetting revive progress.
- A teammate must remain inside the revive circle for 5 continuous seconds to succeed.
- When the 15-second window expires, normal teammate revival is no longer possible and that player
  spectates through a camera following a surviving player.
- A spectator can switch the camera among eligible living teammates but cannot move it independently.
- Clearing an ordinary room does not restore a spectator. During the same stage, they can return
  only if the surviving party reaches a consciousness-download room.
- A consciousness-download room contains a USB/data key and a limited supply of prepared Core/bodies
  with loaner Parts. An active player takes the key back to a fallen teammate, extracts that
  teammate's active consciousness itself, and inserts it into an available body to revive them.
- Each download room contains exactly one reusable USB device. It can carry only one active
  consciousness at a time, has no gameplay expiration countdown, and is cleared for reuse after
  that consciousness is successfully installed into a prepared body. Players can repeat the trip
  for another fallen teammate while another unused prepared body remains.
- If the USB carrier is downed or disconnected, the USB and its consciousness payload drop safely
  at that body's location. Another player can loot the body, recover the device, and continue the
  unfinished transfer.
- The USB exists only within the current stage and cannot be extracted to a personal base.
  Persistent possessions, ownership, research, blueprints, and progression synchronize to the cloud.
- Each download room contains a run-randomized number of prepared bodies from 1 through the current
  player count, strongly weighted toward containing exactly 1. The offline simulation generates the
  result in the demo; a later server will authoritatively reproduce the same seeded rule.
- The bodies, Cores, and Parts are associated with the company behind the current stage. Each Part
  independently selects a company-associated model/variant: 94.9% weak, 5% normal, and 0.1%
  exceptionally strong. Mechanically different tiers use distinct model IDs; copies of the same
  model never receive randomly different stats. A prepared body can still contain a mixture of
  tiers.
- Prepared bodies and their equipment are not reserved exclusively for revival. Any player may
  transfer into an available Core/body or loot its Parts, potentially consuming an opportunity that
  could otherwise restore a dead teammate.
- When a living player transfers into a prepared body, their former body stays at the transfer
  location as loot with its functional Core and Parts. The consciousness moves entirely into the
  new Core; the old Core remains usable hardware but does not retain a duplicate consciousness.
  Another eligible character may immediately transfer into that unoccupied functional Core/body,
  or the party may carry it away as loot.
- The restored player or any teammate can backtrack to the death location and loot/recover the
  original Parts. Equipment that came from a player's company storage retains that player's
  persistent-owner ID: a teammate may carry or use it during the stage, but must physically return
  it to the revived owner before extraction. It returns to company storage only while carried or
  equipped by its owner; otherwise it stays behind and is lost.
- If the stage/run contains no reachable consciousness-download room, the dead player remains a
  spectator until it is cleared and can play again only after returning to preparation for a later
  stage.
- Clearing the designated final objective room secures the stage and unlocks a post-clear recovery
  phase. The stage is completed only when the player explicitly extracts afterward.
- The final objective room may contain a traditional boss, require an achievement or objective, or
  deliver/resolve an important piece of lore instead of always being a boss fight.
- Post-clear recovery remains dangerous. Traps, environmental hazards, and surviving threats can
  still damage and kill the player; death before extraction fails the run and loses all deployed
  equipment and uncommitted loot. Cleared combat encounters do not automatically repopulate.
- Successful extraction is located in the final room. Depending on the room type, the extraction
  point may appear or activate after a boss/objective/lore event, or an existing exit may open when
  the configured clear condition is satisfied. After backtracking for loot, the player must return
  to that final-room extraction point.
- In later cooperative play, at least half of the active living player bodies, rounded up, must be
  inside the extraction point to start a 10-second countdown. Completing it extracts the entire
  party, including living players outside the point. If every active living player enters at any
  time, extraction is immediate. The solo demo therefore extracts immediately. Once quorum starts
  the countdown, players leaving the point do not pause, reset, or cancel it. Dead spectators are
  excluded from the denominator and presence count. A disconnected retained living body remains in
  both calculations until the host kicks it and counts as present only if physically inside the
  extraction point. A downed player remains active during their 15-second revival window and counts
  as present when their downed body is physically inside; expiration moves them to spectator state
  and removes them from both calculations. If extraction resolves while they are still downed, they
  count as dead: their equipped gear and carried inventory remain with the stage and are lost. Their
  synchronized consciousness returns to base under the ordinary recovery rule unless it was the
  active payload in a lost USB.

Confirmed room categories:

- **Combat room:** Fight enemies or waves to clear the room.
- **Core room:** Inspect and change an allowed Core during the stage.
- **Weapon room:** Inspect and change an allowed weapon during the stage.
- **Consciousness-download room:** Restore a dead teammate into one of a limited, randomized supply
  of company-associated prepared Core/bodies. Recovery requires a
  reusable USB/data-transfer device to extract the fallen teammate's active consciousness and
  install it into an available body. There is one USB per room, it carries one consciousness at a
  time, and it can be reused sequentially. Living players may also transfer into or loot those bodies.
- **Recruitment room:** Meet someone trapped or stranded inside the stage who joins and fights with
  the player or party for the remainder of that stage only.
- **Boss room:** Defeat a boss with room-specific mechanics and rewards.
- **Objective/achievement room:** Complete a non-boss challenge or required action.
- **Lore room:** Discover or participate in a story event that can serve as a room or stage
  objective.

The room graph supports exploration and build adaptation: a player may choose between immediate
combat, changing part of a build, recruiting help, pursuing lore, or taking another revealed path.
The choice carries uncertainty because connected paths do not disclose what is behind them.

Every individual room is predefined and authored before play. Procedural generation does not invent
room geometry or narrative scenes during a run. Instead, stages use one of two structures:

- **Farming stage:** The game uses a shared random seed to arrange and connect compatible predefined
  rooms. These repeatable expeditions focus on collecting materials while varying the route,
  encounters, and available opportunities.
- **Story/lore stage:** The complete room graph, placement, encounter order, and important events are
  fixed so narrative pacing and required revelations remain intentional.

## Approved PvE mode family

The first game modes are approved in this development order. The free solo demo includes only story
and farming expeditions. The remaining four modes follow after the demo in the listed order.

1. **Story expeditions:** Fixed authored stages with narrative events, controlled reveals, and
   story-critical objectives.
2. **Farming expeditions:** Random arrangements of compatible authored rooms, focused on recovering
   materials and discovering manufacturer technology.
3. **Boss hunts:** Replayable fights that reward learning a boss and preparing specialized
   Core/Part builds.
4. **Settlement defense:** Missions that protect generators, terminals, important NPCs, or other
   settlement assets whose simulated condition determines success.
5. **Rescue/escort missions:** Missions that recover characters, Cores, or critical machinery while
   preserving authored story outcomes and using real AI movement, combat, and HP rules.
6. **Challenge contracts:** PvE stages altered by declared modifiers such as stronger enemies,
   restricted Parts, restricted Cores, added hazards, or increased rewards.

These modes share the same room-graph expedition, Core/Part build, combat, reward, loss, and
progression foundations. Large-scale raids come later, and none of these modes introduces
competitive PvP.

## Bases, research, and roguelite progression

Each player has their own persistent base progression outside a temporary stage run. A recovered
Part is more than immediate equipment: it can become the first physical sample used to understand a
manufacturer's technology, develop a blueprint, and eventually mass-produce that Part.

Any teammate may collect and temporarily use equipment left at another player's death location.
Every Core, Part, and weapon taken from company storage carries an immutable persistent-owner ID.
If the party extracts successfully, owner-bound equipment returns to company storage only when its
owner is personally carrying or equipping it. A non-owner cannot deposit, research, or retain it,
and carrying it through extraction does not send it back automatically; it stays behind and is lost.
This mismatch triggers a clear warning naming the affected possessions and their owner, but the
warning never blocks or delays extraction.
Previously unbound stage loot remains unowned regardless of who first picks it up. When extraction
commits it, ownership goes to the eligible player carrying or equipping it at that instant.
A failed run still brings no deployed equipment or recovered loot home.

After a failed stage, the party returns to its company/base rather than immediately beginning
another attempt. The temporary room graph, encounters, carried run inventory, uncommitted loot, and
every Core, Part, or other equipment instance deployed into that run are lost. Persistent research,
blueprints, company facilities, base progression, and every item left safely in base storage remain
so every player can prepare a replacement. A player may manufacture a completely new robot or equip
a robot entirely from surviving stored items. Another stage cannot begin until that preparation is
complete; the game supplies no free replacement gear.

Entering a stage is a one-way commitment. The party may backtrack among rooms inside its cleared
room graph, but it cannot return to base, retreat, or extract early. The attempt ends only when the
final objective has been cleared and the party explicitly extracts, or when the party fails.
Consequently, every Core, Part, and item brought into the stage is gone on failure, while anything
that never left the base remains safe.

The cloud records every enemy that actually spawns during an attempt, even when the party fails.
Those records form a persistent encounter log the players can inspect at the base to learn what has
appeared in that stage and manufacture more appropriate equipment before trying another expedition.
The log contains only information the players could directly observe, such as an enemy's visible
identity and witnessed actions. It does not automatically reveal internal statistics, hidden
weaknesses, unseen behaviors, or drop rules. Players learn those details through experience and may
build their own community wiki to share discoveries.

One recovered Part is only a starting sample. Manufacturing knowledge requires five recovered
samples of the exact same Part variant/model ID; related Parts, shared manufacturer lineage, and
weak/normal/exceptional variants do not count toward one another. Every variant has separate sample
progress and a separate blueprint, so unlocking one variant does not unlock its Part family.
An extracted Part remains normal usable equipment until its owner explicitly submits it to
research. Submitting it consumes the item as a sample, so the player must choose between keeping or
equipping it and spending it toward the blueprint. All five identical samples are consumed when the
manufacturing blueprint unlocks. Once researched, stronger variants require progressively more
materials: exceptional costs more than normal, and normal costs more than weak. Exact model costs
remain balance data rather than universal fixed values. Manufacturing completes immediately when
the player confirms it and has the blueprint and materials; there is no production timer or wait.

Non-starter Core research uses the same keep-or-consume choice. Unlocking a Core blueprint requires
five extracted physical copies of that exact Core model, all consumed through explicit research
submission. Copies of different Core models cannot be mixed. Until submitted, every copy remains
usable equipment. The default Neutral starter blueprint is permanently known and exempt.
The player configures an entire robot loadout containing one required Core, one optional weapon
slot, and six optional Part slots. The company saves the exact selected models/variants and empty
slots as a reusable template, calculates and displays the total material cost of all included items,
and manufactures one complete copy immediately after confirmation. There is no multi-loadout batch
or automatic production queue; another copy requires another confirmation and payment of its
recalculated total. `Create New Robot` always produces new item instances and never consumes or
substitutes matching stored equipment.

The company keeps one persistent universal-currency balance. It is safe persistent progression and
is never carried into or lost during a stage. Players may spend it on company upgrades or company/
base purchases of equipment, materials, and physical blueprints. They may sell owned equipment,
materials, and duplicate blueprints only through the company/base interface, never during a stage.
Every sellable model has an authored selling price informed by its rarity; rarity does not change any
other value or mechanic, and the Common Neutral starter always sells for zero. Selling any Epic,
Legendary, or favorited item requires an additional explicit warning and confirmation. Favorited
items trigger that warning at every rarity. Players choose the exact quantity sold from a material
stack. The company shop uses a fixed authored catalog with limited stock, including equipment,
materials, and physical equipment blueprints; it may sell blueprint duplicates even after their
equipment is unlocked. The stock-restoration rule is deferred until the shop UI can be playtested.
The currency's in-world name remains open.

Physical blueprints unlock authored equipment manufacturing, not material conversion. A found
equipment blueprint has rarity and a fixed 1-by-1 spatial footprint, occupies run-inventory space,
and must be carried through successful extraction. At the company/base, consuming it permanently
unlocks its equipment option. An already-known or purchased duplicate remains an ordinary physical
item that takes inventory/storage space and may be sold.

All standard material-tier conversions are available automatically without blueprints. Every
adjacent conversion uses a universal ratio of three units into one unit of the next tier. The
interface may automatically compound that ratio to skip tiers in one confirmed operation—for
example, nine Common units become three Uncommon units and then one distinct Rare material unit.
An authored conversion may also require exact equipment models; the player must obtain those items,
and the conversion consumes them as ingredients without automatically manufacturing substitutes.
This convenience does not create differently rare copies of the same material. There is no
dismantling option for equipment or materials and no process for recovering a crafted output's
ingredients.

A Core-only loadout is valid. A bare skeleton equipped with the zero-cost Neutral starter Core and
no weapon or Parts can deploy into a farming stage and move normally. The underlying frame provides
a universal weak basic attack independent of Cores, weapons, and Parts, allowing this build to defeat
enemies and begin farming despite the Neutral starter having no Core ability. The attack supplies its
own base damage, takes the form of a close-range melee strike from the underlying frame, and applies
the equipped Core-versus-target type multiplier exactly once.

Equipping a weapon does not remove the skeleton strike. On desktop, scrolling cycles between the
equipped weapon and an `Unarmed` combat option. Selecting `Unarmed` puts the weapon away without
unequipping, dropping, or moving it from its loadout slot; selecting the weapon draws it again.
On phone and tablet, the visible weapon slot in the combat HUD is a touch-cycle control. Tapping it
selects the next option and updates its icon; with one equipped weapon, taps alternate between that
weapon and `Unarmed`.

`Equip Existing Robot` is a separate flow. Company storage contains loose Cores, Parts, and weapons,
and the player assembles a robot only from those existing item instances; this flow never
manufactures a missing component. Undeployed equipment remains in a large paginated storage
inventory with a finite data-defined capacity, safely outside stage risk. Pages, search, sorting,
and filtering make the large inventory manageable but do not make its capacity unlimited. After
full-party defeat, the lost deployed items remain gone, but each player's saved loadout template is
retained and preselected with its current combined cost ready for replacement manufacturing. The player may
leave that flow and equip a valid robot from storage instead.

Immediately after every successful extraction, each player enters a storage-resolution session
before continuing normal base play. The screen shows the player's frozen extracted carried inventory
alongside the paginated company storage; the game does not attempt to place anything automatically.
The player reorganizes storage and manually transfers every carried possession they want to keep
into an available company slot. They may permanently discard an existing stored possession to create
room. When they confirm the session, everything still left in the carried inventory is discarded,
after a clear summary and confirmation of the losses. An unconfirmed session persists across closing
or interruption.

The surviving robot's required Core, optional weapon, and six optional Part slots remain as they were
after successful extraction. Equipped items do not enter the storage-resolution session and do not
consume company-storage slots. If the player later removes an equipped item into storage, it must
fit a free slot or take the slot freed by an atomic equipment swap.

Each player can improve their own persistent company. Storage expansion is one upgrade track,
supporting both increased slot capacity on existing pages and additional pages at data-defined
levels and costs. Manufacturing, research, and other facility improvements may become additional
tracks as those systems are designed.

Ownership and possession are separate. Each equipment item and crafting-material quantity
manufactured for a player or committed to company storage receives the immutable ID of its owning
local profile or later network account. Teammates may carry, drop, or temporarily equip applicable
items during a stage without changing the ID. The UI identifies the owner and warns a non-owner that
the item or material cannot enter their company or research inventory. On successful extraction,
the authoritative game simulation commits it only if that owner is personally carrying or equipping
it. Anything held by a
non-owner or left on the ground remains in the stage and is lost at teardown, while stage failure
destroys it under the normal deployed-item rule. Eligible unbound stage loot receives its first
owner only when successful extraction commits it, and that owner is the player carrying or equipping
it at that instant rather than its first picker.

Equipment lending uses no special pre-stage assignment system. The owner puts an owned Core, Part,
or weapon into their own run inventory, joins the stage carrying it, and drops it on the ground. A
teammate may pick it up and use it, but its owner ID never changes. The borrower cannot bring it into
their own company. Before extraction, the borrower must physically return it and the owner must carry
or equip it. If the borrower still holds it or it lies on the ground, it stays behind and is lost
even when the party succeeds. Account-owned crafting materials remain non-transferable.

Loot remains part of temporary run inventory after the final objective is cleared. That clear
unlocks a post-clear recovery phase but does not commit possessions or remove the player from the
stage. The player may backtrack for ground loot, then use the explicit extraction action; there are
no early-extraction rooms or exits. Extraction commits carried/equipped possessions to persistent
cloud inventory and returns surviving deployed equipment for players who are alive and not downed
at that instant. Still-downed players count as dead and extract no items. A failed run loses all
uncommitted run loot even though established base progression remains.

Looted Parts can also be equipped during the current stage. A player may fill an empty slot or swap
an equipped Part with a compatible Part in temporary run inventory without returning to base or
finding a dedicated Part room. This does not make the Part persistent: it is still lost on stage
failure and enters persistent inventory only after explicit post-objective extraction. Parts may
be collected during combat, but they can be equipped, removed, or swapped only after the current
room is cleared.

Temporary run inventory uses a spatial grid rather than a simple item count. The equipped torso
determines how much grid space is available. Every carried Part, weapon, unequipped Core, and
crafting-material item occupies a defined cell footprint; the system must support examples such as
a 1-by-2 arm and an L-shaped leg. Loot can be collected only when its complete footprint fits,
though the player may rearrange carried items to make room.

Every character has the same predetermined 15-by-15 inventory canvas. A bare skeleton with no torso
has zero active inventory cells. The starter/default torso activates a predefined 3-by-3 region, and
every torso model defines its own active-cell mask within the larger canvas. This makes cargo
capacity part of torso progression and creates a tradeoff between inventory space and other torso
abilities. When a torso Resonates with a compatible Core, their specific relationship activates
additional cells. Different torsos can therefore receive different amounts and shapes of bonus
space.

Before a torso or Core change reduces the active mask, the player may rearrange inventory or drop
items manually. If the player confirms the change, every whole item whose transformed footprint is
not entirely contained by the new active cells drops safely into the current cleared room. The game
does not destroy the items or block the equipment change. Removing the torso leaves no active cells,
so all carried inventory drops; other Parts that remain equipped do not drop.

Every dropped item remains at its recorded location for the entire stage attempt. It persists while
the party changes rooms, backtracks, unloads and reloads a room, or saves and resumes the stage. Time
and distance never despawn it. Clearing the final objective leaves the stage open for backtracking;
only items carried or equipped when the player explicitly extracts become persistent.

Players may rotate carried item footprints in 90-degree increments and flip them horizontally or
vertically to make them fit. Inventory orientation is only spatial packing: flipping a left-arm
footprint does not turn that item into a right-arm Part or change any gameplay property.

Identical crafting materials stack up to 99 units within one inventory footprint. Any quantity over
99 must fill or create another stack with its own valid grid placement. Players may split and merge
partial stacks, but no individual stack can exceed 99. Persistent materials are account-owned, and
stacks can merge only when both their material model and persistent-owner ID match. Another player's
bound materials and unbound stage loot remain in separate stacks. If a teammate carries bound
materials through successful extraction, they do not return automatically; the recorded owner must
be the final carrier or the stack is left behind and lost.

If a run ends while the USB carries an active consciousness, that consciousness is lost. The cloud's
synchronized memories/state are not sufficient to recreate the active person for a later stage. The
human player creates a new character and uses persistent unlocks/blueprints and company manufacturing
to prepare its Core, Parts, and other equipment before entering another stage.

## Technology and identity

### Cores

Established story canon:

- The player's Core survived after the company crushed and discarded their body.
- The player's consciousness remains in the Core as the surrounding frame changes.
- A Core can be carried home when its body fails.
- Neural Backup Terminals can preserve a consciousness and restore it into a compatible body.
- After the necessary technology is obtained, the crew can verify consciousness synchronization
  before a mission and use the synchronized state to restore a lost crew member into a repaired or
  replacement body.
- Later campaign technology continuously synchronizes a player's memories/state to a cloud system.
  The active consciousness itself remains in the current Core/body and must be moved with the
  reusable USB/data-transfer device when immediate mission recovery requires another body.
- When the whole party is defeated, the cloud restores each ordinarily synchronized consciousness
  at the company/base without restoring the deployed body or equipment. A consciousness actively
  in transit inside the stage USB remains the exception and is lost if the attempt ends first.
- A consciousness-download room can activate a new robot without resetting rooms the party has
  already cleared.
- A consciousness being carried by the stage USB is permanently lost if the run ends before it is
  installed. Its human player continues with a new character rather than a cloud recreation of the
  lost person.

Established gameplay canon:

- An ordinary Core supplies the character's main ability. The default Neutral starter is the sole
  confirmed Core with no ability.
- A player selects a Core as part of a pre-stage build.
- A consciousness is not permanently bound to a specific Core model. During base preparation, a
  player may transfer into and equip any Core they own.
- Changing to another Core also changes to that Core's connected main ability; they are one linked
  gameplay choice rather than two independently equipped items. Changing to the default Neutral
  starter removes the previous Core ability and leaves that slot empty.
- The character remains the same person during a Core change because their consciousness is moved
  by computer/software into the selected Core.
- Parts may Resonate with a Core and increase its ability.
- Core compositions use three primary types—Machinery, Organic, and Alien—plus the special Neutral
  type. A pure Core is 100% one type, while a hybrid Core has data-defined percentages totaling
  exactly 100%. Hybrid composition may reflect cooperation or shared technology between companies,
  such as a Core that is 70% Organic and 30% Machinery.
- Ordinary pure Cores are permitted but not required, and the setting does not need a model for every
  possible primary-type/archetype combination. Hybrid-only lineages and deliberately absent
  combinations are valid. The fallback starter is the confirmed pure Neutral Core, but later hybrid
  Core models may also contain a Neutral percentage.
- Every ordinary Core also has exactly one pure archetype/subtype independent of its type: Speed
  emphasizes mobility; Tankiness emphasizes durability; Strength supports heavier equipment and
  weapons; Shielder uses a regenerating shield to protect health; and Specialist is a deliberate
  wildcard for unusual or future mechanics. Unlike primary types, archetypes do not use percentages
  and cannot currently be blended.
- The default Neutral starter Core is the sole confirmed exception: it has no archetype/subtype and
  no specialized subtype benefit. It has deliberately weak model-defined base stats and no Core
  ability. Exact stat values remain balance data.
- An archetype/subtype is a design theme, not a standardized passive package. Each Core's stats,
  passive behavior, and main ability are authored individually. Cores sharing a subtype may work
  differently, provided each still clearly expresses that subtype's theme.
- The free public demo contains at least 10 distinct Core models. Ten is a minimum, not a maximum.
- A new game begins with exactly one owned Core: the deliberately weak starter Core, which is 100%
  Neutral. The player must farm and progress out of this fallback build.
- Stronger Core models are obtained through authored discoveries inside stages, quest rewards, and
  story/world events. Different models may use different paths rather than one universal unlock
  method. Ordinary Core loot found inside a stage becomes persistent only through successful
  extraction under the existing run-loot rules.
- Quest and story/world-event Cores awarded inside a stage follow the same physical rule: completing
  the condition creates an at-risk Core reward, but the player must carry or equip it through
  successful extraction. Failure or leaving it behind loses that instance. Cores awarded safely at
  the company/base may become persistent immediately.
- Each authored Core reward defines whether it is repeatable. Repeatable rewards may spawn again on
  a later attempt when their Core was not extracted. A one-time reward, including an Easter egg, can
  spawn only once per campaign/save and is permanently missable if lost. Campaign-critical Cores
  cannot rely on this permanently missable behavior; they require a repeatable or authored recovery
  path.
- Successfully extracting a non-starter Core grants only that one persistent physical copy. It
  records the model as discovered but does not unlock its manufacturing blueprint or create any
  replacement entitlement. If that instance is later lost, the player must reacquire it through an
  eligible source unless its blueprint has been unlocked separately.
- A non-starter Core blueprint unlocks only after the player explicitly submits and consumes five
  extracted physical copies of that exact Core model. Different model IDs cannot share progress.
  Until submission, each copy remains usable equipment. The default Neutral starter is exempt
  because its blueprint is permanently known.
- Every physical copy sharing a stable Core model ID is mechanically identical. Its type percentages,
  archetype or no-archetype state, base stats, passive behavior, main ability or lack of one, costs,
  tags, compatibility, and Resonance data come from the model rather than random instance rolls. Any
  mechanically different version is a separate Core model ID.
- A Core family may have weak, normal, and exceptional variants, each represented by a separate
  deterministic model ID. Quality is defined by the Core's Neutral percentage: more Neutral content
  means a weaker tier, while less Neutral content means a stronger tier. Each variant has separate
  five-copy research progress and its own blueprint.
- Core quality uses exact Neutral percentages: weak variants are `50% Neutral`, normal variants are
  `25% Neutral`, and exceptional variants are `0% Neutral`. The default `100% Neutral` starter is a
  special fallback outside the ordinary quality tiers.
- Every quality variant preserves its Core family's base Machinery/Organic/Alien ratio. The ratio is
  scaled proportionally into the share left after Neutral: weak uses the remaining `50%`, normal uses
  the remaining `75%`, and exceptional uses the full family ratio. A `70% Organic / 30% Machinery`
  family therefore becomes weak `50% Neutral / 35% Organic / 15% Machinery`, normal `25% Neutral /
  52.5% Organic / 22.5% Machinery`, and exceptional `70% Organic / 30% Machinery`.
- Weak, normal, and exceptional variants within the same Core family have identical base stats,
  subtype, passive, main ability, costs, cooldowns, tags, effects, compatibility, and Resonance data.
  Their type composition is the only mechanical difference. Its Neutral percentage already changes
  outgoing and incoming damage through the weighted type-matchup calculation.
- Neutral intentionally smooths matchups. It can improve a family's otherwise disadvantaged damage
  multiplier while reducing its stronger matchups and adding Neutral's defensive vulnerabilities.
  Therefore, weak, normal, and exceptional are general quality labels rather than a promise that the
  higher tier has a better multiplier in every individual matchup. For example, pure Machinery deals
  `0.5x` to Alien, while a `50% Machinery / 50% Neutral` weak variant deals `0.625x`.
- A Core family's weak, normal, and exceptional variants share the same base visual assets. Optional
  add-ons or overlays may be switched on or off for each tier so players can distinguish them without
  requiring three completely separate visual or animation sets.
- Ordinary Core families use a consistent quality progression: weak looks least complete and most
  contaminated, normal looks cleaner and has more finished add-ons, and exceptional looks cleanest
  and most complete. Explicitly special Cores may instead receive their own authored tier design.
- Core quality is visible both on an equipped robot and in the Core's item/loadout presentation. The
  same item picture or portrait can represent that Core as ground loot. Opening run inventory shows
  only ground loot within pickup range as a vertically scrollable list on the left. Desktop hover or
  touchscreen tap reveals that item's own stats and uses rarity color to show its rarity before pickup,
  without an automatic comparison against equipped gear or a required additional non-color rarity
  marker. Pickup requires dragging the list entry into a valid spatial-grid position; clicking does
  not auto-place it. Eligible loot is determined by a balance-defined circular pickup radius centered
  on the player, cannot be reached through solid walls, and disappears from the list immediately upon
  leaving range. The pickup radius has no visible world outline. Every item receives a stable,
  sortable creation/spawn ID; default `Newest` order sorts that ID newest-first. Players may instead
  sort by Rarity, Item Type, Name, or occupied-cell-count Inventory Size, and every sort can be
  reversed. Equal primary-sort values use creation/spawn ID newest-first as their deterministic tie-
  breaker. The chosen sort key and direction persist after inventory closure and application restart.
  Rarity defaults to highest-first—Legendary, Epic, Rare, Uncommon, then Common; Item Type uses Core,
  Weapon, Part, Material, then Blueprint in its forward direction. Combat and the rest of the stage
  continue while the interface prevents the player from moving, attacking, or using abilities,
  leaving them vulnerable. Taking damage does not close the inventory. Damage shakes the world camera
  throughout the stage, including behind the
  partially transparent inventory, while the inventory itself stays anchored and does not display a
  separate damage-warning overlay. Each hit contributes shake according to its damage as a percentage
  of the player's maximum health. Rapid hits accumulate, the total decays over time and remains
  clamped, and players can reduce or disable the final shake in settings.
- Every item has a rarity, including Cores, weapons, Parts, materials, physical equipment blueprints,
  and the default Neutral starter. The initial ordered rarities are Common, Uncommon, Rare, Epic, and
  Legendary, with the Neutral starter set to Common. Rarities come from an extensible data registry
  rather than a closed enum, so more types may be added later. Their initial colors are gray, green,
  blue, purple, and gold in that order. Rarity is fixed by the item model, so every physical copy of
  that model has the same rarity.
  It describes selling price and drop frequency but never changes stats, abilities, or other combat
  mechanics. Weak, normal, and exceptional remain separate Core-quality
  labels rather than rarity names. The Common Neutral starter remains worth zero when sold.
- Each material kind is one exact model with one fixed rarity; the same material does not have
  alternate-rarity versions. Material stacks merge only when their exact material model and ownership
  match. Crafting recipes reference exact input and output models with no other material substituted,
  and authored recipes may produce materials as well as consume them.
- The Neutral starter Core is a permanent default blueprint in ordinary company manufacturing, not
  a separate order service. Once the tutorial awards the player's first company, they may manufacture
  repeated copies through the normal interface. Each copy costs zero materials or currency, sells
  for zero, cannot be dismantled, and cannot contribute research-sample progress.
  This recovery path never deletes or locks equipment left safely in company storage.
- Loot generation begins with one global rarity/drop-frequency table and then applies the current
  stage difficulty's authored modifiers. Exact probabilities and modifier curves remain balance data.
- Core-type effectiveness follows `Machinery > Organic > Alien > Machinery`. Advantage is `1.5x`,
  matching types are `1.0x`, and disadvantage is an intentionally punishing `0.5x`. Against a
  single-type enemy, the game calculates a weighted sum from the Core's composition. A 70% Organic/
  30% Machinery Core against Alien therefore has `(0.70 * 1.5) + (0.30 * 0.5) = 1.20x`
  effectiveness.
- Neutral sits outside that cycle. It deals `0.75x` damage to Machinery, Organic, and Alien, while
  each of those primary types deals `1.25x` damage to Neutral. Neutral versus Neutral is `1.0x`.
  Matchups involving hybrid Cores use each Neutral share's appropriate pair value in the same
  double-weighted formula.
- Enemies may also blend Machinery, Organic, and Alien percentages totaling exactly 100%. When both
  sides are hybrid, the game multiplies each attacker share by each defender share and that type
  pair's matchup value, then adds every contribution:
  `typedMatchupMultiplier = sum_i sum_j(attackerShare_i * defenderShare_j * matchup_i_j)`. For
  example, a 70% Organic/30% Machinery Core against a 60% Alien/40% Machinery enemy produces
  `(0.70 * 0.60 * 1.5) + (0.70 * 0.40 * 0.5) + (0.30 * 0.60 * 0.5) +
  (0.30 * 0.40 * 1.0) = 0.98x`. A pure enemy is simply 100% of one type.
- Type effectiveness is a base-damage multiplier. Every weapon or damaging ability supplies its own
  base damage, after which the game calculates
  `typeScaledBaseDamage = sourceBaseDamage * typedMatchupMultiplier`. For player damage, the equipped
  Core is the attacking composition and the enemy is the defending composition. For enemy damage,
  the enemy is the attacking composition and the player's equipped Core is the defending
  composition. The same double-weighted formula and cycle apply in both directions before later
  mitigation. Reversing the example above gives the enemy a `1.02x` multiplier against that Core.
  The weapon or ability controls its base damage but does not replace its user's type composition.
- Core-type effectiveness applies only to damage. It never modifies healing, shield capacity or
  regeneration, stun duration, status-effect chance or duration, movement, carrying capacity, or
  any other non-damage value; those mechanics follow their own rules.

Unresolved consciousness and Core-transfer rules are tracked as ID-01 through ID-06 under **Open
creator decisions**. Controller bindings are implementation decisions tracked only in the roadmap.

### Bodies and Parts

Established story canon:

- Bodies can be assembled from mismatched scrap.
- Damaged limbs and components can be replaced.
- A consciousness can abandon an unusable frame and enter a compatible one.
- Strong bodies can lift wreckage, light bodies can cross unstable terrain, engineering bodies can
  repair machinery, scouts can navigate dangerous routes, and armored bodies can protect crews.

Established gameplay canon:

- Players equip six independent Part slots before entering a stage: head, torso, left arm, right
  arm, left leg, and right leg. The left/right arms and legs can use different Part models.
- Part artwork is organized into named equipment sets. Each set has one sprite-sheet image containing
  separately drawable Core, head, torso, left-arm, right-arm, left-leg, and right-leg layers for all
  eight directions and required animation frames. A robot may visually combine Parts from multiple
  sets; every equipped layer uses the same current direction, animation clip, and frame.
- The unequipped frame is the special `empty_chassis` visual set. Empty slots draw their exposed
  chassis/socket layer from that set while continuing to provide no Part-derived gameplay benefit.
- Any Part slot may be empty. The underlying skeleton/frame still allows basic movement, even when
  every slot is empty, but an empty slot provides no Part-derived power, ability, stats, effects, or
  Resonance. The equipped Core remains powered and its main ability is usable even when every Part
  slot is empty.
- Players may equip compatible Parts they loot during the current stage without a dedicated Part
  room or a return to base, but only after the current room is cleared. Replaced Parts return to
  temporary run inventory.
- The equipped torso determines the size or usable shape of the temporary inventory grid. Carried
  Parts occupy spatial footprints, including rectangular and L-shaped layouts.
- Parts modify data-defined base statistics as well as granting abilities or Resonance. Supported
  modifiers include health, movement speed, armor, energy, inventory cells, and other validated
  stats. The underlying skeleton supplies baseline playable values, and empty slots add nothing.
- Every Part has its own data-defined level/value for each applicable stat. Values may be positive,
  neutral, or negative, allowing deliberate tradeoffs such as stronger health and armor paired with
  reduced speed or energy rather than making every Part a strict upgrade.
- Every copy of the same Part model has identical gameplay data: stats, ability, slot, effects,
  inventory footprint, compatibility, and Resonance relationships. There are no per-copy random
  stat rolls or affixes. A mechanically different weak, normal, or exceptional version is a
  separate model/variant with its own stable ID.
- When a build change lowers maximum health or energy below the current value, the current value is
  immediately clamped to the new maximum. The excess is discarded and does not return when the
  previous Part is re-equipped.
- When a build change raises maximum health or energy, the current numeric value does not increase.
  The player must heal or regenerate into the added capacity normally.
- Different Parts grant different abilities.
- Equipping a Resonant Part strengthens the Core but gives up the alternative ability another Part
  could have provided.

Unresolved frame, cosmetic, and Part-history rules are tracked as SYS-02 and SYS-03 under **Open
creator decisions**.

### Resonance

Resonance is based primarily on manufacturer compatibility. Every manufactured Core and Part has a
technical lineage. Parts from the same manufacturer can share architecture, protocols, energy
standards, or control systems that allow them to strengthen a compatible Core.

Cross-manufacturer Resonance is possible when the fiction provides a specific relationship. For
example, two manufacturers may have jointly developed technology, one may have licensed or copied
the other's design, or a Part may have been infected or altered until it communicates with the
other manufacturer's Core.

Each cross-manufacturer pairing should have a discoverable reason rather than functioning as an
unexplained statistical bonus. The exact mechanical scope and fictional cost of Resonance remain
open under SYS-01.

### Power and survival

- Power cells keep Cores, bodies, settlements, and generators alive.
- Working power and replacement machinery become scarcer as nearby scrap is picked clean.
- Reclaimed facilities allow the settlements to move from short-term scavenging toward reliable
  production and long-term planning.
- The role of energy remains open under SYS-05.

## Factions and groups

### Scrap Planet settlements

Independent settlements are populated by consciousnesses using repaired or constructed mechanical
bodies. They initially avoid one another or fight over scarce resources. The Clean Slate threat and
the old scavenger's work begin drawing them into cooperation.

### The company

An unnamed company created the player's consciousness to operate across replaceable mechanical
bodies. When the experiment developed independent judgment, resisted orders, and tried to preserve
things marked for disposal, the company classified it as defective, crushed its body, and discarded
it.

The company's name, leadership, relationship to the Hub, current status, and role in Project Clean
Slate remain open.

Later in the campaign, the crew conquers a company and takes control of its facilities and
manufacturing capability. The captured company becomes part of the player's growing organization
and can produce or modify Cores, Parts, bodies, and related technology. It is not yet decided
whether this is the company that created the unique experiment or a different manufacturer.

### Manufacturers

Manufacturers give Cores and Parts their technical lineages. Manufacturer relationships are both a
gameplay system and a source of world history: compatible products can reveal partnerships, stolen
research, infections, acquisitions, sabotage, or shared origins.

The campaign's company takeover changes the player faction from scavengers who can only recover
technology into an organization capable of producing and altering it. Exactly how the company is
captured, who operates it afterward, and how its workers are treated must be established.

### The Hub

The Hub is the governing power over registered worlds and the approving authority behind Project
Clean Slate. The story does not yet establish whether it knows the Scrap Planet is inhabited.

### Reclamation and security forces

Reclamation ships collect designated salvage. Their automated security machines detect intruders.
Abandoned facilities also contain maintenance machines, security units, and central guardians that
continue following old instructions.

The distinction among company forces, Hub forces, contractors, and fully autonomous machines still
needs to be defined.

## Known characters

### The unique experiment

- An artificial or engineered consciousness originally created by the company.
- Designed to operate through changing bodies made from whatever Parts were available.
- Developed independent judgment, questioned instructions, and preserved things ordered discarded.
- Resisted when it understood the company's intentions.
- Was classified as defective, crushed, and sent to the Scrap Planet as industrial waste.
- Survived because its Core remained functional.
- Finds a home among the Scrap Planet's inhabitants and becomes central to defending them.

The experiment is the one exceptional member of the playable group. Other player-controlled crew
members are ordinary inhabitants rather than additional copies of the same experiment.

The experiment's name, voice, pronouns, pre-disposal memories, personality options, and degree of
player customization remain open.

### The old scavenger

- Discovered the reclamation ship's route and loading schedule.
- Provided the plan that allowed the crew to steal an evacuation shuttle.
- Later helped connect settlements that had spent years avoiding one another.

The old scavenger currently has no name, body design, personal history, or resolved character arc.

### The crew

A small crew from the settlement joins the player in infiltrating the reclamation ship and
reclaiming facilities. Crew members fight, force doors, operate unfamiliar systems, repair
equipment, and bring damaged survivors or their Cores home.

No individual crew members are named yet. This group provides a natural narrative explanation for
cooperative multiplayer. One playable member is the unique experiment; the others are ordinary
Scrap Planet inhabitants with their own histories, bodies, and manufacturers.

Story-critical characters appear as AI-controlled allies in escort, defense, and shared-combat
missions. Their AI moves and fights through coded behavior, and their HP changes only because of
implemented combat or mission rules—not a random death roll. Ordinary gameplay cannot permanently
kill a protected story-critical character. Their defeat may cause protection, incapacitation, or
mission failure; permanent death occurs only when the creator explicitly authors it for that
character and story event.

An early death on the first or second mission drives the crew, through grief and frustration, to
discover a way to repair the lost body and turn or recover its Core as a new functioning Core. The
identity of the person who dies and the exact difference between repairing, recovering, converting,
and creating that Core still need clarification before the scene is fully canonized.

## Established story sequence

1. **Creation:** The company creates a consciousness able to operate interchangeable mechanical
   bodies assembled from available Parts.
2. **Awakening:** The experiment develops independent judgment, questions orders, preserves objects
   marked for disposal, and resists the company's plans.
3. **Disposal:** The company labels the experiment defective, crushes its body, and sends it away
   with industrial waste. The discarded frame falls from the sky toward the Scrap Planet.
4. **Scrap Planet awakening:** The wreckage lands on the Scrap Planet. The player's consciousness
   wakes inside the already-installed default Neutral Core within the crushed body, with almost no
   power.
5. **First repair:** The player scavenges a power cell, joint, wire, and replacement hand, repairing
   enough of the body to walk.
6. **A new home:** Scrap Planet inhabitants recognize the Core, repair the player's frame, and teach
   them how the settlements survive.
7. **Scarcity:** Nearby salvage runs low, generators become harder to maintain, and expeditions must
   travel farther.
8. **The reclamation ship:** Acting on the old scavenger's plan, a crew hides in salvage, boards a
   reclamation ship, fights through security machines, and steals its evacuation shuttle.
9. **The abandoned facility:** Navigation records lead the crew to an abandoned company site with
   active maintenance and security systems.
10. **The Backup Terminal:** The crew discovers technology that can preserve consciousnesses and
    restore them into compatible bodies.
11. **The first reclaimed base:** After defeating the central guardian, the crew gains workshops,
    storage, and transport systems, then sends vital supplies home.
12. **Expansion:** Further expeditions reclaim specialized facilities. Settlements trade, share
    routes, restore large systems, and begin planning beyond immediate survival.
13. **The revelation:** The player finds Project Clean Slate records and learns that the Hub has
    scheduled the Scrap Planet for removal without recording its population.
14. **Unity:** Settlements prepare defenses, seek machinery and intelligence, build stronger bodies,
    and cooperate under the growing threat.
15. **The campaign continues:** The player boards the shuttle again to rescue people, reclaim more
    facilities, and keep the planet alive before Clean Slate arrives.

## Additional confirmed campaign events

- On the first or second mission, one member of the group dies tragically.
- Grief and frustration over that loss lead the survivors to discover a way to repair the body and
  recover, convert, or create a functioning Core from it. The exact operation is still open.
- Once the relevant technology is available, crew members can save their consciousness before a
  mission and later be restored if their mission body is lost.
- The later system continuously synchronizes memories/state to the cloud. For immediate mission
  recovery, the reusable USB device extracts the active consciousness itself from the fallen body
  and transfers it into a prepared Core/body.
- As the campaign advances, the crew conquers a company and uses its facilities and manufacturing
  capability as part of its own organization.
- The early death, first Core breakthrough, Backup Terminal discovery, and later company takeover
  must be placed on one exact timeline after the questions below are answered.

## Relationship between story and gameplay

| Story element | Gameplay purpose |
| --- | --- |
| Consciousness housed in a Core | Persistent player identity; ordinary Cores also supply a main ability, while the default Neutral starter does not |
| Six interchangeable mechanical Part slots | Head, torso, independently equipped left/right arms, and independently equipped left/right legs |
| Empty Part slots | The skeleton can move and use any equipped Core ability, but missing Parts grant no Part power, skill, stats, effects, or Resonance |
| Skeleton basic attack | Universal weak close-range melee strike independent of Core, weapon, and Parts; provides the zero-cost farming fallback and still uses Core-type damage scaling |
| Empty weapon slot | A Core-only skeleton remains deployable and can fight using the universal skeleton attack |
| Part stat modifiers | Equipped Parts can alter health, speed, armor, energy, inventory cells, and other validated statistics |
| Part stat profile | Each Part has per-stat levels/values that may create bonuses, neutral effects, or penalties |
| In-stage Part loot | Collected during combat; compatible Parts may replace equipment after room clear but remain at risk until stage clear |
| Inventory canvas | A predetermined 15-by-15 grid holds every possible inventory position |
| Torso inventory | The equipped torso determines which cells of the inventory canvas are active |
| Starter torso inventory | The starter/default torso activates a predefined 3-by-3 region |
| No-torso inventory | A bare skeleton has zero active cells, so all carried inventory drops |
| Loot inventory footprint | Parts, weapons, unequipped Cores, and crafting materials occupy shapes that must fit completely |
| Crafting-material stack | Up to 99 identical, same-owner units share one footprint; overflow or a different ownership state needs another placed stack |
| Persistent ground loot | Dropped items remain in their rooms for the entire stage attempt and can be recovered by backtracking |
| Core/Part Resonance | Manufacturer-based synergy with an explicit opportunity cost |
| Core type contribution | Machinery, Organic, Alien, or Neutral share expressed as a percentage of a Core's normalized composition |
| Neutral type | Deliberately weak special type usable by the pure starter and later hybrids: 0.75x outgoing to primary types, 1.25x incoming from them, and 1.0x against Neutral; its weighted share intentionally smooths strong and weak matchups |
| Core archetype/subtype | One unblended Speed, Tankiness, Strength, Shielder, or Specialist theme for ordinary Cores; the default Neutral starter has none |
| Core-type effectiveness | Damage-only, double-weighted attacker-versus-defender multiplier using the primary cycle plus Neutral's special 0.75x/1.25x/1.0x matchups |
| Manufacturer lineage | Build compatibility and discoverable history between technologies |
| Scrap Planet settlement | Hub area, characters, upgrades, and visible consequences of progress |
| Stolen evacuation shuttle | Travel between the settlement and stages |
| Companies, ships, and facilities | Room-graph stages, bosses, resources, and new capabilities |
| Clearing a room | Reveals the connected route choices and advances the expedition |
| Story expeditions | Fixed stages that deliver authored narrative events and consequences |
| Farming expeditions | Randomized arrangements of authored rooms for materials and technology samples |
| Boss hunts | Replayable mastery fights that encourage specialized Core/Part builds |
| Settlement defense | Protects simulated settlement assets, terminals, generators, or important NPCs |
| Rescue/escort missions | Recovers people, Cores, or machinery through AI-driven protection objectives |
| Challenge contracts | Reuses PvE stages with declared restrictions, threats, and reward modifiers |
| Solo player defeat | Immediately fails the stage; no AI revival or USB rescue |
| Core/weapon rooms | In-stage build adaptation; changing Core also changes its main ability |
| Recruitment rooms | A trapped/stranded ally joins and fights for that stage only |
| Battle assembly room | Holds the battle door until every active player is physically present |
| Final objective room | Boss, achievement/objective, or lore event that secures the stage and unlocks post-clear extraction |
| Final-room extraction | Exit that appears, activates, or opens according to the final room's clear condition |
| Extraction quorum | `ceil(active living players / 2)` starts a 10-second whole-party extraction; all present extracts immediately |
| Downed extraction | A downed body counts for quorum but is dead at extraction and loses its carried/equipped items |
| Continuous consciousness backup | Respawn/recovery fiction without intentional memory loss |
| Cloud synchronization | Continuously preserves memories/state but does not replace USB transfer of the active consciousness |
| Consciousness-download room | Provides USB/data transfer and prepared bodies for recovery or loot |
| USB/data-transfer device | Reusable tool that moves active consciousness from a fallen body into a prepared body |
| Original Parts recovery | Any party member may backtrack to the death location to reclaim equipment |
| Teammate equipment recovery | A teammate may carry/use another player's dropped equipment, but must return it physically so its owner carries/equips it at extraction |
| Equipment analysis | Explicitly consumes a recovered Part or non-starter Core as a research sample instead of keeping it usable |
| Blueprint unlock | Consumes five samples of the exact same Part variant/model ID or non-starter Core model ID; each model tracks separately |
| Create New Robot | Immediately manufactures a saved loadout with one required Core, an optional weapon, and six optional Parts after calculating the included items' combined cost |
| Equip Existing Robot | Assembles a loadout from loose Core, Part, and weapon instances already held in company storage; never manufactures missing items |
| Equipment loan | Owner carries gear into a stage and drops it for a teammate; it extracts only if physically returned to and held/equipped by the owner |
| Unowned loot claim | Successful extraction assigns loot to the eligible player carrying/equipping it then, not the first player who picked it up |
| Stage completion | Explicit post-objective extraction that commits carried/equipped run loot |
| One-way stage entry | No retreat or return to base after launch; only internal backtracking, victory, or failure |
| Cloud encounter log | Persists spawned enemies and directly observed facts without revealing hidden mechanics |
| Successor character | New identity selected from unlocked persistent inventory after permanent consciousness loss |
| Deployed equipment | Core, Parts, and other item instances risked and lost if the stage run fails |
| Living body transfer | Leaves an unoccupied functional Core/body that another eligible character may enter immediately or carry away as loot |
| Story-critical NPC | AI-controlled ally whose movement, combat, HP, and authored defeat outcome replace random permanent death |
| Conquered company | Player-owned manufacturing, research, and later-game progression |
| Supplies sent home | Stage rewards and settlement restoration/progression |
| Project Clean Slate schedule | Campaign urgency and escalating threat |
| Expedition crew | Cooperative multiplayer party |
| PvE | Players cooperate against enemies and shared objectives; competitive PvP is out of scope |
| Security and maintenance machines | Early enemy families |
| Central guardians | Stage bosses |

## Campaign structure currently supported by the draft

### Prologue/tutorial

The opening shows the player falling from the sky as discarded scrap inside a badly damaged frame.
The default Neutral Core—and the player's consciousness—is already installed when the wreckage lands
on the Scrap Planet. The player awakens, searches for power and the first replacement Parts, and
learns movement, energy, scavenging, Part replacement, and the difference between Core identity and
body capability without a separate tutorial detached from the story. Completing the tutorial gives
the player control of a small company, which becomes the initial persistent base for storage,
manufacturing, research, loadout preparation, and upgrades.

### Early campaign

The settlement teaches repair and survival. Increasing scarcity creates the need for expeditions.
The reclamation-ship infiltration introduces the crew, combat, security systems, and the shuttle.
The first or second mission includes the tragic death that motivates the first major Core recovery
or creation breakthrough.

Early stages should teach the room graph gradually: first clear a combat room, reveal unknown path
choices, encounter a build-change or recruitment room, and finally reach a clearly designated final
objective room.

Farming stages can later revisit the same authored room library in different seeded, run-generated
arrangements for material collection. Story and lore stages keep fixed graphs so character moments,
discoveries, and dramatic pacing remain consistent.

### First major stage arc

The abandoned company facility introduces spare frames, the Backup Terminal, stage guardians, and
the reclaim-and-return structure. Defeating its guardian visibly improves the home settlement.

### Mid-campaign expansion

Different facilities provide specialized technology. Builds become more varied. Settlements move
from distrust to trade and cooperation. The player encounters evidence that the outside world knows
more about the Scrap Planet than it admits. The crew eventually conquers a company and turns its
manufacturing capability toward the Scrap Planet's survival.

### Major revelation

Project Clean Slate identifies the entire inhabited planet as removable waste. A schedule exists,
and evacuation is impossible with the available transport capacity.

### Late campaign and ending

Not yet written. The established direction is to prepare the planet, unite settlements, reclaim
critical facilities, rescue people, and confront the approaching operation. The final opponent,
method of stopping Clean Slate, fate of the Hub/company, and ending remain open.

## Open creator decisions

Only genuinely unresolved decisions are listed here. Resolution, controller scope, exact storage
capacity, and other implementation or balance choices remain in `GAME_RELEASE_ROADMAP.md`.

### Story direction

1. **NAR-01 — Early death:** Is the person who dies on the first or second mission the unique
   experiment, an ordinary playable crew member, or a non-player companion?
2. **NAR-02 — Core breakthrough:** Does the crew restore that same consciousness, transfer it into a
   newly functional Core, or create a new consciousness from the repaired body?
3. **NAR-03 — Original company plan:** What did the experiment's creators intend to do that caused
   the player to resist?
4. **NAR-04 — Company takeover:** Is the conquered company the experiment's creator or another
   manufacturer, and does conquest mean facility capture, legal control, leadership defeat, or
   control of the entire corporation?
5. **NAR-05 — Hub responsibility:** Does Hub leadership know the Scrap Planet contains people, or is
   its population being hidden?
6. **NAR-06 — Clean Slate:** What will the operation physically do, how long remains before it
   arrives, and can the player delay it?
7. **NAR-07 — Opposition:** Who gives the opposition a recurring face or voice before the final
   operation arrives?
8. **NAR-08 — Campaign resolution:** What does the player ultimately seek, how does that fulfill the
   title *Conquer the World*, and what permanently prevents the planet from being treated as waste?
9. **NAR-09 — Structure and ending:** How linear is the story, which choices have persistent
   consequences, and is the ending triumphant, bittersweet, tragic, or variable?
10. **NAR-10 — Population origins:** Were Scrap Planet inhabitants manufactured as machines,
    uploaded from organic lives, born as emergent machine consciousnesses, or drawn from several
    origins?

### Consciousness and technology

1. **ID-01 — Core use:** Does every conscious person require a Core, or do some use another storage
   or embodiment system?
2. **ID-02 — Ordinary Core changes:** Does a Core room use the reusable USB technology, a larger
   transfer machine, or another process?
3. **ID-03 — Destruction and restoration:** What happens when an occupied Core is physically
   destroyed, and why is a consciousness lost in a failed USB transfer despite continuous
   synchronization?
4. **ID-04 — Restored identity:** Do the characters, law, and culture regard a restored synchronized
   consciousness as the same person?
5. **ID-05 — Cloud ownership:** Who operates the consciousness cloud, and what happens during an
   outage or loss of access?
6. **ID-06 — Early chronology:** Where do the early death, first Core breakthrough, Backup Terminal,
   continuous synchronization, and company takeover fall on one exact timeline?

### Parts, Resonance, and research

1. **SYS-01 — Resonance cost:** What exact gameplay effects can Resonance strengthen, how can
   multiple Parts combine, and why does the Resonant choice cost the Part's independent ability?
2. **SYS-02 — Frames and cosmetics:** Can the underlying skeleton/frame be changed, and are cosmetic
   layers separate from functional Parts?
3. **SYS-03 — Part history:** How strongly should a Part's original industrial purpose determine its
   combat or utility ability?
4. **SYS-04 — Research prerequisites:** Beyond five exact-model samples, does production require
   analysis time, materials, a facility level, or manufacturer knowledge?
5. **SYS-05 — Energy:** Is energy a moment-to-moment combat resource, a stage resource, a settlement
   resource, or a combination?

### Stages, followers, and cooperation

1. **STG-Q01 — Farming generation:** What room categories, graph limits, difficulty rules, and
   minimum/maximum room counts control farming stages, and does a failed retry use a new seed?
2. **STG-Q02 — Unknown paths:** May exits provide neutral clues such as distance or door shape, or
   must all unknown paths be visually indistinguishable?
3. **STG-Q03 — Backtracking exceptions:** May authored story events permanently close or alter an
   otherwise traversable connection?
4. **NPC-Q01 — Followers:** Why is each temporary follower stranded, what happens to them after the
   stage, and can a non-critical follower die permanently?
5. **COOP-Q01 — Uncooperative players:** In later multiplayer, besides host kick, what happens if a
   player refuses to enter an assembly room?
6. **COOP-Q02 — Spectators:** Can spectators communicate with living teammates through voice, text,
   or pings?
7. **COOP-Q03 — Recovery-body claims:** When prepared bodies are scarce, are competing claims
   resolved by first interaction, host choice, party vote, or another rule?
8. **LOSS-Q01 — Non-item failure state:** Which temporary follower or objective states, if any,
   survive a failed attempt?

### Presentation

1. **PRES-Q01 — Story delivery:** Which combination of dialogue, portraits, in-engine scenes, logs,
   environmental storytelling, and voice acting will the game use?
2. **PRES-Q02 — Tone and rating:** What age rating, mechanical-injury detail, and amount of humor are
   intended?
## Suggested additions - not canon yet

These additions would strengthen the existing story without replacing its premise:

- Create three to five named settlement characters who represent repair, exploration, defense,
  history, and disagreement over how to answer Clean Slate.
- Give the old scavenger a name, distinctive body, reason for studying reclamation ships, and a
  personal stake in uniting the settlements.
- Add a recurring antagonist who can speak for the company or Hub and react to reclaimed facilities
  instead of relying only on silent security machines.
- Define one mystery about the player's creation that connects the personal story to Project Clean
  Slate without making the whole planet important only because of the protagonist.
- Let each reclaimed facility produce a visible improvement at the settlement so story progress and
  gameplay rewards reinforce each other.
- Give each stage a human/community need in addition to loot: power for a failing district, a lost
  crew to rescue, communications needed to unite settlements, or shielding needed for defense.
- Establish clear costs and limits for Backup Terminals so body destruction remains meaningful.
- Introduce at least one Hub citizen or worker who changes after learning the Scrap Planet is alive,
  preventing every outsider from feeling identical.
- Build toward a finale that resolves both the physical Clean Slate threat and the political fact
  that the Scrap Planet is absent from the Hub's definition of personhood.
- Preserve the first broken body in the workshop as a recurring visual reminder of the player's
  beginning and the community's progress.

## Terminology

| Term | Current meaning | Status |
| --- | --- | --- |
| Core | Device housing a consciousness; ordinary models also supply a main ability, while the default Neutral starter supplies none | Canon |
| Part | Replaceable body component that changes capability and may grant an ability | Canon |
| Resonance | Manufacturer-based Part/Core synergy that strengthens the Core in place of another Part ability | Canon; exact cost mechanism open |
| Manufacturer | Company/technical lineage that created a Core or Part and helps determine Resonance | Canon |
| Frame/body | Mechanical body surrounding and operated by a Core | Canon; exact distinction open |
| Scrap Planet | Unregistered inhabited world formed from discarded technology | Canon; formal name open |
| Hub | Authority governing registered worlds, transport, law, and security | Canon; structure open |
| The company | Unnamed creator of the experiment; possibly the company conquered later | Canon; identity open |
| Project Clean Slate | Hub-approved operation scheduled to remove the Scrap Planet as waste | Canon |
| Backup Terminal | Neural system that stores consciousness and restores it into compatible bodies | Canon |
| Conquer the World: Scrapborn | Confirmed game title | Canon |
| Party | One local player in the offline demo; overall later target of one to six networked players | Gameplay canon |
| Stage | Expedition through the connected rooms of a company, ship, facility, or other location | Gameplay canon |
| Room graph | Rooms and their traversable connections, revealed outward as rooms are cleared | Gameplay canon |
| Farming stage | Randomized graph assembled from compatible predefined rooms to gather materials | Gameplay canon |
| Story/lore stage | Fixed authored room graph with intentional narrative order and pacing | Gameplay canon |
| Unknown path | Revealed connection whose destination type and reward remain hidden until entry | Gameplay canon |
| Battle assembly room | Pre-combat room whose door opens only when all active players are present | Gameplay canon |
| Final objective room | Required room whose boss, objective, achievement, or lore event secures the stage and unlocks extraction | Gameplay canon |
| Post-clear recovery | Backtracking phase after the final objective, before the player explicitly extracts | Gameplay canon |
| Post-clear danger | Traps, hazards, and surviving threats remain lethal; death before extraction still fails the run | Gameplay canon |
| Final-room extraction | Configured exit players return to after post-clear looting to finish the stage | Gameplay canon |
| Extraction quorum | Half of active living players rounded up starts a 10-second party countdown; all present is immediate | Post-demo gameplay canon |
| Downed extraction | Counts for quorum during the revive window, but still-downed players are dead when extraction resolves | Post-demo gameplay canon |
| Follower | Trapped/stranded character who accompanies and fights with the party for one stage | Gameplay canon |
| Revive circle | Later-multiplayer area where a teammate remains to revive a player at 1 HP | Post-demo gameplay canon |
| Revival window | Later-multiplayer 15-second downed timer that pauses during an active 5-second revive | Post-demo gameplay canon |
| Spectator state | Later-multiplayer post-expiration state using a camera following a survivor | Post-demo gameplay canon |
| Spectator recovery | Later-multiplayer return through a consciousness-download room | Post-demo gameplay canon |
| Consciousness-download room | Room that downloads a spectator into a prepared Core/body with loaner Parts | Gameplay canon |
| USB/data-transfer device | Reusable device that moves the active consciousness from one Core/body into another | Gameplay canon |
| Dropped USB payload | Recoverable stage object preserving its carried consciousness when the carrier falls/disconnects | Gameplay canon |
| Loaner Parts | Recovery-room equipment distinct from the player's recoverable original Parts | Gameplay canon |
| Part model | Stable definition shared by every identical copy; fixes stats, ability, footprint, compatibility, effects, and Resonance data | Gameplay canon |
| Personal base | Persistent player-owned location for inventory, research, and manufacturing | Gameplay canon |
| Part sample | Usable recovered Part deliberately consumed for research instead of kept/equipped; five identical-variant samples unlock that variant's blueprint | Gameplay canon |
| Core sample | Usable extracted non-starter Core deliberately consumed instead of kept/equipped; five identical-model samples unlock that Core blueprint | Gameplay canon |
| Core model | Stable definition shared by every identical Core copy; fixes all mechanical data, with variations represented by different model IDs | Gameplay canon |
| Core quality variant | Separately researched family model: weak has 50% Neutral, normal 25%, and exceptional 0%; its remaining share preserves the family's primary-type ratio, variants share other mechanics/base visuals, and ordinary tiers progress from contaminated/incomplete to clean/complete unless specially authored; the 100% Neutral starter is outside these tiers | Gameplay canon |
| Item rarity | Fixed model data for every Core, weapon, Part, material, and physical equipment blueprint; initially Common gray, Uncommon green, Rare blue, Epic purple, and Legendary gold; describes selling price and drop frequency without changing stats; expandable later | Gameplay canon |
| Universal currency | Safe persistent company/base balance received from selling there; purchases upgrades, equipment, materials, and blueprints; never deployed or lost in stages; final in-world name open | Gameplay canon |
| Physical equipment blueprint | Found/purchased 1-by-1 item with fixed rarity; a found copy must be extracted and consumed at the company/base to unlock authored equipment manufacturing; known duplicates remain sellable items | Gameplay canon |
| Material forging | Automatically available immediate exact-model conversion using universal 3:1 adjacent-tier steps; tier skipping compounds the ratio, and authored conversions may consume exact equipment as ingredients | Gameplay canon |
| Field loot inspection | Semi-transparent run inventory lists unobstructed loot inside an invisible, balance-defined circular radius; hover/tap reveals stats, drag-to-grid collects it, stable creation ID supplies default ordering and tie-breaking, reversible persistent rarity/type/name/cell-count sorts are available, and world-camera damage shake leaves the vulnerable player's stable inventory open | Gameplay canon |
| Blueprint | Persistent knowledge allowing a researched Part or Core model to be manufactured | Gameplay canon |
| Run | One stage attempt; failure returns the party to base manufacturing before another can begin | Gameplay canon |
| Run inventory | Temporary loot committed only when carried/equipped through post-objective extraction; all of it is lost on failure | Gameplay canon |
| Deployed equipment | Previously owned Core, Parts, and equipment risked in a run and lost if that run fails | Gameplay canon |
| Persistent owner ID | Immutable local-profile/account ownership attached to stored equipment and materials and preserved through all stage possession changes | Gameplay canon |
| Base storage | Large but finite paginated company inventory of loose undeployed equipment kept safe from stage failure | Gameplay canon |
| Storage-resolution session | Mandatory post-extraction view of carried inventory beside company storage; only manually stored carried possessions survive confirmation | Gameplay canon |
| Active equipped loadout | Surviving Core, weapon, and Parts remain equipped after extraction and occupy no company-storage slots | Gameplay canon |
| Company improvement | Persistent per-player facility upgrades, including larger storage pages and additional pages | Gameplay canon |
| Cloud encounter log | Persistent record of spawned enemies and directly observed facts; not a complete official wiki | Gameplay canon |
| Successor character | New identity built from unlocked persistent inventory after a USB-carried consciousness is permanently lost | Gameplay canon |
| Story-critical NPC | Protected AI ally whose permanent death requires an explicitly authored story event | Gameplay/story canon |
| Retained body | Disconnected player's body kept at its last position until reconnection or host kick | Gameplay canon |
| Consciousness cloud | Later technology that continuously preserves memories/state; active consciousness still requires transfer | Canon; operator open |
| New robot | Prepared body/Core that can restore a spectator at a consciousness-download room | Gameplay canon |

---

# Preserved original narrative draft

This is the source draft that established the setting. It is retained for reference and inspiration;
if it conflicts with an approved rule above, the current canon above takes precedence.

## Scrapborn

The Hub controlled the registered worlds. It governed transport, enforced the law, and maintained
the security systems that kept its planets running. Every recognized settlement had a place in its
records.

Far outside that network, discarded technology had accumulated into a world of its own. Broken
machines, failed prototypes, abandoned ships, and entire sections of dismantled factories lay
piled across its surface. No bridge connected it to the Hub. The vessels that occasionally visited
came to recover valuable material or unload more waste.

The inhabitants called it the Scrap Planet.

You arrived there inside a crushed body.

Before that, you had been an experiment. A company had developed a consciousness that could
operate in different mechanical bodies, including frames assembled from mismatched scrap. A
damaged arm could be replaced with another. A ruined chassis could be abandoned for a usable one.
The company wanted a machine that could keep working with whatever parts were available.

During testing, you began making decisions of your own. You questioned instructions. You tried to
preserve things the technicians had ordered you to discard. Eventually, you understood what they
intended to do to you, and you resisted.

They classified the experiment as defective. Your body was crushed, your remains were loaded with
the rest of the industrial waste, and you were sent away.

Your core survived.

When you woke, a faint light flickered behind the bent metal of your chest. Wires hung from a broken
rib cage. One arm dragged through the debris, and a damaged leg barely supported your weight. Each
movement threatened to drain the little power you had left.

You pulled yourself through the wreckage until you found a power cell with enough charge to keep
your core alive. Then you found a joint that still moved, a length of wire, and a replacement hand.

Piece by piece, you repaired enough of yourself to walk.

You soon discovered that other people lived among the scrap. Some occupied old service machines;
others had built their bodies from parts whose original purposes had long been forgotten. Their
settlements gathered around working generators, repair benches, and shelters made from the sides of
shipping containers.

They recognized the light in your chest. They helped you secure your damaged frame and showed you
where to find usable parts.

For a while, survival filled every day. You searched the wreckage for power cells and metal,
learned which machines still followed dangerous old instructions, and returned with whatever you
could carry. A good find might keep a generator running for another week. A failed expedition might
cost someone the body they had spent months repairing.

You learned to change bodies when necessary. Your consciousness remained in the core while the
frame around it changed. A stronger body could lift wreckage that your first one could barely move.
A lighter frame could cross unstable ground without bringing the whole pile down.

The settlement became home. You learned who waited at the gate when a scavenging crew was late, who
could repair almost anything, and who quietly gave away the best parts they found.

But the scrap around the settlement was being picked clean. Working generators were getting harder
to maintain. Every expedition had to travel farther.

Then an old scavenger brought back a plan.

A reclamation ship followed a regular route past the planet. Its crews recovered material from
designated salvage sites before departing for company facilities elsewhere. The scavenger had
watched enough visits to identify its loading schedule.

The ship carried an evacuation shuttle.

With a small crew from the settlement, you traveled to its next collection site and hid among the
salvage. When the material was brought aboard, you slipped into the ship's interior.

Its corridors were orderly and brightly lit. Doors opened on functioning motors. Power flowed
through the walls. Somewhere beyond the cargo compartments were stores of equipment that could
have kept your settlement alive for years.

Security machines detected you before you reached the shuttle bay.

Your crew fought through them, forcing doors and holding corridors while someone worked on the
shuttle controls. You escaped with damaged bodies and a vessel you barely knew how to fly.

Its fuel and cargo space were limited. Moving an entire settlement would take more than a stolen
shuttle, and you needed a dependable source of supplies to keep it running.

The ship's navigation records pointed you toward an abandoned company facility.

The company had stopped using it, but its automated systems remained active. Maintenance machines
patrolled the halls. Security units guarded stores that no employee had opened in years.

You entered together, repairing equipment where you could and fighting through defenses where you
had to. Deep inside, you found a neural Backup Terminal: a system capable of storing a consciousness
and restoring it into a compatible body.

For the first time, your crew had somewhere to prepare spare frames and preserve backups before an
expedition. Losing a body could still leave you stranded or cost precious equipment, but a working
terminal gave you a way to recover and return.

After defeating the facility's central guardian, you gained access to its workshops, storage rooms,
and transport systems.

You sent the first shipment home.

It carried power cells, replacement components, tools, and construction materials. The settlement
repaired its generators. Its workshop gained equipment that could make reliable parts. A damaged
shelter was rebuilt before the next storm.

Further expeditions brought more facilities under your control. Each one supplied something the
planet needed. You assembled bodies suited to different jobs: engineering frames for repairs,
scouts for dangerous routes, and armored machines that could protect the others during a fight.

People began planning beyond the next scavenging trip. Settlements traded supplies. Crews shared
routes and worked together to restore equipment too large for any one group to repair.

Inside one reclaimed facility, you uncovered the documents for **Project Clean Slate**.

The Hub had approved a large cleanup operation. Unregistered accumulations of obsolete technology
were scheduled for removal, and the Scrap Planet was on the list.

The records described its material composition, its salvage value, and the equipment required to
clear it. They contained no population count.

An entire world of living consciousnesses had been entered as waste.

The operation already had a schedule.

You brought the documents home. Some people wanted to leave immediately. Others argued that the
shuttle should keep moving supplies. Families and scavenging crews counted how many passengers it
could carry, how far it could travel, and how much time remained.

There was no quick way to evacuate everyone.

The settlements began preparing to survive the cleanup.

Your expeditions took on a new urgency. You searched company facilities for heavy machinery,
shielding, communications equipment, and records of the cleanup vessels. Workshops built stronger
bodies. Crews reinforced shelters and restored old industrial systems that might help defend the
surface.

The old scavenger helped connect settlements that had spent years avoiding one another. People who
had once fought over a working generator now shared the parts needed to build something larger.

You kept returning from missions in different frames, sometimes carrying another survivor's core
because their body had failed on the way home.

One evening, you passed the repair bench where your first body still lay. Its chest was bent, its
limbs mismatched, and its wires secured with crude repairs. You remembered dragging that frame
through the scrap, searching for enough power to survive another hour.

Around it, the workshop was crowded with people preparing bodies for the next expedition. Outside,
crews tested the settlement's defenses. Supplies were arriving from another reclaimed facility.

Project Clean Slate was coming.

You checked your equipment, joined your crew, and boarded the shuttle.

There were still people to bring home, facilities to reclaim, and a planet to keep alive.

---

# Change log

This compact log records major canon passes rather than every intermediate or superseded answer.
Repository history retains the detailed edits; only the current rules above are authoritative.

- **2026-10-15 — Selling and material crafting:** Added one universal currency, restricted selling
  to the company/base, and made the currency safe progression for upgrades and company purchases.
  Physical 1-by-1 blueprints unlock equipment only; duplicates occupy inventory and may be sold or
  purchased. Material forging needs no blueprint, uses a universal automatically compounded 3:1 tier
  ratio, and may consume exact equipment ingredients. Shops have fixed limited stock, material stacks
  support partial sales, and Epic, Legendary, and favorited sales require warnings. Dismantling was
  removed entirely, and loot rarity uses one global table modified by stage difficulty.
- **2026-10-14 — Document cleanup:** Removed superseded decisions from the open-question list,
  grouped the remaining questions by topic, clarified offline-versus-later-network authority, and
  condensed the historical log.
- **2026-10-14 — Core quality presentation:** Set a consistent contaminated/incomplete-to-clean/
  complete visual progression for ordinary Core tiers, allowed special authored exceptions, exposed
  stats and rarity color through the inventory's scrollable left-side loot list, and reused Core
  presentation across equipped robots, item UI, and field loot. Loot hover performs no equipped-item
  comparison, no extra non-color rarity marker is required, and inventory does not pause combat.
- **2026-10-14 — Field-loot interaction:** Limited the inventory loot list to pickup range, required
  drag-to-grid collection, mapped touchscreen tap to desktop hover, made sorting player-selectable
  with newest-first as default, and left the stationary player vulnerable while simulation continues.
  Pickup uses a balance-defined player-centered circle blocked by solid walls; out-of-range entries
  disappear immediately. Stable creation/spawn IDs supply newest order. Every chosen sort can be
  reversed and persists across restarts; size uses occupied cell count, rarity defaults to
  Legendary/Epic/Rare/Uncommon/Common, type uses Core/Weapon/Part/Material, and equal sort values use
  newest-first ID tie-breaking. The pickup circle is not drawn. Every item model has one fixed,
  extensible rarity using the initial gray/green/blue/purple/gold palette; rarity affects selling price
  and frequency rather than stats, and the zero-value Neutral starter is Common. Damage leaves the
  inventory open and shakes the stage camera behind its semi-transparent, screen-stable UI. Shake
  scales by damage percentage of maximum health; rapid hits accumulate before decay and clamping,
  subject to reduced/off accessibility settings.
- **2026-10-14 — Core system:** Finalized the Machinery/Organic/Alien cycle, Neutral matchups,
  double-weighted damage formula, five Core archetypes, deterministic Core families, and
  weak/normal/exceptional variants. Quality variants use 50%/25%/0% Neutral, preserve their family's
  primary-type ratio, share all other mechanics and base visuals, and may use optional visual
  add-ons. The 100% Neutral starter remains outside those tiers.
- **2026-10-14 — Starter and Core progression:** Established the falling-from-the-sky opening, the
  ability-less zero-cost Neutral starter, universal skeleton melee, Core acquisition and recurrence,
  physical extraction, five-copy exact-model research, and the permanent starter blueprint.
- **2026-10-12 — Run and progression rules:** Established one-way stage entry, post-objective
  extraction, total deployed-item risk, safe base storage, encounter logging, deterministic Parts,
  five-copy research, immediate whole-loadout manufacturing, ownership, storage resolution, spatial
  run inventory, and persistent ground loot.
- **2026-10-12 — Demo and later multiplayer scope:** Set the first release to a solo offline demo on
  desktop, Android, and iOS, with story and farming expeditions. Preserved later 1–6-player
  cross-platform cooperative PvE, including downed, revive, spectator, USB recovery, assembly-room,
  and extraction-quorum rules.
- **2026-10-11 — Story and stage foundation:** Confirmed the title, preserved the original
  *Scrapborn* narrative, defined the unique experiment and Scrap Planet conflict, established fixed
  story stages versus randomized arrangements of authored farming rooms, and introduced
  manufacturer-based Resonance and the eventual company takeover.
