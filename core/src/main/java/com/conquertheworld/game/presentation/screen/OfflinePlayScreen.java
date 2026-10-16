package com.conquertheworld.game.presentation.screen;

import com.conquertheworld.game.application.ConquerTheWorldGame;
import com.conquertheworld.game.presentation.menu.BasicMenuUi;

/** Lets the player begin or continue a solo offline game. */
public final class OfflinePlayScreen extends AbstractMenuScreen {
    private static final String TRAINING_YARD = "training_yard";
    private static final String PILLAR_HALL = "pillar_hall";
    private static final String DIVIDED_FOUNDRY = "divided_foundry";
    private static final String BACK = "back";

    public OfflinePlayScreen(ConquerTheWorldGame game) {
        super(
            game,
            new BasicMenuUi(
                "OFFLINE PLAY",
                "Choose a fighting room",
                "Authored layouts with fixed player and enemy spawns",
                "Esc: Back    Mouse / Arrow Keys + Enter",
                new BasicMenuUi.ButtonSpec(
                    TRAINING_YARD,
                    "TRAINING YARD",
                    "3 ENEMIES - OPEN COVER",
                    true
                ),
                new BasicMenuUi.ButtonSpec(
                    PILLAR_HALL,
                    "PILLAR HALL",
                    "4 ENEMIES - PILLARS",
                    true
                ),
                new BasicMenuUi.ButtonSpec(
                    DIVIDED_FOUNDRY,
                    "DIVIDED FOUNDRY",
                    "4 ENEMIES - DIVIDED COVER",
                    true
                ),
                new BasicMenuUi.ButtonSpec(BACK, "BACK", "", true)
            )
        );
    }

    @Override
    protected void onSelection(String selectedId) {
        switch (selectedId) {
            case TRAINING_YARD, PILLAR_HALL, DIVIDED_FOUNDRY ->
                game.startOfflineGame(selectedId);
            case BACK -> game.showTitleScreen();
            default -> {
                // Disabled and unknown choices do not trigger navigation.
            }
        }
    }

    @Override
    protected void onBack() {
        game.showTitleScreen();
    }
}
