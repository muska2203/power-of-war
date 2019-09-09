package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.AddGameObjectAction;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Units;

//TODO: JavaDocs
public class SuicideFactory extends BaseGameObject {

    private static final long reloadingTime = 4;
    private double timeAfterLastAction = 0;

    SuicideFactory(double x, double y, Player player) {
        super(x, y, player);
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
        return Units.MINION_DEFAULT_ACTION_RADIUS;
    }

    @Override
    public BuildingType getType() {
        return BuildingType.SUICIDE_FACTORY;
    }

    @Override
    public void update(Board board) {
        if (timeAfterLastAction > reloadingTime) {
            timeAfterLastAction -= reloadingTime;
            board.addAction(new AddGameObjectAction(board, getX(), getY(), this.getOwner(), UnitType.SUICIDE));
        }
    }

    @Override
    public void move(long loopTime) {
        timeAfterLastAction += loopTime * Units.SPEED;
        //do nothing
    }

    @Override
    protected double getSpeed() {
        return 0;
    }

    @Override
    public String toString() {
        return "SuicideFactory{id: " + this.getId() + "}";
    }
}
