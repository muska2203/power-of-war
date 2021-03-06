package com.dreamteam.powerofwar.client.action.type;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;

public class SelectGameObjectAction implements Action {

    private GameObjectType type;

    public SelectGameObjectAction(GameObjectType type) {
        this.type = type;
    }

    public GameObjectType getType() {
        return type;
    }
}
