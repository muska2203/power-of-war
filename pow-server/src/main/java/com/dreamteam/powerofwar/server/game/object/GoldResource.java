package com.dreamteam.powerofwar.server.game.object;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.common.types.ResourceType;


//TODO: JavaDocs
public class GoldResource extends Resource {

    GoldResource(double x, double y, Player player) {
        super(x, y, player);
        this.count = 50;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.GOLD;
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
        return GameObjectType.GOLD_MINE;
    }
}
