# Conquer the World: Scrapborn - Release Roadmap

Last reviewed: 2026-10-15

This is the editable plan for taking the current prototype to a public release. It is intentionally
split into small tasks so individual items can be added, removed, reordered, or assigned without
changing the rest of the plan.

Canonical story, lore, and narrative questions are maintained in
[`STORY_BIBLE.md`](STORY_BIBLE.md).

The first practical target is a **free offline public demo for desktop computers, Android, and
iOS**. It has no LAN, Internet multiplayer, account, remote-server, or online-service dependency.
Cross-platform multiplayer remains a post-demo goal. A larger Early Access or 1.0 release should
only be scheduled after the public-demo exit criteria are met.

## How to edit this plan

- `[x]` means the repository already contains the feature or the decision is final.
- `[ ]` means the task is not yet complete.
- `P0` blocks the target release for its section. Work inside an explicitly post-demo section does
  not block the offline public demo.
- `P1` is important for a dependable release.
- `P2` is valuable polish or a post-demo feature.
- Use the task ID when requesting a change, for example: `Remove NET-14` or
  `Add controller rebinding after UX-14`.
- Do not mark an implementation item complete until its acceptance check can be demonstrated in a
  packaged build. Decision items may be marked complete when the creator has approved the rule.
- Task IDs are stable references. Their numeric order may differ from display order when later
  decisions add work to an earlier subsystem.

## Confirmed requirements

- [x] **REQ-01 (P0):** Keep shared game logic in Java/libGDX while adding desktop, Android, and iOS
  launchers.
- [x] **REQ-02 (P0):** Support eight visual character directions: north, north-east, east,
  south-east, south, south-west, west, and north-west.
- [x] **REQ-03 (P0):** Provide at least four distinct movement frames for every direction.
- [x] **REQ-04 (P0):** Preserve crisp pixel-art rendering with nearest-neighbor filtering and
  integer viewport scaling.
- [x] **REQ-05 (P2):** For the later networked release, keep authoritative multiplayer simulation;
  clients send inputs rather than trusted positions.
- [x] **REQ-06 (P2):** After the offline demo, support cross-play among desktop, Android, and iOS
  players through one compatible gameplay protocol.
- [x] **REQ-07 (P0):** Build characters from one Core and a selectable set of Parts, with each
  equipped item affecting the abilities available in a stage.
- [x] **REQ-08 (P0):** Let compatible Parts resonate with the equipped Core to strengthen its
  ability, at the opportunity cost of abilities supplied by other Parts.
- [x] **REQ-09 (P0):** Let players choose a build before entering a stage and fight through that
  stage using the chosen setup.
- [x] **REQ-10 (P0):** Make the original storyline a defining feature of the game.
- [x] **REQ-11 (P0):** Build each stage as a connected set of rooms inside a location such as a
  company, ship, or other facility. Clearing a room reveals the other rooms directly connected to
  it so the player can choose the next route.
- [x] **REQ-12 (P0):** Support multiple room purposes, including combat, Core changing, weapon
  changing, consciousness downloading, follower recruitment, bosses, achievements/objectives, and
  lore.
- [x] **REQ-13 (P0):** Author every individual room before release. Assemble farming stages from
  randomized arrangements of compatible predefined rooms, while story/lore stages use fixed room
  graphs and placements.
- [x] **REQ-14 (P0):** Reveal newly connected exits only as unknown paths; do not reveal the next
  room's type or reward before the party enters it.
- [x] **REQ-15 (P0):** Use roguelite meta-progression: recovered Parts/materials can return to each
  player's own base for analysis and later mass production, while failed stages return the party to
  base manufacturing without deleting the persistent campaign.
- [x] **REQ-16 (P0):** Make consciousness-download rooms physical recovery opportunities: players
  find a reusable USB/data-transfer device, backtrack to a fallen teammate to extract their active
  consciousness, and
  insert it into an available prepared body.

## Decision register

Checked items are approved design rules; unchecked items still need a creator decision. This register
is authoritative for gameplay scope, while `STORY_BIBLE.md` is authoritative for narrative canon.

Current open-decision index:

- **Demo design:** DEC-09 through DEC-12 and DEC-15.
- **Narrative:** DEC-20 through DEC-22.
- **Later multiplayer:** DEC-74 and DEC-148; DEC-148 depends on DEC-10.

- [x] **DEC-01 (P0):** Game promise: Build a fighter around an innate Core and interchangeable body
  Parts, choose between additional abilities or resonance that strengthens the Core, and take that
  personalized build through story-driven combat stages with friends on desktop, Android, or iOS.
- [x] **DEC-02 (P0):** The first release will be a free public demo.
- [x] **DEC-03 (P0):** The supported platform families are desktop computers, Android, and iOS,
  with offline builds for the first demo and cross-play deferred until afterward. Confirm the exact
  desktop operating systems and storefronts before release.
- [x] **DEC-04 (P0):** The primary game mode is clearing a stage represented by connected rooms. A
  final objective room may contain a boss, a required achievement/objective, or an important lore
  event. Clearing it secures the stage and unlocks post-clear backtracking; explicit extraction
  afterward completes the stage.
- [x] **DEC-05 (P0):** The overall game supports 1–6 players per room/stage, including the host;
  the later network server must reject a seventh player cleanly. The offline demo uses one local
  player.
- [x] **DEC-06 (P0):** The first public demo is local/offline only. It has no LAN play, Internet
  multiplayer, remote accounts, backend dependency, or cross-device play; networking comes later.
- [x] **DEC-07 (P0):** Use solo/cooperative PvE: player-controlled characters fight enemies and
  complete shared objectives. Do not implement PvP or a competitive player-versus-player mode in
  the demo or near-term roadmap.
- [x] **DEC-08 (P0):** The main progression is a persistent, story-driven campaign made of stages;
  each stage is a room-clearing expedition rather than a score-only arena match.
- [ ] **DEC-09 (P1):** Choose the minimum supported resolution, display modes, and aspect ratios.
- [ ] **DEC-10 (P1):** Decide whether keyboard/mouse is the only launch input or controllers are
  required.
- [ ] **DEC-11 (P1):** Define the visual/content rating target and prohibited content.
- [ ] **DEC-12 (P1):** Choose whether skin differences are tint-only, unique artwork, or a mix.
- [x] **DEC-13 (P0):** Use six independently equipped Part slots: head, torso, left arm, right arm,
  left leg, and right leg. The left/right arm and leg slots may contain different Part models.
- [x] **DEC-14 (P0):** Include at least 10 distinct Core models in the free public demo. This is a
  minimum rather than a hard maximum. Core taxonomy and model-coverage rules are defined in DEC-121
  through DEC-131; unrestricted selection among owned Cores is defined by DEC-120.
- [ ] **DEC-15 (P0):** Define resonance: which tags/relationships activate it, whether multiple
  Parts stack, and exactly which Core values or behaviors it improves.
- [x] **DEC-16 (P0):** Core and weapon changes use their special stage rooms. Parts work differently:
  players may equip, replace, or remove Parts they loot throughout the current stage without a Part
  room or a return to base. Empty Part slots are allowed by DEC-77.
- [x] **DEC-17 (P0):** Discover Cores and Parts as physical stage loot or authored rewards, carry or
  equip them through successful extraction, and keep each extracted copy usable until deliberately
  consumed for research. Five exact-model copies unlock that model's manufacturing blueprint; the
  permanently known zero-cost Neutral starter Core is the explicit exception. Individual Core
  sources and recurrence follow DEC-133 and DEC-150 through DEC-153.
- [x] **DEC-18 (P0):** Stages are solo PvE in the offline demo and cooperative PvE after multiplayer
  is introduced. Competitive PvP is out of scope for the foreseeable roadmap.
- [x] **DEC-19 (P0):** Preserve the original *Scrapborn* premise in `STORY_BIBLE.md` and use that
  file as the canonical source for future story decisions.
- [ ] **DEC-20 (P0):** Decide whether the character who dies on the first or second mission is the
  unique experiment, an ordinary playable crew member, or a non-player companion.
- [ ] **DEC-21 (P0):** Define whether the Core breakthrough after that death restores the same
  consciousness, creates a new consciousness, or transfers an ordinary consciousness into a Core.
- [ ] **DEC-22 (P0):** Decide whether the conquered company is the experiment's creator or another
  manufacturer and what taking control of that company requires.
- [x] **DEC-23 (P0):** Use two stage structures: randomized farming stages assembled from predefined
  rooms, and fixed story/lore stages with authored progression.
- [x] **DEC-24 (P0):** Revealed room connections appear as unknown paths without showing the room
  type or reward.
- [x] **DEC-25 (P0):** Recruited followers are trapped or stranded inside the stage, join the party
  for that stage only, and leave the active party when the stage ends.
- [x] **DEC-26 (P0):** Changing a Core in a Core room also changes the main ability connected to that
  Core; the two are swapped together. Equipping the default Neutral starter instead grants no Core
  ability.
- [x] **DEC-27 (P0):** Allow players to backtrack through previously cleared rooms.
- [x] **DEC-28 (P0):** Place an assembly room before each battle room. Its battle door opens only
  after every active player is physically present; do not force-teleport teammates into combat.
- [x] **DEC-29 (P0):** Later in the story, player memories/state continuously synchronize to a cloud
  system. The active consciousness remains with its current body/Core and must be transferred to
  another body for immediate mission recovery.
- [x] **DEC-30 (P0):** A teammate can revive a downed player by remaining inside that player's revive
  circle for the required duration. A successful revive returns the player with exactly 1 HP.
- [x] **DEC-31 (P0):** Leaving a downed teammate's revive circle resets that revival attempt's
  progress to zero.
- [x] **DEC-32 (P0):** If the entire party is downed and nobody can revive, the current game/run ends
  because no survivor can reach a consciousness-download room. The party returns to its base/company
  preparation flow rather than automatically restarting the stage.
- [x] **DEC-33 (P0):** A disconnected player's body remains at its last authoritative position so
  the player can reconnect. The host decides whether to keep waiting or kick that player.
- [x] **DEC-34 (P0):** A disconnected retained body counts as present for an assembly room when its
  authoritative position is inside that room.
- [x] **DEC-35 (P0):** Enemies can damage and down a disconnected player's retained body.
- [x] **DEC-36 (P0):** Taking damage does not interrupt or reset a revive attempt; leaving the revive
  circle is what resets its progress.
- [x] **DEC-37 (P0):** A teammate can continue attacking while reviving as long as they remain inside
  the revive circle.
- [x] **DEC-38 (P0):** Multiple teammates inside the revive circle do not make revival faster; one or
  more eligible teammates produce the same fixed progress rate.
- [x] **DEC-39 (P0):** A downed player has a 15-second revival window, after which normal teammate
  revival is no longer possible. Pause this expiration timer while a valid revive is actively
  progressing.
- [x] **DEC-40 (P0):** A teammate must remain inside the revive circle for 5 continuous seconds to
  complete a revival.
- [x] **DEC-41 (P0):** When the 15-second revival window expires, that player enters spectator mode
  and uses a player-following spectator camera.
- [x] **DEC-42 (P0):** Clearing a combat room does not automatically restore its spectators. A dead
  player can return during the same stage only if the surviving party reaches a
  consciousness-download room; otherwise they spectate until the stage/run is cleared and return
  only after the party completes base/manufacturing preparation for a later stage.
- [x] **DEC-43 (P0):** Spectators can switch their camera among eligible living teammates but cannot
  move the camera independently.
- [x] **DEC-44 (P0):** A consciousness-download room enables a dead teammate to return in a prepared
  Core/body containing loaner Parts that are not the player's own. Recovery requires a USB/data key
  from that room, extracting consciousness data from the fallen body, and then inserting the data
  into the prepared body.
- [x] **DEC-45 (P0):** A restored player may backtrack to the death location and loot/recover their
  original Parts. Teammates may carry or use another player's storage-bound Parts during the stage,
  but must physically return them to that owner before extraction. An owner-bound item extracts only
  while carried or equipped by its owner; another player's inventory and the ground do not return it.
  Only previously unbound stage loot can become the final carrier's property.
- [x] **DEC-46 (P0):** A consciousness-download room contains a randomized number of prepared
  replacement bodies from 1 through the current player count, with generation strongly weighted
  toward exactly 1 body.
- [x] **DEC-47 (P0):** Prepared recovery bodies and their Parts are associated with the company that
  owns/created the stage. Each Part independently selects a company-associated model/variant:
  94.9% weak, 5% normal, and 0.1% exceptionally strong. Tiers with different gameplay data are
  distinct models/variants rather than random stat rolls on copies of one model.
- [x] **DEC-48 (P0):** Recovery-room bodies, Cores, and Parts are not reserved for dead teammates.
  Any player may transfer into an available Core/body or loot its equipment.
- [x] **DEC-49 (P0):** Manufacturing research requires several recovered samples rather than
  unlocking a blueprint from only one Part.
- [x] **DEC-50 (P0):** The USB/data-transfer device is reusable and is not consumed by recovery. It
  extracts the active consciousness itself from the fallen body/Core and transfers it into another
  body/Core. The situation is urgent in context, but there is no gameplay countdown after extraction.
- [x] **DEC-51 (P0):** Each consciousness-download room contains exactly one reusable USB device. It
  carries one consciousness at a time and can sequentially restore multiple teammates through
  repeated fallen-body-to-prepared-body trips while unused prepared bodies remain.
- [x] **DEC-52 (P0):** If the USB carrier is downed or disconnected, the USB and its current
  consciousness payload drop safely at that body's location so another player can loot the body,
  recover the device, and continue the transfer.
- [x] **DEC-53 (P0):** The recovery USB is stage-only and cannot be extracted to a personal base.
  Persistent possessions, ownership, research, blueprints, and progression synchronize to the cloud.
- [x] **DEC-54 (P0):** Unlocking a Part's manufacturing blueprint requires five recovered samples.
- [x] **DEC-55 (P0):** All five manufacturing-research samples must be the exact same Part model;
  related Parts, shared manufacturer lineage, and weak/normal/exceptional variants do not count
  toward one another's blueprints.
- [x] **DEC-56 (P0):** Looted Parts/materials remain temporary run inventory until successful
  post-objective extraction commits them to persistent cloud inventory.
- [x] **DEC-57 (P0):** If a run ends while the stage USB carries an active consciousness, that
  consciousness is lost. Cloud-synchronized memories/state cannot recreate the active person for
  a later stage attempt.
- [x] **DEC-58 (P0):** After that permanent consciousness loss, the human player begins the next
  stage attempt as a new character after using persistent unlocks/blueprints and the company to
  manufacture a replacement Core, Parts, and other equipment.
- [x] **DEC-59 (P0):** A stage has no early-extraction room or exit. Clearing the final objective
  unlocks a post-clear recovery phase in which players may backtrack for loot; temporary run loot
  becomes persistent only when the party then chooses the successful extraction action.
- [x] **DEC-60 (P0):** Unlocking a Part's manufacturing blueprint consumes all five required
  samples of that exact Part model.
- [x] **DEC-61 (P0):** Protect story-critical NPCs from unplanned permanent death. They use coded AI
  to move and fight and lose HP only through implemented gameplay damage/rules, never through a
  random death roll. Only an explicitly authored story outcome can permanently kill one.
- [x] **DEC-62 (P0):** Risk every Core, Part, and other equipment item deployed into a stage. A
  failed run loses those item instances along with uncommitted loot; successful post-objective
  extraction returns deployed survivors' equipment and lets the company commit recovered loot.
- [x] **DEC-63 (P0):** When a living player transfers into a prepared recovery body, their previous
  body remains at the transfer location as loot, including its functional Core and Parts. The
  transferred consciousness controls only the new body and is not duplicated into the old Core.
- [x] **DEC-64 (P0):** After extraction, a recovered Part remains a usable inventory item until its
  owner explicitly chooses to submit it as a research sample. The player must choose between
  keeping/equipping the Part and consuming it toward the five-identical-sample blueprint cost.
- [x] **DEC-65 (P0):** An eligible character may immediately transfer into the functional Core/body
  left behind by a living transfer instead of merely carrying it as loot. Resolve the claim and
  consciousness movement authoritatively so the Core cannot be occupied or looted twice.
- [x] **DEC-66 (P0):** After full-party defeat, remove every deployed item and all recovered run
  loot, return the party to its company/base preparation flow, and require the players to
  prepare complete replacement loadouts before entering another stage. A player may either
  manufacture a new robot or equip one entirely from surviving company storage; do not grant free
  replacement equipment or automatically restart the failed stage.
- [x] **DEC-67 (P0):** Continuous cloud synchronization restores the defeated party's synchronized
  consciousnesses at the company/base after full-party defeat, even when nobody survived to retrieve
  the bodies. The existing exception remains: a consciousness actively carried by the stage USB is
  lost if the run ends before installation.
- [x] **DEC-68 (P0):** Entering a stage is a one-way commitment. Players may backtrack within its
  cleared room graph but cannot return to base, retreat, or extract early. The stage ends only when
  the final objective has been cleared and the party explicitly extracts, or when the attempt fails;
  failure loses everything deployed or collected there.
- [x] **DEC-69 (P0):** Upload every enemy that actually spawns during a stage attempt to the party's
  cloud encounter log, including failed attempts, so players can inspect known opposition and
  manufacture appropriate equipment before returning.
- [x] **DEC-70 (P0):** Keep every item left at the company/base safe when a stage attempt fails. Only
  the equipment deployed into the stage and loot collected during it are lost.
- [x] **DEC-71 (P0):** Limit the cloud encounter log to enemies and information players could
  directly observe during play. Do not automatically reveal hidden statistics, weaknesses, unseen
  behaviors, or drop rules; players may investigate and create their own community wiki.
- [x] **DEC-72 (P0):** Keep the first offline public demo solo-only with exactly one local player.
  Same-device cooperative play may be reconsidered after the demo; the later networked game still
  targets 1–6 players.
- [x] **DEC-73 (P0):** In the solo offline demo, AI companions cannot revive the player or operate a
  recovery USB on the player's behalf. Defeating the sole player ends the stage attempt immediately.
  Downed timers, teammate revival, spectating, and USB teammate recovery are later multiplayer rules.
