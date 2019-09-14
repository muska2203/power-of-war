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
        this.state = NONE;
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
        System.out.println(this.state + " " + this.target);
        if (!this.state.equals(NONE) && (target == null || target.isDead())) {
            this.state = NONE;
            this.target = null;
        }
        if (this.state.equals(NONE)) {
            if (!this.isFull()) {
                this.target = getNearestResource(board);
                this.state = SEARCH;
            }
            if (this.target == null) {
                this.target = getNearestBase(board);
                this.state = DELIVERY;
            }
            if (this.target == null) {
                this.state = NONE;
            }
        }
        if (this.state.equals(SEARCH) && GameObjectUtils.checkPossibilityAction(this, target)) {
            this.state = MINING;
        }
        if (this.state.equals(DELIVERY) && GameObjectUtils.checkPossibilityAction(this, target)) {
            board.addAction(new StoreResourcesAction(this));
            this.state = NONE;
            this.target = null;
        }
        if (this.state.equals(MINING)) {
            if (!this.isFull()) {
                board.addAction(new MineResourceAction((Resource) this.target, this));
            } else {
                this.state = NONE;
                this.target = null;
            }
        }

        switch (this.state) {
            case NONE:
            case MINING:
                this.setSpeedVector(new Vector());
                break;
            case DELIVERY:
            case SEARCH:
                this.setSpeedVector(Vector.byTarget(this, this.target));
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
        /**
         * Бездействует
         */
        NONE,

        /**
         * Поиск ресурса/Движение к ресурсу
         */
        SEARCH,

        /**
         * Добыча ресурса
         */
        MINING,

        /**
         * Доставка ресурса на базу
         */
        DELIVERY
    }
}
