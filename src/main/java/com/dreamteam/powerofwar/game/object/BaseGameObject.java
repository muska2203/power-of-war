package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.user.User;
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
    private double x;
    private double y;
    private Vector speedVector;
    private int health = 1;
    private User user;

    BaseGameObject(double x, double y, Vector speedVector, User user) {
        this(x, y, user);
        this.speedVector = Vector.byRadians(getSpeed(), speedVector.getRadians());
    }

    BaseGameObject(double x, double y, User user) {
        this.id = ++objectsCount;
        this.x = x;
        this.y = y;
        this.user = user;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void move(long loopTime) {
        x += speedVector.getX() * Units.SPEED * loopTime;
        y += speedVector.getY() * Units.SPEED * loopTime;
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
    public User getOwner() {
        return user;
    }

    /**
     * Изменяет направление указанного игрового объекта.
     *
     * @param vector новое направление
     */
    protected void setSpeedVector(Vector vector) {
        this.speedVector = Vector.byRadians(getSpeed(), vector.getRadians());
    }

    /**
     * Возвращает относительную скорость объекта.
     *
     * @return скорость объекта.
     */
    protected abstract double getSpeed();
}
