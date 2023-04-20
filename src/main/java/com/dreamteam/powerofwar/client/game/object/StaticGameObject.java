package com.dreamteam.powerofwar.client.game.object;

import com.dreamteam.powerofwar.game.types.GameObjectType;

public class StaticGameObject {

    private int id;
    private double x;
    private double y;
    private double size;
    private int health;
    private GameObjectType type;
    private boolean enemy;

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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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

    @Override
    public String toString() {
        return type.toString() + "[id= " + id + "]";
    }
}
