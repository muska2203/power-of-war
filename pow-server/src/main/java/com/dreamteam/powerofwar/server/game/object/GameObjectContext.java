package com.dreamteam.powerofwar.server.game.object;

import java.util.HashMap;
import java.util.Map;

import com.dreamteam.powerofwar.common.types.GameObjectType;


//TODO: JavaDocs
final public class GameObjectContext {

    private static Map<GameObjectType, GameObjectCreator> constructorMap;

    //TODO: Возможно переделать на автоматический поиск классов, реализующих GameObject
    static {
        fillMap();
    }

    private GameObjectContext() {
    }

    private static void fillMap() {
        constructorMap = new HashMap<>();
        constructorMap.put(GameObjectType.GOLD_MINE, GoldResource::new);
        constructorMap.put(GameObjectType.GOLD_MINER, GoldMiner::new);
        constructorMap.put(GameObjectType.BASE, Base::new);
        constructorMap.put(GameObjectType.WARRIOR, Warrior::new);
        constructorMap.put(GameObjectType.COWARD, CowardMinion::new);
    }

    public static GameObjectCreator getByType(GameObjectType gameObjectType) {
        return constructorMap.get(gameObjectType);
    }
}
