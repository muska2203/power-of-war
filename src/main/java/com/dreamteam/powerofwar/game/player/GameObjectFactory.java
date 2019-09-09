package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.*;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;

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
    public GameObject createObject(double x, double y, GameObjectType type) throws TooManyObjectsException {
        PlayerContext context = player.getContext();
        if (context.isFullOf(type)) {
            throw new TooManyObjectsException(type, player);
        }
        context.addCount(type);
        return GameObjectContext.getByType(type).create(x, y, player);
    }
}