- [ ] **DEC-74 (P2):** For later cooperative multiplayer, decide whether teammate attacks pass
  harmlessly through allies or friendly fire can be enabled as an optional difficulty modifier.
- [x] **DEC-75 (P0):** Approve the first PvE mode family in this order: story expeditions, farming
  expeditions, boss hunts, settlement defense, rescue/escort missions, and challenge contracts.
  Keep large-scale raids outside this first group and keep competitive PvP out of scope.
- [x] **DEC-76 (P0):** Limit the free solo demo to story expeditions and farming expeditions. Build
  boss hunts, settlement defense, rescue/escort missions, and challenge contracts after the demo,
  in that order.
- [x] **DEC-77 (P0):** Allow any of the six Part slots to be empty. The underlying skeleton/frame
  still supports basic movement, but an empty slot supplies no Part-derived power, ability, stat,
  or Resonance contribution. Any ability supplied by the equipped Core remains usable even when all
  six Part slots are empty; the default Neutral starter supplies none.
- [x] **DEC-78 (P0):** Allow players to collect Parts during combat, but allow equipping, removing,
  or swapping Parts only after the current room's clear condition is satisfied. Looted Parts remain
  usable during the same stage without a dedicated Part room.
- [x] **DEC-79 (P0):** Use a spatial grid for temporary run inventory. Every carried loot item
  occupies a defined group of cells, including rectangular and non-rectangular footprints; required
  examples include a 1-by-2 arm and an L-shaped leg. The equipped torso determines the inventory
  grid's available space.
- [x] **DEC-80 (P0):** Before an inventory-capacity change, let the player rearrange items or drop
  them manually. When the player confirms a torso/Core change, automatically drop every whole item
  that no longer fits entirely within the new active-cell mask into the current cleared room. Do not
  block the change, choose among overflow items, or destroy anything.
- [x] **DEC-81 (P1):** Allow every carried item footprint to be rotated in 90-degree increments and
  flipped horizontally or vertically while arranging the inventory grid. These layout transforms
  do not change the item's gameplay identity or a Part's functional body slot, such as left arm
  versus right arm.
- [x] **DEC-82 (P0):** Require Parts, weapons, unequipped Cores, and crafting materials to occupy
  data-defined shapes in the same torso-provided spatial run inventory. Apply the same fit, rotation,
  flip, manual-drop, loss, and successful-extraction rules to all four loot categories.
- [x] **DEC-83 (P0):** Stack identical crafting materials up to 99 units in one inventory footprint.
  Additional units start or fill another stack that must fit in the grid separately. Allow splitting
  and merging stacks without ever exceeding 99 units in one stack. Once materials are owner-bound,
  merge them only with the same material model and persistent-owner ID; never combine different
  owners or bound and unbound quantities in one stack.
- [x] **DEC-84 (P0):** Give the starter/default torso a predefined 3-by-3 active region within the
  shared inventory canvas. A skeleton with no torso has zero active inventory cells, while every
  equipped torso supplies its own data-defined active-cell mask for progression and build tradeoffs.
- [x] **DEC-85 (P0):** Define torso/Core Resonance inventory expansion independently for each torso
  and compatible Core relationship. Each relationship specifies exactly which additional cells it
  unlocks, so different torsos may gain different amounts and grid shapes.
- [x] **DEC-86 (P0):** Use one predetermined 15-by-15 inventory canvas for every character. Torso and
  Resonance rules activate subsets of those 225 fixed positions; inactive cells cannot hold items.
  Preserve item coordinates across mask changes and drop any item touching a newly inactive cell.
  Removing the torso deactivates the entire canvas and drops all carried inventory.
- [x] **DEC-87 (P0):** Keep every dropped loot item at its ground location for the entire current
  stage attempt. Preserve it through room transitions, backtracking, room unloading/reloading, and
  stage save/resume; never despawn it because of time or distance.
- [x] **DEC-88 (P0):** Clearing the final objective does not immediately end the stage. Enter a
  post-clear recovery phase in which players may backtrack and collect persistent ground loot, then
  complete the stage through an explicit extraction action. Only carried/equipped items belonging
  to living players who are not downed at the extraction instant extract; DEC-95 defines the downed
  exception.
- [x] **DEC-89 (P0):** Keep the post-clear recovery phase dangerous. Traps, environmental hazards,
  and surviving threats remain active; dying before extraction fails the attempt and loses deployed
  equipment plus all uncommitted loot. Do not automatically repopulate cleared combat encounters.
- [x] **DEC-90 (P0):** Put the successful extraction point in the final room. Each final-room type
  defines how it becomes available: it may appear/activate after a boss or objective is completed,
  or an existing exit may simply open when the room's configured clear condition is satisfied. After
  post-clear backtracking, the player must return to that extraction point and use it to leave.
- [x] **DEC-91 (P2):** For later cooperative multiplayer, require at least half of the active living
  player bodies, rounded up (`ceil(activeLivingPlayers / 2)`), inside the final-room extraction point
  to start a 10-second party countdown. If every active living player is inside at any time, extract
  the whole party immediately. Countdown completion also extracts the whole party, including living
  players outside the point; solo extraction is immediate because the sole player is everyone.
- [x] **DEC-92 (P2):** Once extraction quorum starts the 10-second countdown, let it continue even if
  players leave and presence falls below the threshold. Do not pause, reset, or cancel it because of
  attendance changes; immediate extraction still occurs if every active living player enters.
- [x] **DEC-93 (P2):** Exclude dead spectators from both the extraction-quorum denominator and the
  number present. Continue counting a disconnected player's retained living body as active until the
  host kicks it; count that body as present only when its authoritative position is inside the
  extraction point.
- [x] **DEC-94 (P2):** Count a downed player whose 15-second revival window has not expired as an
  active living player for extraction quorum. Use the downed body's authoritative position for
  presence, including the all-active-players-present immediate extraction rule. Exclude the player
  only after the window expires and they become a dead spectator.
- [x] **DEC-95 (P2):** At the extraction instant, treat every still-downed player as dead even if
  their revival window has time remaining. Do not extract that player's equipped gear or carried
  inventory; leave it with the stage/body and lose it when the attempt closes. Restore the ordinary
  synchronized consciousness at base, subject to the existing lost-USB-payload exception.
- [x] **DEC-96 (P0):** Let every Part provide data-defined stat modifiers in addition to its ability
  or Resonance contribution. Supported modifiers include health, movement speed, armor, energy, and
  inventory cells, with room for other validated stats. The skeleton keeps baseline playable values;
  an empty Part slot contributes no Part-derived stats.
- [x] **DEC-97 (P0):** When a build change lowers maximum health or maximum energy, immediately clamp
  the current value to the new maximum (`current = min(current, newMaximum)`). Discard the excess;
  re-equipping the previous Part does not restore it.
- [x] **DEC-98 (P0):** When a build change raises maximum health or energy, keep the current numeric
  value unchanged. Do not preserve its percentage or grant the added capacity; the player must heal
  or regenerate into the newly available capacity normally.
- [x] **DEC-99 (P0):** Give every Part its own data-defined stat profile with a level/value for each
  applicable statistic. Allow positive, neutral, and negative modifiers so a Part can trade one
  strength for another, such as gaining health and armor while losing movement speed or energy.
- [x] **DEC-100 (P0):** Make every copy of the same Part model mechanically identical. Its stats,
  ability, equipment slot, tags/effects, inventory footprint, compatibility, and Resonance data are
  fixed by its stable model ID; do not roll per-instance stat values or random affixes. Any version
  with different gameplay data, including weak, normal, or exceptional recovery gear, must use a
  distinct model/variant ID.
- [x] **DEC-101 (P0):** Research each mechanically distinct Part variant separately. A blueprint
  requires five recovered copies of that exact variant/model ID; weak, normal, and exceptional
  versions cannot be mixed, and unlocking one does not unlock the other variants in its Part family.
- [x] **DEC-102 (P0):** Scale manufacturing requirements with Part-variant strength. Normal variants
  require more materials than weak variants, and exceptional variants require more than normal
  variants. Store exact material costs per model so they can be balanced without changing code.
- [x] **DEC-103 (P0):** Complete manufacturing immediately when the player confirms a valid recipe
  and has its blueprint and required materials. Do not use real-world timers, in-game production
  timers, expedition-completion waits, or other manufacturing delays.
- [x] **DEC-104 (P0):** Treat a manufacturing configuration as one complete robot loadout: one
  required Core, one optional weapon slot, and six independently optional Part slots. Save the exact
  model/variant IDs and empty slots as a reusable loadout template. On confirmation, calculate and
  display the combined material cost of every included item, then manufacture one complete copy
  immediately. Do not batch multiple loadouts or run an automatic production queue; each additional
  copy requires another confirmation and payment of the newly calculated total. This `Create New
  Robot` flow always makes new item instances and never silently consumes or substitutes matching
  items from storage.
- [x] **DEC-105 (P0):** Keep `Create New Robot` separate from `Equip Existing Robot`. The equipment
  flow assembles a robot only from item instances already in company storage and does not manufacture
  missing components. Company storage holds loose Cores, Parts, and weapons rather than assembled
  robots. Present those components through a large paginated inventory that remains safe when a
  stage fails.
- [x] **DEC-106 (P0):** After full-party defeat, retain each lost robot's saved loadout configuration
  even though its deployed item instances are gone. Return the player to base with that template
  preselected and its current total cost ready for replacement manufacturing; the player may instead
  choose the separate existing-storage equipment flow.
- [x] **DEC-107 (P0):** Assign every Core, Part, weapon, and crafting-material quantity manufactured
  for a player or committed to their company storage an immutable persistent-owner ID for that local
  profile or later network account. Carrying, dropping, lending, equipping, stack operations, death,
  disconnection, or extraction must never rewrite that ownership ID.
- [x] **DEC-108 (P0):** Allow teammates to carry or temporarily equip another player's owned
  equipment during a stage, but never let them extract it into either company automatically. An
  owner-bound item returns to company storage on successful extraction only if its persistent owner
  is personally carrying or equipping it at that instant. If a teammate or the ground holds it, the
  item remains in the stage and is lost when the attempt closes.
- [x] **DEC-109 (P0):** Clearly label non-owned equipment with its owner's identity and warn that it
  cannot be retained, researched, or deposited into the current player's company and will be lost
  unless physically returned to its owner before extraction. Show the warning before consequential
  use and again near extraction. Newly found unbound stage equipment
  remains unowned through all pickups and handoffs, then becomes bound to its eligible final carrier
  only when successful extraction commits it.
- [x] **DEC-110 (P0):** Make persistent crafting materials account-owned under the same rule as
  equipment. Players cannot give, trade, deposit, or merge their bound materials into another
  player's company inventory. Teammates may carry a bound material stack during a stage, but
  it extracts only when its immutable owner is the final eligible carrier. A stack held by anyone
  else or left on the ground stays behind and is lost. Eligible unbound materials found in the stage
  bind to the eligible player carrying the stack when extraction commits it.
- [x] **DEC-111 (P0):** Use ordinary stage inventory and ground drops for equipment lending; do not
  create a pre-stage loan-assignment system. The owner places their Core, Part, or weapon in their
  own starting run inventory, joins the stage with it, and drops it for a teammate to pick up and
  use. Its immutable owner ID does not change. Before extraction, the teammate must drop or hand it
  back and the owner must carry or equip it. Otherwise it stays behind and is lost when the stage
  closes, even after a successful party extraction.
- [x] **DEC-112 (P0):** Keep newly found loot unowned for the duration of the stage, regardless of
  who first picks it up or how many times it changes hands. At successful extraction, assign each
  carried or equipped unowned item/material stack to the eligible player carrying or equipping it at
  that instant. Uncarried ground loot is not extracted or assigned.
- [x] **DEC-113 (P0):** Do not use ownership alone to retrieve an item from a stage. Successful
  extraction commits only possessions carried or equipped by an eligible player. For an already
  owner-bound item or material stack, that eligible carrier must also match its persistent-owner ID;
  otherwise the possession remains behind and is lost during stage teardown.
- [x] **DEC-114 (P2):** Do not block, pause, or reset extraction when a player carries possessions
  owned by someone else. Show a clear non-blocking warning identifying the affected owner-bound
  items/material stacks and explaining that they will be left behind and lost, then let the existing
  extraction action or countdown continue normally.
- [x] **DEC-115 (P0):** Give each company a finite persistent-storage capacity. Present the large
  inventory across pages with search, sorting, and filtering rather than treating it as unlimited.
  Store page size and total capacity as validated balance data. Defer the starting page count, slots
  per page, and upgrade amounts until storage implementation and playtesting provide real usage data.
- [x] **DEC-116 (P0):** Immediately after every successful extraction, open a storage-resolution
  session before normal base play resumes. Show the player's frozen extracted carried inventory
  alongside their paginated company storage; do not attempt any automatic storage placement. Let the
  player open and reorganize company storage, manually transfer every carried possession they want
  to keep into an available slot, and permanently discard existing stored possessions to free slots.
- [x] **DEC-117 (P0):** Let each player improve their own persistent company. Include storage
  expansion as a company-upgrade track supporting both larger existing pages and additional pages.
  Keep upgrade levels, costs, prerequisites, per-page slot counts, and page counts data-driven;
  additional company improvement tracks remain to be designed.
- [x] **DEC-118 (P0):** When the player confirms the post-extraction storage session, permanently
  discard every possession still left in the extracted carried inventory instead of moving it
  automatically. Before final confirmation, clearly summarize all items/material stacks that will
  be lost. Persist an unconfirmed session so closing the game cannot bypass the decision.
- [x] **DEC-119 (P0):** After successful extraction, keep the surviving robot's required Core,
  optional weapon slot, and six optional Part slots exactly as they were at extraction. Equipped
  items do not enter the storage-resolution session and do not consume company-storage slots while
  equipped. Only the carried run inventory must be manually stored or discarded.
- [x] **DEC-120 (P0):** A consciousness is not permanently bound to one Core model. Between stages,
  the player may transfer into and equip any Core they own. Authorized in-stage Core rooms remain
  the only way to change Core during a stage, and every Core change also swaps its linked main
  ability.
- [x] **DEC-121 (P0):** Classify ordinary Core composition with three primary types: `Machinery`,
  `Organic`, and `Alien`. Add `Neutral` as a deliberately weak special fourth type used by the
  fallback starter Core and permitted in other hybrid Cores.
- [x] **DEC-122 (P0):** Give every ordinary Core one of five cross-type archetypes: `Speed` for
  mobility, `Tankiness` for durability, `Strength` for carrying/using heavier equipment and weapons,
  `Shielder` for a regenerating shield that protects health, and `Specialist` as a controlled
  wildcard for unusual or future mechanics. The default Neutral starter Core is the sole confirmed
  no-archetype exception.
- [x] **DEC-123 (P0):** Allow a Core to blend Machinery, Organic, Alien, and Neutral with data-defined
  percentages that must total exactly 100%. Pure Cores use 100% of one type; hybrid Cores may
  represent company cooperation or other shared technology, such as 70% Organic and 30% Machinery.
- [x] **DEC-124 (P0):** Use the effectiveness cycle `Machinery > Organic > Alien > Machinery`, with
  `1.5x` for advantage, `1.0x` for the same type, and an intentionally severe `0.5x` for
  disadvantage. Against a single-type enemy, sum each Core-type percentage multiplied by that
  type's matchup value. Example: a 70% Organic/30% Machinery Core against Alien produces
  `(0.70 * 1.5) + (0.30 * 0.5) = 1.20x` effectiveness.
- [x] **DEC-125 (P0):** Allow enemies to blend the same three types with percentages totaling
  exactly 100%. Calculate a hybrid-versus-hybrid matchup by multiplying every Core type share by
  every enemy type share and that pair's matchup value, then adding all contributions:
  `typedMatchupMultiplier = sum_i sum_j(attackerShare_i * defenderShare_j * matchup_i_j)`. Pure
  enemies are
  the 100%-one-type special case. Example: 70% Organic/30% Machinery versus 60% Alien/40%
  Machinery produces `(0.70 * 0.60 * 1.5) + (0.70 * 0.40 * 0.5) + (0.30 * 0.60 * 0.5) +
  (0.30 * 0.40 * 1.0) = 0.98x`.
- [x] **DEC-126 (P0):** Apply the current attacker-versus-defender type effectiveness as a base-damage
  multiplier. Each weapon or damaging ability defines its own base damage, then calculates
  `typeScaledBaseDamage = sourceBaseDamage * typedMatchupMultiplier`. For player attacks, the
  equipped Core supplies the attacker percentages and the target enemy supplies the defender
  percentages; a weapon does not replace the Core composition used by this calculation.
- [x] **DEC-127 (P0):** Keep Core-type effectiveness damage-only. Do not apply its multiplier to
  healing, shield capacity or regeneration, stun duration, status-effect chance or duration,
  movement, carrying capacity, or any other non-damage value; those systems use their own rules.
- [x] **DEC-128 (P0):** Apply type effectiveness in both combat directions. For an enemy attack, the
  enemy's type mixture supplies the attacker percentages and the player's equipped Core supplies
  the defender percentages. Use the same double-weighted formula and matchup cycle before later
  mitigation. Reversing the confirmed hybrid example gives `1.02x` enemy damage: `(0.60 * 0.70 *
  0.5) + (0.60 * 0.30 * 1.5) + (0.40 * 0.70 * 1.5) + (0.40 * 0.30 * 1.0)`.
- [x] **DEC-129 (P0):** Do not require a Core model for every primary-type/archetype combination.
  The three primary types and five archetypes define a design vocabulary, not a mandatory 15-model
  grid. Ordinary Core models may be hybrid by design, and intentionally absent combinations are
  valid. The starter Core is the confirmed 100% Neutral model, while later hybrids may also contain
  a Neutral percentage.
- [x] **DEC-130 (P0):** Keep each ordinary Core's archetype/subtype pure for the current design. It
  selects exactly one of `Speed`, `Tankiness`, `Strength`, `Shielder`, or `Specialist`; archetypes do
  not use percentages and cannot be blended. Machinery/Organic/Alien/Neutral type composition may
  be mixed. The default Neutral starter Core has no archetype.
