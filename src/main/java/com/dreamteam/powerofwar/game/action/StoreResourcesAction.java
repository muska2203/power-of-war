package com.dreamteam.powerofwar.game.action;

import com.dreamteam.powerofwar.game.object.Miner;

/**
 * Действие складирования ресурсов указаного добывающего юнита.
 */
public class StoreResourcesAction implements Action {

    private Miner miner;

    /**
     * @param miner добывающий юнит, который должен переложить свои ресурсы в казну игрока-владельца.
     */
    public StoreResourcesAction(Miner miner) {
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
