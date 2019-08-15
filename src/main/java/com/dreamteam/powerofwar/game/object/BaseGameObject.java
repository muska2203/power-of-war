package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.DamageAction;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

/**
 * Основной класс-сущность, использующийся в игре.
 * При создании новых классов-сущностей необходимо наследоваться от этого класса либо от его наслединков.
 */
public class BaseGameObject implements GameObject {

    /**
     * Используется как счетчик объектов для задания идентификатора новым объектам.
     */
    private static int objectsCount = 0;

    private int id;
    private double x;
    private double y;
    private double size;
    private double visibilityRadius;
    private Vector speedVector;
    private GameObjectType gameObjectType;
    private int health = 1;
    /**
     * Относительная скорость объекта. значение не должно быть отрицательным.
     */
    private double speed;
    /**
     * Радиус области действий.
     * Например, для войнов эта характеристика показывает дальность атаки.
     * А для Базы определяет область возможной постройки других строений {@link Building}.
     */
    private double actionRadius;

    public BaseGameObject(double x, double y, double size, double visibilityRadius, double actionRadius, double speed,
                          Vector speedVector, GameObjectType gameObjectType
    ) {
        this.id = ++objectsCount;
        this.x = x;
        this.y = y;
        this.size = size;
        this.visibilityRadius = visibilityRadius;
        this.speedVector = Vector.byRadians(speed, speedVector.getRadians());
        this.gameObjectType = gameObjectType;
        this.speed = speed;
        this.actionRadius = actionRadius;
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
    public double getSize() {
        return size;
    }

    @Override
    public int getHealth() {
        return 1;
    }

    @Override
    public GameObjectType getType() {
        return this.gameObjectType;
    }

    @Override
    public void update(Board board) {
        for (GameObject gameObject : board.getGameObjects()) {
            if (gameObject != this && GameObjectUtils.checkCollision(this, gameObject)) {
                board.addAction(new DamageAction(100, gameObject, 0));
            }
        }
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
        return health <= 0;
    }


    @Override
    public double getActionRadius() {
        return this.actionRadius;
    }

    @Override
    public double getVisibilityRadius() {
        return visibilityRadius;
    }
}
