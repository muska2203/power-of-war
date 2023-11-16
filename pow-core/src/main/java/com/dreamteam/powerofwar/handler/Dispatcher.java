package com.dreamteam.powerofwar.handler;

/**
 * Handles and routes {@link HandleableType} objects to associated handlers.
 */
public interface Dispatcher<T extends HandleableType> {

    <A extends T> void dispatch(A action);
}
