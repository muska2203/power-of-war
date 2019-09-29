package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.exception.TooLessResourcesException;
import com.dreamteam.powerofwar.game.exception.TooManyObjectsException;
import com.dreamteam.powerofwar.game.object.GameObject;
import com.dreamteam.powerofwar.game.object.GameObjectContext;
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
     * Производит объект указанного типа в указанной точке, если это позволяют ресурсы и лимит игрока.
     * В противном случае бросаются исключения.
     *
     * @param x позиция по OX, в которой требуется создать объект.
     * @param y позиция по OY, в которой требуется создать объект.
     * @param type тип создаваемого объекта.
     * @return созданный объект указанного типа.
     * @throws TooManyObjectsException если лимит игрока для объектов указанного типа был превышен.
     * @throws TooLessResourcesException если у игрока не хватает ресурсов для создания объекта указанного типа.
     */
    public GameObject createObject(double x, double y, GameObjectType type)
            throws TooManyObjectsException, TooLessResourcesException {
        PlayerContext context = player.getContext();
        if (context.isFullOf(type)) {
            throw new TooManyObjectsException(type, player);
        }
        if (!context.isEnoughResourcesFor(type)) {
            throw new TooLessResourcesException(type, player);
        }
        context.addObjectCount(type);
        context.minusResourcesFor(type);
        return GameObjectContext.getByType(type).create(x, y, player);
    }
}
