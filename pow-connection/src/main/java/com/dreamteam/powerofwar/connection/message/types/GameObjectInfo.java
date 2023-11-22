package com.dreamteam.powerofwar.connection.message.types;

import com.dreamteam.powerofwar.common.types.GameObjectType;

public class GameObjectInfo {

    private int id;
    private double x;
    private double y;
    private int health;
    private GameObjectType type;
    private boolean enemy;
    private double size;

    public GameObjectInfo() {
    }

    public GameObjectInfo(int id,
                          double x,
                          double y,
                          int health,
                          GameObjectType type,
                          boolean enemy,
                          double size
    ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.health = health;
        this.type = type;
        this.enemy = enemy;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public GameObjectType getType() {
        return type;
    }

    public void setType(GameObjectType type) {
        this.type = type;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
