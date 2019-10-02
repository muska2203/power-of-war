package com.dreamteam.powerofwar.game.object;

import java.util.HashMap;
import java.util.Map;

import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.object.type.UnitType;


//TODO: JavaDocs
final public class GameObjectContext {

    private static Map<GameObjectType,  GameObjectCreator> constructorMap;

    //TODO: Возможно переделать на автоматический поиск классов, реализующих GameObject
    static {
        fillMap();
    }

    private GameObjectContext() {}

    private static void fillMap() {
        constructorMap = new HashMap<>();
        constructorMap.put(ResourceType.GOLD, GoldResource::new);
        constructorMap.put(UnitType.GOLD_MINER, GoldMiner::new);
        constructorMap.put(BuildingType.BASE, Base::new);
        constructorMap.put(UnitType.WARRIOR, Warrior::new);
        constructorMap.put(UnitType.COWARD, CowardMinion::new);
    }

    public static GameObjectCreator getByType(GameObjectType gameObjectType) {
        return constructorMap.get(gameObjectType);
    }
}
