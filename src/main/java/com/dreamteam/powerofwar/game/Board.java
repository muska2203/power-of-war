package com.dreamteam.powerofwar.game;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.game.action.Action;
import com.dreamteam.powerofwar.game.object.GameObject;

/**
 * Игровое поле.
 */
@Component
public class Board {

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 480;

    private double width;
    private double height;
    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private List<Action> actions = new CopyOnWriteArrayList<>();

    public Board() {
        this.width = BOARD_WIDTH;
        this.height = BOARD_HEIGHT;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void cleanDeadObjects() {
        gameObjects.stream()
                .filter(GameObject::isDead)
                .forEach(obj -> obj.getOwner().getContext().minusObjectCount(obj.getType()));
        gameObjects.removeIf(GameObject::isDead);
    }

    public void cleanOverboardObjects() {
        gameObjects.removeIf(object -> object.getX() < 0 || object.getX() > width ||
                object.getY() < 0 || object.getY() > height);
    }

    public void doActions(long loopTime) {
        for (Action action : actions) {
            if (action.isReady(loopTime)) {
                action.execute();
            }
        }
        actions.removeIf(Action::isCompleted);
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }
}
