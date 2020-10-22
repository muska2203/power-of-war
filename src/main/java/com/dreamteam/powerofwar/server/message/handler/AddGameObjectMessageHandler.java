package com.dreamteam.powerofwar.server.message.handler;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.MessageHandler;
import com.dreamteam.powerofwar.game.event.EventListener;
import com.dreamteam.powerofwar.server.message.AddGameObjectMessage;

@Component
public class AddGameObjectMessageHandler implements MessageHandler<AddGameObjectMessage> {

    private EventListener eventListener;

    public AddGameObjectMessageHandler(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void handle(AddGameObjectMessage message) {
        System.out.println(message.getType() + "from session with id = " + message.getSenderSessionId());
    }
}
