package com.dreamteam.powerofwar.client.action.handlers;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.ClientConnection;
import com.dreamteam.powerofwar.client.action.type.AddGameObjectAction;
import com.dreamteam.powerofwar.client.message.AddGameObjectMessage;
import com.dreamteam.powerofwar.common.handler.Handler;

@Component
public class AddGameObjectHandler implements Handler<AddGameObjectAction> {

    private final ClientConnection clientConnection;

    public AddGameObjectHandler(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void handle(AddGameObjectAction action) {
        clientConnection.sendMessage(new AddGameObjectMessage(action.getX(), action.getY(), action.getType()));
    }
}