package com.dreamteam.powerofwar.client;

import com.dreamteam.powerofwar.client.action.RegistryActionDispatcher;
import com.dreamteam.powerofwar.client.action.handlers.AddGameObjectHandler;
import com.dreamteam.powerofwar.client.action.handlers.SelectGameObjectHandler;
import com.dreamteam.powerofwar.client.action.handlers.SelectPlayerHandler;
import com.dreamteam.powerofwar.client.action.type.AddGameObjectAction;
import com.dreamteam.powerofwar.client.action.type.SelectGameObjectAction;
import com.dreamteam.powerofwar.client.action.type.SelectPlayerAction;
import com.dreamteam.powerofwar.client.ui.MainWindow;
import com.dreamteam.powerofwar.client.state.GenericMutableState;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.GameProgram;

public class ClientApplication {

    public static void main(String[] args) {
        Board board = new Board(800, 480);
        GameProgram gameProgram = new GameProgram(board);
        gameProgram.startGame();

        // States
        GenericMutableState<SelectedPlayer> selectedPlayerState = new GenericMutableState<>();
        GenericMutableState<SelectedGameObject> selectedGameObjectState = new GenericMutableState<>();

        // Actions and ActionHandlers
        RegistryActionDispatcher dispatcher = new RegistryActionDispatcher();
        dispatcher.register(SelectPlayerAction.class, new SelectPlayerHandler(selectedPlayerState));
        dispatcher.register(SelectGameObjectAction.class, new SelectGameObjectHandler(selectedGameObjectState));
        dispatcher.register(AddGameObjectAction.class, new AddGameObjectHandler(gameProgram));

        MainWindow mainWindow = new MainWindow(board, dispatcher, selectedPlayerState, selectedGameObjectState);
    }
}
