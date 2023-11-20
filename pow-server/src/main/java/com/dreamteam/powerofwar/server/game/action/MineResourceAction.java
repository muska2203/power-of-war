package com.dreamteam.powerofwar.server.game.action;

import com.dreamteam.powerofwar.server.game.object.Miner;
import com.dreamteam.powerofwar.server.game.object.Resource;

/**
 * Действие добычи ресурса указанным добывающим юнитом.
 */
public class MineResourceAction implements Action {

    private final Resource mine;
    private final Miner miner;

    /**
     * @param mine  игровой объект ресурса, который добывает указанный добывающий юнит.
     * @param miner добывающий юнит, который добывает указанный ресурс.
     */
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
