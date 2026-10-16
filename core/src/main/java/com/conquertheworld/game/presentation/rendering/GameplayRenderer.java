package com.conquertheworld.game.presentation.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.gameplay.character.Player;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttack;
import com.conquertheworld.game.gameplay.entity.RenderableObject;
import com.conquertheworld.game.gameplay.world.GameWorld;

/** Owns world and weapon drawing; HUD and debug overlays have dedicated renderers. */
public final class GameplayRenderer implements AutoCloseable {
    private final SpriteBatch batch = new SpriteBatch();
    private final WeaponRenderer weaponRenderer = new WeaponRenderer();
    private boolean closed;

    public void render(GameWorld world, GameplayCameraController camera) {
        Gdx.gl.glClearColor(
            GameConstants.CLEAR_R,
            GameConstants.CLEAR_G,
            GameConstants.CLEAR_B,
            GameConstants.CLEAR_A
        );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(
            camera.getScreenX(),
            camera.getScreenY(),
            camera.getScreenWidth(),
            camera.getScreenHeight()
        );

        batch.setProjectionMatrix(camera.getWorldCamera().combined);
        batch.begin();
        for (RenderableObject object : world.getRenderables()) {
            if (object instanceof Player) {
                renderPlayerWithWeapon(world, (Player) object);
            } else {
                object.render(batch);
            }
        }
        batch.end();
    }

    private void renderPlayerWithWeapon(GameWorld world, Player visiblePlayer) {
        SwordAttack activeAttack = world.getActiveAttackForPlayer(visiblePlayer);
        boolean renderWeapon = visiblePlayer.hasEquippedWeapon();
        boolean weaponFirst = visiblePlayer.rendersWeaponBeforePlayer();

        if (renderWeapon && weaponFirst) {
            weaponRenderer.render(batch, visiblePlayer, activeAttack);
        }
        visiblePlayer.render(batch);
        if (renderWeapon && !weaponFirst) {
            weaponRenderer.render(batch, visiblePlayer, activeAttack);
        }
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        batch.dispose();
        weaponRenderer.dispose();
    }
}
