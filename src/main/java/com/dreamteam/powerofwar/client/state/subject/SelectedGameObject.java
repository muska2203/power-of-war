package com.dreamteam.powerofwar.client.state.subject;

import com.dreamteam.powerofwar.game.object.type.GameObjectType;

public class SelectedGameObject {

    final private GameObjectType type;

    public SelectedGameObject(GameObjectType type) {
        this.type = type;
    }

    public GameObjectType getType() {
        return this.type;
    }
}
