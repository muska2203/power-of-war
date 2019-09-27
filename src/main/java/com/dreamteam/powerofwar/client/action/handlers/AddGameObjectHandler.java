package com.dreamteam.powerofwar.client.action.handlers;

import com.dreamteam.powerofwar.client.action.ActionHandler;
import com.dreamteam.powerofwar.client.action.type.AddGameObjectAction;
import com.dreamteam.powerofwar.game.event.AddGameObjectEvent;
import com.dreamteam.powerofwar.game.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AddGameObjectHandler implements ActionHandler<AddGameObjectAction> {

    private EventListener eventListener;

    public AddGameObjectHandler(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void onAction(AddGameObjectAction action) {
        eventListener.registerEvent(new AddGameObjectEvent(
                action.getX(), action.getY(), action.getPlayer(), action.getType()));
    }
}