- [x] **DEC-131 (P0):** Treat archetype/subtype as a flexible thematic classification rather than a
  standardized passive package. Author each Core model's stats, passive behavior, and main ability
  individually, while ensuring its overall gameplay still clearly fits its chosen subtype. Two Cores
  with the same subtype do not have to receive identical bonuses or mechanics.
- [x] **DEC-132 (P0):** Begin a new game with exactly one owned Core: the deliberately weak starter
  Core with a 100% `Neutral` type composition. The other demo Core models are not initially owned,
  so the player must farm and progress out of the fallback build.
- [x] **DEC-133 (P0):** Obtain non-starter Core models through authored discoveries inside stages,
  quest rewards, and story/world events. Different Core models may use different acquisition paths;
  they do not all need one universal unlock method. Ordinary in-stage Core loot still becomes
  persistent only through successful extraction under the existing run-loot rules.
- [x] **DEC-134 (P0):** Completing the story-integrated tutorial gives the player control of a small
  company that becomes their initial persistent base for storage, manufacturing, research, loadout
  preparation, and later company improvements.
- [x] **DEC-135 (P0):** Make the weak Neutral starter Core a permanent default blueprint in the
  ordinary company manufacturing flow rather than a separate order service. A player who loses a
  deployed build can manufacture it again and farm back up without deleting or locking equipment
  left safely in company storage.
- [x] **DEC-136 (P0):** Set Neutral's damage matchups outside the primary three-type cycle. Neutral
  deals `0.75x` damage to Machinery, Organic, and Alien; each of those primary types deals `1.25x`
  damage to Neutral; and Neutral versus Neutral is `1.0x`. Against a primary-type hybrid, apply
  these values through the same double-weighted formula.
- [x] **DEC-137 (P0):** Set the default Neutral starter Core's manufacturing cost to zero. It
  consumes no currency, materials, or other persistent resource, ensuring the Core itself is always
  recoverable.
- [x] **DEC-138 (P0):** Allow Neutral to appear as a percentage in non-starter hybrid Cores. Each
  Neutral attacker/defender share uses DEC-136's pair value inside the standard double-weighted sum;
  the entire Core composition must still total exactly 100%.
- [x] **DEC-139 (P0):** Unlock company manufacturing when the tutorial awards the player's first
  company. The default Neutral starter blueprint is permanently available there and uses the normal
  manufacturing interface; no special `Order Starter Core` button or service exists.
- [x] **DEC-140 (P0):** Allow repeated manufacturing of the default Neutral starter Core. Every copy
  has a fixed sale value of zero, so creating duplicates cannot generate currency.
- [x] **DEC-141 (P0):** Treat the default Neutral starter blueprint as known from the start rather
  than researchable. Starter Cores cannot contribute research samples, have zero sale price, and
  cannot be dismantled because the game provides no dismantling action.
- [x] **DEC-142 (P0):** Require only a Core for a valid deployable robot. The weapon slot and all six
  Part slots may be empty. A bare skeleton equipped only with the zero-cost Neutral starter Core can
  enter a farming stage, move normally, and use the skeleton attack defined by DEC-144.
- [x] **DEC-143 (P0):** Give the default Neutral starter Core no archetype/subtype or specialized
  subtype benefit. Its identity is simply a deliberately bad baseline with weak model-defined base
  stats and no Core ability.
- [x] **DEC-144 (P0):** Give the underlying skeleton/frame a universal weak basic attack independent
  of Cores, weapons, and Parts. This lets a Core-only Neutral starter build defeat enemies and farm
  its first materials without giving the Neutral Core an ability. Treat it as a normal damage source:
  its own base damage is multiplied by the equipped Core-versus-target type multiplier exactly once.
- [x] **DEC-145 (P0):** Make the universal skeleton attack a close-range melee strike performed by
  the underlying frame. Exact reach, base damage, timing, hit shape, and animation remain content and
  balance data.
- [x] **DEC-146 (P0):** Keep the weak skeleton melee strike selectable while a weapon is equipped.
  On desktop, scrolling cycles through available combat options, including the equipped weapon and
  an `Unarmed` option. Selecting `Unarmed` puts the weapon away without unequipping, dropping, or
  moving it from its loadout slot; selecting the weapon draws it again.
- [x] **DEC-147 (P0):** On phone and tablet, show the weapon slot on the combat HUD as a touch-cycle
  control. Tapping it selects the next combat option and updates its icon; with one equipped weapon,
  repeated taps alternate between that weapon and `Unarmed`.
- [ ] **DEC-148 (P1):** If controllers are required by DEC-10, define their equivalent combat-option
  cycling input and decide whether desktop also needs a directly bound keyboard alternative to
  scrolling.
- [x] **DEC-149 (P0):** Open the game with the player falling from the sky as discarded scrap inside
  a badly damaged frame. The default Neutral Core is already installed and contains the player's
  consciousness when the wreckage lands on the Scrap Planet and the player awakens.
- [x] **DEC-150 (P0):** A Core granted by a quest or story/world event inside a stage becomes a
  physical, at-risk reward and does not become persistent when the condition merely completes. The
  player must carry or equip it through successful extraction. Failure or leaving it behind loses
  that instance. A Core awarded in the safe company/base may become persistent immediately.
- [x] **DEC-151 (P0):** Give each authored in-stage Core reward a recurrence policy. A repeatable
  reward may spawn again on a later attempt if its Core was not extracted. A one-time reward—such as
  an Easter egg—spawns only once per campaign/save and is permanently missable if the instance is
  lost. Do not make campaign-critical progression depend on a permanently missable Core; required
  Cores must use a repeatable reward or an authored recovery path.
- [x] **DEC-152 (P0):** Successfully extracting a non-starter Core grants only that one persistent
  physical item instance. Extraction records its discovery but does not unlock its manufacturing
  blueprint or grant replacement copies. If the instance is later lost, the player must reacquire it
  through an eligible source unless its blueprint was separately unlocked.
- [x] **DEC-153 (P0):** Unlock a non-starter Core model's manufacturing blueprint by explicitly
  submitting and consuming five extracted physical copies of that exact Core model. Copies of other
  Core models cannot be mixed into the requirement. Until submission, each copy remains usable
  equipment, forcing the same keep/equip-versus-research choice used for Parts. The default Neutral
  starter blueprint remains permanently known and requires no samples.
- [x] **DEC-154 (P0):** Make every physical copy with the same stable Core model ID mechanically
  identical, matching the existing Part-model rule. Type percentages, archetype or no-archetype
  state, base stats, passive behavior, main ability or lack of one, costs, cooldowns, tags, effects,
  compatibility, and Resonance data come from the model and never roll per instance. Any mechanically
  different Core version must use a different stable model ID.
- [x] **DEC-155 (P0):** Allow Core families to have weak, normal, and exceptional variants as
  separate deterministic model IDs. Define a Core variant's quality tier by its Neutral percentage:
  higher Neutral content means a lower-quality variant, and lower Neutral content means a higher-
  quality variant. Research remains variant-specific, so each tier requires five copies of its own
  exact model ID.
- [x] **DEC-156 (P0):** Use exact Neutral percentages rather than ranges for Core quality variants:
  weak is `50% Neutral`, normal is `25% Neutral`, and exceptional is `0% Neutral`. Keep the default
  `100% Neutral` starter as a special fallback outside the ordinary weak/normal/exceptional tiers.
- [x] **DEC-157 (P0):** Preserve a Core family's Machinery/Organic/Alien ratio across all of its
  quality variants. Scale that family ratio into the percentage left after Neutral: weak variants
  place it within the remaining `50%`, normal variants within the remaining `75%`, and exceptional
  variants use the full family ratio. For example, a `70% Organic / 30% Machinery` family becomes
  weak `50% Neutral / 35% Organic / 15% Machinery`, normal `25% Neutral / 52.5% Organic / 22.5%
  Machinery`, and exceptional `70% Organic / 30% Machinery`.
- [x] **DEC-158 (P0):** Within one Core family, keep weak, normal, and exceptional variants identical
  in base stats, subtype, passive, main ability, costs, cooldowns, tags, effects, compatibility, and
  Resonance data. Their only mechanical difference is type composition through the tier's Neutral
  percentage; the existing weighted type formula changes both outgoing and incoming damage.
- [x] **DEC-159 (P0):** Retain Neutral's matchup-smoothing effect. Adding Neutral may improve a
  family's otherwise disadvantaged matchup even though it reduces stronger matchups and introduces
  Neutral's defensive vulnerabilities. Treat weak/normal/exceptional as general quality labels, not
  a guarantee that a higher tier has the better multiplier in every individual matchup.
- [x] **DEC-160 (P1):** Let a Core family's weak, normal, and exceptional variants share the same
  base visual assets. Differentiate tiers through optional add-ons or overlays that can be enabled or
  disabled per variant instead of requiring a completely redrawn visual or animation set.
- [x] **DEC-161 (P1):** Use one consistent quality progression for ordinary Core families: weak
  variants look least complete and most contaminated, normal variants look cleaner and carry more
  finished add-ons, and exceptional variants look cleanest and most complete. Permit explicitly
  special Cores to override this pattern with their own authored visual design.
- [x] **DEC-162 (P0):** When the player opens run inventory, show currently available ground loot in
  a vertically scrollable list on the left. Hovering a loot entry reveals its actual stat
  names/values and uses its rarity color to communicate rarity before pickup; do not require hidden
  analysis merely to learn those gameplay values.
- [x] **DEC-163 (P1):** Show Core quality presentation in both places: on the assembled robot when
  the Core is equipped and on the Core's inventory/loadout UI presentation. Reuse the Core's item
  picture or portrait for its field-loot display.
- [x] **DEC-164 (P1):** Use rarity color without requiring an additional standardized text label,
  icon, border pattern, or silhouette. Natural model artwork may still differ between tiers, but no
  separate non-color rarity marker is required.
- [x] **DEC-165 (P1):** Show the hovered loot item's own stats without automatically comparing them
  against currently equipped equipment.
- [x] **DEC-166 (P0):** Keep gameplay and combat simulation running while run inventory and its loot
  list are open; inventory inspection does not pause the stage.
- [x] **DEC-167 (P0):** Include only ground loot currently within the player's pickup range in the
  inventory's left-side loot list. Do not expose room-wide or stage-wide ground loot through this
  panel.
- [x] **DEC-168 (P0):** Pick up loot by dragging its entry from the left-side list into a valid
  location in the spatial run-inventory grid. Do not auto-place a clicked loot item.
- [x] **DEC-169 (P0):** While run inventory is open, keep the player stationary and disable their
  attacks and abilities. Enemies, hazards, projectiles, damage, and all other stage simulation keep
  running, so the player remains vulnerable.
- [x] **DEC-170 (P0):** On touchscreens, tapping a loot-list entry performs the desktop hover role
  and reveals that item's details; the player can then drag it into the spatial grid.
- [x] **DEC-171 (P1):** Let the player choose how the left-side loot list is sorted. Use newest-first
  as the default order.
- [x] **DEC-172 (P0):** Define pickup range as a data-driven circular radius centered on the player.
  Only ground items whose positions fall inside that circle are eligible for the loot list.
- [x] **DEC-173 (P0):** Remove a ground item from the visible loot list immediately when it leaves
  the player's pickup radius or otherwise stops being available.
- [x] **DEC-174 (P0):** Assign every item a stable, sortable creation/spawn ID when it is first
  created. Implement default `Newest` ordering by sorting those IDs newest-first; later drops and
  pickups do not rewrite the ID.
- [x] **DEC-175 (P1):** Provide player-selectable loot-list sorting by `Newest`, `Rarity`, `Item
  Type`, `Name`, and `Inventory Size`, with `Newest` selected by default.
- [x] **DEC-176 (P0):** Taking damage while run inventory is open does not close it. The interface
  remains open until the player closes it or another explicit state transition requires closure.
- [x] **DEC-177 (P0):** Solid collision walls block pickup even when an item's position lies inside
  the circular pickup radius. Require an unobstructed pickup path rather than reaching through walls.
- [x] **DEC-178 (P1):** Keep the exact pickup-radius distance as validated balance data instead of
  hard-coding a final design value now.
- [x] **DEC-179 (P1):** Let the player reverse every supported loot-list sort between ascending and
  descending order.
- [x] **DEC-180 (P1):** Define `Inventory Size` sorting by the number of grid cells the item's
  footprint actually occupies, not by its bounding-box width or height.
- [x] **DEC-181 (P1):** When `Rarity` sorting is selected, default to highest-first using each rarity
  entry's registry sort rank. The initial order is Legendary, Epic, Rare, Uncommon, then Common.
- [x] **DEC-182 (P1):** Use `Core`, `Weapon`, `Part`, `Material`, then `Blueprint` as the forward `Item
  Type` sort order; reversing the sort reverses that category order.
- [x] **DEC-183 (P0):** Give every item a rarity, including Cores, weapons, Parts, materials, physical
  blueprints, and the default Neutral starter Core. Begin with five ordered rarities: Common,
  Uncommon, Rare, Epic, and Legendary. Store them in an extensible data-driven registry so more
  rarity types can be added later without changing inventory code. Core quality labels—weak, normal,
  and exceptional—are a separate system and are not rarity names.
- [x] **DEC-184 (P1):** When two loot entries have the same value for the player's selected primary
  sort, break the tie deterministically by stable creation/spawn ID in descending, newest-first order.
- [x] **DEC-185 (P1):** Persist the player's selected loot-list sort key and ascending/descending
  direction after closing the inventory and across application restarts.
- [x] **DEC-186 (P1):** Do not draw a pickup-radius circle or other range outline around the player.
  The left-side loot list itself communicates which items are currently reachable.
- [x] **DEC-187 (P0):** Receiving damage causes the world camera to shake throughout a stage,
  including while run inventory is open. Keep the inventory interface anchored and stable rather
  than shaking it, and do not add a separate inventory-only damage warning or overlay.
- [x] **DEC-188 (P1):** Render run inventory with partial transparency so the ongoing world and its
  damage shake remain visible behind it. Keep the exact opacity as UI-tuning data and preserve the
  reduced/off shake accessibility control.
- [x] **DEC-189 (P0):** Assign the zero-cost Neutral starter Core `Common` rarity.
- [x] **DEC-190 (P0):** Rarity describes an item's economic value and drop frequency only. It does
  not apply a stat multiplier, roll, affix, ability change, or any other direct combat/progression
  modification. Here, value means selling price. The Neutral starter remains a deliberate zero-price
  exception despite being Common.
- [x] **DEC-191 (P0):** Fix rarity on the item model rather than rolling it per physical copy. Every
  copy of the same Core, weapon, Part, or material model therefore has the same rarity.
- [x] **DEC-192 (P1):** Scale damage camera-shake strength with the severity of the damage received,
  using clamped balance data so strong hits feel stronger without producing unbounded shake. Apply
  the player's reduced/off screen-shake setting after calculating that base strength.
- [x] **DEC-193 (P1):** Use the initial rarity colors Common gray, Uncommon green, Rare blue, Epic
  purple, and Legendary gold. Keep exact color values in theme data so readability can be tuned.
- [x] **DEC-194 (P0):** Give each material kind one stable material model and one fixed rarity; do
  not create alternate-rarity copies of that same material. Stacking therefore requires the exact
  material model, and crafting recipes do not substitute a differently named or modeled material.
- [x] **DEC-195 (P0):** Allow materials themselves to be crafted through authored recipes. A recipe
  that consumes or produces a material references its exact material model.
- [x] **DEC-196 (P1):** Calculate base damage-shake strength from damage received as a percentage of
  the player's maximum health rather than from raw damage points.
- [x] **DEC-197 (P1):** Accumulate shake contributions from rapid successive hits. Clamp the combined
  result to the configured maximum, decay it over time, and then apply the player's shake setting.
- [x] **DEC-198 (P0):** Use one persistent universal currency for selling and other authored company
  costs. Keep its final in-world name open until the setting/economy presentation is written.
- [x] **DEC-199 (P0):** Permit selling only through the player's company/base interface, never while
  inside a stage. Give each sellable item model an authored base selling price informed by rarity;
  the Neutral starter remains fixed at zero.
- [x] **DEC-200 (P0):** Reserve physical blueprints for unlocking equipment manufacturing, such as
  authored Core, Part, and weapon models. A found blueprint occupies run-inventory space, must be
  carried through successful extraction, and must then be consumed at the company/base to unlock its
  manufacturing option. Material forging never requires a blueprint.
- [x] **DEC-201 (P0):** Allow exact lower-rarity material models to be forged into a distinct,
  higher-rarity material model through an authored recipe, such as nine Common inputs producing one
  Rare output through two compounded 3:1 tier steps. These are different material models, not
  alternate rarities of the same material.
- [x] **DEC-202 (P0):** Provide no dismantling option for equipment or materials. Selling, consuming
  items in research/crafting, and explicit discarding are the supported ways to remove possessions.
- [x] **DEC-203 (P0):** Complete material crafting immediately when the player confirms an available
  valid conversion and owns its exact inputs. Do not use production timers or return ingredients from
  its output.
- [x] **DEC-204 (P1):** Use one global base rarity/drop-frequency table, then apply authored stage-
  difficulty modifiers when generating loot. Keep exact probabilities and modifier curves as balance
  data rather than hard-coded rules.
- [x] **DEC-205 (P0):** A duplicate of an already-unlocked physical equipment blueprint remains an
  ordinary loot item: it occupies run-inventory space, must be extracted to persist, and may be sold
  for universal currency at the company/base.
- [x] **DEC-206 (P0):** Use universal currency as the general progression currency for company
  upgrades and company/base purchases of equipment, materials, and blueprints. Currency remains safe
  in persistent company state and is never deployed into or lost in a stage.
- [x] **DEC-207 (P0):** Standardize material-tier forging at three units of one rarity tier into one
  unit of the next tier. Allow the interface to skip tiers by automatically compounding the same
  ratio—for example, nine Common units become three Uncommon units and then one Rare unit in one
  confirmed operation.
