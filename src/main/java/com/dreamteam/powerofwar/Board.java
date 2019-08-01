package com.dreamteam.powerofwar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private double width;
    private double height;

    public Board(double width, double height) {
        this.width = width;
        this.height = height;
    }

    private List<GameObject> gameObjects = new ArrayList<>();

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
}
