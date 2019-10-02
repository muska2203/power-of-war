package com.dreamteam.powerofwar.game.object;

import java.util.Random;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.type.UnitType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

/**
 * Трусливый миньон. От всех убегает. Старается даже ни с кем не видится. Атаки не наносит
 */
public class CowardMinion extends BaseGameObject {

    CowardMinion(double x, double y, Player player) {
        super(x, y, Vector.byDirection(1, new Random().nextDouble() * 360), player);
    }

    @Override
    protected double getSpeed() {
        return 10;
    }

    @Override
    public double getSize() {
        return Units.MINION_SIZE;
    }

    @Override
    public double getVisibilityRadius() {
        return 30;
    }

    @Override
    public double getActionRadius() {
        return Units.MINION_DEFAULT_ACTION_RADIUS;
    }

    @Override
    public UnitType getType() {
        return UnitType.COWARD;
    }

    @Override
    public void update(Board board) {
        Vector resultVector = new Vector();
        for (GameObject gameObject : board.getGameObjects()) {
            if (gameObject != this && !gameObject.getType().equals(UnitType.COWARD)
                    && GameObjectUtils.checkVisibility(this, gameObject) && !gameObject.isDead()
                    && gameObject.getOwner() != this.getOwner()
            ) {
                Vector vector = new Vector(gameObject.getX() - this.getX(), gameObject.getY() - this.getY());
                resultVector.addVector(vector);
            }
        }
        if (resultVector.getX() != 0 || resultVector.getY() != 0) {
            this.setSpeedVector(resultVector.negate());
        }
    }

    @Override
    public String toString() {
        return "CowardMinion{id: " + this.getId() + "}";
    }
}
