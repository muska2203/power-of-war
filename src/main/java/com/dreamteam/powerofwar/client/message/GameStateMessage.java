package com.dreamteam.powerofwar.client.message;

import java.util.Map;

import com.dreamteam.powerofwar.connection.session.IncomingMessage;
import com.dreamteam.powerofwar.game.object.type.ResourceType;

public class GameStateMessage extends IncomingMessage {

    private Map<ResourceType, Integer> resources;

    public GameStateMessage(Map<ResourceType, Integer> resources) {
        this.resources = resources;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }
}
