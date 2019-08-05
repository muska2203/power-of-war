package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
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
        this.speedVector = speedVector;
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
    public GameObjectType getType() {
        return this.gameObjectType;
    }

    @Override
    public double getSpeedX() {
        return speedVector.getX();
    }

    @Override
    public double getSpeedY() {
        return speedVector.getY();
    }

    @Override
    public void doAction(Board board, long time) {
        x += getSpeedX() * Units.SPEED * time;
        y += getSpeedY() * Units.SPEED * time;
    }

    @Override
    public void stop() {
        speedVector = new Vector();
    }

    @Override
    public void setDirection(double direction) {
        speedVector = Vector.byDirection(speed, direction);
    }

    @Override
    public void setRadians(double radians) {
        speedVector = Vector.byRadians(speed, radians);
    }

    @Override
    public boolean isInCollisionWith(GameObject gameObject) {
        double criticalDist2 = this.size + gameObject.getSize();
        criticalDist2 *= criticalDist2;
        double distByX = this.x - gameObject.getX();
        double distByY = this.y - gameObject.getY();
        double actualDist2 = distByX * distByX + distByY * distByY;
        return actualDist2 <= criticalDist2;
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
