package com.dreamteam.powerofwar.client.ui.playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.Timer;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.action.type.AddGameObjectAction;
import com.dreamteam.powerofwar.client.state.State;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.GameObject;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.handler.Dispatcher;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PlaygroundComponent extends JComponent {

    private Board board;

    private Dispatcher<Action> actionDispatcher;
    private State<SelectedPlayer> selectedPlayerState;
    private State<SelectedGameObject> selectedGameObjectState;
    private Player firstPlayer;
    private Player secondPlayer;

    private Timer repaintTimer;

    private double scale = 1;
    private int xBoardStart = 0;
    private int yBoardStart = 0;

    public PlaygroundComponent(Board board,
                               Dispatcher<Action> actionDispatcher,
                               State<SelectedPlayer> selectedPlayerState,
                               State<SelectedGameObject> selectedGameObjectState,
                               Player firstPlayer,
                               Player secondPlayer) {
        super();

        this.board = board;
        this.actionDispatcher = actionDispatcher;

        this.selectedPlayerState = selectedPlayerState;
        this.selectedGameObjectState = selectedGameObjectState;

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.repaintTimer = new Timer(20, e -> this.repaint());
        // TODO: Implement a game state. Start and stop the timer according to the state
        this.repaintTimer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                SelectedPlayer selectedPlayer = selectedPlayerState.get();
                SelectedGameObject selectedGameObject = selectedGameObjectState.get();
                if (selectedPlayer == null || selectedGameObject == null) {
                    return;
                }

                Player player = selectedPlayer.getPlayer();
                GameObjectType type = selectedGameObject.getType();

                if (type == null || player == null) {
                    return;
                }
                int x = fromUICoordinateX(e.getX());
                int y = fromUICoordinateY(e.getY());

                switch (e.getButton()) {
                    case 1: // Left Button
                        PlaygroundComponent.this.actionDispatcher.dispatch(new AddGameObjectAction(x, y, player, type));
                        break;
                    default:
                        // process ordinary clicks on the map?
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
        g.drawRect(toUICoordinateX(0), toUICoordinateY(0), toUICoordinate(board.getWidth()),
                toUICoordinate(board.getHeight()));

        Map<GameObjectType, java.util.List<GameObject>> gameObjectTypeListMap = board.getGameObjects()
                .stream()
                .collect(Collectors.groupingBy(GameObject::getType));

        Map<Player, java.util.List<GameObject>> gameObjectByUser = board.getGameObjects()
                .stream()
                .collect(Collectors.groupingBy(GameObject::getOwner));

        List<GameObject> gameObjectsFirstUser =
                Optional.ofNullable(gameObjectByUser.get(firstPlayer)).orElse(Collections.emptyList());
        java.util.List<GameObject> gameObjectsSecondUser =
                Optional.ofNullable(gameObjectByUser.get(secondPlayer)).orElse(Collections.emptyList());
        java.util.List<GameObject> gameObjectsCoward =
                Optional.ofNullable(gameObjectTypeListMap.get(GameObjectType.COWARD)).orElse(Collections.emptyList());

        drawObjects(g, Color.GREEN, Color.GREEN, null, gameObjectsFirstUser);
        drawObjects(g, Color.red, Color.red, null, gameObjectsSecondUser);
        drawObjects(g, null, null, Color.WHITE, gameObjectsCoward);
    }

    private void drawObjects(Graphics g, Color bodyColor, Color visionColor,
                             Color actionColor, List<GameObject> gameObjects) {
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
                if (gameObject.getType().equals(GameObjectType.COWARD)) {
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
