package com.dreamteam.powerofwar.server.game.action;

import com.dreamteam.powerofwar.server.game.object.Miner;

/**
 * Действие складирования ресурсов указаного добывающего юнита.
 */
public class StoreResourcesAction extends AddResourceAction {

    private final Miner miner;

    /**
     * @param miner добывающий юнит, который должен переложить свои ресурсы в казну игрока-владельца.
     */
    public StoreResourcesAction(Miner miner) {
        super(miner.getResourceType(), miner.getValue(), miner.getOwner());
        this.miner = miner;
    }

    @Override
    public void execute() {
        super.execute();
        miner.resetValue();
    }
}
