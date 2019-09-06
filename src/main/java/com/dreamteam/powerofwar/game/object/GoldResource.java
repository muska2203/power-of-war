package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.player.Player;


public class GoldResource extends Resource {

    GoldResource(double x, double y, Player player) {
        super(x, y, player);
        this.count = 50;
    }

    @Override
    protected double getSpeed() {
        return 0;
    }

    @Override
    public double getSize() {
        return 10;
    }

    @Override
    public double getVisibilityRadius() {
        return 0;
    }

    @Override
    public double getActionRadius() {
        return 0;
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.GOLD;
    }
}
