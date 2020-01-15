package com.dreamteam.powerofwar.connection.exception;

/**
 * Is thrown when an opened channel is required for any operation, but it was closed.
 */
public class ConnectionClosedException extends Exception {

    public ConnectionClosedException() {
        super();
    }

    public ConnectionClosedException(String message) {
        super(message);
    }
}
