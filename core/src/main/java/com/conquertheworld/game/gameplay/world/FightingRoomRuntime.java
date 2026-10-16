package com.conquertheworld.game.gameplay.world;

import com.conquertheworld.game.content.definition.FightingRoomDefinition;
import com.conquertheworld.game.gameplay.enemy.BasicEnemy;
import com.conquertheworld.game.gameplay.entity.Entity;
import com.conquertheworld.game.gameplay.entity.HitboxCollision;
import com.conquertheworld.game.gameplay.entity.Prop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Mutable room-local props, enemies, collision, and clear state. */
public final class FightingRoomRuntime {
    private final FightingRoomDefinition definition;
    private final List<Prop> props = new ArrayList<>();
    private final List<BasicEnemy> enemies = new ArrayList<>();

    public FightingRoomRuntime(FightingRoomDefinition definition) {
        if (definition == null) {
            throw new IllegalArgumentException("definition cannot be null");
        }
        this.definition = definition;
    }

    void addProp(Prop prop) {
        if (prop == null) {
            throw new IllegalArgumentException("prop cannot be null");
        }
        props.add(prop);
    }

    void addEnemy(BasicEnemy enemy) {
        if (enemy == null) {
            throw new IllegalArgumentException("enemy cannot be null");
        }
        enemies.add(enemy);
    }

    public Prop findCollidingWall(Entity entity) {
        if (entity == null || !entity.isSolid()) {
            return null;
        }
        for (Prop prop : props) {
            if (prop.isSolid() && HitboxCollision.overlaps(entity, prop)) {
                return prop;
            }
        }
        return null;
    }

    /** Removes and returns enemies whose health reached zero. */
    public List<BasicEnemy> removeDefeatedEnemies() {
        List<BasicEnemy> defeated = new ArrayList<>();
        Iterator<BasicEnemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            BasicEnemy enemy = iterator.next();
            if (!enemy.isAlive()) {
                defeated.add(enemy);
                iterator.remove();
            }
        }
        return defeated;
    }

    public FightingRoomDefinition getDefinition() {
        return definition;
    }

    public List<Prop> getProps() {
        return Collections.unmodifiableList(props);
    }

    public List<BasicEnemy> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    public boolean isCleared() {
        return enemies.isEmpty();
    }

    public float getWorldWidth() {
        return definition.getWorldWidth();
    }

    public float getWorldHeight() {
        return definition.getWorldHeight();
    }
}
