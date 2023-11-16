package com.dreamteam.powerofwar.client.action.handlers;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.type.SelectGameObjectAction;
import com.dreamteam.powerofwar.client.state.StateDispatcher;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.handler.Handler;

@Component
public class SelectGameObjectHandler implements Handler<SelectGameObjectAction> {

    private StateDispatcher<SelectedGameObject> gameObjectStateDispatcher;

    public SelectGameObjectHandler(StateDispatcher<SelectedGameObject> gameObjectStateDispatcher) {
        this.gameObjectStateDispatcher = gameObjectStateDispatcher;
    }

    @Override
    public void handle(SelectGameObjectAction action) {
        this.gameObjectStateDispatcher.dispatch(new SelectedGameObject(action.getType()));
    }
}
