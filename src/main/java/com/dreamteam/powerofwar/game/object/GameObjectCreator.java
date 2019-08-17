package com.dreamteam.powerofwar.game.object;

/**
 * Создатель игровых объектов.
 */
public interface GameObjectCreator {

    /**
     * Создает игровой объект с указанными координатами. Тип создаваемого объекта зависит от реализации.
     *
     * @param x координата по оси OX.
     * @param y координата по оси OY.
     * @return игровой объект.
     */
    GameObject create(double x, double y);
}
