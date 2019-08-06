package com.dreamteam.powerofwar.renderer.swing;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.event.AddObjectEvent;
import com.dreamteam.powerofwar.game.event.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SwingGameRenderer extends JFrame {

    private Board board;
    private EventListener eventListener;
    private Thread gameThread = new Thread(this::gameLoop);
    private boolean running;

    public SwingGameRenderer(Board board, EventListener eventListener) {
        this.board = board;
        this.eventListener = eventListener;
        setTitle("Power of War");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new GameComponent());
        pack();
        setMinimumSize(getSize());// enforces the minimum size of both frame and component
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                stopGame();
            }
        });
    }

    public synchronized void start() {
        running = true;
        gameThread.start();
    }

    public synchronized void stopGame() {
        running = false;
    }

    @Override
    public void setDefaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(operation);
    }

    private void gameLoop() {
        while (running) {
            repaint();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class GameComponent extends JComponent {

        GameComponent() {
            super();
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == 1) {
                        eventListener.registerEvent(new AddObjectEvent(e.getX(), e.getY()));
                    }
                }
            });
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension((int) board.getWidth(), (int) board.getHeight());
        }

        @Override
        public Dimension getPreferredSize() {
            return getMinimumSize();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension dim = getSize();
            g.setColor(Color.red);
            g.fillRect(0, 0, dim.width, dim.height);
            g.setColor(Color.black);
            board.getGameObjects().forEach(gameObject -> {
                int size = (int) (gameObject.getSize() * 2);
                int xPosition = (int) (gameObject.getX() - gameObject.getSize());
                int yPosition = (int) (gameObject.getY() - gameObject.getSize());
                g.fillOval(xPosition, yPosition, size, size);
            });
        }
    }
}
