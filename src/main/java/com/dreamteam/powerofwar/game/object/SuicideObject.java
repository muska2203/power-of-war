package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.DamageAction;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.Random;

public class SuicideObject extends BaseGameObject {

    private GameObject target;

    public SuicideObject(double x, double y) {
        super(x, y, Units.MINION_SIZE,
                Units.MINION_DEFAULT_VISIBILITY_RADIUS,
                Units.MINION_DEFAULT_ACTION_RADIUS,
                new Random().nextDouble() * 4 + 1,
                Vector.byDirection(1, new Random().nextDouble() * 360),
                GameObjectType.MINION);
    }

    @Override
    public void update(Board board) {
        for (GameObject gameObject : board.getGameObjects()) {
            if (gameObject != this) {
                if (target != null && target.isDead()) {
                    target = null;
                }
                if (GameObjectUtils.checkVisibility(this, gameObject) && target == null) {
                    target = gameObject;
                }
                if (target != null) {
                    Vector vector = new Vector(target.getX() - this.getX(), target.getY() - this.getY());
                    this.speedVector = Vector.byRadians(this.speed, vector.getRadians());
                }

                if (GameObjectUtils.checkCollision(this, gameObject)) {
                    board.addAction(new DamageAction(100, gameObject, 0));
                }
            }
        }
    }
}
