package com.dreamteam.powerofwar.renderer.swing;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.event.AddGameObjectEvent;
import com.dreamteam.powerofwar.game.event.EventListener;
import com.dreamteam.powerofwar.game.object.*;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SwingGameRenderer extends JFrame {

    private Board board;
    private EventListener eventListener;
    private Thread gameThread = new Thread(this::gameLoop);
    private boolean running;
    private GameObjectType selectedGameObjectType = null;
    private Player firstPlayer = new Player("First Player");
    private Player secondPlayer = new Player("Second Player");
    private Player selectedPlayer = firstPlayer;

    public SwingGameRenderer(Board board, EventListener eventListener) {
        this.board = board;
        this.eventListener = eventListener;
        setTitle("Power of War");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(700, 700));
        Container container = this.getContentPane();
        container.add(BorderLayout.NORTH, new InfoComponent());
        container.add(BorderLayout.CENTER, new GameComponent());
        container.add(BorderLayout.SOUTH, createControlPanel());
        container.setSize(new Dimension(700, 700));
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

    private Component createControlPanel() {
        Box box = Box.createHorizontalBox();
        Button warrior = new Button("Warrior");
        Button cowardMinion = new Button("Coward Minion");
        Button resetChoice = new Button("Reset choice");
        Button cleanField = new Button("Clean field");
        Button firstUser = new Button("First Player");
        Button secondUser = new Button("Second Player");
        Button goldMiner = new Button("Gold Miner");
        Button gold = new Button("Gold");
        Button base = new Button("Base");
        warrior.addActionListener(e -> this.selectedGameObjectType = UnitType.WARRIOR);
        cowardMinion.addActionListener(e -> this.selectedGameObjectType = UnitType.COWARD);
        resetChoice.addActionListener(e -> {
            this.selectedGameObjectType = null;
            this.selectedPlayer = null;
        });
        cleanField.addActionListener(e -> this.killAllObject());
        firstUser.addActionListener(e -> this.selectedPlayer = this.firstPlayer);
        secondUser.addActionListener(e -> this.selectedPlayer = this.secondPlayer);
        goldMiner.addActionListener(e -> this.selectedGameObjectType = UnitType.GOLD_MINER);
        gold.addActionListener(e -> this.selectedGameObjectType = ResourceType.GOLD);
        base.addActionListener(e -> this.selectedGameObjectType = BuildingType.BASE);
        Dimension dimension = new Dimension(70, 70);
        for (Button button : Arrays.asList(
                gold,
                goldMiner,
                base,
                warrior,
                resetChoice,
                cleanField,
                firstUser,
                secondUser)) {
            button.setSize(dimension);
            box.add(button);
        }
        box.setSize(500, 80);
        return box;
    }

    private void killAllObject() {
        List<GameObject> objects = this.board.getGameObjects();
        objects.forEach(object -> object.doDamage(100500));
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

    private class InfoComponent extends JComponent {

        InfoComponent() {
            super();
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension((int) board.getWidth(), 25);
        }

        @Override
        public Dimension getPreferredSize() {
            return getMinimumSize();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension dim = getSize();
            g.setColor(Color.black);
            g.fillRect(0, 0, dim.width, dim.height);
            g.setColor(Color.white);
            g.drawString("Gold: " + selectedPlayer.getContext().getResource(ResourceType.GOLD), 0, 10);
            g.drawString("Warriors: " + selectedPlayer.getContext().getObjectCount(UnitType.WARRIOR), 0, 20);
            g.drawString("Gold Miners: " + selectedPlayer.getContext().getObjectCount(UnitType.GOLD_MINER), 100, 20);
        }

    }

    private class GameComponent extends JComponent {

        private double scale = 1;
        private int xBoardStart = 0;
        private int yBoardStart = 0;

        GameComponent() {
            super();
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (selectedGameObjectType == null || selectedPlayer == null) {
                        return;
                    }
                    int x = fromUICoordinateX(e.getX());
                    int y = fromUICoordinateY(e.getY());

                    switch (e.getButton()) {
                        case 1: eventListener.registerEvent(new AddGameObjectEvent(x, y, selectedPlayer, selectedGameObjectType)); break;
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
            fillScaleData(dim);
            g.setColor(Color.black);
            g.fillRect(0, 0, dim.width, dim.height);
            g.setColor(Color.white);
            g.drawRect(toUICoordinateX(0), toUICoordinateY(0), toUICoordinate(board.getWidth()), toUICoordinate(board.getHeight()));

            Map<GameObjectType, List<GameObject>> gameObjectTypeListMap = board.getGameObjects()
                    .stream()
                    .collect(Collectors.groupingBy(GameObject::getType));

            Map<Player, List<GameObject>> gameObjectByUser = board.getGameObjects()
                    .stream()
                    .collect(Collectors.groupingBy(GameObject::getOwner));

            List<GameObject> gameObjectsFirstUser =
                    Optional.ofNullable(gameObjectByUser.get(firstPlayer)).orElse(Collections.emptyList());
            List<GameObject> gameObjectsSecondUser =
                    Optional.ofNullable(gameObjectByUser.get(secondPlayer)).orElse(Collections.emptyList());
            List<GameObject> gameObjectsCoward =
                    Optional.ofNullable(gameObjectTypeListMap.get(UnitType.COWARD)).orElse(Collections.emptyList());

            drawObjects(g, Color.GREEN, Color.GREEN, null, gameObjectsFirstUser);
            drawObjects(g, Color.red, Color.red, null, gameObjectsSecondUser);
            drawObjects(g, null, null, Color.WHITE, gameObjectsCoward);
        }

        private void drawObjects(Graphics g, Color bodyColor, Color visionColor, Color actionColor, List<GameObject> gameObjects) {
            if (gameObjects == null || gameObjects.size() == 0) {
                return;
            }
            if (bodyColor != null) {
                g.setColor(bodyColor);
                for (GameObject gameObject : gameObjects) {
                    int size = toUICoordinate(gameObject.getSize() * 2);
                    int xPosition = toUICoordinateX(gameObject.getX() - gameObject.getSize());
                    int yPosition = toUICoordinateY(gameObject.getY() - gameObject.getSize());
                    g.drawString(gameObject.toString(), xPosition - 2, yPosition - 2);
                    g.fillOval(xPosition, yPosition, size, size);
                }
            }
            if (visionColor != null) {
                g.setColor(visionColor);
                for (GameObject gameObject : gameObjects) {
                    if (gameObject.getType().equals(UnitType.COWARD)) {
                        int size = toUICoordinate(gameObject.getVisibilityRadius() * 2);
                        int xPosition = toUICoordinateX(gameObject.getX() - gameObject.getVisibilityRadius());
                        int yPosition = toUICoordinateY(gameObject.getY() - gameObject.getVisibilityRadius());
                        g.drawOval(xPosition, yPosition, size, size);
                    }
                }
            }
            if (actionColor != null) {
                g.setColor(actionColor);
                for (GameObject gameObject : gameObjects) {
                    int size = toUICoordinate(gameObject.getActionRadius() * 2);
                    int xPosition = toUICoordinateX(gameObject.getX() - gameObject.getActionRadius());
                    int yPosition = toUICoordinateY(gameObject.getY() - gameObject.getActionRadius());
                    g.drawOval(xPosition, yPosition, size, size);
                }
            }
        }

        private int toUICoordinateX(double coordinate) {
            return toUICoordinate(coordinate) + xBoardStart;
        }

        private int toUICoordinateY(double coordinate) {
            return toUICoordinate(coordinate) + yBoardStart;
        }

        private int toUICoordinate(double coordinate) {
            return (int) (coordinate * scale);
        }

        private int fromUICoordinateX(double coordinate) {
            return fromUICoordinate(coordinate - xBoardStart);
        }

        private int fromUICoordinateY(double coordinate) {
            return fromUICoordinate(coordinate - yBoardStart);
        }

        private int fromUICoordinate(double coordinate) {
            return (int) (coordinate / scale);
        }

        private void fillScaleData(Dimension dim) {
            double widthScale = dim.width / board.getWidth();
            double heightScale = dim.height / board.getHeight();
            if (widthScale > heightScale) {
                scale = heightScale;
                xBoardStart = (int) ((dim.width - board.getWidth() * scale) / 2);
                yBoardStart = 0;
            } else {
                scale = widthScale;
                xBoardStart = 0;
                yBoardStart = (int) ((dim.height - board.getHeight() * scale) / 2);
            }
        }
    }
}
