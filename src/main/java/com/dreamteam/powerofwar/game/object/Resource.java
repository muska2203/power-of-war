package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.user.User;

public abstract class Resource extends BaseGameObject {

    protected int count;

    Resource(double x, double y, User user) {
        super(x, y, user);
    }

    public int getCount() {
        return this.count;
    }

    public void mineResource(int delta) {
        this.count -= delta;
    }

    @Override
    public void move(long loopTime) {
        // do nothing
    }

    @Override
    public String toString() {
        return "Resource[ count = " + count + " ]";
    }

    @Override
    public boolean isDead() {
        return this.count <= 0;
    }
}
