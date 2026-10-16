package com.conquertheworld.game.presentation.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.conquertheworld.game.application.ConquerTheWorldGame;
import com.conquertheworld.game.application.session.GameplaySession;
import com.conquertheworld.game.content.definition.FightingRoomDefinition;
import com.conquertheworld.game.gameplay.world.GameWorld;
import com.conquertheworld.game.presentation.input.GameplayInputController;
import com.conquertheworld.game.presentation.rendering.GameplayCameraController;
import com.conquertheworld.game.presentation.rendering.GameplayDebugRenderer;
import com.conquertheworld.game.presentation.rendering.GameplayHudRenderer;
import com.conquertheworld.game.presentation.rendering.GameplayRenderer;

import java.io.IOException;

/** Coordinates one gameplay session without owning its subsystem implementations. */
public final class GameplayScreen extends ScreenAdapter {
    private final ConquerTheWorldGame game;
    private final GameplayCameraController camera = new GameplayCameraController();
    private final GameplayInputController input = new GameplayInputController();

    private GameplaySession session;
    private GameplayRenderer worldRenderer;
    private GameplayHudRenderer hudRenderer;
    private GameplayDebugRenderer debugRenderer;
    private boolean disposed;

    public GameplayScreen(ConquerTheWorldGame game, boolean multiplayer) throws IOException {
        this(game, multiplayer, null);
    }

    public GameplayScreen(
        ConquerTheWorldGame game,
        boolean multiplayer,
        FightingRoomDefinition fightingRoom
    ) throws IOException {
        if (game == null) {
            throw new IllegalArgumentException("game cannot be null");
        }
        this.game = game;

        try {
            worldRenderer = new GameplayRenderer();
            hudRenderer = new GameplayHudRenderer();
            debugRenderer = new GameplayDebugRenderer();
            session = new GameplaySession(multiplayer, fightingRoom);
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } catch (IOException | RuntimeException exception) {
            dispose();
            throw exception;
        }
    }

    @Override
    public void render(float delta) {
        if (input.isPauseRequested()) {
            game.showPauseScreen(this);
            return;
        }
        if (input.isHitboxToggleRequested()) {
            debugRenderer.toggleHitboxes();
        }

        GameWorld world = session.getWorld();
        followPlayer(world);
        camera.update();
        input.updateAim(world, camera);
        world.update(delta);
        followPlayer(world);
        camera.update();

        worldRenderer.render(world, camera);
        debugRenderer.render(
            world,
            camera,
            session.isMultiplayer(),
            session.getLocalPlayerId()
        );
        hudRenderer.render(
            world,
            camera,
            session.isMultiplayer(),
            session.getLocalPlayerId(),
            session.isConnected(),
            session.isHost(),
            debugRenderer.isShowingHitboxes()
        );
    }

    private void followPlayer(GameWorld world) {
        camera.follow(world.getPlayer(), world.getWorldWidth(), world.getWorldHeight());
    }

    @Override
    public void resize(int width, int height) {
        if (session == null || hudRenderer == null) {
            return;
        }
        GameWorld world = session.getWorld();
        camera.resize(
            width,
            height,
            world.getPlayer(),
            world.getWorldWidth(),
            world.getWorldHeight()
        );
        hudRenderer.resize(height);
    }

    public FightingRoomDefinition getFightingRoom() {
        return session.getWorld().getFightingRoom();
    }

    public boolean isMultiplayer() {
        return session.isMultiplayer();
    }

    @Override
    public void dispose() {
        if (disposed) {
            return;
        }
        disposed = true;

        if (session != null) {
            session.close();
            session = null;
        }
        if (worldRenderer != null) {
            worldRenderer.close();
            worldRenderer = null;
        }
        if (hudRenderer != null) {
            hudRenderer.close();
            hudRenderer = null;
        }
        if (debugRenderer != null) {
            debugRenderer.close();
            debugRenderer = null;
        }
    }
}
