package com.conquertheworld.game.presentation.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.conquertheworld.game.gameplay.world.GameWorld;
import com.conquertheworld.game.presentation.rendering.GameplayCameraController;

/** Converts gameplay keys and the pointer into screen-level commands and world aim. */
public final class GameplayInputController {
    private final Vector3 pointerWorld = new Vector3();

    public boolean isPauseRequested() {
        return Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);
    }

    public boolean isHitboxToggleRequested() {
        return Gdx.input.isKeyJustPressed(Input.Keys.F3);
    }

    public void updateAim(GameWorld world, GameplayCameraController camera) {
        if (world == null || camera == null) {
            return;
        }
        camera.unprojectPointer(
            Gdx.input.getX(),
            Gdx.input.getY(),
            Gdx.graphics.getHeight(),
            pointerWorld
        );
        world.setLocalAimTarget(pointerWorld.x, pointerWorld.y);
    }
}
