package com.conquertheworld.game.presentation.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Lightweight menu UI used before the full visual style and UI asset set exist.
 * It deliberately uses only generated shapes and libGDX's default font.
 */
public final class BasicMenuUi implements AutoCloseable {
    public enum Page {
        TITLE,
        OFFLINE_PLAY
    }

    public enum Action {
        NONE,
        OPEN_OFFLINE_PLAY,
        START_NEW_GAME,
        BACK_TO_TITLE,
        QUIT
    }

    private static final Color BACKGROUND = new Color(0.025f, 0.035f, 0.055f, 1f);
    private static final Color BACKGROUND_BAND = new Color(0.045f, 0.065f, 0.09f, 1f);
    private static final Color PANEL = new Color(0.055f, 0.075f, 0.105f, 0.97f);
    private static final Color BUTTON = new Color(0.095f, 0.13f, 0.17f, 1f);
    private static final Color BUTTON_SELECTED = new Color(0.14f, 0.29f, 0.31f, 1f);
    private static final Color BUTTON_DISABLED = new Color(0.07f, 0.085f, 0.105f, 1f);
    private static final Color ACCENT = new Color(0.28f, 0.86f, 0.72f, 1f);
    private static final Color TITLE = new Color(0.9f, 0.96f, 0.95f, 1f);
    private static final Color TEXT = new Color(0.88f, 0.91f, 0.92f, 1f);
    private static final Color MUTED = new Color(0.48f, 0.55f, 0.59f, 1f);

    private static final ButtonSpec[] TITLE_BUTTONS = {
        new ButtonSpec("PLAY", "OFFLINE", true, Action.OPEN_OFFLINE_PLAY),
        new ButtonSpec("SETTINGS", "COMING LATER", false, Action.NONE),
        new ButtonSpec("CREDITS", "COMING LATER", false, Action.NONE),
        new ButtonSpec("QUIT", "", true, Action.QUIT)
    };

    private static final ButtonSpec[] OFFLINE_BUTTONS = {
        new ButtonSpec("NEW GAME", "START THE CURRENT PROTOTYPE", true, Action.START_NEW_GAME),
        new ButtonSpec("CONTINUE", "NO SAVE FOUND", false, Action.NONE),
        new ButtonSpec("BACK", "", true, Action.BACK_TO_TITLE)
    };

    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont titleFont = createFont();
    private final BitmapFont bodyFont = createFont();
    private final GlyphLayout glyphLayout = new GlyphLayout();

    private Page page = Page.TITLE;
    private Rectangle[] buttonBounds = new Rectangle[0];
    private int selectedIndex;
    private int width;
    private int height;
    private int lastMouseX = Integer.MIN_VALUE;
    private int lastMouseY = Integer.MIN_VALUE;

    public void show(Page nextPage) {
        page = nextPage;
        selectedIndex = firstEnabledIndex(buttons());
        lastMouseX = Integer.MIN_VALUE;
        lastMouseY = Integer.MIN_VALUE;
        rebuildLayout();
    }

    public void resize(int width, int height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        rebuildLayout();
    }

    public Action update() {
        ButtonSpec[] buttons = buttons();
        updateMouseSelection(buttons);

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)
            || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedIndex = nextEnabledIndex(buttons, selectedIndex, -1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)
            || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedIndex = nextEnabledIndex(buttons, selectedIndex, 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            return page == Page.OFFLINE_PLAY ? Action.BACK_TO_TITLE : Action.QUIT;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
            || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            return selectedAction(buttons);
        }

        if (Gdx.input.justTouched()) {
            int clicked = buttonAt(Gdx.input.getX(), height - Gdx.input.getY());
            if (clicked >= 0 && buttons[clicked].enabled()) {
                selectedIndex = clicked;
                return buttons[clicked].action();
            }
        }

        return Action.NONE;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        ButtonSpec[] buttons = buttons();

