package com.dreamteam.powerofwar.client.action.handlers;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.client.action.type.AddGameObjectAction;
import com.dreamteam.powerofwar.game.event.AddGameObjectEvent;
import com.dreamteam.powerofwar.game.event.EventListener;

@Component
public class AddGameObjectHandler implements Handler<AddGameObjectAction> {

    private EventListener eventListener;

    public AddGameObjectHandler(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void handle(AddGameObjectAction action) {
        eventListener.registerEvent(new AddGameObjectEvent(
                action.getX(), action.getY(), action.getPlayer(), action.getType()));
    }
}
