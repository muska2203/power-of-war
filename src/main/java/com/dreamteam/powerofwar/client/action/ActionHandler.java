package com.dreamteam.powerofwar.client.action;

/**
 * Represents the interface to implement a handler for a specific action type.
 *
 * @param <A> An {@link Action} class the handler implementation should process.
 */
public interface ActionHandler<A extends Action> {

    /**
     * Handles the specified Action object.
     *
     * <p>
     * Implementations should contain all required business logic related to the action type.
     */
    void onAction(A action);
}
