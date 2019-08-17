package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.Random;

/**
 * Трусливый миньон. От всех убегает. Старается даже ни с кем не видится. Атаки не наносит
 */
public class CowardMinion extends Minion {

    public CowardMinion(double x, double y) {
        super(x, y, Vector.byDirection(1, new Random().nextDouble() * 360),
                new Random().nextDouble() * 4 + 1,
                MinionType.WARRIOR,
                3,
                Units.MINION_DEFAULT_ACTION_RADIUS);
    }

    @Override
    public void update(Board board) {
        Vector resultVector = new Vector();
        for (GameObject gameObject : board.getGameObjects()) {
            if (gameObject != this && GameObjectUtils.checkVisibility(this, gameObject) && !gameObject.isDead()) {
                Vector vector = new Vector(gameObject.getX() - this.getX(), gameObject.getY() - this.getY());
                resultVector.addVector(vector);
            }
        }
        if (resultVector.getX() != 0 || resultVector.getY() != 0) {
            this.speedVector = Vector.byRadians(this.speed, resultVector.getRadians()).negate();
        }
    }
}
