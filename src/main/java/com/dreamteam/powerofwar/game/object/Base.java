package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.AddGameObjectAction;
import com.dreamteam.powerofwar.game.user.User;
import com.dreamteam.powerofwar.phisics.Units;

public class Base extends BaseGameObject {

    private double goldBalance = 0;

    Base(double x, double y, User user) {
        super(x, y, user);
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
    public void update(Board board) {
        if (canCreateGoldMiner()) {
            board.addAction(
                    new AddGameObjectAction(board, this.getX() + this.getSize() + 5, this.getY(), GoldMiner::new, this.getOwner())
            );
            // платим за создание нового рабочего
            this.goldBalance -= 10;
        }
    }

    public void addGold(double value) {
        this.goldBalance += value;
    }

    public double getGoldBalance() {
        return this.goldBalance;
    }

    private boolean canCreateGoldMiner() {
        return this.goldBalance > 10;
    }

    @Override
    public String toString() {
        return "Base{ " +
                "gold = " + goldBalance +
                '}';
    }
}
