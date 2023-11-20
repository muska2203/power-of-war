package com.dreamteam.powerofwar.server.game.object;

import com.dreamteam.powerofwar.common.physics.Units;
import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.common.types.ResourceType;

public class GoldMiner extends Miner {

    GoldMiner(double x, double y, Player player) {
        super(x, y, player);
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.GOLD;
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
