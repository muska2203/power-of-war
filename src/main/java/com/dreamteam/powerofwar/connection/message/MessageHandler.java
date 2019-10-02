package com.dreamteam.powerofwar.connection.message;

/**
 * Defines a class which handles a message of type {@code A}.
 *
 * @param <A> message type.
 */
public interface MessageHandler<A extends Message> {

    /**
     * Handles a message that was received.
     *
     * @param message the message that was received.
     */
    void handle(A message);
}
