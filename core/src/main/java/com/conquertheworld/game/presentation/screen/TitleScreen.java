package com.conquertheworld.game.presentation.screen;

import com.conquertheworld.game.application.ConquerTheWorldGame;
import com.conquertheworld.game.presentation.menu.BasicMenuUi;

/** First screen shown in the normal offline application flow. */
public final class TitleScreen extends AbstractMenuScreen {
    private static final String PLAY = "play";
    private static final String QUIT = "quit";

    public TitleScreen(ConquerTheWorldGame game) {
        super(
            game,
            new BasicMenuUi(
                "CONQUER THE WORLD",
                "SCRAPBORN",
                "A machine rises from a forgotten world",
                "Mouse / Arrow Keys + Enter",
                new BasicMenuUi.ButtonSpec(PLAY, "PLAY", "OFFLINE", true),
                new BasicMenuUi.ButtonSpec("settings", "SETTINGS", "COMING LATER", false),
                new BasicMenuUi.ButtonSpec("credits", "CREDITS", "COMING LATER", false),
                new BasicMenuUi.ButtonSpec(QUIT, "QUIT", "", true)
            )
        );
    }

    @Override
    protected void onSelection(String selectedId) {
        switch (selectedId) {
            case PLAY -> game.showOfflinePlayScreen();
            case QUIT -> game.quit();
            default -> {
                // Disabled and unknown choices do not trigger navigation.
            }
        }
    }

    @Override
    protected void onBack() {
        game.quit();
    }
}
