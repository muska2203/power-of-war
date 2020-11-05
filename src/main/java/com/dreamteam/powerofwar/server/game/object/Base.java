package com.dreamteam.powerofwar.server.game.object;

import com.dreamteam.powerofwar.server.game.Board;
import com.dreamteam.powerofwar.server.game.action.AddResourceAction;
import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.game.types.ResourceType;
import com.dreamteam.powerofwar.server.game.player.Player;
import com.dreamteam.powerofwar.phisics.Units;

/**
 * TODO: JavaDoc
 */
public class Base extends BaseGameObject {

    private int resourceCountByTime = 5;
    private int castTime = 10;
    private double timeAfterLastResourceCreation = 0;

    public Base(double x, double y, Player player) {
        super(x, y, player);
        super.setHealth(1000);
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
    public void update(Board board) {
        if (castTime <= timeAfterLastResourceCreation) {
            board.addAction(new AddResourceAction(ResourceType.GOLD, resourceCountByTime, this.getOwner()));
            timeAfterLastResourceCreation = 0;
        }
    }

    @Override
    public void move(long loopTime) {
        timeAfterLastResourceCreation += loopTime * Units.SPEED;
    }

    @Override
    public String toString() {
        return "Base[" + getHealth() + "]";
    }
}