- [x] **DEC-208 (P1):** Require an explicit sale warning and confirmation for every Epic, Legendary,
  or favorited item. Favorited items trigger the warning regardless of rarity; Common, Uncommon, and
  Rare items need no extra rarity-based warning unless they are favorited.
- [x] **DEC-209 (P1):** Place physical `Blueprint` items after `Material` in the forward `Item Type`
  sorting order.
- [x] **DEC-210 (P0):** Give every physical equipment blueprint a fixed 1-by-1 spatial-inventory
  footprint.
- [x] **DEC-211 (P1):** Allow the company shop to sell a physical equipment blueprint even when the
  player has already unlocked it. The purchased duplicate remains a normal sellable item.
- [x] **DEC-212 (P0):** Make all standard material-tier conversions available automatically without
  acquiring or consuming blueprints, including compounded tier skipping.
- [x] **DEC-213 (P0):** Permit an authored material conversion to require exact equipment models as
  additional ingredients. The player must manufacture or otherwise obtain those items, and confirming
  the conversion consumes them along with the required materials.
- [x] **DEC-214 (P1):** Give company shops a fixed authored catalog with limited stock. Defer the
  exact stock-restoration rule until the shop UI and economy can be evaluated in play.
- [x] **DEC-215 (P1):** When selling a material stack, let the player choose the exact quantity to
  sell rather than requiring sale of the entire stack.

## Current repository snapshot

### Already present

- [x] **NOW-01:** Gradle/libGDX desktop project with `core` and `lwjgl3` modules.
- [x] **NOW-02:** WASD movement, mouse aiming, camera follow, wall collision, and debug hitboxes.
- [x] **NOW-03:** Deterministic 24-by-18 tile arena with shared client/server collision layout.
- [x] **NOW-04:** Knight and Rogue character definitions loaded from JSON.
- [x] **NOW-05:** Quick and Heavy sword data, attack areas, timing, cooldown data, and rendering.
- [x] **NOW-06:** Local play, LAN host/join, LAN discovery, direct-IP configuration, and a headless
  UDP server prototype.
- [x] **NOW-07:** Server-authoritative movement and attacks with player/world snapshots.
- [x] **NOW-08:** A two-client networking smoke-test program.
- [x] **NOW-09:** Basic responsive title and offline-play screens route local players through
  `Play -> New Game -> GameWorld`, support mouse/touch plus keyboard navigation, provide Back and
  Quit actions, and visibly disable unfinished Settings, Credits, and Continue options.
- [x] **NOW-10:** Responsibility-based, lowercase Java packages separate application flow, config,
  content definitions, gameplay, presentation, networking, tests, and the desktop launcher. Assets
  are grouped by data and texture purpose, while architecture/networking/reference documents live
  under `docs` instead of production source.

### Important gaps observed in the current build

- [ ] **GAP-01 (P0):** The player renderer supports only left/right directions.
- [ ] **GAP-02 (P0):** Movement uses only two frames per direction.
- [ ] **GAP-03 (P0):** There is no enemy damage, health, death, stage objective, reward, or completed
  PvE stage loop.
- [ ] **GAP-04 (P0):** The basic title/offline-play shell exists, but there is no boot screen,
  stage/loadout preparation, working Continue/save flow, pause menu, settings screen, credits screen,
  or results screen. The lobby browser remains post-demo work.
- [ ] **GAP-05 (P2):** Post-demo multiplayer is only prototype-grade LAN networking, not production
  cross-platform Internet networking. This does not block the offline demo.
- [ ] **GAP-06 (P1):** There is no sound, music, combat effect art, or final weapon artwork.
- [ ] **GAP-07 (P1):** Automated gameplay, data, protocol, and rendering tests are limited.
- [x] **GAP-08 (resolved):** Replace the generated starter README with the game overview, current
  prototype scope, module description, documentation links, and build commands.
- [ ] **GAP-09 (P1):** Multiple skin entries currently reuse the same player texture with a tint;
  Knight and Rogue do not yet have distinct production-ready character art.
- [ ] **GAP-10 (P0):** The repository currently contains only the desktop launcher; Android and iOS
  launchers, controls, lifecycle handling, packaging, and platform tests do not exist yet.
- [ ] **GAP-11 (P0):** Core, Part, Resonance, loadout, progression, and stage systems are not yet
  represented in game data or code.

---

# Milestone A - Product definition and technical baseline

**Exit gate:** The intended offline public-demo experience, platform, local player count, visual
direction, and explicit lack of networking are written down. A clean checkout can be built and
tested by another person.

## Product definition

- [x] **PLAN-01 (P0):** Complete decisions DEC-01 through DEC-08.
- [ ] **PLAN-02 (P0):** Write a five-minute core gameplay loop from spawn to match result.
- [x] **PLAN-03 (P0):** Win by clearing the final objective and completing explicit extraction; lose
  the solo demo attempt when the player is defeated or abandons the stage before extraction.
- [ ] **PLAN-04 (P0):** Define the minimum number of maps, characters, weapons, and modes for the demo.
- [ ] **PLAN-05 (P1):** Create a short art-direction page with palette, sprite scale, outline,
  lighting, UI, and animation examples.
- [ ] **PLAN-06 (P1):** Create a short audio-direction page with music mood and sound priorities.
- [x] **PLAN-07 (P0):** Keep LAN/Internet multiplayer, cross-play, accounts/backends, PvP,
  same-device co-op, boss hunts, settlement defense, rescue/escort missions, challenge contracts,
  and large-scale raids outside the free public demo.
- [ ] **PLAN-08 (P0):** Define measurable offline-demo success criteria such as crash rate, stage
  completion rate, frame time, loading time, input responsiveness, and save reliability.
- [x] **PLAN-09 (P0):** Write the story premise, setting, central conflict, player role, major
  factions, and reason that Cores and Parts exist in the world. See `STORY_BIBLE.md`.
- [ ] **PLAN-10 (P0):** Outline the demo's opening, stage progression, major reveal, boss, and
  ending/continuation hook.

## Cross-platform foundation

- [ ] **PLAT-01 (P0):** Confirm the desktop targets: Windows, macOS, and/or Linux.
- [ ] **PLAT-02 (P0):** Add and run a libGDX Android launcher/module.
- [ ] **PLAT-03 (P0):** Add and run a libGDX iOS launcher/module using the selected supported
  backend and toolchain.
- [ ] **PLAT-04 (P0):** Set up access to macOS, Xcode, signing, and Apple developer tooling needed
  to build and publish iOS releases.
- [ ] **PLAT-05 (P0):** Keep simulation, game rules, data, and protocol code in the shared `core`
  module without desktop-only APIs.
- [ ] **PLAT-06 (P0):** Replace desktop-only assumptions in input, file paths, networking,
  lifecycle, logging, and window/display handling with platform-safe interfaces.
- [ ] **PLAT-07 (P0):** Design touch controls for movement, aim, attack, abilities, interaction,
  weapon/Unarmed selection, pause, and menus. Implement the combat weapon-slot icon as the cycling
  control defined by DEC-147.
- [ ] **PLAT-08 (P0):** Add configurable touch-control position, size, opacity, sensitivity, and
  left/right-handed layout.
- [ ] **PLAT-09 (P0):** Scale gameplay UI for phone, tablet, desktop, aspect-ratio, notch, and safe
  area differences.
- [ ] **PLAT-10 (P2):** For the post-demo networked release, use one network protocol version and
  shared gameplay-data hash across every platform.
- [ ] **PLAT-11 (P2):** After the offline demo, add cross-play tests containing at least one desktop,
  one Android, and one iOS client in the same room and stage.
- [ ] **PLAT-12 (P0):** Handle Android/iOS pause, background, resume, rotation policy, interruption,
  network change, and operating-system termination safely.
- [ ] **PLAT-13 (P0):** Define mobile performance, memory, battery, temperature, and download-size
  budgets.
- [ ] **PLAT-14 (P2):** For the post-demo networked release, add platform-specific store
  authentication only behind a shared player identity that does not divide cross-play.
- [ ] **PLAT-15 (P1):** Automate signed development/test builds for all selected desktop systems,
  Android, and iOS.

## Build and project hygiene

- [ ] **BASE-01 (P0):** Document the required JDK version and verify it on a clean Windows machine.
- [ ] **BASE-02 (P0):** Make `gradlew.bat build` the single clean build command.
- [ ] **BASE-03 (P0):** Add standard `core/src/test` and `lwjgl3/src/test` test locations.
- [ ] **BASE-04 (P0):** Add a CI job that compiles, tests, and packages on every pull request.
- [ ] **BASE-05 (P1):** Add a formatting/static-analysis task and run it in CI.
- [ ] **BASE-06 (P1):** Add a version string and build number shown in the UI and logs.
- [ ] **BASE-07 (P1):** Separate development, test, and release configuration defaults.
- [ ] **BASE-08 (P1):** Add structured logging with useful startup, platform, save, and failure
  messages; add network diagnostics when multiplayer is active.
- [ ] **BASE-09 (P1):** Add a top-level crash handler that writes a user-shareable crash log.
- [ ] **BASE-10 (P1):** Update `README.md` with the actual game, controls, build steps, and status.
- [ ] **BASE-11 (P1):** Fix broken character encoding in existing Markdown documentation.
- [ ] **BASE-12 (P1):** Move manual smoke tests out of production source or wrap them in a proper
  automated test task.
- [ ] **BASE-13 (P1):** Add a third-party dependency and asset-license inventory.

## Runtime architecture

- [ ] **ARCH-01 (P0):** Introduce explicit game states/screens for boot, title, base/preparation,
  gameplay, pause, results/storage resolution, and error handling. Add a lobby state only with later
  multiplayer.
- [ ] **ARCH-02 (P0):** Make state transitions release/dispose resources reliably.
- [ ] **ARCH-03 (P0):** Replace per-player texture loading with a shared asset manager.
- [ ] **ARCH-04 (P0):** Define loading progress and a missing/corrupt-asset error path.
- [ ] **ARCH-05 (P1):** Split input reading from player simulation so local, remote, AI, replay,
  and controller input can use the same commands.
- [ ] **ARCH-06 (P1):** Separate match rules and combat resolution from rendering in `GameWorld`.
- [ ] **ARCH-07 (P1):** Use a bounded/fixed simulation step and clamp unusually large frame deltas.
- [ ] **ARCH-08 (P1):** Define stable IDs and schema migration rules for all JSON gameplay data.
- [ ] **ARCH-09 (P1):** Validate all referenced textures, characters, weapons, attacks, and skins at
  startup with actionable error messages.

---

# Milestone B - Eight-direction character assets and animation

**Exit gate:** Every demo character has production-approved eight-direction movement in the offline
build. Each direction has at least four movement frames, with no left/right-only fallback visible
during normal play. Later multiplayer reuses the same directional state and assets.

## Asset contract

The required directions are:

| Direction ID | Name | Center angle |
| --- | --- | ---: |
| `E` | East | 0 degrees |
| `NE` | North-east | 45 degrees |
| `N` | North | 90 degrees |
| `NW` | North-west | 135 degrees |
| `W` | West | 180 degrees |
| `SW` | South-west | 225 degrees |
| `S` | South | 270 degrees |
| `SE` | South-east | 315 degrees |

Minimum budget **per independently drawn skin, skeleton, character sheet, or animated Part layer**:

- 8 directions x 4 movement frames = **32 movement frames**.
- At least 1 idle frame x 8 directions = **8 idle frames**.
- Recommended: 2 idle frames x 8 directions = **16 idle frames**.
- Recommended minimum total with two idle frames = **48 frames per sheet**.
- At the current 16-by-16 source-frame size, a simple six-column by eight-row sheet would be
  96-by-128 pixels. A texture atlas with named regions is also acceptable.
- Mirroring east/west frames does **not** count as separately authored eight-direction art unless
  that shortcut is explicitly approved in DEC-12.

The current four skin definitions all point to the same texture. If Knight and Rogue receive one
unique base sheet each and color variants remain tint-only, the demo needs at least **64 newly
drawn movement frames** total. If all four listed skins become unique artwork, it needs at least
**128 movement frames** total.

## Direction and sheet design

- [ ] **ART-01 (P0):** Decide whether body facing follows aim, movement, or a documented hybrid.
  Recommended for the current mouse-aim design: direction follows aim while the walk/idle clip is
  selected from movement state.
- [ ] **ART-02 (P0):** Lock the exact clockwise angle boundaries used to map an angle to 8 sectors.
- [ ] **ART-03 (P0):** Lock source frame width, frame height, character origin, foot position, and
  weapon-hand anchor positions.
- [ ] **ART-04 (P0):** Choose sprite-sheet row/column order or named texture-atlas regions.
- [ ] **ART-05 (P0):** Write a filename convention for character, skin, direction, clip, and frame.
- [ ] **ART-06 (P0):** Decide whether the first walk frame may also be the minimum idle frame.
- [ ] **ART-07 (P1):** Define frame duration, loop order, and whether diagonal animation timing
  differs from cardinal animation timing.
- [ ] **ART-08 (P1):** Define per-direction body, hurtbox, hand, shadow, and weapon-layer anchors.
- [ ] **ART-09 (P1):** Create an artist-facing template showing safe bounds and anchor pixels.
- [ ] **ART-10 (P0):** Define the eight-direction layer order and attachment anchors for the base
  skeleton, torso, head, left/right arms, left/right legs, held weapon, effects, and shadow.
- [ ] **ART-11 (P1):** Define reusable Core-family presentation with data-driven tier add-ons or
  overlays. For ordinary families, make weak look least complete/most contaminated, normal cleaner
  with more finished add-ons, and exceptional cleanest/most complete. Allow special authored
  overrides while reusing each family's base visual and animation assets across quality variants.
- [ ] **ART-12 (P1):** Render the equipped Core's tier treatment on the assembled robot and create a
  matching item/UI portrait reusable by inventory, loadout, comparison, and field-loot displays.

## Animation code and data

- [ ] **ANIM-01 (P0):** Add a `Direction8` type containing all eight directions.
- [ ] **ANIM-02 (P0):** Add a tested angle/vector-to-direction conversion.
- [ ] **ANIM-03 (P0):** Replace hard-coded left/right `TextureRegion` fields with animation clips.
- [ ] **ANIM-04 (P0):** Extend skin data to describe direction rows/regions and clip frame counts.
- [ ] **ANIM-05 (P0):** Validate that every required direction contains at least four walk frames.
- [ ] **ANIM-06 (P0):** Load all eight idle and walk clips through the shared asset manager.
- [ ] **ANIM-07 (P0):** Select the walk clip when movement magnitude is above the dead zone.
- [ ] **ANIM-08 (P0):** Select the idle clip when movement is below the dead zone.
- [ ] **ANIM-09 (P0):** Preserve the last valid direction when input/aim is inside its dead zone.
- [ ] **ANIM-10 (P0):** Reset or transition animation frames cleanly when direction/clip changes.
- [ ] **ANIM-11 (P0):** Make animation frame advancement handle long frame times correctly.
- [ ] **ANIM-12 (P0):** Keep diagonal movement speed normalized to cardinal movement speed.
- [ ] **ANIM-13 (P2):** For later multiplayer, ensure remote players choose the same direction from
  synchronized state.
- [ ] **ANIM-14 (P0):** Update weapon position and front/behind layering for all eight directions.
- [ ] **ANIM-15 (P1):** Add optional per-direction hand/grip anchors to skin data.
- [ ] **ANIM-16 (P1):** Add a developer animation viewer that cycles every clip and direction.
- [ ] **ANIM-17 (P1):** Add automated tests for all angle boundaries, including 0/360 wraparound.
- [ ] **ANIM-18 (P1):** Add data-validation tests for missing, duplicated, or undersized frames.
- [ ] **ANIM-19 (P0):** Compose the animated character from the base skeleton and equipped Part
  layers, omitting a Part layer when its slot is empty while keeping movement functional.

## Required character artwork

- [ ] **CHAR-01 (P0):** Draw/approve Knight idle art for all eight directions.
- [ ] **CHAR-02 (P0):** Draw/approve at least four Knight movement frames for each direction.
- [ ] **CHAR-03 (P0):** Draw/approve Rogue idle art for all eight directions.
- [ ] **CHAR-04 (P0):** Draw/approve at least four Rogue movement frames for each direction.
- [ ] **CHAR-05 (P0):** Verify silhouettes remain readable against every floor/wall variant.
- [ ] **CHAR-06 (P0):** Verify feet do not slide and the visual origin is stable across frames.
- [ ] **CHAR-07 (P0):** Verify the weapon hand and weapon do not visibly detach in any direction.
- [ ] **CHAR-08 (P0):** Verify character art fits collision and draw bounds without clipping.
- [ ] **CHAR-09 (P1):** Decide whether `knight_gold` is a tint or unique sheet; supply approved art.
- [ ] **CHAR-10 (P1):** Decide whether `rogue_shadow` is a tint or unique sheet; supply approved art.
- [ ] **CHAR-11 (P1):** Create a preview/contact sheet for each final character skin.
- [ ] **CHAR-12 (P1):** Record source-file ownership and license for every shipped sheet.
- [ ] **CHAR-13 (P0):** Draw/approve the visible base skeleton for all eight directions with at
  least four movement frames per direction so a character with empty slots remains readable.
- [ ] **CHAR-14 (P0):** Draw/approve every demo Part as a correctly aligned modular layer for all
  eight directions and at least four movement frames per direction.

## Animation acceptance checks

- [ ] **ANIM-QA-01 (P0):** Walk continuously in all eight directions without a missing frame.
- [ ] **ANIM-QA-02 (P0):** Rotate aim through 360 degrees while idle and while moving.
- [ ] **ANIM-QA-03 (P2):** For later multiplayer, observe every direction and frame on a remote
  client.
- [ ] **ANIM-QA-04 (P0):** Test direction boundaries immediately below, on, and above each boundary.
- [ ] **ANIM-QA-05 (P0):** Test at 30, 60, 120, and 144 FPS for consistent animation speed.
- [ ] **ANIM-QA-06 (P1):** Test every skin, weapon hand, and sword combination.

---

# Milestone C - Complete playable match loop

