package com.dreamteam.powerofwar.client.action.handlers;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.ClientConnection;
import com.dreamteam.powerofwar.client.action.type.AddGameObjectAction;
import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.client.message.AddGameObjectMessage;

@Component
public class AddGameObjectHandler implements Handler<AddGameObjectAction> {

    private ClientConnection clientConnection;

    public AddGameObjectHandler(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void handle(AddGameObjectAction action) {
        clientConnection.sendMessage(new AddGameObjectMessage(action.getX(), action.getY(), action.getType()));
    }
}
