package com.dreamteam.powerofwar.server.message;

import java.util.HashMap;
import java.util.Map;

import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.game.object.type.ResourceType;

public class GameStateMessage implements Message {

    private Map<ResourceType, Integer> resources;

    public GameStateMessage(Map<ResourceType, Integer> resources) {
        this.resources = new HashMap<>(resources);
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }
}
