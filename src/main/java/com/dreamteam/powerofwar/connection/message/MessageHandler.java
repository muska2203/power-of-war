package com.dreamteam.powerofwar.connection.message;

/**
 * Defines a class which handles a message of type {@code A}.
 *
 * @param <T> message type.
 */
public interface MessageHandler<T extends Message> extends GenericHandler<T> {

    /**
     * Handles a message that was received.
     *
     * @param message the message that was received.
     */
    void handle(T message);
}
