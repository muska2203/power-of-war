package com.dreamteam.powerofwar.client.action.handlers;

import com.dreamteam.powerofwar.client.action.ActionHandler;
import com.dreamteam.powerofwar.client.action.type.SelectGameObjectAction;
import com.dreamteam.powerofwar.client.state.StateDispatcher;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;

public class SelectGameObjectHandler implements ActionHandler<SelectGameObjectAction> {

    private StateDispatcher<SelectedGameObject> gameObjectStateDispatcher;

    public SelectGameObjectHandler(StateDispatcher<SelectedGameObject> gameObjectStateDispatcher) {
        this.gameObjectStateDispatcher = gameObjectStateDispatcher;
    }

    @Override
    public void onAction(SelectGameObjectAction action) {
        this.gameObjectStateDispatcher.dispatch(new SelectedGameObject(action.getType()));
    }
}
