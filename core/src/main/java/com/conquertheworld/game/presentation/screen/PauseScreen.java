package com.conquertheworld.game.presentation.screen;

import com.conquertheworld.game.application.ConquerTheWorldGame;
import com.conquertheworld.game.presentation.menu.BasicMenuUi;

/** Retains a gameplay screen while offering resume and safe navigation actions. */
public final class PauseScreen extends AbstractMenuScreen {
    private static final String RESUME = "resume";
    private static final String RESTART = "restart";
    private static final String TITLE = "title";

    private final PausedGameplayLease<GameplayScreen> gameplayLease;

    public PauseScreen(ConquerTheWorldGame game, GameplayScreen pausedGameplay) {
        super(
            game,
            createMenu(pausedGameplay)
        );
        if (pausedGameplay == null) {
            throw new IllegalArgumentException("pausedGameplay cannot be null");
        }
        gameplayLease = new PausedGameplayLease<>(pausedGameplay, GameplayScreen::dispose);
    }

    private static BasicMenuUi createMenu(GameplayScreen gameplay) {
        if (gameplay == null) {
            throw new IllegalArgumentException("gameplay cannot be null");
        }
        boolean multiplayer = gameplay.isMultiplayer();
        return new BasicMenuUi(
            multiplayer ? "GAME MENU" : "GAME PAUSED",
            gameplay.getFightingRoom().getDisplayName(),
            multiplayer
                ? "The online session continues while this menu is open."
                : "Combat and room simulation are frozen.",
            "Esc: Resume    Mouse / Arrow Keys + Enter",
            new BasicMenuUi.ButtonSpec(RESUME, "RESUME", "", true),
            new BasicMenuUi.ButtonSpec(
                RESTART,
                "RESTART ROOM",
                multiplayer ? "OFFLINE ONLY" : "LOSE CURRENT ROOM PROGRESS",
                !multiplayer
            ),
            new BasicMenuUi.ButtonSpec(TITLE, "TITLE SCREEN", "LEAVE CURRENT GAME", true)
        );
    }

    @Override
    protected void onSelection(String selectedId) {
        switch (selectedId) {
            case RESUME -> resumeGameplay();
            case RESTART -> {
                GameplayScreen gameplay = gameplayLease.get();
                if (!gameplay.isMultiplayer()) {
                    game.startOfflineGame(gameplay.getFightingRoom().getId());
                }
            }
            case TITLE -> game.showTitleScreen();
            default -> {
                // Disabled and unknown choices do not trigger navigation.
            }
        }
    }

    @Override
    protected void onBack() {
        resumeGameplay();
    }

    private void resumeGameplay() {
        game.resumeGameplay(gameplayLease.release());
    }

    @Override
    protected void onDispose() {
        gameplayLease.close();
    }
}
