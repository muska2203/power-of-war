package com.dreamteam.powerofwar.client.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.ui.playground.GameContainer;

/**
 * Main Window Frame of the application.
 */
// TODO: Most likely it should be a prototype bean
@Component
public class MainWindow extends JFrame {

    public MainWindow(GameContainer gameContainer) {
        setTitle("Power of War");
        setSize(new Dimension(700, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(gameContainer);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // TODO: Action and state
                // stopGame();
            }
        });

        setMinimumSize(getSize());// enforces the minimum size of both frame and component

        // TODO: Do it outside the constructor. Maybe ClientApplication class or other starting code.
        setVisible(true);
    }
}
