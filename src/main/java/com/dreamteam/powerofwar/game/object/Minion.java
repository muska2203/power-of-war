package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

public class Minion extends BaseGameObject {

    private MinionType minionType;
    private double attackDamage;
    private double attackRange;

    public Minion(double x, double y, Vector speedVector, double speed, MinionType minionType, double attackDamage, double attackRange) {
        super(x, y, Units.MINION_SIZE, Units.MINION_DEFAULT_VISIBILITY_RADIUS, attackRange, speed, speedVector, GameObjectType.MINION);
        this.minionType = minionType;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
    }

    public MinionType getMinionType() {
        return minionType;
    }

    public double getAttackDamage() {
        return this.attackDamage;
    }

}
