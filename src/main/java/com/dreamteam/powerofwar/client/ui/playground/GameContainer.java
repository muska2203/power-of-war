package com.dreamteam.powerofwar.client.ui.playground;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.state.State;
import com.dreamteam.powerofwar.client.state.StateDispatcher;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.handler.Dispatcher;

@Component
public class GameContainer extends Container {

    private Board board;
    private Player firstPlayer = new Player("First Player");
    private Player secondPlayer = new Player("Second Player");
    private Player selectedPlayer = firstPlayer;

    public GameContainer(Board board,
                         Dispatcher<Action> actionDispatcher,
                         State<SelectedPlayer> selectedPlayerState,
                         State<SelectedGameObject> selectedGameObjectState,
                         StateDispatcher<SelectedPlayer> selectedPlayerStateDispatcher) {
        this.board = board;
        selectedPlayerStateDispatcher.dispatch(new SelectedPlayer(firstPlayer));

        this.setLayout(new BorderLayout());
        // TODO: Resolve the "current player" issue (move data to State) and inject as a dependency
        add(BorderLayout.NORTH, new InfoBarComponent(selectedPlayer, board));
        // TODO: Resolve the "first player and second player" issue (move data to State) and inject as a dependency
        add(BorderLayout.CENTER, new PlaygroundComponent(board, actionDispatcher, selectedPlayerState,
                selectedGameObjectState, firstPlayer, secondPlayer));
        add(BorderLayout.SOUTH, new ControlComponent(actionDispatcher, board, firstPlayer, secondPlayer));
        setSize(new Dimension(700, 700));
    }
}
