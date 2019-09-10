package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;

import java.util.Map;


//TODO: JavaDocs
public class GameObjectContext {

    private static Map<GameObjectType, GameObjectCreator> constructorMap;

    //TODO: Возможно переделать на автоматический поиск классов, реализующих GameObject
    static {
        constructorMap = Map.of(
                ResourceType.GOLD, GoldResource::new,
                UnitType.GOLD_MINER, GoldMiner::new,
                BuildingType.BASE, Base::new,
                BuildingType.SUICIDE_FACTORY, SuicideFactory::new,
                UnitType.SUICIDE, SuicideObject::new,
                UnitType.COWARD, CowardMinion::new
        );
    }

    public static GameObjectCreator getByType(GameObjectType gameObjectType) {
        return constructorMap.get(gameObjectType);
    }
}
