package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.user.User;
import com.dreamteam.powerofwar.phisics.Units;

public class GoldMiner extends Miner {

    GoldMiner(double x, double y, User user) {
        super(x, y, user);
    }

    @Override
    public GameObjectType getResourceType() {
        return GameObjectType.GOLD;
    }

    @Override
    public int getCapacity() {
        return 7;
    }

    @Override
    protected double getSpeed() {
        return 6;
    }

    @Override
    public double getSize() {
        return Units.MINION_SIZE;
    }

    @Override
    public double getVisibilityRadius() {
        return Units.MINION_DEFAULT_VISIBILITY_RADIUS;
    }

    @Override
    public double getActionRadius() {
        return Units.MINION_DEFAULT_ACTION_RADIUS;
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.GOLD_MINER;
    }
}
