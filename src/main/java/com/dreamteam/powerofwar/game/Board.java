package com.dreamteam.powerofwar.game;

import com.dreamteam.powerofwar.game.object.GameObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Игровое поле.
 */
public class Board {

    private double width;
    private double height;
    private List<GameObject> gameObjects = new CopyOnWriteArrayList<>();

    public Board(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public List<GameObject> getGameObjects() {
        return Collections.unmodifiableList(gameObjects);
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

    public void cleanOverboardObjects() {
        gameObjects.removeIf(object -> object.getX() < 0 || object.getX() > width || object.getY() < 0 || object.getY() > height);
    }

    public void checkCollisions() {
        for (GameObject gameObject : gameObjects) {
            gameObjects.removeIf(object -> object != gameObject && object.isInCollisionWith(gameObject));
        }
    }
}
