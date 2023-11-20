package com.dreamteam.powerofwar.client.action.handlers;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.type.SelectGameObjectAction;
import com.dreamteam.powerofwar.common.handler.Handler;
import com.dreamteam.powerofwar.common.state.StateDispatcher;
import com.dreamteam.powerofwar.common.state.subject.SelectedGameObject;

@Component
public class SelectGameObjectHandler implements Handler<SelectGameObjectAction> {

    private final StateDispatcher<SelectedGameObject> gameObjectStateDispatcher;

    public SelectGameObjectHandler(StateDispatcher<SelectedGameObject> gameObjectStateDispatcher) {
        this.gameObjectStateDispatcher = gameObjectStateDispatcher;
    }

    @Override
    public void handle(SelectGameObjectAction action) {
        this.gameObjectStateDispatcher.dispatch(new SelectedGameObject(action.getType()));
    }
}