**Exit gate:** Players can prepare a loadout, start a stage, understand the objective, fight, extract
or fail, resolve storage/base consequences, and choose another prepared stage or quit without using
command-line configuration.

## Core, Parts, Resonance, and loadouts

- [ ] **CORE-01 (P0):** Define a stable, data-driven `Core` model with ID, name, description,
  ability, targeting rules, costs, cooldown, power values, tags, effects, unlock requirements, one
  archetype for ordinary Cores, and normalized Machinery/Organic/Alien/Neutral composition
  percentages. Permit a missing archetype only for the registered default Neutral starter model.
- [ ] **CORE-02 (P0):** Define a stable, data-driven `Part` model with ID, slot, granted ability,
  fixed per-stat levels/values and modifiers, tags, effects, compatibility, visuals, inventory-cell
  footprint, Resonance data, and unlock requirements. Give torso Parts an additional inventory-grid
  mask definition. Every item instance referencing the same model ID must resolve the same gameplay
  data.
- [ ] **CORE-03 (P0):** Define a `Loadout` containing exactly one permitted Core, one optional weapon
  slot, and six independently optional Part slots: head, torso, left arm, right arm, left leg, and
  right leg. Accept a Core-only loadout as valid.
- [ ] **CORE-04 (P0):** Define the mathematical and behavioral rules for Core/Part Resonance.
  Include torso-specific Resonance effects that unlock the additional inventory cells defined by
  that torso/Core relationship.
- [ ] **CORE-05 (P0):** Show every active Resonance and its exact effect before a player confirms a
  loadout.
- [ ] **CORE-06 (P0):** Clearly show the opportunity cost: which independent Part ability is lost,
  replaced, or weakened when that Part is used for Resonance.
- [ ] **CORE-07 (P0):** Prevent invalid, incompatible, locked, or duplicate loadout combinations.
- [ ] **CORE-08 (P0):** Add the selected Core, Parts, abilities, and Resonances to authoritative
  player state using stable IDs rather than display names.
- [ ] **CORE-09 (P0):** Validate loadouts in the authoritative game simulation before the player can
  confirm preparation or enter a stage; run the same validation on the server when multiplayer is
  added.
- [ ] **CORE-10 (P2):** For later multiplayer, replicate enough loadout state for every platform to
  render and simulate the same player abilities.
- [ ] **CORE-11 (P0):** Implement a shared ability framework supporting cooldowns, costs, casting,
  targeting, interruption, effects, and authoritative outcomes in both offline simulation and the
  later multiplayer server.
- [ ] **CORE-12 (P0):** Implement at least 10 mechanically distinct, content-complete Core models for
  the free public demo. Give each ordinary model its authored type composition, single subtype,
  stats, passive behavior, main ability, presentation, and balance data; implement the default
  Neutral starter as the explicit no-subtype, no-ability exception.
- [ ] **CORE-13 (P0):** Implement the minimum Parts for every selected slot in DEC-13.
- [ ] **CORE-14 (P0):** Implement at least one resonant and one non-resonant viable build for every
  launch Core.
- [ ] **CORE-15 (P0):** Lock the starting loadout at the stage boundary, allow Core and weapon
  changes only through their authorized rooms, and allow equipped Parts to be replaced or removed
  using Parts in temporary run inventory only after the current room is cleared, per DEC-78.
- [ ] **CORE-16 (P0):** Save discovered Core models, owned physical Core instances, Core manufacturing
  blueprints, unlocked Parts, and the player's last valid loadout as separate persistent state.
- [ ] **CORE-41 (P0):** Implement data-defined Core acquisition sources for in-stage discoveries,
  quests, and story/world events, including `repeatable`, `one-time`, and optional authored-recovery
  policies. Track one-time spawn consumption per campaign/save, prevent unintended duplicate reward
  claims, preserve stable model IDs, and route every in-stage Core reward through physical possession
  and extraction before granting persistent ownership. Allow safe company/base rewards to commit
  immediately and validate that campaign-critical Cores are not permanently missable. Grant exactly
  one physical instance per successful extraction without implicitly granting its blueprint.
- [ ] **CORE-42 (P0):** Keep Core discovery, physical instance ownership, and manufacturing-blueprint
  knowledge separate in data, saves, UI, rewards, and validation. Test extracting, storing, deploying,
  losing, reacquiring, and submitting a Core without accidentally unlocking or duplicating its
  blueprint. Require and consume five copies with the same stable model ID for non-starter research.
- [ ] **CORE-43 (P0):** Enforce Core-model determinism in content validation, loot, manufacturing,
  saves, and later networking. Instances may store identity, ownership, source, and non-mechanical
  provenance, but cannot override their model's type percentages, archetype, stats, passive, ability,
  costs, tags, compatibility, or Resonance data. Reject random per-instance Core rolls and require a
  separate stable model ID for every mechanical variation.
- [ ] **CORE-44 (P0):** Add Core-family and quality-tier data for weak, normal, and exceptional model
  variants. Enforce exactly `50%`, `25%`, and `0%` Neutral respectively, keep all four type shares
  normalized to 100%, and proportionally scale the family's base Machinery/Organic/Alien ratio into
  the share remaining after Neutral. Share all other mechanical data across a family's tiers so type
  composition is their only mechanical difference. Reference one shared family visual plus optional
  per-tier add-ons/overlays, ordinary cleanliness/completeness progression, and an explicit special-
  design override where authored. Treat the 100% Neutral starter as outside those tiers and ensure
  research progress never crosses variant model IDs.
- [ ] **CORE-17 (P0):** Recover safely when saved loadout content was removed, renamed, or changed.
- [ ] **CORE-18 (P1):** Add comparison details for damage, cooldown, range, duration, and Resonance
  changes without requiring external documentation.
- [ ] **CORE-19 (P1):** Add automated tests for compatibility, Resonance stacking, stat limits,
  invalid builds, and save migration. Add protocol-encoding coverage with later multiplayer.
- [ ] **CORE-20 (P1):** Add telemetry or structured playtest logging for Core/Part pick rates and
  stage outcomes, subject to privacy choices.
- [ ] **CORE-21 (P0):** Give every manufactured Core and Part a stable manufacturer ID and technical
  lineage in gameplay data.
- [ ] **CORE-22 (P0):** Make same-manufacturer Resonance rules explicit and data-driven.
- [ ] **CORE-23 (P0):** Give every cross-manufacturer Resonance a data-driven relationship and a
  story explanation such as collaboration, licensing, copied technology, or infection.
- [ ] **CORE-24 (P0):** Treat Core and main ability as one atomic loadout choice so equipping a new
  Core immediately removes the previous Core ability and grants the new one. Equipping the default
  Neutral starter removes the previous ability and grants no replacement Core ability.
- [ ] **CORE-25 (P0):** Implement the computer/software process that transfers the active player
  consciousness into any selected player-owned Core without changing player identity. Permit this
  freely during base preparation; during a stage, permit it only through an authorized Core room or
  an explicitly defined consciousness-recovery flow.
- [ ] **CORE-26 (P0):** Implement the fictional continuous cloud memory/state synchronization through
  the demo's durable local save, with a stable owner/version for recovery. Keep the active
  consciousness bound to its current Core until a USB transfer or another explicit migration occurs;
  replace or extend the persistence transport only when later online accounts are implemented.
- [ ] **CORE-27 (P0):** Activate a new robot body from the saved consciousness and selected Core in
  the authoritative game state. Replicate that identity/body change to every client only in the
  later multiplayer implementation.
- [ ] **CORE-28 (P0):** When a USB-carried consciousness is permanently lost at run failure, retire
  that character identity and let the human player create/select a new character, then manufacture
  its loadout from persistent unlocks/blueprints before another stage attempt.
- [ ] **CORE-29 (P0):** After ordinary full-party defeat, restore each synchronized consciousness at
  the company/base without restoring its lost deployed Core/body/equipment. Exclude a consciousness
  currently marked as in transit inside a lost USB payload.
- [ ] **CORE-30 (P0):** Keep baseline movement available through the underlying skeleton/frame when
  any or all Part slots are empty, and ensure an empty slot grants no Part ability, power, stat,
  tag, effect, or Resonance contribution. Do not disable any ability supplied by the equipped Core
  when Parts are missing; the default Neutral starter intentionally supplies none. Keep the weak
  skeleton attack available independently of all Part slots.
- [ ] **CORE-31 (P0):** Make in-stage Part swaps atomic: validate slot compatibility and temporary
  run ownership, require the current room to be cleared, unequip the old Part into run inventory,
  equip the selected looted Part, and update abilities, stats, visuals, Resonance, and torso-provided
  inventory space exactly once. Drop every item invalidated by the resulting active-cell mask into
  the cleared room.
- [ ] **CORE-32 (P0):** Build one deterministic stat-aggregation pipeline combining skeleton base
  values, equipped Part modifiers, Core effects, Resonance effects, and temporary status effects.
  Recalculate health, speed, armor, energy, inventory cells, and other registered stats atomically
  whenever the build changes. Clamp current health/energy to a lowered maximum and never restore
  discarded excess when the previous build returns. When a maximum increases, leave the current
  numeric value unchanged.
- [ ] **CORE-33 (P1):** Show before/after ability and stat changes when inspecting or equipping a
  Part, including health, speed, armor, energy, inventory-mask changes, and active Resonance effects.
- [ ] **CORE-34 (P0):** Define validation bounds and stacking rules for every registered stat so
  positive and negative Part modifiers cannot produce invalid maximum health, energy, movement,
  armor, inventory masks, or other impossible character state.
- [ ] **CORE-35 (P0):** Enforce Part-model determinism in content validation, saves, manufacturing,
  loot generation, and networking. Item instances may store ownership, source, and condition, but
  may not override their model's stats, ability, footprint, compatibility, or Resonance data. Treat
  every mechanically different weak, normal, or exceptional version as a separate stable model ID.
- [ ] **CORE-36 (P0):** Create stable registries for the three primary Core types, the special
  Neutral type, and five archetypes. Reject unknown types/archetypes, negative percentages, and Core
  compositions whose percentages do not total exactly 100% after canonical normalization. Require
  the starter Core to be 100% Neutral, but do not require ordinary content to contain any pure
  primary-type composition or complete coverage of the 15 possible primary-type/archetype pairings.
  Require exactly one archetype enum per ordinary Core model and reject multiple or percentage-
  blended archetypes. Permit no archetype only for the registered default Neutral starter model.
  Keep model-specific stats and behaviors in each Core definition rather than deriving a mandatory
  shared bonus from the archetype enum.
- [ ] **CORE-37 (P0):** Implement deterministic Core-type effectiveness from a data-defined matchup
  matrix. Compute the double-weighted sum across every attacker and defender type share using `1.5x`
  advantage, `1.0x` same-type, and `0.5x` disadvantage. Treat a pure combatant as a 100%-one-type
  composition. Expose the result to the authoritative damage pipeline as its typed base-damage
  multiplier in both player-to-enemy and enemy-to-player directions. Add Neutral's special matrix
  entries: `0.75x` outgoing to primary types, `1.25x` incoming from primary types, and `1.0x`
  Neutral-versus-Neutral.
- [ ] **CORE-38 (P1):** Show a Core's type-percentage composition, archetype, matchup direction, and
  calculated effectiveness in loadout, comparison, encounter-preparation, and applicable combat UI.
- [ ] **CORE-39 (P0):** Test pure and hybrid Core calculations, validation of both Core and enemy
  percentages, all nine pure-type matchups, hybrid-versus-hybrid pair sums, deterministic rounding,
  save/load, and later network replication. Include `70% Organic/30% Machinery versus Alien =
  1.20x`, `70% Organic/30% Machinery versus 60% Alien/40% Machinery = 0.98x`, and the reversed
  enemy-to-player matchup `= 1.02x` as fixtures. Test Neutral against every primary type in both
  directions, Neutral versus Neutral, Neutral against primary-type hybrids, and player Cores that
  mix Neutral with one or more primary types.
- [ ] **CORE-40 (P0):** Require every weapon and damaging ability to expose validated base damage.
  Resolve the attacker's and defender's current type compositions at hit time, then multiply that
  source base damage by CORE-37's effectiveness exactly once. Player attacks use the equipped Core
  against the enemy; enemy attacks use the enemy against the player's equipped Core. Enforce that
  the type multiplier cannot enter any non-damage calculation.

## Spatial run inventory

- [ ] **INV-01 (P0):** Represent the temporary run inventory as a fixed 15-by-15 cell canvas plus an
  active-cell mask. Use an empty mask when the torso slot is empty, the predefined 3-by-3 mask for
  the starter torso, and the equipped torso plus active Resonance for every other mask.
- [ ] **INV-02 (P0):** Represent each carried Part, weapon, unequipped Core, crafting-material item,
  and physical blueprint with a data-driven footprint made from one or more cells, supporting both
  rectangles such as a 1-by-2 arm and non-rectangular shapes such as an L-shaped leg. Require every
  physical equipment blueprint to use a 1-by-1 footprint.
- [ ] **INV-03 (P0):** Validate placement against grid bounds, disabled cells, and occupied cells;
  never allow two carried items to overlap. Apply rotation and horizontal/vertical flips before
  validating the transformed footprint.
- [ ] **INV-04 (P0):** Add an inventory interface for selecting, dragging, previewing, placing, and
  reorganizing item footprints with rotation and flip controls plus clear valid/invalid placement
  feedback. Require ground loot to be dragged from the in-range left-side list into a valid grid
  position rather than auto-placing it.
- [ ] **INV-05 (P0):** When collecting loot, place it only if its complete footprint fits; otherwise
  leave it safely in the room so the player can rearrange or choose another item.
- [ ] **INV-06 (P0):** When a cleared-room Part swap returns the old equipped Part to inventory,
  preserve every valid item placement and drop each whole item that does not fit the resulting mask
  into that cleared room when the player confirms.
- [ ] **INV-07 (P0):** Recalculate available cells atomically when a torso is equipped, removed, or
  replaced or when a Core change alters torso Resonance. Preview which items will drop, then preserve
  valid placements and place every invalidated whole item safely on the cleared-room map.
- [ ] **INV-08 (P0):** Save and restore each item's stable sortable creation/spawn ID, grid origin,
  orientation, and horizontal/vertical flip state as part of temporary stage state. Never rewrite
  the ID because of a drop, pickup, room transition, or save/resume.
- [ ] **INV-09 (P1):** Add automated tests for rectangular and L-shaped placement, borders,
  overlap, every rotation/flip combination, full grids, torso-capacity changes, collection,
  automatic mask-overflow drops, swapping, material stack boundaries, splitting/merging, and save
  restoration. Cover circular range and solid-wall filtering, drag-to-grid pickup, stable-ID sorting,
  every selected sort key in both directions, occupied-cell-count size sorting, newest-first stable-
  ID tie-breaking, persisted sort preferences, five initial and additional registry rarities, fixed
  model rarity, exact-model material stacking, and inventory-open damage without player movement,
  combat actions, or automatic closure.
- [ ] **INV-10 (P0):** Define and validate inventory footprints for every demo Part, weapon, Core,
  crafting-material item, and physical equipment blueprint; reject missing or disconnected footprint
  data at content-load time and reject any blueprint footprint other than 1-by-1.
- [ ] **INV-11 (P0):** Implement crafting-material stacks with a hard maximum of 99 identical units
  per footprint, including partial collection, merge, split, drop, loss, extraction, and save/restore
  behavior. Require every overflow stack to occupy its own valid grid placement. Preserve one
  ownership state per stack and permit merging only when exact material model and persistent-owner ID
  both match; treat unbound stage loot as a separate ownership state until extraction commits it.
  Each material model supplies one fixed rarity, so instances never roll alternate rarities.
- [ ] **INV-12 (P0):** Make torso grid definitions data-driven, including rectangular dimensions,
  active-cell masks within the 15-by-15 canvas, and torso/Core-relationship-specific Resonance cells.
  Validate every mask, define the starter torso's 3-by-3 region, and use an empty mask when no torso
  is equipped.
- [ ] **INV-13 (P0):** Resolve active-mask reductions deterministically and atomically: keep items
  whose complete transformed footprints remain on active cells, remove every invalidated whole item
  from inventory, and spawn each removed item safely near the player in the current cleared room.
- [ ] **INV-14 (P0):** Give every ground-loot item a stable instance ID, stage ID, room ID, position,
  creation/spawn order, orientation, quantity, and state. Persist it for the entire attempt across
  backtracking, room unload/reload, and stage save/resume, then remove it only during explicit stage
  teardown.
- [ ] **INV-15 (P1):** Stress-test persistent ground loot with repeated drops, room transitions,
  save/resume, collection, multiplayer ownership changes, and stage teardown without duplication,
  disappearance, or unbounded rendering/simulation work in inactive rooms.
- [ ] **INV-16 (P0):** Add one required fixed rarity ID to every item model and resolve it through an
  ordered, extensible registry containing display name, theme color, sort rank, selling-price data,
  and drop-frequency data. Seed the registry with Common gray, Uncommon green, Rare blue, Epic purple,
  and Legendary gold; assign Common to the Neutral starter; and validate every Core, weapon, Part,
  material, and physical-equipment-blueprint reference at content-load time. Item instances inherit their
  model's rarity without a roll. Rarity must never alter stats or abilities; there is no dismantling
  system.
- [ ] **INV-17 (P0):** Make standard material-tier conversions available without blueprints. Validate
  every input/output model and quantity, prevent substitution by another material kind, consume exact
  inputs atomically, and preserve the crafted model's fixed rarity through storage, deployment,
  extraction, and stacking. Enforce the universal adjacent-tier ratio of three inputs to one output
  and support one confirmed tier-skipping operation by atomically compounding that ratio, such as nine
  Common units to one Rare unit. Allow an authored conversion to consume exact equipment models as
  additional ingredients, with no automatic manufacturing or substitution.

## Base, research, and roguelite progression

- [ ] **META-01 (P0):** Create a persistent personal base/profile for each player, independent of
  temporary stage/run state.
- [ ] **META-02 (P0):** Represent recovered equipment and crafting-material stacks as instances
  referencing a stable model ID, with manufacturer where applicable, condition, source, current
  holder, and immutable persistent-owner ID. Keep holder and owner distinct, and keep instance
  metadata separate
  from immutable model gameplay data; condition must not create random stat differences between
  copies of the same model.
