package com.dreamteam.powerofwar.common.state.subject;

import com.dreamteam.powerofwar.common.types.GameObjectType;

public class SelectedGameObject {

    private final GameObjectType type;

    public SelectedGameObject(GameObjectType type) {
        this.type = type;
    }

    public GameObjectType getType() {
        return this.type;
    }
}
