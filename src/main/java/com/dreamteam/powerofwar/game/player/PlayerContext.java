package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.object.GameObjectType;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO: JavaDoc
 */
public class PlayerContext {

    private Map<GameObjectType, Integer> countByObjectTypes = new HashMap<>();

    public PlayerContext () {
        for (GameObjectType type : GameObjectType.values()) {
            countByObjectTypes.put(type, 0);
        }
    }

    public int getCount(GameObjectType type) {
        return countByObjectTypes.get(type);
    }

    public void addCount(GameObjectType type) {
        countByObjectTypes.replace(type, countByObjectTypes.get(type) + 1);
    }

    public void minusCount(GameObjectType type) {
        countByObjectTypes.replace(type, countByObjectTypes.get(type) - 1);
    }
}
