package com.dreamteam.powerofwar.connection.exception;

/**
 * Is thrown when an opened channel is required for any operation, but it was closed.
 */
public class ChannelClosedException extends Exception {

    public ChannelClosedException() {
        super();
    }

    public ChannelClosedException(String message) {
        super(message);
    }
}
