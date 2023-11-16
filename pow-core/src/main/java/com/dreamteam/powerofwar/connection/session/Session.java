package com.dreamteam.powerofwar.connection.session;

import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;

/**
 * Represents a connection to another program.
 */
public interface Session {

    /**
     * Sends the message by current connection.
     *
     * @param message message to send.
     * @throws ConnectionClosedException if the connection has already been closed.
     */
    <T extends Message> void send(T message) throws ConnectionClosedException;

    /**
     * Sends messages by current connection.
     *
     * @param messages messages to send.
     * @throws ConnectionClosedException if the connection has already been closed.
     */
    void sendAll(Message... messages) throws ConnectionClosedException;

    /**
     * Receive a message.
     */
    <T extends Message> T receiveMessage();

    /**
     * Closes connection if it is opened.
     */
    void disconnect();

    /**
     * Returns session id.
     */
    int getId();

    /**
     * Called when connection has been disconnected, right before the Session invalidated.
     */
    default void onDisconnect() {

    }

    /**
     * Called once the Session is ready for messages.
     */
    default void onReady() {

    }
}
