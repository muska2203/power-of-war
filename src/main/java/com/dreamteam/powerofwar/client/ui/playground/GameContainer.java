package com.dreamteam.powerofwar.client.ui.playground;

import com.dreamteam.powerofwar.client.action.ActionDispatcher;
import com.dreamteam.powerofwar.client.state.State;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.player.Player;
import java.awt.*;

public class GameContainer extends Container {

    private Board board;
    private GameObjectType selectedGameObjectType = null;
    private Player firstPlayer = new Player("First Player");
    private Player secondPlayer = new Player("Second Player");
    private Player selectedPlayer = firstPlayer;

    public GameContainer(Board board, ActionDispatcher actionDispatcher,
                         State<SelectedPlayer> selectedPlayerState,
                         State<SelectedGameObject> selectedGameObjectState) {
        this.board = board;

        this.setLayout(new BorderLayout());
        add(BorderLayout.NORTH, new InfoBarComponent(selectedPlayer, board));
        add(BorderLayout.CENTER, new PlaygroundComponent(board, actionDispatcher, selectedPlayerState, selectedGameObjectState,
                firstPlayer, secondPlayer));
        add(BorderLayout.SOUTH, new ControlComponent(actionDispatcher, board, firstPlayer, secondPlayer));
        setSize(new Dimension(700, 700));
    }
}
