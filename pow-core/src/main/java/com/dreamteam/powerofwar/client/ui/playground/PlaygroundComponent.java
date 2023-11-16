package com.dreamteam.powerofwar.client.ui.playground;

import javax.swing.JComponent;
import javax.swing.Timer;
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

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.action.type.AddGameObjectAction;
import com.dreamteam.powerofwar.client.game.GameContext;
import com.dreamteam.powerofwar.client.game.object.StaticGameObject;
import com.dreamteam.powerofwar.client.state.State;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.handler.Dispatcher;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PlaygroundComponent extends JComponent {

    private GameContext gameContext;

    private Dispatcher<Action> actionDispatcher;

    private Timer repaintTimer;

    private double scale = 1;
    private int xBoardStart = 0;
    private int yBoardStart = 0;

    public PlaygroundComponent(Dispatcher<Action> actionDispatcher,
                               State<SelectedGameObject> selectedGameObjectState,
                               GameContext gameContext) {
        super();

        this.gameContext = gameContext;
        this.actionDispatcher = actionDispatcher;

        this.repaintTimer = new Timer(20, e -> this.repaint());
        // TODO: Implement a game state. Start and stop the timer according to the state
        this.repaintTimer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                SelectedGameObject selectedGameObject = selectedGameObjectState.get();
                if (selectedGameObject == null) {
                    return;
                }

                GameObjectType type = selectedGameObject.getType();

                if (type == null) {
                    return;
                }
                int x = fromUICoordinateX(e.getX());
                int y = fromUICoordinateY(e.getY());

                switch (e.getButton()) {
                    case 1: // Left Button
                        PlaygroundComponent.this.actionDispatcher.dispatch(new AddGameObjectAction(x, y, type));
                        break;
                    default:
                        // process ordinary clicks on the map?
                }
            }
        });
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension((int) gameContext.getWidth(), (int) gameContext.getHeight());
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
        g.drawRect(toUICoordinateX(0), toUICoordinateY(0), toUICoordinate(gameContext.getWidth()),
                toUICoordinate(gameContext.getHeight()));

        Map<GameObjectType, java.util.List<StaticGameObject>> gameObjectTypeListMap = gameContext.getGameObjects()
                .values()
                .stream()
                .collect(Collectors.groupingBy(StaticGameObject::getType));

        Map<Boolean, java.util.List<StaticGameObject>> gameObjectByUser = gameContext.getGameObjects()
                .values()
                .stream()
                .collect(Collectors.groupingBy(StaticGameObject::isEnemy));

        List<StaticGameObject> ownerObjects =
                Optional.ofNullable(gameObjectByUser.get(false)).orElse(Collections.emptyList());
        List<StaticGameObject> enemies =
                Optional.ofNullable(gameObjectByUser.get(true)).orElse(Collections.emptyList());
        List<StaticGameObject> gameObjectsCoward =
                Optional.ofNullable(gameObjectTypeListMap.get(GameObjectType.COWARD)).orElse(Collections.emptyList());

        drawObjects(g, Color.GREEN, ownerObjects);
        drawObjects(g, Color.red, enemies);
        drawObjects(g, Color.blue, gameObjectsCoward);
    }

    private void drawObjects(Graphics g, Color bodyColor, List<StaticGameObject> gameObjects) {
        if (gameObjects == null || gameObjects.size() == 0) {
            return;
        }
        if (bodyColor != null) {
            g.setColor(bodyColor);
            for (StaticGameObject gameObject : gameObjects) {
                int size = toUICoordinate(gameObject.getSize() * 2);
                int xPosition = toUICoordinateX(gameObject.getX() - gameObject.getSize());
                int yPosition = toUICoordinateY(gameObject.getY() - gameObject.getSize());
                g.drawString(gameObject.toString(), xPosition - 2, yPosition - 2);
                g.fillOval(xPosition, yPosition, size, size);
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
        double widthScale = dim.width / gameContext.getWidth();
        double heightScale = dim.height / gameContext.getHeight();
        if (widthScale > heightScale) {
            scale = heightScale;
            xBoardStart = (int) ((dim.width - gameContext.getWidth() * scale) / 2);
            yBoardStart = 0;
        } else {
            scale = widthScale;
            xBoardStart = 0;
            yBoardStart = (int) ((dim.height - gameContext.getHeight() * scale) / 2);
        }
    }
}
