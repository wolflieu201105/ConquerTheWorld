package com.conquertheworld.game.gameplay.combat;

import com.conquertheworld.game.gameplay.character.Player;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttack;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttackFactory;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordType;
import com.conquertheworld.game.gameplay.enemy.BasicEnemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Owns active attacks and the offline damage step. */
public final class CombatSystem {
    public static final int LOCAL_PLAYER_ID = 0;
    public static final int ENEMY_ATTACK_OWNER_ID = -1;

    private final CombatDamageResolver damageResolver;
    private final Map<Integer, SwordAttack> attacks = new HashMap<>();
    private int nextLocalAttackId = -1;
    private int nextEnemyAttackId = 1;

    public CombatSystem() {
        this(new CombatDamageResolver());
    }

    CombatSystem(CombatDamageResolver damageResolver) {
        if (damageResolver == null) {
            throw new IllegalArgumentException("damageResolver cannot be null");
        }
        this.damageResolver = damageResolver;
    }

    public SwordAttack startPlayerAttack(Player player) {
        if (player == null || !player.hasEquippedWeapon()) {
            throw new IllegalArgumentException("An equipped player is required");
        }
        return addAttack(
            nextLocalAttackId--,
            LOCAL_PLAYER_ID,
            player.getGameX(),
            player.getGameY(),
            player.getAimAngleDegrees(),
            player.getEquippedSwordType()
        );
    }

    public SwordAttack startEnemyAttack(BasicEnemy enemy) {
        if (enemy == null) {
            throw new IllegalArgumentException("enemy cannot be null");
        }
        return addAttack(
            nextEnemyAttackId++,
            ENEMY_ATTACK_OWNER_ID,
            enemy.getGameX(),
            enemy.getGameY(),
            enemy.getAimAngleDegrees(),
            SwordType.QUICK
        );
    }

    public SwordAttack addAttack(
        int attackId,
        int ownerPlayerId,
        float x,
        float y,
        float aimAngleDegrees,
        SwordType swordType
    ) {
        SwordAttack attack = SwordAttackFactory.create(
            swordType,
            attackId,
            ownerPlayerId,
            x,
            y,
            aimAngleDegrees
        );
        attacks.put(attackId, attack);
        return attack;
    }

    /** Advances attacks, applies offline damage, and returns attacks that expired this step. */
    public List<SwordAttack> updateOffline(
        float delta,
        Player player,
        List<BasicEnemy> enemies
    ) {
        List<SwordAttack> expired = new ArrayList<>();
        Iterator<Map.Entry<Integer, SwordAttack>> iterator = attacks.entrySet().iterator();
        while (iterator.hasNext()) {
            SwordAttack attack = iterator.next().getValue();
            attack.update(delta);
            resolveDamage(attack, player, enemies);
            if (attack.isExpired()) {
                expired.add(attack);
                iterator.remove();
            }
        }
        return expired;
    }

    public void updateNetwork(float delta) {
        for (SwordAttack attack : attacks.values()) {
            attack.update(delta);
        }
    }

    public SwordAttack getAttack(int attackId) {
        return attacks.get(attackId);
    }

    public SwordAttack removeAttack(int attackId) {
        return attacks.remove(attackId);
    }

    public List<SwordAttack> removeAttacksExcept(Set<Integer> retainedIds) {
        if (retainedIds == null) {
            throw new IllegalArgumentException("retainedIds cannot be null");
        }
        List<SwordAttack> removed = new ArrayList<>();
        Iterator<Map.Entry<Integer, SwordAttack>> iterator = attacks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, SwordAttack> entry = iterator.next();
            if (!retainedIds.contains(entry.getKey())) {
                removed.add(entry.getValue());
                iterator.remove();
            }
        }
        return removed;
    }

    public Map<Integer, SwordAttack> getAttacks() {
        return Collections.unmodifiableMap(attacks);
    }

    public SwordAttack getActiveAttackForPlayer(int playerId) {
        for (SwordAttack attack : attacks.values()) {
            if (attack.getOwnerPlayerId() == playerId) {
                return attack;
            }
        }
        return null;
    }

    private void resolveDamage(
        SwordAttack attack,
        Player player,
        List<BasicEnemy> enemies
    ) {
        if (attack.getOwnerPlayerId() == ENEMY_ATTACK_OWNER_ID) {
            if (player != null) {
                damageResolver.resolve(attack, player);
            }
            return;
        }

        for (BasicEnemy enemy : enemies) {
            damageResolver.resolve(attack, enemy);
        }
    }
}
