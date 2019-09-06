package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.Base;
import com.dreamteam.powerofwar.game.object.GameObjectType;

/**
 * Фабрика игровых объектов. Добавляет проверки перед созданием объектов.
 */
public class GameObjectFactory {

    private Player player;

    public GameObjectFactory(Player player) {
        this.player = player;
    }

    /**
     * TODO: JavaDoc
     */
    public Base createBase(double x, double y) throws TooManyObjectsException {
        if (player.getContext().getCount(GameObjectType.BASE) >= 1) {
            throw new TooManyObjectsException(GameObjectType.BASE, player);
        }
        player.getContext().addCount(GameObjectType.BASE);
        return new Base(x, y, player);
    }
}
