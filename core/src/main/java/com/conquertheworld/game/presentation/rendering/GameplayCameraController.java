package com.conquertheworld.game.presentation.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.conquertheworld.game.config.GameConstants;
import com.conquertheworld.game.gameplay.character.Player;

/** Owns the gameplay camera, UI camera, and integer-scaled viewport. */
public final class GameplayCameraController {
    private final OrthographicCamera worldCamera = new OrthographicCamera();
    private final OrthographicCamera uiCamera = new OrthographicCamera();

    private int screenX;
    private int screenY;
    private int screenWidth;
    private int screenHeight;

    public GameplayCameraController() {
        worldCamera.setToOrtho(
            false,
            GameConstants.CAMERA_VIEW_WIDTH,
            GameConstants.CAMERA_VIEW_HEIGHT
        );
    }

    public void resize(
        int width,
        int height,
        Player target,
        float worldWidth,
        float worldHeight
    ) {
        int logicalWidth = Math.round(GameConstants.CAMERA_VIEW_WIDTH);
        int logicalHeight = Math.round(GameConstants.CAMERA_VIEW_HEIGHT);
        int scale = integerScale(width, height);

        screenWidth = logicalWidth * scale;
        screenHeight = logicalHeight * scale;
        screenX = (width - screenWidth) / 2;
        screenY = (height - screenHeight) / 2;

        worldCamera.setToOrtho(
            false,
            GameConstants.CAMERA_VIEW_WIDTH,
            GameConstants.CAMERA_VIEW_HEIGHT
        );
        uiCamera.setToOrtho(false, width, height);
        follow(target, worldWidth, worldHeight);
        update();
    }

    public void follow(Player target, float worldWidth, float worldHeight) {
        float targetX = target == null
            ? worldWidth * 0.5f
            : target.getGameX() + target.getDrawWidth() * 0.5f;
        float targetY = target == null
            ? worldHeight * 0.5f
            : target.getGameY() + target.getDrawHeight() * 0.5f;

        float halfWidth = worldCamera.viewportWidth * worldCamera.zoom * 0.5f;
        float halfHeight = worldCamera.viewportHeight * worldCamera.zoom * 0.5f;
        worldCamera.position.set(
            clampAxis(targetX, halfWidth, worldWidth),
            clampAxis(targetY, halfHeight, worldHeight),
            0f
        );
    }

    public void update() {
        worldCamera.update();
        uiCamera.update();
    }

    public Vector3 unprojectPointer(
        int pointerX,
        int pointerY,
        int windowHeight,
        Vector3 destination
    ) {
        int clampedX = MathUtils.clamp(pointerX, screenX, screenX + screenWidth - 1);
        int minimumY = windowHeight - screenY - screenHeight;
        int maximumY = windowHeight - screenY - 1;
        int clampedY = MathUtils.clamp(pointerY, minimumY, maximumY);
        destination.set(clampedX, clampedY, 0f);
        return worldCamera.unproject(
            destination,
            screenX,
            screenY,
            screenWidth,
            screenHeight
        );
    }

    static float clampAxis(float target, float halfView, float worldSize) {
        if (halfView * 2f >= worldSize) {
            return worldSize * 0.5f;
        }
        return MathUtils.clamp(target, halfView, worldSize - halfView);
    }

    static int integerScale(int width, int height) {
        int logicalWidth = Math.round(GameConstants.CAMERA_VIEW_WIDTH);
        int logicalHeight = Math.round(GameConstants.CAMERA_VIEW_HEIGHT);
        return Math.max(1, Math.min(width / logicalWidth, height / logicalHeight));
    }

    public OrthographicCamera getWorldCamera() {
        return worldCamera;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
