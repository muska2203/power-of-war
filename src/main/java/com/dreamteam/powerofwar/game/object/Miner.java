package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.action.StoreResourcesAction;
import com.dreamteam.powerofwar.game.action.MineResourceAction;
import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.player.Player;
import com.dreamteam.powerofwar.phisics.Vector;

import static com.dreamteam.powerofwar.game.object.Miner.State.*;

/**
 * TODO: JavaDoc
 */
public abstract class Miner extends BaseGameObject {

    private static final double MINIMAL_SPEED_FACTOR = 0.5;
    protected int value = 0;
    protected GameObject target;
    private State state;

    Miner(double x, double y, Player player) {
        super(x, y, new Vector(), player);
        this.state = WAITING_FOR_RESOURCE;
    }

    public int getValue() {
        return value;
    }

    public void addResource(int value) {
        this.value += value;
    }

    @Override
    public String toString() {
        return "Miner[ " + this.getValue() + " / " + this.getCapacity() + " ]";
    }

    public void resetValue() {
        this.value = 0;
    }

    @Override
    public void update(Board board) {
        switch (state) {
            case WAITING_FOR_RESOURCE:
                target = getNearestResource(board);
                if (target != null && !this.isFull()) {
                    this.setSpeedVector(Vector.byPoints(this.getPoint(), target.getPoint()));
                    state = GOING_TO_RESOURCE;
                    break;
                }

                target = getNearestBase(board);
                if (target != null) {
                    this.setSpeedVector(Vector.byPoints(this.getPoint(), target.getPoint()));
                    state = GOING_TO_BASE;
                    break;
                }
                this.setSpeedVector(new Vector());
                break;
            case GOING_TO_RESOURCE:
                if (target == null || target.isDead()) {
                    state = WAITING_FOR_RESOURCE;
                    break;
                }
                if (GameObjectUtils.checkPossibilityAction(this, target)) {
                    this.setSpeedVector(new Vector());
                    state = MINING;
                }
                break;
            case MINING:
                if (target == null || target.isDead()) {
                    state = WAITING_FOR_RESOURCE;
                    break;
                }
                if (this.isFull()) {
                    target = getNearestBase(board);
                    if (target != null && !target.isDead()) {
                        this.setSpeedVector(Vector.byPoints(this.getPoint(), target.getPoint()));
                        state = GOING_TO_BASE;
                    }
                    break;
                }
                board.addAction(new MineResourceAction((Resource) target, this));
                break;
            case GOING_TO_BASE:
                if (target == null || target.isDead()) {
                    state = WAITING_FOR_RESOURCE;
                    break;
                }
                if (GameObjectUtils.checkPossibilityAction(this, target)) {
                    this.setSpeedVector(new Vector());
                    state = DELIVERY;
                }
                break;
            case DELIVERY:
                if (target == null || target.isDead()) {
                    state = WAITING_FOR_RESOURCE;
                    break;
                }
                board.addAction(new StoreResourcesAction(this));
                state = WAITING_FOR_RESOURCE;
                break;
        }
    }

    @Override
    public double getSpeedFactor() {
        return MINIMAL_SPEED_FACTOR + (1 - MINIMAL_SPEED_FACTOR) * (1 - 1.0D * getValue() / getCapacity());
    }

    private GameObject getNearestResource(Board board) {
        return GameObjectUtils.getNearestObject(this, board.getGameObjects(),
                gameObject -> getResourceType().equals(gameObject.getType()));
    }

    private GameObject getNearestBase(Board board) {
        return GameObjectUtils.getNearestObject(this, board.getGameObjects(),
                gameObject -> BuildingType.BASE.equals(gameObject.getType()),
                gameObject -> !this.getOwner().isEnemyFor(gameObject.getOwner()));
    }

    public abstract ResourceType getResourceType();

    public abstract int getCapacity();

    public boolean isFull() {
        return value >= getCapacity();
    }

    public enum State {
        WAITING_FOR_RESOURCE,
        GOING_TO_RESOURCE,
        MINING,
        GOING_TO_BASE,
        DELIVERY
    }
}