        shapes.setProjectionMatrix(camera.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(BACKGROUND);
        shapes.rect(0f, 0f, width, height);
        shapes.setColor(BACKGROUND_BAND);
        shapes.rect(0f, height * 0.72f, width, height * 0.28f);
        shapes.setColor(ACCENT);
        shapes.rect(0f, height * 0.715f, width, Math.max(2f, height * 0.005f));

        if (buttonBounds.length > 0) {
            Rectangle first = buttonBounds[0];
            Rectangle last = buttonBounds[buttonBounds.length - 1];
            float panelPadding = MathUtils.clamp(width * 0.03f, 18f, 32f);
            shapes.setColor(PANEL);
            shapes.rect(
                first.x - panelPadding,
                last.y - panelPadding,
                first.width + panelPadding * 2f,
                first.y + first.height - last.y + panelPadding * 2f
            );
        }

        for (int index = 0; index < buttons.length; index++) {
            ButtonSpec button = buttons[index];
            Rectangle bounds = buttonBounds[index];
            if (!button.enabled()) {
                shapes.setColor(BUTTON_DISABLED);
            } else if (index == selectedIndex) {
                shapes.setColor(BUTTON_SELECTED);
            } else {
                shapes.setColor(BUTTON);
            }
            shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);

            if (button.enabled() && index == selectedIndex) {
                shapes.setColor(ACCENT);
                shapes.rect(bounds.x, bounds.y, Math.max(4f, width * 0.006f), bounds.height);
            }
        }
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        for (int index = 0; index < buttons.length; index++) {
            ButtonSpec button = buttons[index];
            if (button.enabled() && index == selectedIndex) {
                shapes.setColor(ACCENT);
            } else {
                shapes.setColor(button.enabled() ? MUTED : BUTTON);
            }
            Rectangle bounds = buttonBounds[index];
            shapes.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
        shapes.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float titleScale = MathUtils.clamp(height / 270f, 1.45f, 2.8f);
        float bodyScale = MathUtils.clamp(height / 600f, 0.9f, 1.35f);
        float smallScale = bodyScale * 0.78f;

        if (page == Page.TITLE) {
            drawCentered(batch, titleFont, "CONQUER THE WORLD", width * 0.5f, height - 62f,
                titleScale, TITLE);
            drawCentered(batch, bodyFont, "SCRAPBORN", width * 0.5f, height - 98f,
                bodyScale * 1.2f, ACCENT);
            drawCentered(batch, bodyFont, "A machine rises from a forgotten world",
                width * 0.5f, height - 124f, smallScale, MUTED);
        } else {
            drawCentered(batch, titleFont, "OFFLINE PLAY", width * 0.5f, height - 70f,
                titleScale, TITLE);
            drawCentered(batch, bodyFont, "Prepare alone. Bring back what survives.",
                width * 0.5f, height - 106f, bodyScale, ACCENT);
        }

        for (int index = 0; index < buttons.length; index++) {
            ButtonSpec button = buttons[index];
            Rectangle bounds = buttonBounds[index];
            Color labelColor = button.enabled() ? TEXT : MUTED;
            float labelX = bounds.x + 24f;
            float labelY = bounds.y + bounds.height * 0.5f + 5f * bodyScale;
            drawLeft(batch, bodyFont, button.label(), labelX, labelY, bodyScale, labelColor);

            if (!button.hint().isEmpty()) {
                drawRight(batch, bodyFont, button.hint(), bounds.x + bounds.width - 18f,
                    labelY, smallScale, button.enabled() ? ACCENT : MUTED);
            }
        }

        String footer = page == Page.TITLE
            ? "Mouse / Arrow Keys + Enter"
            : "Esc: Back    Mouse / Arrow Keys + Enter";
        drawCentered(batch, bodyFont, footer, width * 0.5f, 28f, smallScale, MUTED);
        batch.end();
    }

    private void updateMouseSelection(ButtonSpec[] buttons) {
        int mouseX = Gdx.input.getX();
        int mouseY = height - Gdx.input.getY();
        if (mouseX == lastMouseX && mouseY == lastMouseY) {
            return;
        }
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        int hovered = buttonAt(mouseX, mouseY);
        if (hovered >= 0 && buttons[hovered].enabled()) {
            selectedIndex = hovered;
        }
    }

