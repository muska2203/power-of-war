package com.dreamteam.powerofwar.server.game.object;

import java.util.Collection;

import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.game.types.ObjectFunctionType;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;
import com.dreamteam.powerofwar.server.game.Board;
import com.dreamteam.powerofwar.server.game.action.DamageAction;
import com.dreamteam.powerofwar.server.game.player.Player;

/**
 * TODO: Javadoc
 */
public class Warrior extends BaseGameObject {

    private GameObject target;
    private int damage = 10;
    private int castTime = 2;
    private double timeAfterStartedCast = 0;
    private int reloadTime = 6;
    private double timeAfterLastAction = 6;
    private State state = State.WAITING_FOR_ENEMY;

    Warrior(double x, double y, Player player) {
        super(x, y, player);
        super.setHealth(100);
    }

    @Override
    protected double getSpeed() {
        return 4;
    }

    @Override
    public double getSize() {
        return Units.MINION_SIZE;
    }

    @Override
    public double getVisibilityRadius() {
        return Units.MINION_DEFAULT_VISIBILITY_RADIUS;
    }

    @Override
    public double getActionRadius() {
        return Units.MINION_DEFAULT_ACTION_RADIUS;
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.WARRIOR;
    }

    @SuppressWarnings("checkstyle:FallThrough")
    @Override
    public void update(Board board) {
        switch (state) {
            case WAITING_FOR_ENEMY:
                target = this.getNearestEnemy(board.getGameObjects());
                if (target != null) {
                    state = State.CHASING_FOR_ENEMY;
                }
                break;
            case CHASING_FOR_ENEMY:
                target = this.getNearestEnemy(board.getGameObjects());
                if (target == null) {
                    state = State.WAITING_FOR_ENEMY;
                    break;
                }
                if (GameObjectUtils.checkPossibilityAction(this, target)) {
                    state = State.READY_TO_CAST;
                }
                break;
            case READY_TO_CAST:
                if (target.isDead() || !GameObjectUtils.checkPossibilityAction(this, target)) {
                    state = State.WAITING_FOR_ENEMY;
                    break;
                }
                if (timeAfterLastAction >= reloadTime) {
                    state = State.CASTING_ACTION;
                    timeAfterStartedCast = 0;
                }
                break;
            case CASTING_ACTION:
                if (target.isDead() || !GameObjectUtils.checkPossibilityAction(this, target)) {
                    state = State.WAITING_FOR_ENEMY;
                    break;
                }
                if (timeAfterStartedCast >= castTime) {
                    board.addAction(new DamageAction(damage, target, 0));
                    timeAfterLastAction = 0;
                    timeAfterStartedCast = 0;
                    state = State.READY_TO_CAST;
                }
                break;
            default:
                // do nothing
        }

        if (state.equals(State.CHASING_FOR_ENEMY) && target != null) {
            Vector vector = new Vector(target.getX() - this.getX(), target.getY() - this.getY());
            this.setSpeedVector(vector);
        } else {
            this.setSpeedVector(new Vector());
        }
    }

    private GameObject getNearestEnemy(Collection<GameObject> objects) {
        return GameObjectUtils.getNearestObject(this, objects,
                gameObject -> !gameObject.isDead(),
                gameObject -> !gameObject.getOwner().equals(this.getOwner()),
                gameObject -> GameObjectUtils.checkVisibility(this, gameObject),
                gameObject -> gameObject.getType().getFunctionType() != ObjectFunctionType.RESOURCE);
    }

    @Override
    public void move(long loopTime) {
        switch (state) {
            case CASTING_ACTION:
                timeAfterStartedCast += loopTime * Units.SPEED;
                break;
            case CHASING_FOR_ENEMY:
                super.move(loopTime);
                break;
            default:
                // maybe move on default and do something else in other cases?
        }
        timeAfterLastAction += loopTime * Units.SPEED;
    }

    @Override
    public String toString() {
        return "Warrior[" + this.getHealth() + "]";
    }

    public enum State {
        CASTING_ACTION,
        READY_TO_CAST,
        CHASING_FOR_ENEMY,
        WAITING_FOR_ENEMY
    }
}
