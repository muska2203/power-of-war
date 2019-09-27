package com.dreamteam.powerofwar.client.action;

/**
 * Handles and routes {@link Action} objects to associated handlers.
 */
public interface ActionDispatcher {

    <A extends Action> void dispatch(A action);
}
