package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.AddResourceAction;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Units;

import static com.dreamteam.powerofwar.phisics.Units.SPEED;

/**
 * TODO: JavaDoc
 */
public class Base extends BaseGameObject {

    private int resourceCountByTime = 5;
    private int castTime = 4;
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
    public BuildingType getType() {
        return BuildingType.BASE;
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
        timeAfterLastResourceCreation += loopTime * SPEED;
    }

    @Override
    public String toString() {
        return "Base[" + getHealth() + "]";
    }
}
