package com.conquertheworld.game.presentation.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.conquertheworld.game.gameplay.character.Player;
import com.conquertheworld.game.gameplay.enemy.BasicEnemy;
import com.conquertheworld.game.gameplay.entity.RenderableObject;
import com.conquertheworld.game.gameplay.world.GameWorld;

import java.util.Map;

/** Owns screen-space gameplay status and world-space entity labels. */
public final class GameplayHudRenderer implements AutoCloseable {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final Vector3 labelPoint = new Vector3();
    private boolean closed;

    public GameplayHudRenderer() {
        font.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
    }

    public void resize(int windowHeight) {
        float fontScale = MathUtils.clamp(windowHeight / 720f, 1f, 1.75f);
        font.getData().setScale(fontScale);
    }

    public void render(
        GameWorld world,
        GameplayCameraController camera,
        boolean multiplayer,
        int localPlayerId,
        boolean connected,
        boolean host,
        boolean showHitboxes
    ) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera.getUiCamera().combined);
        batch.begin();

        if (multiplayer) {
            renderMultiplayerUi(world, camera, localPlayerId, connected, host);
        } else {
            renderOfflineUi(world, camera);
        }

        drawOutlined(
            (multiplayer ? "Esc Menu" : "Esc Pause")
                + " | F3 Hitboxes: " + (showHitboxes ? "ON" : "OFF"),
            camera.getScreenX() + 8f,
            camera.getScreenY() + 22f,
            false
        );
        batch.end();
    }

    private void renderOfflineUi(GameWorld world, GameplayCameraController camera) {
        Player localPlayer = world.getPlayer();
        if (localPlayer != null) {
            renderEntityLabel(
                camera,
                localPlayer,
                "YOU - " + localPlayer.getCharacterType().getDisplayName()
            );
            String healthStatus = localPlayer.isAlive()
                ? "HP: " + healthText(
                    localPlayer.getCurrentHealth(),
                    localPlayer.getMaximumHealth()
                )
                : "HP: 0 / " + Math.round(localPlayer.getMaximumHealth()) + " - DEFEATED";
            drawOutlined(
                healthStatus,
                camera.getScreenX() + 8f,
                camera.getScreenY() + camera.getScreenHeight() - 10f,
                false
            );
            String roomStatus = world.isFightingRoomCleared()
                ? world.getFightingRoom().getDisplayName() + " - ROOM CLEARED"
                : world.getFightingRoom().getDisplayName() + " - ENEMIES: "
                    + world.getEnemies().size();
            drawOutlined(
                roomStatus,
                camera.getScreenX() + 8f,
                camera.getScreenY() + camera.getScreenHeight() - 26f,
                false
            );
        }
        for (BasicEnemy enemy : world.getEnemies()) {
            renderEntityLabel(
                camera,
                enemy,
                "ENEMY HP " + healthText(enemy.getCurrentHealth(), enemy.getMaximumHealth())
            );
        }
    }

    private void renderMultiplayerUi(
        GameWorld world,
        GameplayCameraController camera,
        int localPlayerId,
        boolean connected,
        boolean host
    ) {
        for (Map.Entry<Integer, Player> entry : world.getNetworkPlayers().entrySet()) {
            Player visiblePlayer = entry.getValue();
            String label = entry.getKey() == localPlayerId
                ? "YOU (P" + entry.getKey() + ") - "
                    + visiblePlayer.getCharacterType().getDisplayName()
                : "P" + entry.getKey() + " - "
                    + visiblePlayer.getCharacterType().getDisplayName();
            renderEntityLabel(camera, visiblePlayer, label);
        }

        String role = host ? "HOST" : "JOIN";
        String status = connected
            ? role + " | You: P" + localPlayerId
                + " | Players: " + world.getNetworkPlayers().size()
            : role + " | Connecting...";
        drawOutlined(
            status,
            camera.getScreenX() + 8f,
            camera.getScreenY() + camera.getScreenHeight() - 10f,
            false
        );
    }

    private void renderEntityLabel(
        GameplayCameraController camera,
        RenderableObject object,
        String label
    ) {
        labelPoint.set(
            object.getRenderX() + object.getDrawWidth() * 0.5f,
            object.getRenderY() + object.getDrawHeight() + 7f,
            0f
        );
        camera.getWorldCamera().project(
            labelPoint,
            camera.getScreenX(),
            camera.getScreenY(),
            camera.getScreenWidth(),
            camera.getScreenHeight()
        );
        drawOutlined(label, labelPoint.x, labelPoint.y, true);
    }

    private static String healthText(float current, float maximum) {
        return Math.round(current) + " / " + Math.round(maximum);
    }

    private void drawOutlined(String text, float x, float y, boolean centered) {
        glyphLayout.setText(font, text);
        float drawX = centered ? Math.round(x - glyphLayout.width * 0.5f) : Math.round(x);
        float drawY = Math.round(y);
        font.setColor(Color.BLACK);
        font.draw(batch, text, drawX - 1f, drawY);
        font.draw(batch, text, drawX + 1f, drawY);
        font.draw(batch, text, drawX, drawY - 1f);
        font.draw(batch, text, drawX, drawY + 1f);
        font.setColor(Color.WHITE);
        font.draw(batch, text, drawX, drawY);
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        font.dispose();
        batch.dispose();
    }
}
