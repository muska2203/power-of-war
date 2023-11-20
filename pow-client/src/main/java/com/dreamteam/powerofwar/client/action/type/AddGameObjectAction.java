package com.dreamteam.powerofwar.client.action.type;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.common.types.GameObjectType;

public class AddGameObjectAction implements Action {

    private final double x;
    private final double y;
    private final GameObjectType type;

    public AddGameObjectAction(double x, double y, GameObjectType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public GameObjectType getType() {
        return type;
    }
}
