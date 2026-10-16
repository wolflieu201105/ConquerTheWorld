package com.conquertheworld.game.presentation.rendering;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class GameplayCameraControllerTest {
    @Test
    void choosesLargestIntegerScaleThatFits() {
        assertEquals(2, GameplayCameraController.integerScale(1_280, 720));
        assertEquals(3, GameplayCameraController.integerScale(960, 768));
        assertEquals(1, GameplayCameraController.integerScale(200, 120));
    }

    @Test
    void clampsCameraInsideLargeWorldAndCentersSmallWorld() {
        assertEquals(160f, GameplayCameraController.clampAxis(-20f, 160f, 1_000f));
        assertEquals(840f, GameplayCameraController.clampAxis(980f, 160f, 1_000f));
        assertEquals(50f, GameplayCameraController.clampAxis(80f, 160f, 100f));
    }
}