- [ ] **META-03 (P0):** Allow any teammate to collect and temporarily use a dead player's dropped
  equipment without changing its persistent owner. At successful extraction, commit an owner-bound
  item only when its owner is personally carrying or equipping it. If a non-owner carries it or it is
  on the ground, do not route it automatically; leave it in the stage and delete it at teardown.
- [ ] **META-04 (P0):** Bind eligible previously unowned Parts, Cores, weapons, and materials to their
  final eligible carrier when successful extraction commits them, then transfer them to that
  player's company rather than the first picker or host. Include equipped loot as carried by its
  wearer, leave ground loot uncommitted, and never overwrite an existing persistent-owner ID.
- [ ] **META-05 (P0):** Add a base analysis/research process that examines a recovered Part or
  non-starter Core and lets its owner either retain it as usable equipment or explicitly submit and
  consume it as one research sample toward reproduction.
- [ ] **META-06 (P0):** Require five recovered samples before analysis can unlock a Part or
  non-starter Core manufacturing blueprint. All five must have the exact same variant/model ID and
  must be consumed when the blueprint unlocks. Track weak, normal, and exceptional Part variants as
  separate research progress and blueprints; track every Core model separately; exempt the default
  Neutral starter blueprint; and define any remaining manufacturer-knowledge or research
  prerequisites.
- [ ] **META-29 (P0):** Add manufacturing for researched non-starter Core models with data-defined
  material costs, ownership, storage, and whole-loadout integration. Never manufacture a researched
  Core for free unless its own authored cost explicitly says so; the default Neutral starter remains
  the zero-cost exception.
- [ ] **META-07 (P0):** Add mass production of researched Part models/variants with explicit resource
  costs, producible-model limits, and storage rules. Use data-defined per-model costs, enforcing
  `exceptional > normal > weak` for materials. Consume the materials and deliver the Part
  immediately on confirmation, with no production timer. Manufacturing the same model always
  produces its fixed gameplay data rather than a randomly rolled quality or affix set.
- [ ] **META-20 (P0):** Add reusable whole-loadout manufacturing configurations containing stable
  model/variant IDs for one required Core, one optional weapon, and six optional Part slots,
  including intentionally empty slots. Before confirmation, revalidate every required
  unlock/blueprint, calculate and show
  the sum of all current item costs, verify materials and storage, and identify any unavailable
  component. One confirmation consumes the displayed total and immediately creates exactly one
  complete configured loadout using entirely new item instances; never draw matching instances from
  storage. Provide no multi-loadout batch or automatic queue.
- [ ] **META-21 (P0):** Add a separate `Equip Existing Robot` flow that assembles a robot from loose
  company-owned Core, Part, and weapon instances already in storage. Do not manufacture or
  automatically replace missing items in this flow. Validate the required Core, optional weapon,
  optional Parts, compatibility, ownership, and loadout before deployment. Allow the player's
  consciousness to transfer into any owned Core selected through this flow.
- [ ] **META-22 (P0):** Build a large paginated company-storage interface for loose undeployed Cores,
  weapons, Parts, and other persistent equipment. Add stable paging, sorting, filtering,
  search, item details, favorite/unfavorite controls, and clear equipped/reserved/deployed states
  without exposing run inventory as base storage. Enforce a finite data-defined capacity, show used/
  maximum space on every page, prevent pagination or filtering from hiding the fact that storage is
  full, and apply unlocked company storage-capacity upgrades.
- [ ] **META-25 (P0):** Implement a transactional post-extraction storage-resolution session. Keep
  a durable frozen snapshot of the player's extracted carried inventory, show it beside company
  storage without automatic placement, and allow manual transfers and storage-page reordering. Keep
  the surviving equipped loadout outside this session. On confirmation, list and permanently discard
  everything remaining in the carried-inventory snapshot. Also allow confirmed deletion of existing
  stored possessions to free slots. Save unresolved state across interruption/crash, prevent
  duplication, and commit changes atomically.
- [ ] **META-26 (P0):** Add persistent per-player company improvement with data-driven upgrade tracks.
  Implement storage expansion first, allowing defined upgrades to increase slots on existing pages,
  add new pages, or do both. Apply costs and prerequisites without changing item ownership or
  bypassing unresolved storage sessions. Leave room for later manufacturing, research, and facility
  upgrade tracks.
- [ ] **META-27 (P0):** Preserve the successful survivor's required Core, optional weapon, and six
  optional Part slots as the active robot loadout at base without consuming storage slots. When the
  player later removes an equipped item into company storage, require a valid free slot or an atomic
  swap that frees one;
  never delete or auto-store equipped gear because the base inventory is full.
- [ ] **META-23 (P0):** When a party wipe deletes a deployed robot's items, preserve its saved
  loadout template, reopen base preparation with that configuration selected, recalculate its total
  manufacturing cost against current data, and show unavailable blueprints or insufficient
  materials. Also allow the player to leave that screen and equip a robot from existing storage.
- [ ] **META-24 (P2):** For later multiplayer, support physical equipment lending through existing
  systems only: let the owner place an owned Core, Part, or weapon in their starting run inventory,
  deploy with it, and drop it during the stage for a teammate to collect or equip. Preserve the owner
  ID through every handoff, and warn the borrower to return it physically. Extract the item only if
  its owner carries or equips it when extraction resolves; otherwise leave it behind and delete it
  during teardown. Add no pre-stage assignment or loan reservation.
- [ ] **META-08 (P0):** Separate the torso-sized spatial run inventory, extracted inventory,
  research knowledge, blueprints, manufactured inventory, and equipped loadouts.
- [ ] **META-09 (P0):** On full-party defeat, end the current stage attempt and preserve the
  campaign, research, blueprints, and base/company manufacturing state while losing every equipment
  item instance deployed into the failed run. Return to preparation rather than starting again.
- [ ] **META-10 (P0):** On run failure, discard every uncommitted Part/material in temporary run
  inventory and every deployed equipment item instance while preserving undeployed persistent
  inventory, research, blueprints, and base state.
- [ ] **META-15 (P0):** Keep newly looted Parts/materials in authoritative temporary run inventory
  alongside looted weapons and Cores. Every item must fit the equipped torso's spatial grid; Parts
  may be equipped or swapped during that stage. Commit the loot to persistent cloud inventory only
  after the party clears the final objective and explicitly extracts from the post-clear recovery
  phase. Do not provide an early-extraction route.
- [ ] **META-16 (P0):** Move selected starting Cores, Parts, and equipment into an authoritative
  deployed manifest when the stage begins. On victory, return an owner-bound item only when its owner
  is its final eligible carrier, and bind each unowned loot item to its eligible final carrier. Treat
  every other stage item as unextracted and delete it at teardown; on failure, destroy/remove every
  deployed instance.
- [ ] **META-17 (P0):** After full-party defeat, let each player manufacture a replacement loadout,
  equip one from surviving company storage, or manufacture the zero-cost default Neutral Core to
  farm back up. Never provide free copies of stronger lost gear.
- [ ] **META-28 (P0):** Implement the default Neutral starter blueprint through ordinary company
  manufacturing. Give it zero manufacturing cost and sale value, exclude it from research-sample
  progress, expose no dismantling action, and ensure repeated copies cannot generate persistent value.
- [ ] **META-18 (P0):** Keep all item instances left in company/base storage outside the deployed
  manifest and guarantee that stage completion, failure, disconnect, or teardown cannot delete them.
- [ ] **META-11 (P2):** For the post-demo networked release, persist each player's base progression
  across desktop, Android, and iOS with conflict-safe cloud ownership and save synchronization for
  possessions, research, blueprints, and progression.
- [ ] **META-12 (P2):** For later multiplayer, make extraction/reward assignment authoritative and prevent item
  duplication or ownership changes through disconnect, reconnect, lending, pickup, death,
  extraction, stack split/merge, or host migration. Commit owner-bound equipment and materials only
  when carried/equipped by their persistent owner, never auto-route possessions held by someone
  else, and bind eligible unowned loot exactly once.
- [ ] **META-13 (P1):** Add base UI for recovered samples, analysis progress, known manufacturers,
  blueprints, immediate manufacturing, paginated loose-item storage, and loadout preparation.
- [ ] **META-14 (P1):** Balance farming-stage material income and production costs so discovery
  remains meaningful without requiring excessive repetition.
- [ ] **META-19 (P0):** Implement the demo's in-world consciousness "cloud," persistent inventory,
  blueprints, progression, and encounter log entirely through a durable local offline save. Do not
  require an account, Internet connection, or remote backend.
- [ ] **META-30 (P0):** Add one persistent universal-currency balance to the local profile with safe
  save/load, overflow validation, and atomic transaction history. Keep its player-facing name in data
  until the fiction names it. Never add it to a deployed manifest or remove it because a run fails.
- [ ] **META-31 (P0):** Add a company/base-only selling interface for owned stored equipment and
  materials, including extracted duplicate blueprints. Show the exact currency return before
  confirmation, derive authored prices from item model and rarity data, prevent selling during stages,
  and keep the Neutral starter's price at zero. Require an additional explicit warning for Epic,
  Legendary, or favorited items. For material stacks, provide a quantity selector and atomically sell
  only the chosen amount.
- [ ] **META-32 (P0):** Represent authored equipment-manufacturing unlocks as physical 1-by-1 blueprint
  items found during stages or purchased at the company/base. Give them fixed rarity and selling data.
  Commit a found blueprint only through normal successful extraction, then require the player to
  consume it at the company/base to persistently unlock its equipment option. Keep already-known or
  purchased duplicate blueprints as ordinary sellable items instead of consuming or discarding them
  automatically. Do not use blueprints for material-tier conversions.
- [ ] **META-33 (P0):** Omit dismantling from all item actions, menus, and backend transactions.
  Ensure selling, research consumption, recipe consumption, and explicit discard cannot accidentally
  invoke or expose a dismantling yield.
- [ ] **META-34 (P1):** Build a data-driven global rarity/drop-frequency table and apply stage-
  difficulty modifiers through a validated loot-generation step. Test probability boundaries,
  deterministic seeded results, unavailable models, and modifier extremes without hard-coded tiers.
- [ ] **META-35 (P0):** Add company/base progression purchases using the universal currency. Support
  authored company upgrades and purchasable equipment, materials, and physical equipment blueprints.
  Use a fixed authored catalog with limited stock, allow already-unlocked blueprint duplicates to be
  purchased, and validate price, remaining stock, ownership, storage capacity, and unlock conditions
  atomically. Keep stock restoration data-driven and unresolved until the shop UI is playtested.

## Approved PvE mode backlog

These are the first six game modes to develop, in the confirmed order below. Story and farming
expeditions are required for the demo. The other four modes are post-demo work in the listed order.
All six should reuse the same movement, combat, Core/Part loadout, room, objective, reward, and
failure systems.

- [ ] **MODE-01 (P0):** Build a data-driven mission contract that defines a mode's room-graph rules,
  objectives, success/failure conditions, protected targets, encounter modifiers, and rewards
  without creating a separate game architecture for every mode.
- [ ] **MODE-02 (P0):** Build story expeditions from fixed authored stage graphs with scripted
  narrative events, controlled encounter order, authored consequences, and story-critical
  objectives.
- [ ] **MODE-03 (P0):** Build farming expeditions by randomizing compatible authored rooms from a
  saved seed, with material-focused rewards and graph validation that prevents unreachable
  objectives or progression dead ends.
- [ ] **MODE-04 (P2):** After the demo, build replayable boss hunts around a known boss or boss
  family, specialized build preparation, boss-specific mechanics, and rewards worth pursuing across
  repeated attempts.
- [ ] **MODE-05 (P2):** After boss hunts, build settlement-defense missions in which enemies
  threaten generators, terminals, important NPCs, or other configured targets whose real simulated
  HP and state determine mission success or failure.
- [ ] **MODE-06 (P2):** After settlement defense, build rescue/escort missions for recovering
  characters, Cores, or critical machinery, with authored or AI-driven movement and damage rules
  instead of random narrative death.
- [ ] **MODE-07 (P2):** After rescue/escort missions, build challenge contracts as PvE stages
  composed from reusable modifiers, such as stronger enemies, altered hazards, restricted Cores or
  Parts, and increased rewards.
- [ ] **MODE-08 (P2):** Keep large-scale raid development behind these six modes and do not turn any
  of them into a competitive player-versus-player mode.

## Stage progression

- [ ] **STAGE-01 (P0):** Implement beginning, progressing through, completing, failing, retrying,
  and leaving a room-graph stage.
- [ ] **STAGE-02 (P0):** Represent every stage as a graph of room nodes and traversable connections.
- [ ] **STAGE-03 (P0):** Add a pre-stage screen showing story context, objective, party, loadout,
  difficulty, and rewards.
- [ ] **STAGE-04 (P0):** Add stage start, checkpoint/room transition, completion, failure, retry,
  and return flows.
- [ ] **STAGE-05 (P0):** Add the enemies, hazards, objectives, and PvE encounters required by
  DEC-18; do not add opposing player-controlled characters.
- [ ] **STAGE-06 (P0):** Add a final encounter or objective that tests the selected Core/Part build.
- [ ] **STAGE-07 (P0):** Grant stage rewards exactly once from the authoritative successful
  extraction result, never merely from clearing the final objective.
- [ ] **STAGE-08 (P2):** For later multiplayer, make all party members agree on stage ID, version,
  seed, difficulty, and progress.
- [ ] **STAGE-09 (P2):** For later multiplayer, make story dialogue/cutscenes skippable without
  desynchronizing a party.
- [ ] **STAGE-10 (P1):** Add stage-specific checkpoints/recovery if the selected game structure
  needs them.
- [ ] **STAGE-11 (P0):** Add a pre-mission consciousness-synchronization check backed by the demo's
  local save. Define restoration behavior for interrupted writes and corrupted or unavailable local
  data; add network-outage behavior only with later online persistence.
- [ ] **STAGE-12 (P0):** Add the campaign event in which the crew conquers a company and unlocks
  player-controlled manufacturing/research progression.
- [ ] **STAGE-13 (P0):** Hide unexplored rooms until the current room is cleared, then reveal its
  directly connected room choices.
- [ ] **STAGE-14 (P0):** Define data-driven entry, clear, reward, and exit conditions for each room.
- [ ] **STAGE-15 (P0):** Prevent travel through locked connections until the current room's clear
  condition is satisfied.
- [ ] **STAGE-16 (P0):** Add a stage map that distinguishes the current room, cleared rooms,
  revealed choices, unexplored rooms, locked paths, and the known final objective.
- [ ] **STAGE-17 (P0):** Implement combat rooms that clear when their configured enemies or waves
  are defeated.
- [ ] **STAGE-18 (P0):** Implement Core rooms where players can inspect and change permitted Cores.
- [ ] **STAGE-19 (P0):** Implement weapon rooms where players can inspect and change permitted
  weapons.
- [ ] **STAGE-20 (P0):** Implement recruitment rooms containing a trapped or stranded follower who
  joins and fights with the party for the remainder of that stage only.
- [ ] **STAGE-21 (P0):** Implement boss rooms with boss-specific clear and reward rules.
- [ ] **STAGE-22 (P0):** Implement non-boss final rooms that clear through a configured achievement,
  objective, interaction, discovery, or lore event.
- [ ] **STAGE-23 (P0):** Mark the stage secured after its designated final objective room is cleared,
  unlock post-clear backtracking and extraction, and complete the stage only when the player chooses
  the extraction action.
- [ ] **STAGE-24 (P0):** Validate that every stage has a reachable start, legal connections, at
  least one reachable final objective, and no unintended progression dead ends.
- [ ] **STAGE-25 (P2):** For later multiplayer, synchronize room visibility, clear state, route
  choice, rewards, followers, loadout changes, and final completion for the entire party.
- [ ] **STAGE-26 (P0):** Build farming-stage room graphs by authoritatively randomizing compatible
  predefined room templates from a saved seed, with the same algorithm reusable by a later server.
- [ ] **STAGE-27 (P0):** Display each newly revealed connection as an unknown path without exposing
  its destination room type, encounter, reward, or lore before entry.
- [ ] **STAGE-28 (P1):** Save campaign consequences of cleared stages, discoveries, and important
  lore objectives while removing temporary followers from the active party at stage end.
- [ ] **STAGE-29 (P0):** Create an authored room-template format containing geometry, doors,
  compatible connections, encounters, clear rules, rewards, and environmental presentation.
- [ ] **STAGE-30 (P0):** Create fixed authored graphs for story/lore stages so narrative rooms,
  encounters, reveals, and objectives always occur in their intended order.
- [ ] **STAGE-31 (P0):** Validate randomized farming graphs for reachable final objectives, legal
  door connections, room-type limits, difficulty progression, and absence of soft locks.
- [ ] **STAGE-32 (P0):** Make entering a Core room swap the selected Core and its linked main ability
  together for every authoritative gameplay and UI system. Selecting the default Neutral starter
  removes the old Core ability and leaves the Core-ability slot empty.
- [ ] **STAGE-33 (P1):** Give every recruitable stage follower a reason they became trapped or
  stranded there and a clear reason they stop accompanying the party afterward.
- [ ] **STAGE-34 (P0):** Allow travel back through unlocked connections into previously cleared
  rooms without replaying their completed encounter unless a reset rule explicitly applies.
- [ ] **STAGE-35 (P0):** Place an assembly room before every battle room and keep the battle door
  locked until every active party member is physically present in the assembly room.
- [ ] **STAGE-36 (P2):** For later multiplayer, make the server authoritatively track assembly-room
  presence, open the door once every party body's position is inside, count a disconnected retained
  body already inside as present, and recalculate the requirement when players move, reconnect, or
  are kicked.
- [ ] **STAGE-37 (P2):** Implement consciousness-download rooms that restore spectators into a
  prepared Core/body with a predefined loaner Part loadout without resetting already cleared rooms.
  Generate between 1 and the current player count of bodies, strongly weighted toward exactly 1.
