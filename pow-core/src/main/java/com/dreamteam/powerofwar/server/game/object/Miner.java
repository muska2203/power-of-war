package com.dreamteam.powerofwar.server.game.object;

import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.game.types.ObjectFunctionType;
import com.dreamteam.powerofwar.game.types.ResourceType;
import com.dreamteam.powerofwar.phisics.Vector;
import com.dreamteam.powerofwar.server.game.Board;
import com.dreamteam.powerofwar.server.game.action.MineResourceAction;
import com.dreamteam.powerofwar.server.game.action.StoreResourcesAction;
import com.dreamteam.powerofwar.server.game.player.Player;

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
        this.state = State.SEARCH_RESOURCE;
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
            case SEARCH_RESOURCE: {
                if (!this.isFull()) {
                    target = this.getNearestResource(board);
                    this.setSpeedVector(Vector.byTarget(this, target));
                }
                if (target != null && GameObjectUtils.checkPossibilityAction(this, target)) {
                    state = State.MINING;
                    this.setSpeedVector(new Vector());
                    break;
                }
                if (target == null) {
                    target = this.getNearestBase(board);
                    if (target != null) {
                        state = State.DELIVERY;
                        this.setSpeedVector(Vector.byTarget(this, target));
                    }
                }
                break;
            }
            case MINING: {
                if (target == null || target.isDead()) {
                    this.resetState();
                    break;
                }
                if (!this.isFull()) {
                    board.addAction(new MineResourceAction((Resource) target, this));
                    break;
                }

                target = getNearestBase(board);
                if (target != null) {
                    state = State.DELIVERY;
                    this.setSpeedVector(Vector.byTarget(this, target));
                }
                break;
            }
            case DELIVERY: {
                if (target == null || target.isDead()) {
                    this.resetState();
                    break;
                }

                if (GameObjectUtils.checkPossibilityAction(this, target)) {
                    this.resetState();
                    board.addAction(new StoreResourcesAction(this));
                }
                break;
            }
            default:
                // do nothing
        }
    }

    private void resetState() {
        target = null;
        state = State.SEARCH_RESOURCE;
        this.setSpeedVector(new Vector());
    }

    @Override
    public double getSpeedFactor() {
        return MINIMAL_SPEED_FACTOR + (1 - MINIMAL_SPEED_FACTOR) * (1 - 1.0D * getValue() / getCapacity());
    }

    private GameObject getNearestResource(Board board) {
        return GameObjectUtils.getNearestObject(this, board.getGameObjects(),
                gameObject -> gameObject.getType().getFunctionType() == ObjectFunctionType.RESOURCE &&
                        ((Resource) gameObject).getResourceType() == getResourceType());
    }

    private GameObject getNearestBase(Board board) {
        return GameObjectUtils.getNearestObject(this, board.getGameObjects(),
                gameObject -> GameObjectType.BASE.equals(gameObject.getType()),
                gameObject -> !this.getOwner().isEnemyFor(gameObject.getOwner()));
    }

    public abstract ResourceType getResourceType();

    public abstract int getCapacity();

    public boolean isFull() {
        return value >= getCapacity();
    }

    public enum State {
        /**
         * Поиск ресурса. Состояние майнера по умолчанию. Майнер всегда хочет работать/добывать ресурсы.
         * Ищет ресурс и направляется к нему (по ходу может находить более близкие источники).
         */
        SEARCH_RESOURCE,

        /**
         * Добыча ресурса. Может занимать некоторое время
         */
        MINING,

        /**
         * Доставка ресурса на базу/Возвращение на базу
         */
        DELIVERY
    }
}
