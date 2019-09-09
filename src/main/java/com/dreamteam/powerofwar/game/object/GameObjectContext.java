package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


//TODO: JavaDocs
public class GameObjectContext {

    private static Map<GameObjectType,  GameObjectCreator> constructorMap;

    //TODO: Возможно переделать на автоматический поиск классов, реализующих GameObject
    static {
        fillMap();
    }

    private static void fillMap() {
        constructorMap = new HashMap<>();
        constructorMap.put(ResourceType.GOLD, GoldResource::new);
        constructorMap.put(UnitType.GOLD_MINER, GoldMiner::new);
        constructorMap.put(BuildingType.BASE, Base::new);
        constructorMap.put(BuildingType.SUICIDE_FACTORY, SuicideFactory::new);
        constructorMap.put(UnitType.SUICIDE, SuicideObject::new);
        constructorMap.put(UnitType.COWARD, CowardMinion::new);
    }

    public static GameObjectCreator getByType(GameObjectType gameObjectType) {
        return constructorMap.get(gameObjectType);
    }

    public static Set<GameObjectType> getTypes() {
        return constructorMap.keySet();
    }
}
