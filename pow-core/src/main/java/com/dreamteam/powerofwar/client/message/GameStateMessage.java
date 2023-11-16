package com.dreamteam.powerofwar.client.message;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dreamteam.powerofwar.client.game.object.StaticGameObject;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.game.types.ResourceType;

public class GameStateMessage implements Message {

    private Map<ResourceType, Integer> resources;
    private Map<Integer, StaticGameObject> gameObjects;

    public GameStateMessage(Map<ResourceType, Integer> resources, List<StaticGameObject> gameObjects) {
        this.resources = resources;
        this.gameObjects = gameObjects.stream().collect(Collectors.toMap(StaticGameObject::getId, Function.identity()));
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }

    public Map<Integer, StaticGameObject> getGameObjects() {
        return gameObjects;
    }
}