- [ ] **STAGE-44 (P2):** Spawn the reusable USB/data-transfer device in the download room, let an
  active player carry it to a fallen teammate, extract that teammate's active consciousness, return
  it to an available prepared body, and complete the transfer authoritatively without consuming the
  reusable device. Allow only one carried consciousness payload at a time, clear it after successful
  installation, and permit the next recovery trip.
- [ ] **STAGE-38 (P2):** Mark recovery-room Parts as loaned/replacement equipment and prevent them
  from silently overwriting ownership of the player's original Parts.
- [ ] **STAGE-39 (P2):** Preserve a dead player's original recoverable Parts at the death location and
  allow any teammate to backtrack, collect, and use them. Preserve their persistent-owner IDs and
  require the teammate to return them physically to the revived owner before extraction. Extract
  only items carried/equipped by that owner; lose anything held by a teammate or left on the ground.
- [ ] **STAGE-40 (P2):** If no reachable consciousness-download room exists, keep the dead player in
  spectator state for the rest of the stage attempt and restore play only after the party returns to
  preparation and enters a later stage.
- [ ] **STAGE-41 (P2):** Generate recovery-body count and contents authoritatively from the shared
  stage seed so every client sees the same 1-to-player-count result. For each generated Part,
  independently select a company-associated model/variant: 94.9% weak, 5% normal, and 0.1%
  exceptionally strong. The selection chooses a stable model ID; it never rerolls gameplay data
  within that model.
- [ ] **STAGE-42 (P2):** Let any living player claim/transfer into or loot a prepared recovery body,
  and let teammates choose which fallen player's extracted data receives any remaining body.
- [ ] **STAGE-43 (P2):** Prevent two clients from consuming, looting, transferring into, or assigning
  the same prepared body, Core, Part, USB device, or consciousness payload.
- [ ] **STAGE-45 (P2):** Prevent the single USB payload from being duplicated or installed into
  multiple bodies. Do not add an expiration countdown; keep the payload until successful
  installation, but destroy/lose the active consciousness if the run ends first.
- [ ] **STAGE-46 (P2):** If the USB carrier is downed, disconnected, or otherwise loses possession,
  drop the USB and its single consciousness payload safely at the retained/fallen body's position
  and allow another player to loot and continue the transfer.
- [ ] **STAGE-47 (P2):** Delete/return the stage-only USB at run completion, failure, or teardown and
  prevent it from entering personal-base inventory or cloud persistence.
- [ ] **STAGE-48 (P2):** When a living player transfers into a prepared body, leave the former body,
  functional Core, and Parts at the transfer location as ordinary loot. Move the consciousness
  atomically to the new Core, clear it from the old Core, and prevent duplicate player control.
- [ ] **STAGE-49 (P2):** Let another eligible character immediately transfer into an unoccupied
  functional Core/body left by a living transfer. Server-authoritatively arbitrate simultaneous
  transfer/loot attempts and allow exactly one successful claimant.
- [ ] **STAGE-50 (P0):** Lock the party into a stage after launch. Allow normal backtracking through
  its cleared rooms, but provide no return-to-base, retreat, or early-extraction action; resolve the
  attempt only through post-final-objective extraction or failure.
- [ ] **STAGE-51 (P0):** Record every authoritative enemy spawn during each attempt—its observable
  identity/appearance, stage, and actions actually witnessed—in the party's persistent cloud
  encounter log even when the attempt fails. Do not grant unspawned-room information, internal
  statistics, hidden weaknesses, unseen behaviors, or drop tables.
- [ ] **STAGE-52 (P0):** After the final objective clears, preserve the room graph and all ground
  loot, allow unrestricted backtracking through unlocked rooms, activate the configured extraction
  point in the final room, and extract only the items currently carried or equipped when the player
  returns there and confirms the action.
- [ ] **STAGE-53 (P0):** During post-clear recovery, continue simulating traps, environmental
  hazards, damage, death, and any surviving threats. Preserve cleared encounter state without
  respawning defeated combat waves, and route post-clear death through normal run failure.
- [ ] **STAGE-54 (P0):** Add data-driven final-room extraction definitions supporting an exit that
  appears, activates, or opens when the configured boss, objective, achievement, interaction, or lore
  clear condition completes. Validate that every final-room type provides a usable extraction point.
- [ ] **STAGE-55 (P2):** For later multiplayer, authoritatively compute extraction quorum as
  `ceil(activeLivingPlayers / 2)`, start the 10-second countdown when quorum is inside the final-room
  extraction point, and immediately extract when every active living player is inside. On countdown
  completion, extract the entire surviving party even if some living players remain outside. Once
  started, never pause, reset, or cancel the countdown because players leave the point. Exclude dead
  spectators; include retained disconnected living bodies until the host kicks them, using their
  authoritative positions for presence. Count downed players until their revival windows expire and
  use their body positions for presence, but mark them dead if still downed when extraction resolves.

## Combat rules

- [ ] **COMBAT-01 (P0):** Add health/max-health to authoritative player state.
- [ ] **COMBAT-02 (P0):** Add validated base-damage values to attack, weapon, and damaging-ability
  data.
- [ ] **COMBAT-03 (P0):** Resolve attacks against eligible enemy targets in the authoritative
  simulation and keep player-versus-player damage disabled.
- [ ] **COMBAT-04 (P0):** Prevent an individual swing from damaging the same target repeatedly
  unless the attack explicitly allows it.
- [ ] **COMBAT-05 (P0):** Prevent the attacker from damaging itself.
- [ ] **COMBAT-06 (P2):** For later cooperative multiplayer, resolve DEC-74 and implement the chosen
  friendly-fire rule without creating a competitive PvP mode.
- [ ] **COMBAT-07 (P0):** Apply attack cooldown consistently in the offline simulation and preserve
  the same authoritative rule when networking is added later.
- [ ] **COMBAT-08 (P2):** For later multiplayer, add hit confirmation and damage events to the
  network protocol.
- [ ] **COMBAT-09 (P0):** Add hurt feedback: sound, effect, optional short hit pause, and clamped
  world-camera shake that becomes stronger as the received damage percentage of maximum health
  increases. Accumulate rapid-hit contributions, decay the result over time, and clamp the combined
  strength. Keep all screen-space UI, including the semi-transparent run inventory, anchored while
  the world view shakes. Add flash only after its accessibility controls exist.
- [ ] **COMBAT-10 (P0):** Add death state and block dead-player movement/attacks.
- [ ] **COMBAT-11 (P0):** Add safe stage-spawn placement and the body-activation placement needed by
  authorized recovery flows; the solo demo has no automatic combat respawn.
- [ ] **COMBAT-12 (P1):** Add brief spawn or body-activation protection only if playtests show it is
  needed.
- [ ] **COMBAT-13 (P1):** Record a noncompetitive defeat cause for results and diagnostics, including
  enemy, hazard, self-inflicted, and simultaneous damage; do not add kill scoring.
- [ ] **COMBAT-14 (P1):** Add knockback only if it improves playtests and remains server-authoritative.
- [ ] **COMBAT-15 (P1):** Add combat-state unit and integration tests, including damage-to-shake
  percentage scaling, rapid-hit accumulation/decay, maximum shake clamping, reduced/off settings,
  and stable screen-space UI.
- [ ] **COMBAT-16 (P2):** For later multiplayer, add an authoritative downed state before final
  death/replacement when at least one teammate can still attempt a revive.
- [ ] **COMBAT-17 (P2):** Display a revive circle around each downed player and accumulate progress
  only while an eligible living teammate remains inside it for 5 continuous seconds.
- [ ] **COMBAT-18 (P2):** Reset revive progress to zero whenever the reviving teammate leaves the
  circle; incoming damage does not pause, reduce, or reset the attempt.
- [ ] **COMBAT-24 (P2):** Allow a reviving teammate to aim and attack normally while inside the
  circle without interrupting revive progress.
- [ ] **COMBAT-19 (P2):** Complete revival on the server and return the revived player with exactly
  1 HP, a valid body state, and synchronized visuals/audio.
- [ ] **COMBAT-20 (P0):** In the solo demo, immediately end the stage attempt when the sole player is
  defeated; do not enter downed, revive, spectator, or AI-rescue states.
- [ ] **COMBAT-21 (P2):** Add tests for simultaneous revives, multiple revivers, interrupted revives,
  disconnects, all-player defeat, and exactly-1-HP restoration.
- [ ] **COMBAT-22 (P2):** Use one fixed revive progress rate whenever at least one eligible teammate
  is inside the circle; do not stack speed from additional teammates.
- [ ] **COMBAT-23 (P2):** Add an authoritative 15-second downed-state expiration timer with clear
  HUD/audio warnings, pause it during valid revive progress, and disable teammate revival after it
  expires.
- [ ] **COMBAT-25 (P2):** Enter spectator state after the revival window expires and prevent the
  spectator from moving, attacking, interacting, triggering rooms, or affecting assembly presence.
- [ ] **COMBAT-26 (P2):** Add a spectator camera locked to a living player; do not allow independent
  spectator camera movement.
- [ ] **COMBAT-27 (P2):** Allow a spectator to cycle among eligible living players when more than one
  teammate remains.
- [ ] **COMBAT-28 (P2):** Test revive timing, downed-timer pause/resume, exact expiration boundaries,
  spectator restrictions, camera targets, target death, disconnects, and reconnects.
- [ ] **COMBAT-29 (P2):** Restore a spectator only through a reached consciousness-download room,
  using its prepared Core/body and loaner Parts; ordinary room completion does not restore them.
- [ ] **COMBAT-30 (P0):** Build the authoritative typed-damage step:
  `typeScaledBaseDamage = sourceBaseDamage * typedMatchupMultiplier`. Resolve the attacker's and
  defender's current type compositions at hit resolution, apply the multiplier exactly once before
  later mitigation/final damage handling, and test both combat directions, weapon swaps, Core swaps,
  target changes, rounding boundaries, and intentional Neutral smoothing in both advantageous and
  disadvantaged matchups. Do not force quality tiers into a strictly monotonic per-matchup order.
- [ ] **COMBAT-31 (P0):** Implement the universal weak skeleton attack as a validated base-damage
  close-range melee source available without a weapon, Parts, or Core ability and selectable as
  `Unarmed` while a weapon is equipped. Route it through COMBAT-30 exactly once, use data-defined
  reach/timing/hit shape, and test a Core-only Neutral build entering and completing the minimum
  farming encounter plus holster/draw switching with a weapon.

## Match flow

- [ ] **GAME-01 (P0):** Define offline base/loadout-to-stage start conditions for the demo and later
  multiplayer lobby start conditions separately.
- [ ] **GAME-02 (P0):** Place valid spawn points in map data.
- [ ] **GAME-03 (P0):** Add an offline pre-stage loadout confirmation/start transition; add party
  ready/countdown state only with later multiplayer.
- [ ] **GAME-04 (P0):** Implement the selected objective and win condition.
- [ ] **GAME-05 (P0):** Implement shared PvE objective progress without competitive player scoring.
- [ ] **GAME-06 (P0):** Add authoritative match timer where required.
- [ ] **GAME-07 (P0):** Separate final-objective clear, post-clear recovery, successful extraction,
  and failure states. Freeze objective changes at final clear but freeze inventory/reward results
  only when extraction or failure ends the attempt.
- [ ] **GAME-08 (P0):** Add PvE results data: completion/failure, objective outcome, recovered/lost
  items, useful player stats, and the reason the stage ended; do not declare a PvP winner.
- [ ] **GAME-09 (P0):** Add return-to-base/company and return-to-lobby flows; do not offer an
  immediate rematch after full-party defeat until every player has prepared a valid loadout through
  new-robot manufacturing or existing company storage.
- [ ] **GAME-10 (P2):** For later multiplayer, handle host/server shutdown with a clear client
  message.
- [ ] **GAME-11 (P2):** For later multiplayer, handle objective timeouts, host loss, and
  last-player-disconnect cases without introducing competitive tie or overtime rules.
- [ ] **GAME-12 (P0):** Build the story-integrated opening tutorial around the weak Neutral starter
  Core. Begin with the discarded player/frame falling from the sky and crashing onto the Scrap
  Planet with the Neutral Core already installed. Teach movement, combat, scavenging, Part
  replacement, Core identity, and stage commitment, then award the player's small company and
  transition into its base-management flow.
- [ ] **GAME-13 (P0):** Unlock extraction only after final-objective completion. When the explicit
  final-room extraction action is confirmed, atomically commit each player's carried/equipped
  temporary loot and provide no early-extraction exit.
- [ ] **GAME-14 (P0):** On full-party defeat, show the lost deployed equipment/run loot, return the
  party to manufacturing/loadout preparation, and block stage launch until all players are equipped.
- [ ] **GAME-15 (P0):** Show the enemy entries learned during the completed or failed attempt on the
  results/base-preparation screens. Show only directly observable facts and let players infer useful
  counters themselves when manufacturing later loadouts.
- [ ] **GAME-16 (P0):** Treat death/full-party defeat during post-clear recovery exactly like any
  other failed attempt: commit no run loot, remove deployed items, and return to base preparation.
- [ ] **GAME-17 (P2):** Resolve multiplayer extraction once for the whole party, preserving each
  non-downed living player's carried/equipped inventory at the authoritative completion instant and
  preventing duplicate commits from simultaneous entry, countdown completion, disconnect, or
  reconnect. Exclude still-downed players and their equipment from the successful item commit.
- [ ] **GAME-18 (P2):** Before and during later-multiplayer extraction, compare each eligible
  player's carried/equipped owner-bound possessions with that player's persistent-owner ID. Clearly
  list mismatches and warn that those possessions will be lost, but never block, pause, reset, or
  extend extraction because of them. Record the losses in the extraction result.
- [ ] **GAME-19 (P0):** After every successful extraction, immediately route each player through
  their own mandatory storage-resolution session. Show their extracted carried inventory beside the
  paginated company inventory, keep equipped gear equipped and outside the session, perform no
  automatic placement, and allow manual transfers and storage rearrangement. Before confirming,
  warn that every carried item not placed in company storage will be discarded; do not allow another
  stage while the session is unresolved.

## Gameplay HUD

- [ ] **HUD-01 (P0):** Display local health clearly.
- [ ] **HUD-02 (P0):** Display PvE objective state and remaining time where applicable, without a
  competitive player score.
- [ ] **HUD-03 (P0):** Display attack cooldown/readiness without obscuring the play area.
- [ ] **HUD-04 (P2):** For later multiplayer, display connection/reconnecting state when network
  play is active.
- [ ] **HUD-05 (P0):** Show clear death, spectating, respawn, victory, and defeat messages.
- [ ] **HUD-06 (P1):** Replace debug-style player labels with final name/team treatment.
- [ ] **HUD-07 (P2):** For later co-op, add a noncompetitive party-stat summary if playtests show it
  is useful; do not present it as a PvP scoreboard.
- [ ] **HUD-08 (P1):** Keep all HUD content readable at every supported resolution/UI scale.
- [ ] **HUD-09 (P2):** For later multiplayer, show extraction players-present/required, the shared
  10-second countdown, and a clear immediate-extraction state when the entire living party enters.

---

# Milestone D - Menus, content, and presentation

**Exit gate:** A new player can install, configure, understand, play, and leave the game without a
developer present. All visible placeholder content is removed or intentionally labeled.

## Menus and input

- [ ] **UX-01 (P0):** Add a boot/loading screen.
- [ ] **UX-02 (P0):** Finish the title menu's Settings and Credits destinations. The basic responsive
  shell already provides functional Play and Quit actions while showing unfinished destinations as
  disabled instead of pretending they work.
- [ ] **UX-03 (P0):** Add an offline Play screen with New Game/Continue, stage/loadout preparation,
  and Back actions. The basic shell already launches the existing world through New Game, disables
  Continue until saves exist, and supports Back; stage/loadout preparation remains. Do not show
  nonfunctional Create Room or Join Room actions in the demo.
- [ ] **UX-04 (P0):** Add character, skin, and weapon selection UI.
- [ ] **UX-05 (P2):** After the offline demo, add a LAN server list instead of selecting the first
  discovered server.
- [ ] **UX-06 (P2):** After the offline demo, add direct-IP entry if direct connection remains
  supported.
- [ ] **UX-07 (P2):** For the later multiplayer release, add a lobby showing players, selections,
  ready status, and host controls.
- [ ] **UX-08 (P0):** Add pause/resume, settings, abandon-stage, and quit confirmation. Clearly warn
  that abandoning after launch counts as failure and loses all deployed equipment and run loot; it
  is never a safe return-to-base action.
- [ ] **UX-09 (P0):** Add understandable error dialogs for startup, platform, version, content-data,
  save, and packaging failures, always with a recovery action. Add connection/server cases with later
  multiplayer.
- [ ] **UX-10 (P0):** Ensure keyboard navigation works on every required screen.
- [ ] **UX-11 (P1):** Add mouse hover/focus/disabled states and UI sounds.
- [ ] **UX-12 (P1):** Add configurable movement, attack, pause, and debug bindings.
- [ ] **UX-13 (P1):** Add controller navigation and gameplay if DEC-10 requires it.
- [ ] **UX-14 (P1):** Handle controller disconnect/reconnect cleanly.
- [ ] **UX-15 (P2):** For the later multiplayer release, add a Create Room page with room name,
  privacy/invite setting, a selectable player limit from 1 through 6, stage, mode, difficulty, and a
  clear Create button.
- [ ] **UX-16 (P2):** For the later multiplayer release, add a Join Room page with compatible room
  results, refresh, selection, password/invite entry if used, direct-IP fallback if retained, and
  clear failure messages.
- [ ] **UX-17 (P0):** Add a cloud encounter-log screen showing only enemies and details observed in
  prior attempts. Do not turn it into a complete official wiki or expose hidden mechanics.
- [ ] **UX-18 (P0):** Show the current combat option and make switching readable: desktop mouse-wheel
  input cycles between the equipped weapon and `Unarmed`, holstering or drawing the weapon without
  changing the loadout. On touchscreens, tapping the visible combat weapon slot cycles options and
  updates its icon. Add any controller/keyboard equivalents selected in DEC-148.
