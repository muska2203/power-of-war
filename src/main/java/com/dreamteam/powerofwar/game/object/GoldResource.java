package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.user.User;

public class GoldResource extends Resource {

    GoldResource(double x, double y, User user) {
        super(x, y, user);
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
