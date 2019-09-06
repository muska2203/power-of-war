package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.player.Player;

/**
 * Создатель игровых объектов.
 */
public interface GameObjectCreator {

    /**
     * Создает игровой объект с указанными координатами. Тип создаваемого объекта зависит от реализации.
     *
     * @param x координата по оси OX.
     * @param y координата по оси OY.
     * @param player пользователь, которые создает объект.
     * @return игровой объект.
     * @throws TooManyObjectsException бросается в случае, если был привышен лимит объектов для указанного пользователя.
     */
    GameObject create(double x, double y, Player player) throws TooManyObjectsException;
}
