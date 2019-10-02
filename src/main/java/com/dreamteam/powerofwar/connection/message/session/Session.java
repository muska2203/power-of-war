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
     * @param <T>
     */
    <T extends Message> void messageReceived(T message);
    void send(Message message) throws ChannelClosedException;
    void sendAll(Message... messages) throws ChannelClosedException;
    void disconnect();
    void onDisconnect();
    void onReady();

}
