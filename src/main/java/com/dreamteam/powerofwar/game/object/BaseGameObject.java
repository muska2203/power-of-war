package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

/**
 * Основной класс-сущность, использующийся в игре.
 * При создании новых классов-сущностей необходимо наследоваться от этого класса либо от его наслединков.
 */
public abstract class BaseGameObject implements GameObject {

    /**
     * Используется как счетчик объектов для задания идентификатора новым объектам.
     */
    private static int objectsCount = 0;

    private int id;
    private Point point;
    private Vector speedVector = new Vector();
    private int health = 1;
    private Player player;

    BaseGameObject(double x, double y, Vector speedVector, Player player) {
        this(x, y, player);
        this.speedVector = Vector.byRadians(getSpeed(), speedVector.getRadians());
    }

    BaseGameObject(double x, double y, Player player) {
        this.id = ++objectsCount;
        this.point = new Point(x, y);
        this.player = player;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void move(long loopTime) {
        double dx = speedVector.getX() * Units.SPEED * loopTime * getSpeedFactor();
        double dy = speedVector.getY() * Units.SPEED * loopTime * getSpeedFactor();

        this.point = new Point(this.point.getX() + dx, this.point.getY() + dy);
    }

    @Override
    public void update(Board board) {
        // do nothing
    }

    @Override
    public void doDamage(int damage) {
        health -= damage;
    }

    @Override
    public boolean isDead() {
        return getHealth() <= 0;
    }

    @Override
    public double getSpeedFactor() {
        return 1;
    }

    @Override
    public Player getOwner() {
        return player;
    }

    /**
     * Изменяет направление указанного игрового объекта.
     *
     * @param vector новое направление
     */
    protected void setSpeedVector(Vector vector) {
        if (vector.getLength() == 0) {
            this.speedVector = vector;
        } else {
            this.speedVector = Vector.byRadians(getSpeed(), vector.getRadians());
        }
    }

    /**
     * Возвращает относительную скорость объекта.
     *
     * @return скорость объекта.
     */
    protected abstract double getSpeed();
}
