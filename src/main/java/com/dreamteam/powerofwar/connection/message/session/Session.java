package com.dreamteam.powerofwar.connection.message.session;

import com.dreamteam.powerofwar.connection.exception.ConnectionClosedException;
import com.dreamteam.powerofwar.connection.message.Message;

/**
 * Represents connection to another program.
 * TODO: JavaDocs
 */
public interface Session extends MessageListener {

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
     * Closes connection if it is opened.
     */
    void disconnect();

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
