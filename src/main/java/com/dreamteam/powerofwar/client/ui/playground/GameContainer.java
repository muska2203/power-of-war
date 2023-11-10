package com.dreamteam.powerofwar.client.ui.playground;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.game.GameContext;
import com.dreamteam.powerofwar.client.state.State;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.handler.Dispatcher;

@Component
public class GameContainer extends Container {


    public GameContainer(Dispatcher<Action> actionDispatcher,
                         State<SelectedGameObject> selectedGameObjectState,
                         GameContext gameContext) {

        this.setLayout(new BorderLayout());
        // TODO: Resolve the "current player" issue (move data to State) and inject as a dependency
        add(BorderLayout.NORTH, new InfoBarComponent(gameContext));
        // TODO: Resolve the "first player and second player" issue (move data to State) and inject as a dependency
        add(BorderLayout.CENTER, new PlaygroundComponent(actionDispatcher, selectedGameObjectState, gameContext));
        add(BorderLayout.SOUTH, new ControlComponent(actionDispatcher));
        setSize(new Dimension(700, 700));
    }
}
