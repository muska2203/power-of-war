package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.user.User;
import com.dreamteam.powerofwar.phisics.Vector;

public abstract class Miner extends BaseGameObject {

    protected int value = 0;
    protected GameObject target;

    Miner(double x, double y, User user) {
        super(x, y, new Vector(), user);
    }

    public abstract GameObjectType getResource();

    public abstract int getCapacity();

    public int getValue() {
        return value;
    }

    public void addResource(int value) {
        this.value += value;
    }

    @Override
    public String toString() {
        return "Miner[ " + this.getValue() + " / " + this.getCapacity() + " ]";
    }

    public void resetValue() {
        this.value = 0;
    }
}
