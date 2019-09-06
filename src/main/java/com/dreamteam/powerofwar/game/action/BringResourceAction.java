package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.object.Miner;

public class BringResourceAction implements Action {

    private Miner miner;

    public BringResourceAction(Miner miner) {
        this.miner = miner;
    }

    @Override
    public void execute() {
        this.miner.resetValue();
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
