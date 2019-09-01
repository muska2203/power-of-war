package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.object.Base;
import com.dreamteam.powerofwar.game.object.Miner;

public class BringResourceAction implements Action {

    private Base base;
    private Miner miner;

    public BringResourceAction(Base base, Miner miner) {
        this.base = base;
        this.miner = miner;
    }

    @Override
    public void execute() {
        this.base.addGold(this.miner.getValue());
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
