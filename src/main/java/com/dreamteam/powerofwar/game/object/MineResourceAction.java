package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.action.Action;

public class MineResourceAction implements Action {

    private Resource mine;
    private Miner miner;

    public MineResourceAction(Resource mine, Miner miner) {
        this.mine = mine;
        this.miner = miner;
    }

    @Override
    public void execute() {
        int delta = StrictMath.min(this.mine.getCount(), this.miner.getCapacity() - this.miner.getValue());
        this.mine.mineResource(delta);
        this.miner.addResource(delta);
    }

    @Override
    public boolean isReady(long gameLoop) {
        return true;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }
}
