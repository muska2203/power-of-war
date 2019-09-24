package com.dreamteam.powerofwar.client.ui;

import com.dreamteam.powerofwar.client.action.ActionDispatcher;
import com.dreamteam.powerofwar.client.ui.playground.GameContainer;
import com.dreamteam.powerofwar.client.state.State;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.game.Board;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * Main Window Frame of the application.
 */
public class MainWindow extends JFrame {

    private Board board;
    private ActionDispatcher actionDispatcher;

    public MainWindow(Board board, ActionDispatcher actionDispatcher, State<SelectedPlayer> selectedPlayerState,
                      State<SelectedGameObject> selectedGameObjectState) {
        this.board = board;
        this.actionDispatcher = actionDispatcher;

        setTitle("Power of War");
        setSize(new Dimension(700, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new GameContainer(this.board, this.actionDispatcher, selectedPlayerState, selectedGameObjectState));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // TODO: Action and state
//                stopGame();
            }
        });

        setMinimumSize(getSize());// enforces the minimum size of both frame and component
        setVisible(true);
    }
}
