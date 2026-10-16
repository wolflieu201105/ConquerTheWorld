package com.conquertheworld.game.application;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.conquertheworld.game.content.definition.GameDataRegistry;
import com.conquertheworld.game.gameplay.world.WorldLayout;
import com.conquertheworld.game.networking.config.NetworkConfig;
import com.conquertheworld.game.presentation.screen.GameplayScreen;
import com.conquertheworld.game.presentation.screen.OfflinePlayScreen;
import com.conquertheworld.game.presentation.screen.PauseScreen;
import com.conquertheworld.game.presentation.screen.TitleScreen;

import java.io.IOException;

/** Owns high-level screen navigation and nothing specific to one screen. */
public final class ConquerTheWorldGame extends Game {
    @Override
    public void create() {
        if (NetworkConfig.isClientMode()) {
            try {
                replaceScreen(new GameplayScreen(this, true));
            } catch (IOException exception) {
                throw new GdxRuntimeException("Could not start multiplayer", exception);
            }
            return;
        }

        showTitleScreen();
    }

    public void showTitleScreen() {
        replaceScreen(new TitleScreen(this));
    }

    public void showOfflinePlayScreen() {
        replaceScreen(new OfflinePlayScreen(this));
    }

    public void startOfflineGame() {
        startOfflineGame(WorldLayout.DEFAULT_ROOM_ID);
    }

    public void startOfflineGame(String fightingRoomId) {
        try {
            replaceScreen(new GameplayScreen(
                this,
                false,
                GameDataRegistry.get().getFightingRoom(fightingRoomId)
            ));
        } catch (IOException exception) {
            throw new GdxRuntimeException("Could not start offline game", exception);
        }
    }

    public void quit() {
        Gdx.app.exit();
    }

    public void showPauseScreen(GameplayScreen gameplayScreen) {
        if (gameplayScreen == null || getScreen() != gameplayScreen) {
            return;
        }
        setScreen(new PauseScreen(this, gameplayScreen));
    }

    public void resumeGameplay(GameplayScreen gameplayScreen) {
        Screen pauseScreen = getScreen();
        setScreen(gameplayScreen);
        if (pauseScreen != null) {
            pauseScreen.dispose();
        }
    }

    private void replaceScreen(Screen nextScreen) {
        Screen previousScreen = getScreen();
        setScreen(nextScreen);
        if (previousScreen != null) {
            previousScreen.dispose();
        }
    }

    @Override
    public void dispose() {
        Screen currentScreen = getScreen();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        super.dispose();
    }
}
