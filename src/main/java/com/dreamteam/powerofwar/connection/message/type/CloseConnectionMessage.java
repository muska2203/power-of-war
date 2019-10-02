package com.dreamteam.powerofwar.connection.message.type;

import com.dreamteam.powerofwar.connection.message.Message;

public class CloseConnectionMessage implements Message {

    private int connectionId;

    public CloseConnectionMessage(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getConnectionId() {
        return connectionId;
    }
}
