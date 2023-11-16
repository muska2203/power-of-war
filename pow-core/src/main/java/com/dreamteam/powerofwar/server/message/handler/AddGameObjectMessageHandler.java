package com.dreamteam.powerofwar.server.message.handler;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.MessageHandler;
import com.dreamteam.powerofwar.server.game.event.AddGameObjectEvent;
import com.dreamteam.powerofwar.server.game.event.EventListener;
import com.dreamteam.powerofwar.server.message.AddGameObjectMessage;

@Component
public class AddGameObjectMessageHandler implements MessageHandler<AddGameObjectMessage> {

    private EventListener eventListener;

    public AddGameObjectMessageHandler(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void handle(AddGameObjectMessage message) {
        eventListener.registerEvent(
                new AddGameObjectEvent(message.getX(), message.getY(), message.getInitiator(), message.getType())
        );
    }
}
