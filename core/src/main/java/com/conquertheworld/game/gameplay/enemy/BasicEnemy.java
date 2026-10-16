package com.conquertheworld.game.gameplay.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.gameplay.combat.Damageable;
import com.conquertheworld.game.gameplay.combat.Health;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordType;
import com.conquertheworld.game.gameplay.entity.Entity;
import com.conquertheworld.game.gameplay.entity.Hitbox;
import com.conquertheworld.game.gameplay.entity.Prop;

import java.util.Collections;

/** Temporary enemy body driven by {@link BasicEnemyBrain}. */
public final class BasicEnemy extends Entity implements Damageable {
    private static final float MOVE_SPEED = 55f;
    private static final float MAXIMUM_HEALTH = 60f;
    private static final float BODY_SIZE = 32f;
    private static final float COLLISION_INSET = 5f;

    private final BasicEnemyBrain brain = new BasicEnemyBrain();
    private final Texture bodyTexture;
    private final Health health = new Health(MAXIMUM_HEALTH);
    private float attackFlashSeconds;

    public BasicEnemy(float x, float y) {
        super(
            x,
            y,
            GameConstants.DEFAULT_GAME_Z,
            0f,
            0f,
            BODY_SIZE,
            BODY_SIZE,
            BODY_SIZE - COLLISION_INSET * 2f,
            BODY_SIZE - COLLISION_INSET * 2f,
            BODY_SIZE,
            BODY_SIZE,
            MOVE_SPEED,
            createBodyTexture(),
            true
        );
        bodyTexture = texture.getTexture();
        setHitboxes(Collections.singletonList(
            new Hitbox(
                COLLISION_INSET,
                COLLISION_INSET,
                BODY_SIZE - COLLISION_INSET * 2f,
                BODY_SIZE - COLLISION_INSET * 2f
            )
        ));
    }

    public void updateBehavior(float targetCenterX, float targetCenterY, float delta) {
        attackFlashSeconds = Math.max(0f, attackFlashSeconds - Math.max(0f, delta));
        brain.update(
            gameX + drawWidth * 0.5f,
            gameY + drawHeight * 0.5f,
            targetCenterX,
            targetCenterY,
            delta
        );
        setMovementVector(brain.getMoveX(), brain.getMoveY());
    }

    public boolean consumeAttackRequested() {
        boolean requested = brain.consumeAttackRequested();
        if (requested) {
            attackFlashSeconds = SwordType.QUICK.getDuration();
        }
        return requested;
    }

    public float getAimAngleDegrees() {
        return brain.getAimAngleDegrees();
    }

    @Override
    public Health getHealth() {
        return health;
    }

    public void stop(float delta) {
        attackFlashSeconds = Math.max(0f, attackFlashSeconds - Math.max(0f, delta));
        setMovementVector(0f, 0f);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(attackFlashSeconds > 0f ? Color.ORANGE : Color.WHITE);
        super.render(batch);
        batch.setColor(Color.WHITE);
    }

    @Override
    public boolean wallCollide(Prop wall) {
        return true;
    }

    public void dispose() {
        bodyTexture.dispose();
    }

    private static TextureRegion createBodyTexture() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.18f, 0.04f, 0.05f, 1f);
        pixmap.fillRectangle(2, 3, 12, 11);
        pixmap.setColor(0.62f, 0.12f, 0.12f, 1f);
        pixmap.fillRectangle(4, 1, 8, 3);
        pixmap.fillRectangle(0, 6, 3, 6);
        pixmap.fillRectangle(13, 6, 3, 6);
        pixmap.setColor(1f, 0.58f, 0.12f, 1f);
        pixmap.fillRectangle(4, 6, 3, 2);
        pixmap.fillRectangle(9, 6, 3, 2);
        pixmap.setColor(0.08f, 0.02f, 0.025f, 1f);
        pixmap.fillRectangle(5, 13, 2, 3);
        pixmap.fillRectangle(9, 13, 2, 3);

        Texture generatedTexture = new Texture(pixmap);
        generatedTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        pixmap.dispose();
        return new TextureRegion(generatedTexture);
    }
}
