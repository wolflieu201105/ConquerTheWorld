package com.conquertheworld.game.presentation.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.conquertheworld.game.application.ConquerTheWorldGame;
import com.conquertheworld.game.presentation.menu.BasicMenuUi;

/** Shared lifecycle and rendering shell for the temporary menu screens. */
abstract class AbstractMenuScreen extends ScreenAdapter {
    protected final ConquerTheWorldGame game;

    private final SpriteBatch batch = new SpriteBatch();
    private final OrthographicCamera camera = new OrthographicCamera();
    private final BasicMenuUi menuUi;

    protected AbstractMenuScreen(ConquerTheWorldGame game, BasicMenuUi menuUi) {
        this.game = game;
        this.menuUi = menuUi;
    }

    @Override
    public final void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            onBack();
            return;
        }

        String selectedId = menuUi.update();
        if (selectedId != null) {
            onSelection(selectedId);
            if (game.getScreen() != this) {
                return;
            }
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0.025f, 0.035f, 0.055f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        menuUi.render(batch, camera);
    }

    protected abstract void onSelection(String selectedId);

    protected abstract void onBack();

    /** Lets specialized menu screens release resources they own beyond the shared menu UI. */
    protected void onDispose() {
    }

    @Override
    public final void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        menuUi.resize(width, height);
    }

    @Override
    public final void dispose() {
        onDispose();
        menuUi.close();
        batch.dispose();
    }
}
