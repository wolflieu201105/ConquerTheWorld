package com.conquertheworld.game.presentation.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.conquertheworld.game.gameplay.character.Player;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordAttack;
import com.conquertheworld.game.gameplay.combat.weapon.sword.SwordType;
import com.conquertheworld.game.gameplay.entity.Hitbox;
import com.conquertheworld.game.gameplay.entity.Prop;
import com.conquertheworld.game.gameplay.world.GameWorld;
import com.conquertheworld.game.networking.config.NetworkConfig;

import java.util.List;
import java.util.Map;

/** Owns the optional gameplay collision and attack visualization. */
public final class GameplayDebugRenderer implements AutoCloseable {
    private final ShapeRenderer renderer = new ShapeRenderer();
    private boolean showHitboxes = NetworkConfig.shouldShowHitboxes();
    private boolean closed;

    public void toggleHitboxes() {
        showHitboxes = !showHitboxes;
        Gdx.app.log("Debug", "Hitbox overlay " + (showHitboxes ? "enabled" : "disabled"));
    }

    public boolean isShowingHitboxes() {
        return showHitboxes;
    }

    public void render(
        GameWorld world,
        GameplayCameraController camera,
        boolean multiplayer,
        int localPlayerId
    ) {
        if (!showHitboxes) {
            return;
        }

        renderer.setProjectionMatrix(camera.getWorldCamera().combined);
        Gdx.gl.glLineWidth(2f);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        for (Prop prop : world.getProps()) {
            if (prop.isSolid()) {
                renderer.setColor(Color.MAGENTA);
                renderer.rect(
                    prop.getGameX(),
                    prop.getGameY(),
                    prop.getCollisionWidth(),
                    prop.getCollisionDepth()
                );
            }
        }

        if (!multiplayer) {
            renderPlayerHitboxes(world.getPlayer(), Color.GREEN);
        } else {
            for (Map.Entry<Integer, Player> entry : world.getNetworkPlayers().entrySet()) {
                renderPlayerHitboxes(
                    entry.getValue(),
                    entry.getKey() == localPlayerId ? Color.GREEN : Color.YELLOW
                );
            }
        }

        for (SwordAttack attack : world.getAttacks().values()) {
            boolean localAttack = attack.getOwnerPlayerId() == localPlayerId;
            if (attack.getSwordType() == SwordType.HEAVY) {
                renderer.setColor(localAttack ? Color.BLUE : Color.RED);
            } else {
                renderer.setColor(localAttack ? Color.CYAN : Color.ORANGE);
            }
            renderHitboxes(attack.getGameX(), attack.getGameY(), attack.getHitboxes());
        }
        renderer.end();
        Gdx.gl.glLineWidth(1f);
    }

    private void renderPlayerHitboxes(Player player, Color color) {
        if (player != null) {
            renderer.setColor(color);
            renderHitboxes(player.getGameX(), player.getGameY(), player.getHitboxes());
        }
    }

    private void renderHitboxes(float entityX, float entityY, List<Hitbox> hitboxes) {
        for (Hitbox hitbox : hitboxes) {
            renderer.rect(
                entityX + hitbox.getXOffset(),
                entityY + hitbox.getYOffset(),
                0f,
                0f,
                hitbox.getWidth(),
                hitbox.getHeight(),
                1f,
                1f,
                hitbox.getRotationDegrees()
            );
        }
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        renderer.dispose();
    }
}