    private int buttonAt(float x, float y) {
        for (int index = 0; index < buttonBounds.length; index++) {
            if (buttonBounds[index].contains(x, y)) {
                return index;
            }
        }
        return -1;
    }

    private Action selectedAction(ButtonSpec[] buttons) {
        if (selectedIndex < 0 || selectedIndex >= buttons.length) {
            return Action.NONE;
        }
        ButtonSpec selected = buttons[selectedIndex];
        return selected.enabled() ? selected.action() : Action.NONE;
    }

    private void rebuildLayout() {
        ButtonSpec[] buttons = buttons();
        buttonBounds = new Rectangle[buttons.length];
        if (width <= 0 || height <= 0) {
            return;
        }

        float buttonWidth = MathUtils.clamp(width * 0.56f, 280f, 420f);
        buttonWidth = Math.min(buttonWidth, width - 48f);
        float buttonHeight = MathUtils.clamp(height * 0.09f, 42f, 58f);
        float gap = MathUtils.clamp(height * 0.022f, 8f, 14f);
        float groupHeight = buttons.length * buttonHeight + (buttons.length - 1) * gap;
        float groupCenterY = height * 0.41f;
        float top = groupCenterY + groupHeight * 0.5f;
        float x = (width - buttonWidth) * 0.5f;

        for (int index = 0; index < buttons.length; index++) {
            float y = top - buttonHeight - index * (buttonHeight + gap);
            buttonBounds[index] = new Rectangle(x, y, buttonWidth, buttonHeight);
        }
    }

    private ButtonSpec[] buttons() {
        return page == Page.TITLE ? TITLE_BUTTONS : OFFLINE_BUTTONS;
    }

    private static int firstEnabledIndex(ButtonSpec[] buttons) {
        for (int index = 0; index < buttons.length; index++) {
            if (buttons[index].enabled()) {
                return index;
            }
        }
        return -1;
    }

    private static int nextEnabledIndex(ButtonSpec[] buttons, int current, int direction) {
        if (buttons.length == 0) {
            return -1;
        }
        int candidate = current;
        for (int attempt = 0; attempt < buttons.length; attempt++) {
            candidate = Math.floorMod(candidate + direction, buttons.length);
            if (buttons[candidate].enabled()) {
                return candidate;
            }
        }
        return current;
    }

    private static BitmapFont createFont() {
        BitmapFont font = new BitmapFont();
        font.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
        font.setUseIntegerPositions(true);
        return font;
    }

    private void drawCentered(
        SpriteBatch batch,
        BitmapFont font,
        String text,
        float centerX,
        float baselineY,
        float scale,
        Color color
    ) {
        prepareFont(font, scale, color);
        glyphLayout.setText(font, text);
        font.draw(batch, text, Math.round(centerX - glyphLayout.width * 0.5f), Math.round(baselineY));
    }

    private void drawLeft(
        SpriteBatch batch,
        BitmapFont font,
        String text,
        float x,
        float baselineY,
        float scale,
        Color color
    ) {
        prepareFont(font, scale, color);
        font.draw(batch, text, Math.round(x), Math.round(baselineY));
    }

    private void drawRight(
        SpriteBatch batch,
        BitmapFont font,
        String text,
        float rightX,
        float baselineY,
        float scale,
        Color color
    ) {
        prepareFont(font, scale, color);
        glyphLayout.setText(font, text);
        font.draw(batch, text, Math.round(rightX - glyphLayout.width), Math.round(baselineY));
    }

    private static void prepareFont(BitmapFont font, float scale, Color color) {
        font.getData().setScale(scale);
        font.setColor(color);
    }

    @Override
    public void close() {
        shapes.dispose();
        titleFont.dispose();
        bodyFont.dispose();
    }

    private record ButtonSpec(String label, String hint, boolean enabled, Action action) {
    }
}
