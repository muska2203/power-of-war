package com.dreamteam.powerofwar.server.message;

import com.dreamteam.powerofwar.game.types.GameObjectType;

public class AddGameObjectMessage extends PlayerMessage {

    private final double x;
    private final double y;
    private final GameObjectType type;

    public AddGameObjectMessage(double x, double y, GameObjectType type) {
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
