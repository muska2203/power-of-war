package com.dreamteam.powerofwar.client.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dreamteam.powerofwar.common.types.ResourceType;
import com.dreamteam.powerofwar.connection.message.types.GameObjectInfo;

public class GameContext {

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 480;

    private Map<Integer, GameObjectInfo> gameObjects = new ConcurrentHashMap<>();

    private Map<ResourceType, Integer> resources = new ConcurrentHashMap<>();

    public double getWidth() {
        return BOARD_WIDTH;
    }

    public double getHeight() {
        return BOARD_HEIGHT;
    }

    public Map<Integer, GameObjectInfo> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(Map<Integer, GameObjectInfo> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<ResourceType, Integer> resources) {
        this.resources = resources;
    }

    public Integer getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }
}
