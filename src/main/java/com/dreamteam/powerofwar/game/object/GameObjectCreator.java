package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.user.User;

/**
 * Создатель игровых объектов.
 */
public interface GameObjectCreator {

    /**
     * Создает игровой объект с указанными координатами. Тип создаваемого объекта зависит от реализации.
     *
     * @param x координата по оси OX.
     * @param y координата по оси OY.
     * @param user пользователь, которые создает объект.
     * @return игровой объект.
     */
    GameObject create(double x, double y, User user);
}
