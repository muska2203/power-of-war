package com.dreamteam.powerofwar.connection.message.type;

import com.dreamteam.powerofwar.connection.message.Event;

public class CloseConnectionEvent implements Event {

    private int connectionId;

    public CloseConnectionEvent(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getConnectionId() {
        return connectionId;
    }
}
