package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.player.Player;

public abstract class Resource extends BaseGameObject {

    protected int count;

    Resource(double x, double y, Player player) {
        super(x, y, player);
    }

    public int getCount() {
        return this.count;
    }

    public void mineResource(int value) {
        this.count -= value;
    }

    @Override
    public void move(long loopTime) {
        // do nothing
    }

    @Override
    public void update(Board board) {
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

    public abstract ResourceType getResourceType();
}
