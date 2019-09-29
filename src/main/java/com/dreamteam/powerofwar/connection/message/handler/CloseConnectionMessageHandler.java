package com.dreamteam.powerofwar.connection.message.handler;

import com.dreamteam.powerofwar.connection.message.EventHandler;
import com.dreamteam.powerofwar.connection.message.type.CloseConnectionEvent;
import com.dreamteam.powerofwar.connection.server.ServerConnection;
import org.springframework.stereotype.Component;

@Component
public class CloseConnectionEventHandler implements EventHandler<CloseConnectionEvent> {

    private ServerConnection serverConnection;

    public CloseConnectionEventHandler(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public void handleMessage(CloseConnectionEvent message) {
        System.out.println("handled");
//        serverConnection.closeChannel(message.getConnectionId());
    }
}
