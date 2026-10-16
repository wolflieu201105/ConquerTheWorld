package com.conquertheworld.game.presentation.screen;

import java.util.function.Consumer;

/** Owns a retained gameplay resource until it is resumed or the pause flow exits. */
final class PausedGameplayLease<T> implements AutoCloseable {
    private T retained;
    private final Consumer<T> disposer;

    PausedGameplayLease(T retained, Consumer<T> disposer) {
        if (retained == null || disposer == null) {
            throw new IllegalArgumentException("retained resource and disposer are required");
        }
        this.retained = retained;
        this.disposer = disposer;
    }

    T get() {
        if (retained == null) {
            throw new IllegalStateException("Paused gameplay has already been released or disposed");
        }
        return retained;
    }

    T release() {
        T result = get();
        retained = null;
        return result;
    }

    boolean ownsResource() {
        return retained != null;
    }

    @Override
    public void close() {
        if (retained != null) {
            T owned = retained;
            retained = null;
            disposer.accept(owned);
        }
    }
}
