package com.dreamteam.powerofwar.connection.message.session;

import com.dreamteam.powerofwar.connection.message.Message;

public interface MessageListener {
    /**
     * Handles the specified message.
     */
    <T extends Message> void receiveMessage();
}
