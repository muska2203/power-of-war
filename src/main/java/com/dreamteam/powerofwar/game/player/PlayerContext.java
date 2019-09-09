package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.object.GameObjectContext;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO: JavaDoc
 */
public class PlayerContext {

    private static final int MAX_COUNT = 1000;

    //TODO: JavaDocs
    private Map<GameObjectType, Integer> countByObjectTypes = new HashMap<>();

    //TODO: JavaDocs
    private Map<GameObjectType, Integer> objectLimitMap = new HashMap<>();

    public PlayerContext () {
        for (GameObjectType type : GameObjectContext.getTypes()) {
            countByObjectTypes.put(type, 0);
        }
        objectLimitMap.put(BuildingType.BASE, 1);
    }

    public boolean isFullOf(GameObjectType type) {
        return countByObjectTypes.get(type) >= getLimit(type);
    }

    public void addCount(GameObjectType type) {
        countByObjectTypes.replace(type, countByObjectTypes.get(type) + 1);
    }

    public void minusCount(GameObjectType type) {
        countByObjectTypes.replace(type, countByObjectTypes.get(type) - 1);
    }

    public int getLimit(GameObjectType type) {
        Integer limit = objectLimitMap.get(type);
        return limit != null ? limit : MAX_COUNT;
    }
}
