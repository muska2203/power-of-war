package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Units;

/**
 * TODO: JavaDoc
 */
public class Base extends BaseGameObject {

    public Base(double x, double y, Player player) {
        super(x, y, player);
    }

    @Override
    protected double getSpeed() {
        return 0;
    }

    @Override
    public double getSize() {
        return Units.BUILDING_SIZE;
    }

    @Override
    public double getVisibilityRadius() {
        return Units.BUILDING_DEFAULT_VISIBILITY_RADIUS;
    }

    @Override
    public double getActionRadius() {
        return Units.BUILDING_DEFAULT_ACTION_RADIUS;
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.BASE;
    }

    @Override
    public void move(long loopTime) {
        // do nothing
    }

    @Override
    public String toString() {
        return "Base[" + getHealth() + "]";
    }
}
