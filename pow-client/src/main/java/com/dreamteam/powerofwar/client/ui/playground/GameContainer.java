package com.dreamteam.powerofwar.client.ui.playground;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.game.GameContext;
import com.dreamteam.powerofwar.common.handler.Dispatcher;
import com.dreamteam.powerofwar.common.state.State;
import com.dreamteam.powerofwar.common.state.subject.SelectedGameObject;

@Component
public class GameContainer extends Container {


    public GameContainer(Dispatcher<Action> actionDispatcher,
                         State<SelectedGameObject> selectedGameObjectState,
                         GameContext gameContext) {

        this.setLayout(new BorderLayout());
        add(BorderLayout.NORTH, new InfoBarComponent(gameContext));
        add(BorderLayout.CENTER, new PlaygroundComponent(actionDispatcher, selectedGameObjectState, gameContext));
        add(BorderLayout.SOUTH, new ControlComponent(actionDispatcher));
        setSize(new Dimension(700, 700));
    }
}
