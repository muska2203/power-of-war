package com.dreamteam.powerofwar.connection.message.types;

import java.util.List;
import java.util.Map;

import com.dreamteam.powerofwar.common.types.ResourceType;
import com.dreamteam.powerofwar.connection.message.Message;

public class FullGameStateMessage implements Message {

    private final Map<ResourceType, Integer> resources;

    private final List<GameObjectInfo> gameObjectsInfo;

    public FullGameStateMessage(Map<ResourceType, Integer> resources, List<GameObjectInfo> gameObjectsInfo) {
        this.resources = resources;
        this.gameObjectsInfo = gameObjectsInfo;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }

    public List<GameObjectInfo> getGameObjectsInfo() {
        return gameObjectsInfo;
    }
}
