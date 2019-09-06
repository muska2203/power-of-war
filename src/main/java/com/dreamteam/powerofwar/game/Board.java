package com.dreamteam.powerofwar.game;

import com.dreamteam.powerofwar.game.action.Action;
import com.dreamteam.powerofwar.game.object.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Игровое поле.
 */
public class Board {

    private double width;
    private double height;
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();

    public Board(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        if (gameObject != null) {
            gameObjects.add(gameObject);
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void cleanDeadObjects() {
        gameObjects.removeIf(GameObject::isDead);
    }

    public void cleanOverboardObjects() {
        gameObjects.removeIf(object -> object.getX() < 0 || object.getX() > width || object.getY() < 0 || object.getY() > height);
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
