package com.dreamteam.powerofwar.common.physics;

public interface Coordinate {

    /**
     * Возвращает позицию объекта по оси "OX" относительно игровой доски.
     */
    double getX();

    /**
     * Возвращает позицию объекта по оси "OY" относительно игровой доски.
     */
    double getY();
}
