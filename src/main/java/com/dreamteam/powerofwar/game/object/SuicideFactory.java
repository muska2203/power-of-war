package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.AddGameObjectAction;
import com.dreamteam.powerofwar.game.user.User;
import com.dreamteam.powerofwar.phisics.Units;


public class SuicideFactory extends BaseGameObject {

    private static final long reloadingTime = 4;
    private double timeAfterLastAction = 0;

    SuicideFactory(double x, double y, User user) {
        super(x, y, user);
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
    public GameObjectType getType() {
        return GameObjectType.SUICIDE_FACTORY;
    }

    @Override
    public void update(Board board) {
        if (timeAfterLastAction > reloadingTime) {
            timeAfterLastAction -= reloadingTime;
            board.addAction(new AddGameObjectAction(board, getX(), getY(), GameObjectType.SUICIDE, this.getOwner()));
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
