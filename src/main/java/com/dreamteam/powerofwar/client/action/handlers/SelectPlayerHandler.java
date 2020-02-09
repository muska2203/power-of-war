package com.dreamteam.powerofwar.client.action.handlers;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.client.action.type.SelectPlayerAction;
import com.dreamteam.powerofwar.client.state.StateDispatcher;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;

@Component
public class SelectPlayerHandler implements Handler<SelectPlayerAction> {

    private StateDispatcher<SelectedPlayer> dispatcher;

    public SelectPlayerHandler(StateDispatcher<SelectedPlayer> dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(SelectPlayerAction action) {
        this.dispatcher.dispatch(new SelectedPlayer(action.getPlayer()));
    }
}
