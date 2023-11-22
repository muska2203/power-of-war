package com.dreamteam.powerofwar.client.message;

import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.connection.message.Message;

public class AddGameObjectMessage implements Message {

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
