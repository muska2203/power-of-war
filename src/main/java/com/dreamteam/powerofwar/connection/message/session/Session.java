package com.dreamteam.powerofwar.connection.message.session;

import com.dreamteam.powerofwar.connection.exception.ChannelClosedException;
import com.dreamteam.powerofwar.connection.message.Message;

/**
 * Represents connection to another program.
 * TODO: JavaDocs
 */
public interface Session {

    /**
     * Handles the specified message.
     *
     * @param message incoming message
     * @param <T> type of message.
     */
    <T extends Message> void messageReceived(T message);

    /**
     * Sends the message by current connection.
     *
     * @param message message to send.
     * @throws ChannelClosedException if the connection has already been closed.
     */
    void send(Message message) throws ChannelClosedException;

    /**
     * Sends messages by current connection.
     *
     * @param messages messages to send.
     * @throws ChannelClosedException if the connection has already been closed.
     */
    void sendAll(Message... messages) throws ChannelClosedException;

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
