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
        switch (state) {
            case NONE: {
                // target == null
                if (!this.isFull()) {
                    target = this.getNearestResource(board);
                    state = SEARCH;
                }
                // Если ресурс не найден или майнер уже не может больше набрать ресурса, то идем на базу.
                if (target == null) {
                    target = this.getNearestBase(board);
                    if (target != null) {
                        state = DELIVERY;
                    }
                }
                this.setSpeedVector(target == null ? new Vector() : Vector.byTarget(this, target));
                break;
            }
            case SEARCH: {
                // target == Resource
                if (target == null || target.isDead()) {
                    target = null;
                    state = NONE;
                    this.setSpeedVector(new Vector());
                } else {
                    if (GameObjectUtils.checkPossibilityAction(this, target)) {
                        state = MINING;
                        this.setSpeedVector(new Vector());
                    }
                }
                break;
            }
            case MINING: {
                // target == Resource
                if (target == null || target.isDead()) {
                    state = NONE;
                    target = null;
                    this.setSpeedVector(new Vector());
                } else {
                    if (!isFull()) {
                        board.addAction(new MineResourceAction((Resource) target, this));
                    } else {
                        target = this.getNearestBase(board);
                        if (target == null) {
                            state = NONE;
                            this.setSpeedVector(new Vector());
                        } else {
                            state = DELIVERY;
                            this.setSpeedVector(Vector.byTarget(this, target));
                        }
                    }
                }
                break;
            }
            case DELIVERY: {
                // target == Base
                if (target == null || target.isDead()) {
                    target = null;
                    state = NONE;
                    this.setSpeedVector(new Vector());
                } else {
                    if (GameObjectUtils.checkPossibilityAction(this, target)) {
                        board.addAction(new StoreResourcesAction(this));
                        state = NONE;
                        target = null;
                        this.setSpeedVector(new Vector());
                    }
                }
                break;
            }
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
