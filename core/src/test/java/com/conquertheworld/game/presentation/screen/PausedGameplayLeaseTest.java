package com.conquertheworld.game.presentation.screen;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PausedGameplayLeaseTest {
    @Test
    void resumeReleasesGameplayWithoutDisposingIt() {
        Object gameplay = new Object();
        AtomicInteger disposeCount = new AtomicInteger();
        PausedGameplayLease<Object> lease = new PausedGameplayLease<>(
            gameplay,
            ignored -> disposeCount.incrementAndGet()
        );

        assertTrue(lease.ownsResource());
        assertSame(gameplay, lease.release());
        assertFalse(lease.ownsResource());
        lease.close();
        assertEquals(0, disposeCount.get());
    }

    @Test
    void restartOrTitleExitDisposesRetainedGameplayExactlyOnce() {
        AtomicInteger disposeCount = new AtomicInteger();
        PausedGameplayLease<Object> lease = new PausedGameplayLease<>(
            new Object(),
            ignored -> disposeCount.incrementAndGet()
        );

        lease.close();
        lease.close();

        assertEquals(1, disposeCount.get());
        assertFalse(lease.ownsResource());
        assertThrows(IllegalStateException.class, lease::get);
    }
}
