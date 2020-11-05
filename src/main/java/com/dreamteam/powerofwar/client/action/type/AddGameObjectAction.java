package com.dreamteam.powerofwar.client.action.type;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.server.game.player.Player;

public class AddGameObjectAction implements Action {

    private double x;
    private double y;
    private Player player;
    private GameObjectType type;

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

    public Player getPlayer() {
        return player;
    }

    public GameObjectType getType() {
        return type;
    }
}
