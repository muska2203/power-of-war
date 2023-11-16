package com.dreamteam.powerofwar.server.game.action;

import com.dreamteam.powerofwar.server.game.object.GameObject;

/**
 * Действие нанесения точечного урона по указанному объекту.
 */
public class DamageAction implements Action {

    private int damage;
    private GameObject target;
    private long delay;
    private long currentState = 0;
    private boolean isCompleted = false;

    /**
     * @param damage исходящий урон.
     * @param target цель, которой будет нанесен урон.
     * @param delay  задержка перед нанесением урона.
     */
    public DamageAction(int damage, GameObject target, long delay) {
        this.damage = damage;
        this.target = target;
        this.delay = delay;
    }


    @Override
    public void execute() {
        target.doDamage(damage);
        isCompleted = true;
    }

    @Override
    public boolean isReady(long gameLoop) {
        this.currentState += gameLoop;
        return this.delay <= currentState;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
