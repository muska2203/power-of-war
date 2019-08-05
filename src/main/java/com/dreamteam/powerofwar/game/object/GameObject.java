package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;

public interface GameObject {

    double getX();

    double getY();

    double getSize();

    /**
     * Возвращает радиус области видимости
     * @return радиус области видимости
     */
    double getVisibilityRadius();

    double getActionRadius();

    GameObjectType getType();

    double getSpeedX();

    double getSpeedY();

    /**
     * Основной метод любого объекта. Здесь описывается логика объекта в определенный момент времени.
     * @param board игровое поле
     * @param time текущее время
     */
    void doAction(Board board, long time);

    void stop();

    /**
     * @param direction число, обозначающее направление в градусах относительно вертикальной линии.
     */
    void setDirection(double direction);

    /**
     * @param radians число, обозначающее направление в радианах относительно вертикальной линии.
     */
    void setRadians(double radians);

    boolean isInCollisionWith(GameObject gameObject);

    
}