- [ ] **UX-19 (P0):** When run inventory opens, place currently available ground loot in a vertically
  scrollable list on the left, filtered to a data-defined circular pickup radius centered on the
  player and blocked by solid walls. Remove unavailable/out-of-range entries immediately. Default to
  descending stable creation/spawn ID (`Newest`) and allow sorting by Newest, Rarity, Item Type,
  Name, or occupied-cell-count Inventory Size; every mode supports ascending/descending reversal.
  Resolve equal primary values by descending creation/spawn ID, and save the selected key and
  direction across inventory closure and application restarts. Default Rarity to highest-first by
  registry rank—initially Legendary, Epic, Rare, Uncommon, Common—and use Core, Weapon, Part,
  Material, Blueprint as forward Item Type order.
  Desktop hover and touchscreen tap show that item's model, rarity color, actual stat names/values,
  relevant ability, and inventory footprint without an equipped-item comparison or mandatory
  non-color rarity marker. Require drag-to-grid pickup rather than automatic placement. Use the same
  Core portrait in field loot and inventory/loadout UI. Do not draw the pickup-radius boundary. Keep
  simulation running while the interface blocks player movement, attacks, and abilities, leaving the
  player vulnerable; taking damage does not close the interface. Make the panel partially transparent
  while keeping it screen-stable: damage shakes the world camera behind it throughout the stage, not
  the inventory itself, and adds no inventory-only warning overlay.

## Settings and accessibility

- [ ] **SET-01 (P0):** Add master, music, and sound-effect volume controls.
- [ ] **SET-02 (P0):** Add fullscreen/windowed, resolution, VSync, and frame-limit settings.
- [ ] **SET-03 (P0):** Save settings locally and recover from corrupt settings safely.
- [ ] **SET-04 (P1):** Add UI scale and text-size options.
- [ ] **SET-05 (P1):** Do not communicate team, danger, or health by color alone. Rarity is the
  explicit exception under DEC-164: it uses tier color without requiring a standardized additional
  marker, although item artwork and the visible stat panel still provide context.
- [ ] **SET-06 (P1):** Add a saved screen-shake intensity control, including reduced and off choices,
  before implementing damage shake. Scale each contribution by damage as a percentage of maximum
  health, accumulate rapid hits, decay and clamp the combined shake to tested bounds, then apply the
  chosen accessibility intensity. Camera shake affects the stage view but never the inventory UI.
  Add equivalent controls before introducing any screen-flash effects.
- [ ] **SET-07 (P1):** Add high-contrast aiming/cursor options.
- [ ] **SET-08 (P1):** Add hold/toggle options where continuous input may be difficult.
- [ ] **SET-09 (P1):** Test common forms of color-vision deficiency.
- [ ] **SET-10 (P1):** Make all player-facing text ready for localization even if the demo is English
  only.

## World and gameplay content

- [ ] **WORLD-01 (P0):** Replace the code-generated-only arena definition with a maintainable map
  data format or editor workflow.
- [ ] **WORLD-02 (P0):** Add map validation for boundaries, reachable areas, and spawn points.
- [ ] **WORLD-03 (P2):** For later multiplayer, ensure clients receive a map ID/hash or map data from
  the server.
- [ ] **WORLD-04 (P0):** Produce the minimum map count chosen in PLAN-04.
- [ ] **WORLD-05 (P0):** Playtest spawn fairness and travel time to objectives.
- [ ] **WORLD-06 (P1):** Add environmental decoration without weakening collision readability.
- [ ] **WORLD-07 (P1):** Add map preview/name/author metadata.
- [ ] **WORLD-08 (P1):** Add map rotation or voting if more than one map ships.
- [ ] **WORLD-09 (P0):** Implement story-critical NPC AI for navigation, following/escorting,
  targeting, fighting, damage reactions, HP, incapacitation, and mission scripting.
- [ ] **WORLD-10 (P0):** Make each story-critical NPC's defeat outcome explicitly authored. Do not
  use random permanent-death rolls; allow configured protection, incapacitation, mission failure,
  or a deliberate scripted death selected by the creator.
- [ ] **WORLD-11 (P0):** Give every applicable enemy and boss validated type percentages totaling
  exactly 100% for the Core-effectiveness system. Support both pure and hybrid enemy compositions,
  and author clear visual/readable cues for them.

## Final visual and audio pass

- [ ] **PRES-01 (P0):** Replace generated fallback swords with licensed final quick/heavy sword art.
- [ ] **PRES-02 (P0):** Add attack/swing effects that match actual attack timing and area.
- [ ] **PRES-03 (P0):** Add hit, damage, death, spawn, objective, and victory/defeat effects.
- [ ] **PRES-04 (P0):** Add movement, swing, impact, UI, objective, and match-result sounds.
- [ ] **PRES-05 (P1):** Add music for title/lobby and gameplay.
- [ ] **PRES-06 (P1):** Add smooth music transitions and independent volume buses.
- [ ] **PRES-07 (P1):** Add shadows/grounding so character positions are visually unambiguous.
- [ ] **PRES-08 (P1):** Add a final font with a confirmed redistribution license.
- [ ] **PRES-09 (P1):** Create icon, logo, key art, screenshots, and trailer-ready captures.
- [ ] **PRES-10 (P1):** Audit the whole build for placeholder, debug, inconsistent, or unlicensed art.

---

# Milestone E - Post-demo production networking

**This entire milestone is out of scope for the first offline public demo.** Its exit gate applies to
the first later networked release: the selected network scope works under realistic latency, loss,
disconnects, and hostile input, and failures return players to usable screens.

## Required for LAN and direct-IP releases

- [ ] **NET-01 (P0):** Version the complete handshake and reject incompatible clients clearly.
- [ ] **NET-02 (P0):** Include a gameplay-data/map hash and reject mismatched content.
- [ ] **NET-03 (P0):** Add explicit reliable messages for connect, disconnect, match transitions,
  damage, death, score, loadout, and results.
- [ ] **NET-04 (P0):** Add sequence numbers and stale/duplicate handling for all packet types.
- [ ] **NET-05 (P0):** Add snapshot interpolation for remote players instead of only extrapolation
  and hard correction.
- [ ] **NET-06 (P0):** Detect connection loss, mark the player disconnected without moving/removing
  the retained body, and let the same player reconnect unless the host has kicked that session.
- [ ] **NET-07 (P0):** Validate every client-provided enum, number, rate, transition, and payload
  length before using it.
- [ ] **NET-08 (P0):** Rate-limit connections, input, attacks, discovery, and malformed traffic.
- [ ] **NET-09 (P0):** Prevent session-token guessing/reuse and avoid printing secrets in logs.
- [ ] **NET-10 (P0):** Bound player count to six per room/stage, bound attack count, packet size, and
  all network collections, and reject a seventh connection with a clear room-full response.
- [ ] **NET-11 (P0):** Test late join, leave, host shutdown, packet reordering, packet duplication,
  and packet loss.
- [ ] **NET-12 (P0):** Test at target latency/jitter/loss levels and document supported limits.
- [ ] **NET-13 (P1):** Add network statistics visible in a developer overlay.
- [ ] **NET-14 (P1):** Add replayable protocol fuzz tests for malformed packets.
- [ ] **NET-15 (P1):** Run six-player soak tests for several hours with a realistic maximum of
  enemies, attacks, abilities, effects, disconnects, and reconnects.
- [ ] **NET-16 (P0):** Preserve a disconnected player's authoritative identity, body, loadout,
  health, and last position so the same player can reclaim that state after reconnecting; keep the
  retained body targetable and vulnerable to normal enemy damage.
- [ ] **NET-17 (P0):** Give the host a lobby/gameplay control to kick a disconnected or idle player
  instead of removing that player automatically.
- [ ] **NET-18 (P0):** Remove a kicked player's retained body and update party-size, assembly-room,
  encounter, follower, and defeat requirements safely.
- [ ] **NET-19 (P0):** Prevent another client from claiming a retained disconnected session and
  rotate/revalidate credentials during a legitimate reconnect.
- [ ] **NET-20 (P0):** Synchronize persistent possessions, ownership, research, blueprints, and base
  progression to the player's cloud account without persisting stage-only USB devices or transient
  room state. Commit run loot only after authoritative completion of the entire stage's final
  objective; never commit it through an early-extraction event.
- [ ] **NET-21 (P0):** Persist authoritative enemy-spawn discoveries to each eligible party member's
  cloud encounter log on both victory and failure, idempotently and without duplicating rewards or
  leaking enemies that never spawned or hidden data the players did not observe.

## Additional work only if public Internet play is selected

- [ ] **ONLINE-01 (P0):** Choose the hosting model: player-hosted, dedicated servers, relay, or a
  documented combination.
- [ ] **ONLINE-02 (P0):** Choose discovery: invite codes, friend invites, lobby service, or server
  browser.
- [ ] **ONLINE-03 (P0):** Solve NAT traversal/relay; do not expect players to configure routers.
- [ ] **ONLINE-04 (P0):** Add authenticated player/session identity appropriate to the platform.
- [ ] **ONLINE-05 (P0):** Encrypt/authenticate Internet traffic or use a proven secure transport.
- [ ] **ONLINE-06 (P0):** Deploy servers/services in at least one region with health monitoring.
- [ ] **ONLINE-07 (P0):** Add server deployment, rollback, backups, secrets, and incident procedures.
- [ ] **ONLINE-08 (P0):** Publish service availability, region, latency, and shutdown expectations.
- [ ] **ONLINE-09 (P1):** Add moderation/report/block tools if strangers can interact.
- [ ] **ONLINE-10 (P1):** Add privacy-compliant retention/deletion rules for accounts and telemetry.
- [ ] **ONLINE-11 (P1):** Load-test backend capacity above the expected launch peak.
- [ ] **ONLINE-12 (P1):** Prepare a fallback when lobby, identity, relay, or dedicated servers fail.

---

# Milestone F - Quality, performance, and release readiness

**Exit gate:** The release candidate passes the test matrix, packages cleanly, has no known P0
issues, and has documented support/legal/release procedures.

## Automated quality

- [ ] **TEST-01 (P0):** Unit-test collision, movement normalization, attack cooldown, damage,
  objective state, direction mapping, and stage transitions.
- [ ] **TEST-02 (P0):** Test JSON parsing, schema versions, cross-references, ranges, and missing
  assets.
- [ ] **TEST-03 (P2):** For later multiplayer, round-trip-test every network packet and protocol
  version rejection.
- [ ] **TEST-04 (P2):** Before the networked release, convert the current network smoke program into
  a repeatable CI test.
- [ ] **TEST-05 (P2):** For later multiplayer, add an automated server-authority test that attempts
  invalid movement, attacks, damage, and loadouts.
- [ ] **TEST-06 (P1):** Add deterministic simulation tests for identical input sequences.
- [ ] **TEST-07 (P1):** Add a long-running offline soak test with leak checks; extend it to
  multiplayer before the networked release.
- [ ] **TEST-08 (P1):** Archive test reports and release build hashes in CI.

## Manual test matrix

- [ ] **QA-01 (P0):** Test clean install, first launch, normal exit, relaunch, and uninstall.
- [ ] **QA-02 (P0):** Test every character, skin, weapon, map, and game mode combination.
- [ ] **QA-03 (P0):** Test offline new-game, continue, save/load, stage completion/failure, pause,
  background/resume, and quit/relaunch flows with exactly one local player and all networking
  disabled.
- [ ] **QA-13 (P2):** For the later networked release, test host, join, direct IP, disconnect,
  reconnect, packet loss, and server shutdown.
- [ ] **QA-04 (P0):** Test keyboard layouts and all rebinding conflict cases.
- [ ] **QA-05 (P0):** Test supported resolutions, DPI scaling, window resize, fullscreen, and Alt-Tab.
- [ ] **QA-06 (P0):** Test on minimum-spec, recommended-spec, and high-refresh Windows machines.
- [ ] **QA-07 (P0):** Test fresh and corrupt settings/data and read-only/unavailable save paths.
- [ ] **QA-08 (P0):** Test loss of focus during movement, attack, loading, and connection.
- [ ] **QA-09 (P0):** Test rapid menu actions and repeated offline stage/base/menu cycles.
- [ ] **QA-10 (P1):** Recruit players unfamiliar with the project and record where they get stuck.
- [ ] **QA-11 (P1):** Run an accessibility review against SET-04 through SET-10.
- [ ] **QA-12 (P1):** Verify every error message tells the player what happened and what to do next.

## Performance and stability

- [ ] **PERF-01 (P0):** Define CPU, memory, GPU, frame-time, loading-time, and network budgets.
- [ ] **PERF-02 (P0):** Profile six-player gameplay at maximum attacks, enemies, abilities, effects,
  and map complexity on the weakest supported desktop and mobile hardware.
- [ ] **PERF-03 (P0):** Remove per-frame allocations and unbounded collections from hot paths.
- [ ] **PERF-04 (P0):** Verify every texture, batch, renderer, font, socket, and thread is disposed.
- [ ] **PERF-05 (P0):** Test pause/resume and repeated screen changes for leaks.
- [ ] **PERF-06 (P0):** Maintain the target frame rate on the minimum supported hardware.
- [ ] **PERF-07 (P1):** Add lightweight opt-in crash/error reporting if privacy requirements permit.
- [ ] **PERF-08 (P1):** Add a reproducible performance benchmark scene.

## Packaging, store, legal, and support

- [ ] **SHIP-01 (P0):** Produce a versioned Windows package with a bundled compatible runtime.
- [ ] **SHIP-02 (P0):** Confirm the packaged build does not depend on an installed JDK or IDE.
- [ ] **SHIP-03 (P0):** Add game executable metadata, icons, and a safe writable settings/log path.
- [ ] **SHIP-04 (P0):** Test install/update/uninstall without deleting unrelated or player-owned data.
- [ ] **SHIP-05 (P0):** Add `LICENSE`, third-party notices, asset credits, and privacy policy as needed.
- [ ] **SHIP-06 (P0):** Confirm commercial/redistribution rights for every asset, font, sound, song,
  dependency, name, and logo.
- [ ] **SHIP-07 (P0):** Complete the chosen store page, system requirements, content disclosures,
  screenshots, and support contact.
- [ ] **SHIP-08 (P0):** Create a release checklist, changelog, rollback plan, and known-issues list.
- [ ] **SHIP-09 (P0):** Define where users report bugs and what diagnostic files they should attach.
- [ ] **SHIP-10 (P0):** Back up source, source art/audio, signing credentials, and release artifacts.
- [ ] **SHIP-11 (P1):** Code-sign the executable/installer if the selected distribution benefits.
- [ ] **SHIP-12 (P1):** Prepare controller/store metadata and achievements only if included in scope.
- [ ] **SHIP-13 (P0):** Produce, sign, install, update, and test the Android App Bundle/APK on the
  minimum and target Android versions.
- [ ] **SHIP-14 (P0):** Produce, sign, install, update, and test the iOS archive through TestFlight
  on the minimum and target iOS versions.
- [ ] **SHIP-15 (P0):** Complete Google Play and Apple App Store privacy, permission, age-rating,
  encryption, data-safety, screenshot, review, and account requirements.
- [ ] **SHIP-16 (P0):** Confirm desktop, Android, and iOS offline-demo builds use compatible content
  and save-schema versions and do not require a reachable server before submission.

---

# Free public-demo release gates

Do not publish the free public demo until every P0 gate below is true.

- [ ] **GATE-01:** Product scope and the match's objective are written and implemented.
- [ ] **GATE-02:** Eight directions and at least four movement frames per direction ship for every
  playable character/skin promised for the demo.
- [ ] **GATE-03:** A complete start-match-result-rematch/quit loop works without developer tools.
- [ ] **GATE-04:** Health, damage, death, objective state, cooldowns, inventory, and saves are
  controlled by the offline game simulation rather than debug tools or editable UI state.
- [ ] **GATE-05:** Menus, settings, errors, and controls work in a packaged build.
- [ ] **GATE-06:** The complete demo works with networking disabled and without LAN, an Internet
  connection, an account, or a remote service.
- [ ] **GATE-07:** No known crash, save/settings corruption, remote-code-execution path, or easily
  exploited authority or item-duplication issue remains, including in disabled prototype networking
  code shipped with the demo.
- [ ] **GATE-08:** Performance meets the written minimum-hardware budget.
- [ ] **GATE-09:** All shipped content is final or deliberately labeled, licensed, and credited.
- [ ] **GATE-10:** Fresh-install playtests succeed with players who did not build the game.
- [ ] **GATE-11:** CI produces the exact versioned package and all required tests pass.
- [ ] **GATE-12:** Store/support/privacy/release materials are ready before the download is public.
- [ ] **GATE-13:** Desktop, Android, and iOS release candidates can each start offline, load/save
  progression, and complete the same demo content independently without connecting to one another.

# After the public demo

- [ ] **POST-01 (P1):** Triage incoming issues by severity and publish known workarounds.
- [ ] **POST-02 (P1):** Patch crashes, exploits, save corruption, progression blockers, and severe
  packaging/input failures first.
- [ ] **POST-03 (P1):** Review playtime, stage completion, crashes, performance, save reliability,
  and player feedback against PLAN-08.
- [ ] **POST-04 (P1):** Rebalance characters/weapons from observed outcomes and structured playtests.
- [ ] **POST-05 (P1):** Decide the Early Access/1.0 content plan only after demo findings are reviewed.
- [ ] **POST-06 (P1):** Keep protocol/data migration compatibility or communicate breaking versions.
- [ ] **POST-07 (P1):** Maintain a public changelog and reproducible archive for every release.
- [ ] **POST-08 (P2):** Consider additional maps, modes, characters, weapons, progression, cosmetics,
  localization, achievements, and other platforms after the core experience is stable.

---

# Roadmap change requests

Use stable task or decision IDs when requesting edits. New ideas should be placed directly in the
relevant milestone after approval rather than duplicated in a second summary.

- **Add:** Nothing pending.
- **Remove:** Nothing pending.
- **Change or clarify:** Nothing pending.

The decision register near the top of this file is the authoritative gameplay decision summary.
Narrative canon and unresolved story questions live in `STORY_BIBLE.md`.
