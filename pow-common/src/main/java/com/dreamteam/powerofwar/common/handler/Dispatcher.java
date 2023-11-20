package com.dreamteam.powerofwar.common.handler;

/**
 * Handles and routes {@link HandleableType} objects to associated handlers.
 */
public interface Dispatcher<T extends HandleableType> {

    <A extends T> void dispatch(A action);
}
