package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.DamageAction;
import com.dreamteam.powerofwar.game.user.User;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

import java.util.Optional;
import java.util.Random;

public class SuicideObject extends BaseGameObject {

    private GameObject target;

    SuicideObject(double x, double y, User user) {
        super(x, y, Units.MINION_SIZE,
                Units.MINION_DEFAULT_VISIBILITY_RADIUS,
                Units.MINION_DEFAULT_ACTION_RADIUS,
                4,
                Vector.byDirection(1, new Random().nextDouble() * 360),
                GameObjectType.SUICIDE, user);
    }

    @Override
    public void update(Board board) {
        if (target != null && (target.isDead() || !GameObjectUtils.checkVisibility(this, target))) {
            target = null;
        }
        GameObject nearest = null;
        for (GameObject gameObject : board.getGameObjects()) {
            if (gameObject != this && gameObject.getOwner() != this.getOwner()) {
                if (GameObjectUtils.checkVisibility(this, gameObject)) {
                    if (nearest == null) {
                        nearest = gameObject;
                    } else if (GameObjectUtils.getDistance(nearest, this) > GameObjectUtils.getDistance(gameObject, this)) {
                        nearest = gameObject;
                    }
                }
                if (GameObjectUtils.checkCollision(this, gameObject)) {
                    board.addAction(new DamageAction(100, gameObject, 0));
                    board.addAction(new DamageAction(100, this, 0));
                }
            }
        }
        this.target = nearest;
        if (target != null) {
            Vector vector = new Vector(target.getX() - this.getX(), target.getY() - this.getY());
            this.speedVector = Vector.byRadians(this.speed, vector.getRadians());
        }
    }
}
